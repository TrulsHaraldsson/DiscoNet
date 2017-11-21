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
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import gui.GUINode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import models.BoardImpl;

/**
 *
 * @author truls
 */
public class ClientModule extends SimpleApplication implements 
        GameStateListener, DiskStateListener, TimeListener, ScoreListener, PlayerMoveEmitter {

    ArrayList<PlayerMoveListener> playerMoveListeners = new ArrayList<>();
    private final GUINode gui = new GUINode();
    
    private final PlayState playState = new PlayState();
    private final EndState endState = new EndState();
    private final SetupState setupState = new SetupState();
    
    @Override
    public void notifyGameState(GameState state) {
        gui.notifyGameState(state);
        switch(state){
            case PLAY:
                playState.setEnabled(true);
                break;
            case END:
                endState.setEnabled(true);
                break;
            case SETUP:
                setupState.setEnabled(true);
                break;
        }
    }

    @Override
    public void notifyDiskState(List<DiskState> diskStates) {
        playState.notifyDiskState(diskStates);
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
        
        flyCam.setMoveSpeed(200f);
        
        playState.setEnabled(false);
        endState.setEnabled(false);
        setupState.setEnabled(false);
        
        stateManager.attach(playState);
        stateManager.attach(endState);
        stateManager.attach(setupState);
        
        notifyGameState(GameState.SETUP);
        
        getCamera().setLocation(new Vector3f(-84f, 0.0f, 720f));
        getCamera().setRotation(new Quaternion(0.0f, 1.0f, 0.0f, 0.0f));
        
        flyCam.setEnabled(false);
        
        setDisplayStatView(false);

    }

}
