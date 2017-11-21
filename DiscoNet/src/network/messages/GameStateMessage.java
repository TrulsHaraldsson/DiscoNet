/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.messages;

import api.GameState;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author hannes
 */
@Serializable
public class GameStateMessage extends AbstractTCPMessage {
    private final GameState state;

    public GameStateMessage() {
        this.state = null;
    }
    
    public GameStateMessage(GameState state){
        this.state = state;
    }
    
    public GameState getGameState(){
        return state;
    }
}
