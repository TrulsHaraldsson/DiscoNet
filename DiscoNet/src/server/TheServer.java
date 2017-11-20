/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.app.SimpleApplication;
import network.ServerHandler;

/**
 *
 * @author truls
 */
public class TheServer extends SimpleApplication {

    private ServerHandler serverHandler;
    
    @Override
    public void simpleInitApp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(String[] args){
        /*TheServer s = new TheServer();
        s.start(); //TODO: Headless */
    }
    
}
