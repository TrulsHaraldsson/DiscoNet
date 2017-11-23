/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import api.DiskState;
import api.DiskStateListener;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.List;
import models.BoardImpl;
import models.DiskImpl;

/**
 *
 * @author truls
 */
public class SetupState extends BaseAppState implements DiskStateListener{

    ClientModule app;
    
    @Override
    protected void initialize(Application app) {
        this.app = (ClientModule) app;
    }

    @Override
    protected void cleanup(Application app) {
        System.out.println("SetupState cleanup");
    }

    @Override
    protected void onEnable() {
        System.out.println("SetupState enabled");
        
        Node root = app.getRootNode();
        // Create empty board
        BoardImpl board = new BoardImpl(app.getAssetManager());
        root.attachChild(board);
        
        // Set board invisible
        board.setCullHint(Spatial.CullHint.Always);
        
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

    @Override
    public void notifyDiskState(List<DiskState> diskStates) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
