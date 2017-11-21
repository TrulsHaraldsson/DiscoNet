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
import api.MoveDirection;
import api.Player;
import api.PlayerMoveListener;
import api.ScoreEmitter;
import api.ScoreListener;
import api.TimeEmitter;
import api.TimeListener;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import java.util.ArrayList;
import network.messages.JoinAckMessage;
import network.messages.JoinMessage;
import network.messages.PlayerMoveMessage;

/**
 *
 * @author truls
 */
public class ClientHandler implements GameStateEmitter, DiskStateEmitter, ScoreEmitter, TimeEmitter, PlayerMoveListener, MessageListener<Client>{
    
    private final ArrayList<GameStateListener> gameStateListeners = new ArrayList<>();
    private final ArrayList<DiskStateListener> diskStateListeners = new ArrayList<>();
    private final ArrayList<ScoreListener> scoreListeners = new ArrayList<>();
    private final ArrayList<TimeListener> timeListeners = new ArrayList<>();
    
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
        if(m instanceof JoinMessage){
            JoinMessage joinMessage = (JoinMessage) m;
            System.out.println("Join message received");
        }
        if(m instanceof PlayerMoveMessage){
            PlayerMoveMessage playerMoveMessage = (PlayerMoveMessage) m;
            System.out.println("Move message receied");
        }
        if(m instanceof JoinAckMessage){
            JoinAckMessage joinAckMessage = (JoinAckMessage) m;
            System.out.println("Join ack message received");
        }
    }

    @Override
    public void notifyPlayerMove(Player player, MoveDirection direction, boolean isPressed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    } 

    public PlayerMoveListener getPlayerMoveListener(){
        return this;
    }
    
}
