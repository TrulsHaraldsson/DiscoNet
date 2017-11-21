/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import network.ClientHandler;

/**
 *
 * @author truls
 */
public class BootstrapClient {
    
    public static void main(String[] args){
        ClientHandler ch = new ClientHandler();
        ClientModule cm = new ClientModule();
        
        // ClienModule emmits player moves to client handler
        cm.addPlayerMoveListener(ch);
        
        // ClientHandler emitts DiskState and GameState to ClientModule
        ch.addDiskStateListener(cm);
        ch.addGameStateListener(cm);
        ch.addTimeListener(cm);
        ch.addScoreListener(cm);
        
        cm.start();
    }
}
