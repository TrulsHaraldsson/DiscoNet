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
public class InitAckMessage extends AbstractClientMessage {

    public InitAckMessage() {
        super(-1);
    }
    
    public InitAckMessage(int id) {
        super(id);
    }
    
}
