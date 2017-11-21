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
public class DiskStateMessage extends AbstractUDPMessage {

    private final List<DiskState> diskStates;

    public DiskStateMessage() {
        this.diskStates = null;
    }
    
    public DiskStateMessage(List<DiskState> diskState) {
        this.diskStates = diskState;
    }
    
    public List<DiskState> getDiskStates(){
        return diskStates;
    }
    
}
