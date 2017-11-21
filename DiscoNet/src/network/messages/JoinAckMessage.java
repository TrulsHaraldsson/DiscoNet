/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.messages;

import api.Player;
import com.jme3.network.AbstractMessage;

/**
 *
 * @author hannes
 */
public class JoinAckMessage extends AbstractMessage {
    private final Player newPlayer;
    private final boolean joined;
    
    /**
     * Join is possible.
     * @param newPlayer
     */
    public JoinAckMessage(Player newPlayer){
        this.newPlayer = newPlayer;
        this.joined = true;
        this.setReliable(true);
    }
    
    /**
     * Join is not possible
     */
    public JoinAckMessage(){
        this.newPlayer = null;
        this.joined = false;
    }
    
    public Player getNewPlayer(){
        return newPlayer;
    }
    
    public boolean getJoined(){
        return this.joined;
    }
}
