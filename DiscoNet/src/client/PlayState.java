/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import api.DiskState;
import api.DiskStateListener;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import java.util.List;
import models.BoardImpl;
import models.DiskImpl;
import models.PlayerDisk;

/**
 *
 * @author truls
 */
public class PlayState extends BaseAppState implements DiskStateListener{
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
    }
    
}
