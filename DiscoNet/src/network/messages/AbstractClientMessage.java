/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.messages;

import api.Player;
import com.jme3.network.AbstractMessage;

/**
 *
 * @author hannes
 */
public class AbstractClientMessage extends AbstractMessage {

    private final Player player;
    
    public AbstractClientMessage(Player player){
        this.player = player;
    }
            
    
    public Player getPlayer(){
        return player;
    }
}
