/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.messages;

import api.models.Player;

/**
 *
 * @author hannes
 */
public class JoinAckMessage extends AbstractTCPMessage {
    private final Player newPlayer;
    private final boolean joined;
    
    /**
     * Join is possible.
     * @param newPlayer
     */
    public JoinAckMessage(Player newPlayer){
        super();
        this.newPlayer = newPlayer;
        this.joined = true;
    }
    
    /**
     * Join is not possible
     */
    public JoinAckMessage(){
        super();
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
