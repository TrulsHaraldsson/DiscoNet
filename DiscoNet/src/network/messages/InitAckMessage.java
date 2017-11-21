/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.messages;

import api.Player;

/**
 *
 * @author hannes
 */
public class InitAckMessage extends AbstractClientMessage {
    
    public InitAckMessage(Player player) {
        super(player);
    }
    
}
