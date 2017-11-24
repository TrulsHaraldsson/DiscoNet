/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.messages;


/**
 *
 * @author hannes
 */
public class AbstractClientMessage extends AbstractTCPMessage {

    private int id;
    
    public AbstractClientMessage(){}
    
    public AbstractClientMessage(int id){
        super();
        this.id = id;
    }
            
    
    public int getID(){
        return id;
    }
}
