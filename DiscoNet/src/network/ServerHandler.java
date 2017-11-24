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
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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
                joinAckMessage = new JoinAckMessage(result.get(), true);
                
            } catch (InterruptedException ex) {
                joinAckMessage = new JoinAckMessage(-1, false);
            } catch (ExecutionException e){
                joinAckMessage = new JoinAckMessage(-1, false);
            }
            
            serverModule.enqueue(new Callable() {
                @Override
                public Object call() throws Exception {
                    serverModule.onPlayerJoined(result.get());
                    return true;
                }
            });
            
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
            server.broadcast(im);
            System.out.println("Init message broadcasted.");
        } else if (m instanceof InitAckMessage){
            System.out.println("Source : " + source);
            System.out.println("Server connections : " + server.getConnections());
            
            serverModule.enqueue(new Callable() {
                @Override
                public Object call() throws Exception {
                    serverModule.onPlayerReady(((InitAckMessage) m).getID());
                    return true;
                }
            });
        }
    }
    
    @Override
    public void addPlayerMoveListener(PlayerMoveListener playerMoveListener) {
        serverModule = (ServerModule) playerMoveListener;
    }

    @Override
    public void notifyGameState(GameState state) {
        server.broadcast(new GameStateMessage(GameState.PLAY));
    }

    @Override
    public void notifyScore(Map<String, Integer> scores) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyTime(float time) {
        System.out.println("ServerHandler time = " + time);
        server.broadcast(new TimeMessage(time));
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
                server.broadcast(new DiskStateMessage(disks));                
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
