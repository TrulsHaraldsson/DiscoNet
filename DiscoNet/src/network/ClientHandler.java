/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import api.DiskStateEmitter;
import api.DiskStateListener;
import api.GameStateEmitter;
import api.GameStateListener;
import api.IDProvider;
import api.IDRequester;
import api.MoveDirection;
import api.PlayerMoveListener;
import api.ScoreEmitter;
import api.ScoreListener;
import api.TimeEmitter;
import api.TimeListener;
import client.ClientModule;
import com.jme3.cinematic.PlayState;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import static network.NetworkUtils.*;
import network.messages.DiskStateMessage;
import network.messages.GameStateMessage;
import network.messages.InitAckMessage;
import network.messages.InitMessage;
import network.messages.JoinAckMessage;
import network.messages.JoinMessage;
import network.messages.PlayerMoveMessage;
import network.messages.RequestStartMessage;
import network.messages.StartMessage;
import network.messages.TimeMessage;

/**
 *
 * @author truls
 */
public class ClientHandler implements GameStateEmitter, DiskStateEmitter, ScoreEmitter, 
        TimeEmitter, PlayerMoveListener, IDProvider, MessageListener<Client>{
    
    private ClientModule gameStateListener;
    private ClientModule diskStateListener;
    private ClientModule scoreListener;
    private ClientModule timeListener;
    
    private IDRequester idRequester;
    Client myClient;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public ClientHandler(){
        NetworkUtils.initSerializables();
        connectToServer();
        
        myClient.addMessageListener(this, StartMessage.class);
        myClient.addMessageListener(this, InitMessage.class);
        myClient.addMessageListener(this, InitAckMessage.class);
        myClient.addMessageListener(this, JoinMessage.class);
        myClient.addMessageListener(this, JoinAckMessage.class);
        myClient.addMessageListener(this, PlayerMoveMessage.class);
        myClient.addMessageListener(this, DiskStateMessage.class);
        myClient.addMessageListener(this, GameStateMessage.class);
        myClient.addMessageListener(this, TimeMessage.class);

    }

    @SuppressWarnings("CallToPrintStackTrace")
    private void connectToServer(){
           try{
            System.out.println("Trying to connect to server");
            myClient = Network.connectToServer(SERVER_HOSTNAME, SERVER_PORT);
            myClient.start();
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    @Override
    public void addGameStateListener(GameStateListener gameStateListener) {
        this.gameStateListener = (ClientModule) gameStateListener;
    }

    @Override
    public void addDiskStateListener(DiskStateListener diskStateListener) {
        this.diskStateListener = (ClientModule) diskStateListener;
    }

    @Override
    public void addScoreListener(ScoreListener scoreListener) {
        this.scoreListener = (ClientModule) scoreListener;
    }

    @Override
    public void addTimeListener(TimeListener timeListener) {
        this.timeListener = (ClientModule) timeListener;
    }
    
    @SuppressWarnings("CallToPrintStackTrace")
    @Override
    public void messageReceived(Client source, final Message m) {
        if(m instanceof JoinAckMessage){
            JoinAckMessage joinAckMessage = (JoinAckMessage) m;
            if (joinAckMessage.getJoined()){
                idRequester.setID(joinAckMessage.getID());  
            }            
            idRequester = null;
        } else if (m instanceof GameStateMessage){
            gameStateListener.enqueue(new Callable() {
                @Override
                public Object call() throws Exception {
                    gameStateListener.notifyGameState(((GameStateMessage) m).getGameState());
                    return true;
                }
            });                     
        } else if(m instanceof InitMessage){
            //Respond to server with own id
            System.out.println(((InitMessage) m).toString());
            System.out.println("init players received id: " + ((InitMessage)m).getPlayers().get(0).getID());
            Future<Integer> result = diskStateListener.enqueue(new Callable() {
                @Override
                public Object call() throws Exception {
                    diskStateListener.notifyDiskState(((InitMessage)m).getPlayers());
                    return diskStateListener.getID();
                }
            });
            // Send init message back when done setting up.
            try {
                myClient.send(new InitAckMessage(result.get()));
            } catch (InterruptedException | ExecutionException e) {
                //TODO: Die!?
                System.out.println("Something wrong when sending InitAck.");
                e.printStackTrace();
            }
            
            
        } else if (m instanceof DiskStateMessage){
            diskStateListener.enqueue(new Callable() {
                @Override
                public Object call() throws Exception {
                    diskStateListener.notifyDiskState(((DiskStateMessage)m).getDiskStates());
                    return true;
                }
            });
            
        } else if(m instanceof TimeMessage){
            timeListener.enqueue(new Callable() {
                @Override
                public Object call() throws Exception {
                    timeListener.notifyTime(((TimeMessage) m).getTime());
                    return true;
                }
            });
        } else {
            System.out.println("This message does not exist!");
        }
    }
    
    public void sendRequestStartMessage(){
        myClient.send(new RequestStartMessage());
    }

    public PlayerMoveListener getPlayerMoveListener(){
        return this;
    }

    @Override
    public void notifyPlayerMove(int diskID, MoveDirection direction, boolean isPressed) {
        myClient.send(new PlayerMoveMessage(diskID, direction, isPressed));
    }

    @Override
    public void requestID(IDRequester idr) {
        this.idRequester = idr;
        myClient.send(new JoinMessage());
    }
    
    public void destroy(){
        myClient.close();
    }
    
}
