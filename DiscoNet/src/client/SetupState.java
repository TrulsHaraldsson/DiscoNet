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
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.List;
import models.BoardImpl;

/**
 *
 * @author truls
 */
public class SetupState extends BaseAppState{

    SimpleApplication simpleApplication;
    
    @Override
    protected void initialize(Application app) {
        simpleApplication = (SimpleApplication)app;
    }

    @Override
    protected void cleanup(Application app) {
        System.out.println("SetupState cleanup");
    }

    @Override
    protected void onEnable() {
        System.out.println("SetupState enabled");
        
        Node root = simpleApplication.getRootNode();
        // Create empty board
        BoardImpl board = new BoardImpl(simpleApplication.getAssetManager());
        root.attachChild(board);
        
        // Set board invisible
        board.setCullHint(Spatial.CullHint.Always);
            
    }

    @Override
    protected void onDisable() {
        System.out.println("SetupState disabled");
    }
    
}
