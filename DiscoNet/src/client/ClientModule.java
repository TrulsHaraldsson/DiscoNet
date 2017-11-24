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
import api.MoveDirection;
import api.PlayerMoveEmitter;
import api.PlayerMoveListener;
import api.ScoreListener;
import api.TimeListener;
import com.jme3.app.LostFocusBehavior;
import com.jme3.app.SimpleApplication;
import com.jme3.input.controls.ActionListener;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import gui.GUINode;
import java.util.List;
import network.ClientHandler;

/**
 *
 * @author truls
 */
public class ClientModule extends SimpleApplication implements 
        GameStateListener, DiskStateListener, TimeListener, PlayerMoveEmitter, IDRequester {

    PlayerMoveListener playerMoveListeners;
    private final GUINode gui = new GUINode();
    
    private final PlayState playState;
    private final EndState endState;
    private final SetupState setupState;
    private final ClientHandler client;
    
    private ActionListener actionListener;
    
    protected int myID;
    
    private IDProvider idProvider;
    
    public ClientModule(IDProvider idProvider, ClientHandler ch){
        this.idProvider = idProvider;
        this.client = ch;
        setupState = new SetupState();
        playState = new PlayState(setupState);
        endState = new EndState();
        playState.addScoreListener(gui);
    }
    
    private void initActionListener(){
        this.actionListener = new ActionListener() {
            @Override
            public void onAction(String name, boolean keyPressed, float tpf) {
                // TODO: Change to only send to server
                if (name.equals("Up")){
                    playerMoveListeners.notifyPlayerMove(myID, MoveDirection.UP, keyPressed);                    
                } else if (name.equals("Down")){
                        playerMoveListeners.notifyPlayerMove(myID, MoveDirection.DOWN, keyPressed);                                                
                } else if (name.equals("Left")){
                        playerMoveListeners.notifyPlayerMove(myID, MoveDirection.LEFT, keyPressed);                                                
                } else if (name.equals("Right")){
                        playerMoveListeners.notifyPlayerMove(myID, MoveDirection.RIGHT, keyPressed);
                } else if (name.equals("RequestStart") && !keyPressed){
                    System.out.println("Enter pressed");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Sending new start request");
                            client.sendRequestStartMessage();
                        }
                    }).start();
                }
            }
        };
    }
    
    public ActionListener getActionListener(){
        return this.actionListener;
    }
    
    @Override
    public void notifyGameState(GameState state) {
        System.out.println("Game state : " + state);
        gui.notifyGameState(state);
        switch(state){
            case PLAY:
                playState.setEnabled(true);
                setupState.setEnabled(false);
                endState.setEnabled(false);
                break;
            case END:
                endState.setEnabled(true);
                playState.setEnabled(true);
                setupState.setEnabled(false);
                break;
            case SETUP:
                setupState.setEnabled(true);
                playState.setEnabled(false);
                endState.setEnabled(false);
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
        //System.out.println("ClientModule: time = " + time);
        gui.notifyTime(time);
    }

    @Override
    public void addPlayerMoveListener(PlayerMoveListener playerMoveListener) {
        this.playerMoveListeners = playerMoveListener;
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
        
        // init sunlight
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1,1,-2).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        
        super.setLostFocusBehavior(LostFocusBehavior.Disabled);
    }
    
    @Override
    public void destroy(){
        client.destroy();
        super.destroy();
    }

    @Override
    public void setID(int id) {
        this.myID = id;
        System.out.println("Finally new ID!" + id);
    }

    public int getID() {
        return this.myID;
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        gui.update(tpf);
    }
}
