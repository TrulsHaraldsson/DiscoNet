/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import api.DiskState;
import api.DiskStateEmitter;
import api.DiskStateListener;
import api.GameState;
import api.GameStateEmitter;
import api.GameStateListener;
import api.PlayerMoveEmitter;
import api.PlayerMoveListener;
import java.util.List;

/**
 *
 * @author truls
 */
public class ClientModule implements GameStateListener, DiskStateListener, PlayerMoveEmitter {
    
    public ClientModule(GameStateEmitter gameStateEmitter, DiskStateEmitter diskStateEmitter){
        gameStateEmitter.addGameStateListener(this);
        diskStateEmitter.addDiskStateListener(this);
    }

    @Override
    public void notifyGameState(GameState state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyDiskState(List<DiskState> diskStates) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addPlayerMoveListener(PlayerMoveListener playerMoveListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
