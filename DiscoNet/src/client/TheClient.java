/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import api.GameState;
import api.GameStateListener;
import com.jme3.app.SimpleApplication;
import gui.GUIModule;
import network.ClientHandler;

/**
 *
 * @author truls
 */
public class TheClient extends SimpleApplication implements GameStateListener{

    private ClientHandler clientHandler;
    
    @Override
    public void simpleInitApp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyGameState(GameState state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(String[] args){
        /*ClientHandler ch = new ClientHandler();
        ClientModule cm = new ClientModule(ch, ch);
        GUIModule gm = new GUIModule(ch, ch, ch);
        
        TheClient c = new TheClient();
        c.start(); //TODO: Hostname etc.*/
    }
}
