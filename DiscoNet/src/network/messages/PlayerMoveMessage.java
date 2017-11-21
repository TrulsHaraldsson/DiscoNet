/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.messages;

import api.MoveDirection;
import api.models.Player;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author hannes
 */
@Serializable
public class PlayerMoveMessage extends AbstractClientMessage {
    private final MoveDirection mDirection;
    private final boolean isPressed;

    public PlayerMoveMessage() {
        super(null);
        this.mDirection = null;
        this.isPressed = false;
    }
    
    public PlayerMoveMessage(Player player, MoveDirection mDirection, boolean isPressed) {
        super(player);
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
