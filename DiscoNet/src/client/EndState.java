/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.scene.Node;

/**
 *
 * @author truls
 */
public class EndState extends BaseAppState{

    ClientModule app;
    
    @Override
    protected void initialize(Application app) {
        this.app = (ClientModule)app;
    }

    @Override
    protected void cleanup(Application app) {
        System.out.println("Endstate cleanup");
    }

    @Override
    protected void onEnable() {
        System.out.println("EndState Enabled");
        Node root = app.getRootNode();
        
        // Remove all disks and board
        root.detachAllChildren();
        
        
        //Add key bindings
        app.getInputManager().addMapping("Join", new KeyTrigger(KeyInput.KEY_RETURN));
        // Add them as listeners
        app.getInputManager().addListener(app.getActionListener(),"Join");
    }

    @Override
    protected void onDisable() {
        System.out.println("EndState disabled");
        // remove key mappings
        app.getInputManager().deleteMapping("Join");
    }
    
}
