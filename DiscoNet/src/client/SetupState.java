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
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;
import models.DiskImpl;
import models.SetupInitiater;

/**
 *
 * @author truls
 */
public class SetupState extends BaseAppState implements DiskStateListener{

    ClientModule app;
    List<DiskImpl> disks;
    Node board;
    
    @Override
    protected void initialize(Application app) {
        this.app = (ClientModule) app;
        disks = new ArrayList<>();
    }

    @Override
    protected void cleanup(Application app) {
        System.out.println("SetupState cleanup");
    }
    
    public List<DiskImpl> getInitiateDisks(){
        return disks;
    }
    
    @Override
    protected void onEnable() {
        System.out.println("SetupState enabled");
        disks = SetupInitiater.getPassiveDisks(app.getAssetManager());
        
        Node root = app.getRootNode();
        // Create empty board
        board = SetupInitiater.getBoard(app.getAssetManager());
        root.attachChild(board);
        
        // Set board invisible
        //board.setCullHint(Spatial.CullHint.Always);
        
        //Add key bindings
        app.getInputManager().addMapping("RequestStart", new KeyTrigger(KeyInput.KEY_RETURN));
        // Add them as listeners
        app.getInputManager().addListener(app.getActionListener(),"RequestStart");
            
    }

    @Override
    protected void onDisable() {
        // remove key mappings
        app.getInputManager().deleteMapping("RequestStart");
        System.out.println("SetupState disabled");
    }
    
    /**
     * only player diskStates
     * @param diskStates 
     */
    @Override
    public void notifyDiskState(List<DiskState> diskStates) {
        disks.addAll(SetupInitiater.createPlayerDisks(diskStates, app.getAssetManager()));
        // Add all disks to board.
        for (DiskImpl disk : disks) {
            board.attachChild(disk);
            //disk.setLocalTranslation(0, 0, 100);
            
        }
        System.out.println(disks.size() + " Disks added to board.");
    }
    
}
