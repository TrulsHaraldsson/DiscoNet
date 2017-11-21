/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;

/**
 *
 * @author truls
 */
public class EndState extends BaseAppState{

    SimpleApplication simpleApplication;
    
    @Override
    protected void initialize(Application app) {
        simpleApplication = (SimpleApplication)app;
    }

    @Override
    protected void cleanup(Application app) {
        System.out.println("Endstate cleanup");
    }

    @Override
    protected void onEnable() {
        Node root = simpleApplication.getRootNode();
        
        // Remove all disks and board
        root.detachAllChildren();
    }

    @Override
    protected void onDisable() {
        System.out.println("EndState disabled");
    }
    
}
