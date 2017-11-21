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
import java.util.List;

/**
 *
 * @author truls
 */
public class PlayState extends BaseAppState implements DiskStateListener{

    SimpleApplication simpleApplication;
    
    @Override
    protected void initialize(Application app) {
        simpleApplication = (SimpleApplication)app;
    }

    @Override
    protected void cleanup(Application app) {
        System.out.println("PlayState cleanup");
    }

    @Override
    protected void onEnable() {
        System.out.println("PlayState enabled");
    }

    @Override
    protected void onDisable() {
        System.out.println("PlayState disabled");
    }

    @Override
    public void notifyDiskState(List<DiskState> diskStates) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
