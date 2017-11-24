/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.system.JmeContext;
import network.ServerHandler;

/**
 *
 * @author truls
 */
public class BootstrapServer {
    
    public static void main(String[] args){
        ServerHandler sh = new ServerHandler();
        ServerModule sm = new ServerModule(sh);
        
        // Server handler emitts player moves to server
        sh.addPlayerMoveListener(sm.getPlayerMoveListener());
        
        // Server emitts GameStates, DiskStates, Time and Scores
        // to ServerHandler
        /*
        sm.addDiskStateListener(sh.getDiskStateListener());
        sm.addGameStateListener(sh.getGameStateListener());
        sm.addTimeListener(sh.getTimeListener());
        sm.addScoreListener(sh.getScoreListener());
        */
        sm.start(JmeContext.Type.Headless);
    }
}
