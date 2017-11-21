/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.messages;

import api.models.Player;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author hannes
 */
@Serializable
public class JoinAckMessage extends AbstractTCPMessage {
    private final Player newPlayer;
    private final boolean joined;
    
    /**
     * Join is possible.
     * @param newPlayer
     */
    public JoinAckMessage(Player newPlayer){
        this.newPlayer = newPlayer;
        this.joined = true;
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
