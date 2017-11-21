/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import api.GameState;
import api.GameStateListener;
import api.ScoreListener;
import api.TimeListener;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.Map;

/**
 *
 * @author truls
 */
public class GUINode extends Node implements TimeListener, ScoreListener, GameStateListener{
    
    private BitmapText txtPoint;
    private BitmapText txtTime;
    private BitmapText txtInfo;
    
    public void initGUI(AssetManager assetManager, AppSettings appSettings){
        BitmapFont guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        
        txtTime = new BitmapText(guiFont, false);
        txtTime.setSize(guiFont.getCharSet().getRenderedSize() * 4);
        txtTime.setText("TIME:");
        txtTime.setLocalTranslation(appSettings.getWidth()*0.01f, appSettings.getHeight()*0.95f, 0);
        
        txtPoint = new BitmapText(guiFont, false);
        txtPoint.setSize(guiFont.getCharSet().getRenderedSize() * 4);
        txtPoint.setText("POINTS:");
        txtPoint.setLocalTranslation(appSettings.getWidth()*0.01f, appSettings.getHeight()*0.80f, 0);
        
        txtInfo = new BitmapText(guiFont, false);
        txtInfo.setSize(guiFont.getCharSet().getRenderedSize() * 4);
        txtInfo.setText("PRESS P\nTO PLAY\nAGAIN OR\nE TO EXIT:");
        txtInfo.setLocalTranslation(appSettings.getWidth()*0.01f, appSettings.getHeight()*0.40f, 0);
        
        super.attachChild(txtPoint);
        super.attachChild(txtTime);
    }
    
    @Override
    public void notifyTime(float time) {
        int sec = (int) (time % 100f);
        int hundreds = (int) (time * 100f) % 100;
        
        if(hundreds < 10){
            txtTime.setText("TIME: " + sec + ":0" + hundreds);
        }else{
            txtTime.setText("TIME: " + sec + ":" + hundreds);
        }    
    }

    @Override
    public void notifyScore(Map<String, Integer> scores) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Integer> s : scores.entrySet()){
            String row = s.getKey() + ":" + s.getValue() + "\n";
            sb.append(row);
        }
        txtPoint.setText(sb); 
    }

    @Override
    public void notifyGameState(GameState state) {
        switch(state){
            case PLAY:
                super.detachChild(txtInfo);
            case END:
                super.attachChild(txtInfo);
            case SETUP:
                break;
        } 
    }
    
}
