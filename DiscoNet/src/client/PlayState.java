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
import java.util.List;

/**
 *
 * @author truls
 */
public class PlayState extends BaseAppState implements DiskStateListener{
    private ClientModule app;
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
        bindKeys();
        System.out.println("PlayState enabled");
    }

    @Override
    protected void onDisable() {
        unbindKeys();
        System.out.println("PlayState disabled");
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
