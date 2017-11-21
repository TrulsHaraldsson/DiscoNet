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
public class JoinMessage extends AbstractClientMessage {
    public JoinMessage(Player player){
        super(player);
    }
}
