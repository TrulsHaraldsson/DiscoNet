/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.messages;

import api.Player;

/**
 *
 * @author ted
 */
public class HelloWorldMessage extends AbstractClientMessage{
    
    private final String hello = "Hello World";
    
    public HelloWorldMessage(Player player) {
        super(player);
    }    
    
    public String getString(){
        return this.hello;
    }
}
