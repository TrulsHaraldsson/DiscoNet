/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.messages;

/**
 *
 * @author truls
 */
public class TimeMessage extends AbstractUDPMessage{
    
    private float time;
    
    public TimeMessage(){}
    
    public TimeMessage(float time){
        this.time = time;
    }
}
