/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import api.models.Player;

/**
 *
 * @author truls
 */
public interface PlayerMoveListener {
    
    void notifyPlayerMove(Player player, MoveDirection direction, boolean isPressed);
}
