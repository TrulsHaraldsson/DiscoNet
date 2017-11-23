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
    private BitmapText txtSetupText;
    
    public void initGUI(AssetManager assetManager, AppSettings appSettings){
        BitmapFont guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        
        float width = (float)appSettings.getWidth();
        float charsetRenderSize = guiFont.getCharSet().getRenderedSize();
        
        txtTime = new BitmapText(guiFont, false);
        txtTime.setText("TIME:");
        scaleProperlyToWindowSize(txtTime, width, charsetRenderSize);
        txtTime.setLocalTranslation(appSettings.getWidth()*0.01f, appSettings.getHeight()*0.95f, 0);
        
        txtPoint = new BitmapText(guiFont, false);
        txtPoint.setText("POINTS:");
        scaleProperlyToWindowSize(txtPoint, width, charsetRenderSize);
        txtPoint.setLocalTranslation(appSettings.getWidth()*0.01f, appSettings.getHeight()*0.80f, 0);
        
        txtSetupText = new BitmapText(guiFont, false);
        txtSetupText.setText("WAITING FOR GAME TO START");
        scaleProperlyToWindowSize(txtSetupText, width, charsetRenderSize);
        txtSetupText.setLocalTranslation(appSettings.getWidth()*0.5f - txtSetupText.getLineWidth() / 2, 
                appSettings.getHeight()*0.50f + txtSetupText.getLineHeight() / 2, 0);        
    }
    
    private void scaleProperlyToWindowSize(BitmapText text, float widthWindow, float charsetRenderSize){
        float size = widthWindow / charsetRenderSize;
        text.setSize(size);
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
                super.attachChild(txtTime);
                super.attachChild(txtPoint);
                super.detachChild(txtSetupText);
                break;
            case END:
                break;
            case SETUP:
                super.attachChild(txtSetupText);
                super.detachChild(txtTime);
                super.detachChild(txtPoint);
                break;
        } 
    }
    
}
