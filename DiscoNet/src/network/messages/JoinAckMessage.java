/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.messages;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author hannes
 */
@Serializable
public class JoinAckMessage extends AbstractTCPMessage {
    private final int id;
    private final boolean joined;
    
    /**
     * Join is possible.
     * @param id
     */
    public JoinAckMessage(int id){
        this.id = id;
        this.joined = true;
    }
    
    /**
     * Join is not possible
     */
    public JoinAckMessage(){
        this.id = -1;
        this.joined = false;
    }
    
    public int getID(){
        return id;
    }
    
    public boolean getJoined(){
        return this.joined;
    }
}
