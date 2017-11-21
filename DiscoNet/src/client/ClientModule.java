/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import api.DiskState;
import api.DiskStateListener;
import api.GameState;
import api.GameStateListener;
import api.PlayerMoveEmitter;
import api.PlayerMoveListener;
import api.ScoreListener;
import api.TimeListener;
import com.jme3.app.SimpleApplication;
import gui.GUINode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author truls
 */
public class ClientModule extends SimpleApplication implements 
        GameStateListener, DiskStateListener, TimeListener, ScoreListener, PlayerMoveEmitter {

    ArrayList<PlayerMoveListener> playerMoveListeners = new ArrayList<>();
    private final GUINode gui = new GUINode();
    
    @Override
    public void notifyGameState(GameState state) {
        gui.notifyGameState(state);
    }

    @Override
    public void notifyDiskState(List<DiskState> diskStates) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void notifyTime(float time) {
        gui.notifyTime(time);
    }

    @Override
    public void notifyScore(Map<String, Integer> scores) {
        gui.notifyScore(scores);
    }

    @Override
    public void addPlayerMoveListener(PlayerMoveListener playerMoveListener) {
        playerMoveListeners.add(playerMoveListener);
    }  

    @Override
    public void simpleInitApp() {
        gui.initGUI(assetManager, settings);
        
        guiNode.attachChild(gui);
    }

}
