/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.messages;

import api.MoveDirection;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author hannes
 */
@Serializable
public class PlayerMoveMessage extends AbstractClientMessage {
    private MoveDirection mDirection;
    private boolean isPressed;
    
    public PlayerMoveMessage(){}
    
    public PlayerMoveMessage(int id, MoveDirection mDirection, boolean isPressed) {
        super(id);
        this.mDirection = mDirection;
        this.isPressed = isPressed;
    }
    
    public MoveDirection getDirection(){
        return mDirection;
    }
    
    public boolean isPressed(){
        return isPressed;
    }
    
}
