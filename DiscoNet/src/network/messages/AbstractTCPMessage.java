/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.messages;

import com.jme3.network.AbstractMessage;

/**
 *
 * @author hannes
 */
public class AbstractTCPMessage extends AbstractMessage {
    
    public AbstractTCPMessage(){
        this.setReliable(true);
    }
    
}
