/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import gui.GUIModule;
import network.ClientHandler;

/**
 *
 * @author truls
 */
public class BootstrapClient {
    
    public static void main(String[] args){
        ClientHandler ch = new ClientHandler();
        ClientModule cm = new ClientModule();
        GUIModule gm = new GUIModule();
        
        cm.addPlayerMoveListener(ch);
        ch.addDiskStateListener(cm);
        ch.addGameStateListener(cm);
        
        ch.addTimeListener(gm.getTimeListener());
        ch.addScoreListener(gm.getScoreListener());
        ch.addGameStateListener(gm.getGameStateListener());
    }
}
