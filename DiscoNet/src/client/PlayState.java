/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import api.DiskState;
import api.DiskStateListener;
import api.ScoreEmitter;
import api.ScoreListener;
import api.models.Disk;
import api.physics.CollisionResult;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.BoardImpl;
import models.DiskImpl;
import models.GameConstants;
import models.PlayerDisk;
import physics.CollisionDetectorImpl;
import physics.CollisionSolverImpl;

/**
 *
 * @author truls
 */
public class PlayState extends BaseAppState implements DiskStateListener, ScoreEmitter{
    private final List<ScoreListener> scoreListeners = new ArrayList<>();
    
    private CollisionDetectorImpl collisionDetectorImpl = new CollisionDetectorImpl();
    private CollisionSolverImpl collisionSolverImpl = new CollisionSolverImpl();
    
    private ClientModule app;
    
    private List<DiskImpl> disks;
    private BoardImpl board;
    
    private final SetupState setupState;
    
    public PlayState(SetupState setupState){
        this.setupState = setupState;
    }
    
    @Override
    protected void initialize(Application app) {
        this.app = (ClientModule) app;
    }

    @Override
    protected void cleanup(Application app) {
        System.out.println("PlayState cleanup");
    }

    @Override
    protected void onEnable() {
        System.out.println("PlayState enabled");
        bindKeys();
        disks = setupState.getInitiateDisks();
        board = setupState.getBoard();
        
        if(board == null){
            throw new RuntimeException("Board is not instantiated");
        }
        
        if(disks == null || disks.isEmpty()){
            throw new RuntimeException("Disks are not instantiated");
        }
        
        app.getRootNode().attachChild(board);
        
        // Add all disks to board.
        for (DiskImpl disk : disks) {
            board.attachChild(disk);
        }
        
        System.out.println(disks.size() + " Disks added to board.");
    }

    @Override
    protected void onDisable() {
        unbindKeys();
        disks.clear();
        board = null;
        System.out.println("PlayState disabled");
    }
    
    @Override
    public void update(float tpf){
        for(DiskImpl disk : disks){
            disk.integrate(tpf);
        }
        
        List<CollisionResult> collisions = collisionDetectorImpl.getCollisions((List<Disk>)(List<?>) disks, tpf);
        collisionSolverImpl.resolveCollisions(collisions, tpf);
        
        for(DiskImpl disk : disks){
            collisionSolverImpl.collisionWithWall(disk, GameConstants.FREE_AREA_WIDTH/2f);
        }
    }  
    
    private void bindKeys(){
        // Add key mappings
        app.getInputManager().addMapping("Up",  new KeyTrigger(KeyInput.KEY_U));
        app.getInputManager().addMapping("Right",   new KeyTrigger(KeyInput.KEY_K));
        app.getInputManager().addMapping("Left",  new KeyTrigger(KeyInput.KEY_H));
        app.getInputManager().addMapping("Down", new KeyTrigger(KeyInput.KEY_J));
        // Add them as listeners
        app.getInputManager().addListener(app.getActionListener(),"Up", "Right", "Left", "Down");
        
    }
    
    private void unbindKeys(){
        // remove key mappings
        app.getInputManager().deleteMapping("Up");
        app.getInputManager().deleteMapping("Right");
        app.getInputManager().deleteMapping("Left");
        app.getInputManager().deleteMapping("Down");
    }

    @Override
    public void notifyDiskState(List<DiskState> diskStates) {
        Map<Integer, Integer> scores = new HashMap();
        for(DiskState diskState : diskStates){
            for(DiskImpl disk : disks){
                if(disk.getID() == diskState.getID()){
                    disk.setPosition(diskState.getPosition());
                    disk.setVelocity(diskState.getVelocity());
                    disk.setAcceleration(diskState.getAcceleration());
                    disk.setPoints(diskState.getPoints());
                }
            }
        }
        // Notify the new scores
        for (DiskImpl disk : disks) {
            if(disk instanceof PlayerDisk){
                scores.put(disk.getID(), disk.getPoints());
            }
        }
        notifyScoreListeners(scores);
    }

    @Override
    public void addScoreListener(ScoreListener scoreListener) {
        scoreListeners.add(scoreListener);
    }
    
    public void notifyScoreListeners(Map<Integer, Integer> scores){
        for (ScoreListener scoreListener : scoreListeners) {
            scoreListener.notifyScore(scores);
        }
    }
    
}
