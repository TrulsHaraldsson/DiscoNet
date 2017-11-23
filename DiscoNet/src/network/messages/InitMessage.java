/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.messages;

import api.DiskState;
import com.jme3.network.serializing.Serializable;
import java.util.List;

/**
 *
 * @author hannes
 */
@Serializable
public class InitMessage extends AbstractTCPMessage {
    private List<DiskState> players;

    public InitMessage() {
    }
    
    public InitMessage(List<DiskState> diskState) {
        this.players = diskState;
    }
    
    public List<DiskState> getPlayers(){
        return players;
    }
}
