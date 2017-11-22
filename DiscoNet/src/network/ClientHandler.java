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
import com.jme3.cinematic.PlayState;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import java.io.IOException;
import java.util.ArrayList;
import static network.NetworkUtils.*;
import network.messages.DiskStateMessage;
import network.messages.GameStateMessage;
import network.messages.InitAckMessage;
import network.messages.InitMessage;
import network.messages.JoinAckMessage;
import network.messages.JoinMessage;
import network.messages.PlayerMoveMessage;
import network.messages.StartMessage;

/**
 *
 * @author truls
 */
public class ClientHandler implements GameStateEmitter, DiskStateEmitter, ScoreEmitter, 
        TimeEmitter, PlayerMoveListener, IDProvider, MessageListener<Client>{
    
    private final ArrayList<GameStateListener> gameStateListeners;
    private final ArrayList<DiskStateListener> diskStateListeners;
    private final ArrayList<ScoreListener> scoreListeners;
    private final ArrayList<TimeListener> timeListeners;
    
    private IDRequester idRequester;
    Client myClient;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public ClientHandler(){
        NetworkUtils.initSerializables();
        connectToServer();
        
        gameStateListeners = new ArrayList<>();
        diskStateListeners = new ArrayList<>();
        scoreListeners = new ArrayList<>();
        timeListeners = new ArrayList<>();
        
        myClient.addMessageListener(this, StartMessage.class);
        myClient.addMessageListener(this, InitMessage.class);
        myClient.addMessageListener(this, InitAckMessage.class);
        myClient.addMessageListener(this, JoinMessage.class);
        myClient.addMessageListener(this, JoinAckMessage.class);
        myClient.addMessageListener(this, PlayerMoveMessage.class);
        myClient.addMessageListener(this, DiskStateMessage.class);
        myClient.addMessageListener(this, GameStateMessage.class);

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
        gameStateListeners.add(gameStateListener);
    }

    @Override
    public void addDiskStateListener(DiskStateListener diskStateListener) {
        diskStateListeners.add(diskStateListener);
    }

    @Override
    public void addScoreListener(ScoreListener scoreListener) {
        scoreListeners.add(scoreListener);
    }

    @Override
    public void addTimeListener(TimeListener timeListener) {
        timeListeners.add(timeListener);
    }
    
    @Override
    public void messageReceived(Client source, Message m) {
        if(m instanceof JoinAckMessage){
            JoinAckMessage joinAckMessage = (JoinAckMessage) m;
            if (joinAckMessage.getJoined()){
                idRequester.setID(joinAckMessage.getID());
            }
            idRequester = null;
        } else if (m instanceof GameStateMessage){
            for(GameStateListener l : gameStateListeners){
                l.notifyGameState(((GameStateMessage) m).getGameState());                
            }                        
        } else if(m instanceof InitMessage){
            //Respond to server with own id
            for(DiskStateListener l : diskStateListeners){
                l.notifyDiskState(((DiskStateMessage) m).getDiskStates());
            }
            myClient.send(new InitAckMessage(myClient.getId()));
            
        } else if (m instanceof DiskStateMessage){
            for(DiskStateListener l : diskStateListeners){
                l.notifyDiskState(((DiskStateMessage) m).getDiskStates());
            }
            
        } else {
            System.out.println("This message does not exist!");
        }
    }

    public PlayerMoveListener getPlayerMoveListener(){
        return this;
    }

    @Override
    public void notifyPlayerMove(int diskID, MoveDirection direction, boolean isPressed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void requestID(IDRequester idr) {
        this.idRequester = idr;
    }
    
}
