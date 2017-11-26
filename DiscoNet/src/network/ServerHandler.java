/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import api.DiskState;
import api.DiskStateListener;
import api.GameState;
import api.GameStateListener;
import api.PlayerMoveEmitter;
import api.PlayerMoveListener;
import api.ScoreListener;
import api.TimeListener;
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import network.messages.DiskStateMessage;
import network.messages.GameStateMessage;
import network.messages.InitAckMessage;
import network.messages.InitMessage;
import network.messages.JoinAckMessage;
import network.messages.JoinMessage;
import network.messages.PlayerMoveMessage;
import network.messages.RequestStartMessage;
import network.messages.TimeMessage;
import server.ServerModule;

/**
 * Handles all the networking done by server.
 * Emits what has been received.
 *  
 * @author truls
 */
public class ServerHandler implements MessageListener<HostedConnection>, PlayerMoveEmitter, GameStateListener, ScoreListener, TimeListener, DiskStateListener {
    private ServerModule serverModule;
    
    private static final String GAME_STATE_ATTRIBUTE = "GameState";
    private static final String INIT_STATUS_ATTRIBUTE = "InitStatus";
    
    private enum InitStatus {
        NOTHING,
        JOINED,
        INITIALIZED,
    }
    
    private Server server;
    
    public ServerHandler(){
        // init messages
        NetworkUtils.initSerializables();
        initServer();   
    }
    
    
    @SuppressWarnings("CallToPrintStackTrace")
    private void initServer(){
        try {
            System.out.println("Using port " + NetworkUtils.SERVER_PORT);
            // create and start the server
            server = Network.createServer(NetworkUtils.SERVER_PORT);
            server.start();
            
        } catch (IOException e) {
            e.printStackTrace();
            destroy();
        }
        System.out.println("Server started");
        // add a listener that reacts on incoming network packets
        server.addMessageListener(this, JoinMessage.class,
                PlayerMoveMessage.class, RequestStartMessage.class, InitAckMessage.class);
        System.out.println("ServerListener activated and added to server");
    }
    
    public void destroy() {
        System.out.println("Server going down");
        server.close();
        System.out.println("Server down");
    }
    
    /**
     * reads messages from network.
     * @param source
     * @param m 
     */
    @SuppressWarnings("CallToPrintStackTrace")
    @Override
    public void messageReceived(HostedConnection source, final Message m) {
        
        if (m instanceof JoinMessage){                
            // TODO: send message back to client about if it can join or not.
            final Future<Integer> result = serverModule.enqueue(new Callable(){
                @Override
                public Object call() throws Exception{
                    return serverModule.initId();
                }
            });
            JoinAckMessage joinAckMessage;
            try{
                int id = result.get();
                if (id != -1){
                    joinAckMessage = new JoinAckMessage(id, true);
                    source.setAttribute(GAME_STATE_ATTRIBUTE, GameState.SETUP);
                    source.setAttribute(INIT_STATUS_ATTRIBUTE, InitStatus.JOINED);
                } else {
                    joinAckMessage = new JoinAckMessage(id, false);
                    source.setAttribute(GAME_STATE_ATTRIBUTE, GameState.END);
                }
                
            } catch (InterruptedException | ExecutionException ex) {
                joinAckMessage = new JoinAckMessage(-1, false);
                source.setAttribute(GAME_STATE_ATTRIBUTE, GameState.END);
            }
            // TODO: maybe remove this if states work good.
            /*serverModule.enqueue(new Callable() {
                @Override
                public Object call() throws Exception {
                    serverModule.onPlayerJoined(result.get());
                    return true;
                }
            });*/
            
            server.broadcast(Filters.equalTo(source), joinAckMessage);    
            
        } else if (m instanceof PlayerMoveMessage){
            // send to simple application
            serverModule.enqueue(new Callable() {
                @Override
                public Object call() throws Exception {
                    serverModule.notifyPlayerMove(((PlayerMoveMessage) m).getID(), ((PlayerMoveMessage) m).getDirection(), ((PlayerMoveMessage) m).isPressed());
                    return true;
                }
            });
        } else if (m instanceof RequestStartMessage){
            List<DiskState> players = serverModule.getPlayerDiskStates();
            InitMessage im = new InitMessage(players);
            //server.broadcast(im);
            Predicate<HostedConnection> predicate = p -> p.getAttribute(GAME_STATE_ATTRIBUTE) != GameState.SETUP;
            List<HostedConnection> validConnections = getFilteredHosts(predicate);
            server.broadcast(Filters.in(validConnections), im);
            System.out.println("Init message broadcasted.");
        } else if (m instanceof InitAckMessage){
            System.out.println("Source : " + source);
            System.out.println("Server connections : " + server.getConnections());
            source.setAttribute(INIT_STATUS_ATTRIBUTE, InitStatus.INITIALIZED);
            
            Predicate<HostedConnection> predicate = p -> p.getAttribute(INIT_STATUS_ATTRIBUTE) != InitStatus.JOINED;
            List<HostedConnection> joinedConnections = getFilteredHosts(predicate);
            if (joinedConnections.isEmpty()) {
                serverModule.enqueue(new Callable() {
                    @Override
                    public Object call() throws Exception {
                        serverModule.onPlayersReady();
                        return true;
                    }
                });
            }
            
            // TODO: Remove if states work good.
            /*serverModule.enqueue(new Callable() {
                @Override
                public Object call() throws Exception {
                    serverModule.onPlayerReady(((InitAckMessage) m).getID());
                    return true;
                }
            });*/
        }
    }
    
    private List<HostedConnection> getFilteredHosts(Predicate p){
        List<HostedConnection> hosts = new ArrayList();
        Collection connections = server.getConnections();
        hosts.addAll(connections);
        hosts.removeIf(p);
        return hosts;
    }
    
    @Override
    public void addPlayerMoveListener(PlayerMoveListener playerMoveListener) {
        serverModule = (ServerModule) playerMoveListener;
    }

    @Override
    public void notifyGameState(GameState state) {
        Predicate<HostedConnection> predicate;
        Collection hosts;
        if (null != state)switch (state) {
            case PLAY:
                predicate = p -> p.getAttribute(GAME_STATE_ATTRIBUTE) != GameState.SETUP;
                hosts = getFilteredHosts(predicate);
                setAttributes(hosts, GAME_STATE_ATTRIBUTE, state);
                server.broadcast(Filters.in(hosts) , new GameStateMessage(state));
                startHeartBeat();
                break;
            case END:
                predicate = p -> p.getAttribute(GAME_STATE_ATTRIBUTE) != GameState.PLAY;
                hosts = getFilteredHosts(predicate);
                setAttributes(hosts, GAME_STATE_ATTRIBUTE, state);
                server.broadcast(Filters.in(hosts) , new GameStateMessage(state));
                server.broadcast(new GameStateMessage(state));
                break;
            case SETUP:
                setAttributes(server.getConnections(), INIT_STATUS_ATTRIBUTE, InitStatus.NOTHING);
                break;
            default:
                break;
        }
    }
    
    private void setAttributes(Collection<HostedConnection> conns, String attribute, Object value){
        conns.forEach((conn) -> {
            conn.setAttribute(attribute, value);
        });
    }

    @Override
    public void notifyScore(Map<Integer, Integer> scores) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyTime(float time) {
        System.out.println("ServerHandler time = " + time);
        Predicate<HostedConnection> predicate = p -> p.getAttribute(GAME_STATE_ATTRIBUTE) != GameState.PLAY;
        Collection hosts = getFilteredHosts(predicate);
        server.broadcast(Filters.in(hosts), new TimeMessage(time));
    }

    @Override
    public void notifyDiskState(List<DiskState> diskStates) {
        broadcastDiskStates(diskStates);
    }
    
    public PlayerMoveEmitter getPlayerMoveEmitter(){
        return this;
    }

    public DiskStateListener getDiskStateListener() {
        return this;
    }

    public GameStateListener getGameStateListener() {
        return this;
    }

    public TimeListener getTimeListener() {
        return this;
    }

    public ScoreListener getScoreListener() {
        return this;
    }
    
    private void broadcastDiskStates(final List<DiskState> disks){
        new Thread(new Runnable(){
            @Override
            public void run(){
                Predicate<HostedConnection> predicate = p -> p.getAttribute(GAME_STATE_ATTRIBUTE) == GameState.END;
                Collection hosts = getFilteredHosts(predicate);
                server.broadcast(Filters.in(hosts), new DiskStateMessage(disks));                
            }
        }).start();
    }
    
    public void startHeartBeat(){
        new Thread(new HeartBeatSender()).start();
    }
    
    /**
     * Sends out a heart beat to all clients every TIME_SLEEPING seconds.
     */
    private class HeartBeatSender implements Runnable {

        private final int TIME_SLEEPING = 100; // timebetween heartbeats

        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run() {
            while (serverModule.isPlaying()) { //TODO: Swith to check playState.isEnabled? 
                try {
                    Thread.sleep(TIME_SLEEPING);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                DiskStateMessage m = new DiskStateMessage(serverModule.getDiskStates());
                server.broadcast(m);
            }
        }
    }
    
}
