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
    private int id;
    private boolean joined;
    
    public JoinAckMessage(){}
    
    /**
     * Join is possible.
     * @param id
     */
    public JoinAckMessage(int id, boolean joined){
        this.id = id;
        this.joined = joined;
    }
  
    public int getID(){
        return id;
    }
    
    public boolean getJoined(){
        return this.joined;
    }
}
