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
import api.IDProvider;
import api.IDRequester;
import api.PlayerMoveEmitter;
import api.PlayerMoveListener;
import api.ScoreListener;
import api.TimeListener;
import com.jme3.app.SimpleApplication;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import gui.GUINode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author truls
 */
public class ClientModule extends SimpleApplication implements 
        GameStateListener, DiskStateListener, TimeListener, ScoreListener, PlayerMoveEmitter, IDRequester {

    ArrayList<PlayerMoveListener> playerMoveListeners = new ArrayList<>();
    private final GUINode gui = new GUINode();
    
    private final PlayState playState = new PlayState();
    private final EndState endState = new EndState();
    private final SetupState setupState = new SetupState();
    
    private ActionListener actionListener;
    
    protected int myID;
    
    private IDProvider idProvider;
    
    public ClientModule(IDProvider idProvider){
        this.idProvider = idProvider;
    }
    
    private void initActionListener(){
        this.actionListener = new ActionListener() {
            @Override
            public void onAction(String name, boolean keyPressed, float tpf) {
                // TODO: Change to only send to server
                if (name.equals("Up")){
                } else if (name.equals("Down")){
                } else if (name.equals("Left")){
                } else if (name.equals("Right")){
                }
            }
        };
    }
    
    public ActionListener getActionListener(){
        return this.actionListener;
    }
    
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
        if(playState.isEnabled()){
            playState.notifyDiskState(diskStates);
        } else if (setupState.isEnabled()){
            setupState.notifyDiskState(diskStates);
        }
        
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
        initActionListener();
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
        
        idProvider.requestID(this);
    }

    @Override
    public void setID(int id) {
        this.myID = id;
        System.out.println("Finally new ID!" + id);
    }

    public int getID() {
        return this.myID;
    }
    

}
