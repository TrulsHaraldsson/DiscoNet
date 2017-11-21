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
import com.jme3.app.SimpleApplication;
import gui.GUIModule;
import java.util.List;
import network.ClientHandler;

/**
 *
 * @author truls
 */
public class ClientModule extends SimpleApplication implements GameStateListener, DiskStateListener, PlayerMoveEmitter {

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
    
    public PlayerMoveEmitter getPlayerEmmitEmitter(){
        return this;
    }

    @Override
    public void simpleInitApp() {
        
    }
    
}
