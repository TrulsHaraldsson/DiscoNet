/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.messages;

import api.MoveDirection;
import api.Player;

/**
 *
 * @author hannes
 */
public class PlayerMoveMessage extends AbstractClientMessage {
    private final MoveDirection mDirection;
    private final boolean isPressed;
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
