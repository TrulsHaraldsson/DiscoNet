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
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.Map;
import models.GameConstants;

/**
 *
 * @author truls
 */
public class GUINode extends Node implements TimeListener, ScoreListener, GameStateListener{
    
    private BitmapText txtPoint;
    private BitmapText txtTime;
    private BitmapText txtSetupText;
    private BitmapText txtPlayerHint;
    private BitmapText txtEnd;
    private BitmapText txtWinner;
    
    private BitmapFont guiFont;
    private AppSettings appSettings;
    
    private Map.Entry<Integer, Integer> winner;
    
    // Used to make animations
    private float animationTime = 0.0f;
    
    // Used to display Time
    private float gameTime = GameConstants.GAME_TIME;
    
    private GameState state;
    
    public void initGUI(AssetManager assetManager, AppSettings appSettings){
        this.appSettings = appSettings;
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        
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
        txtSetupText.setLocalTranslation(appSettings.getWidth()*0.5f - txtSetupText.getLineWidth() / 2.0f, 
                appSettings.getHeight()*0.50f + txtSetupText.getLineHeight() / 2.0f, 0);
        
        txtPlayerHint = new BitmapText(guiFont, false);
        txtPlayerHint.setText("PRESS ENTER");
        scaleProperlyToWindowSize(txtPlayerHint, width, charsetRenderSize);
        txtPlayerHint.setLocalTranslation(appSettings.getWidth()*0.5f - txtPlayerHint.getLineWidth() / 2.0f, 
                appSettings.getHeight()*0.30f + txtPlayerHint.getLineHeight() / 2.0f, 0);
        
        txtEnd = new BitmapText(guiFont, false);
        txtEnd.setText("GAME IS ALREADY RUNNING");
        scaleProperlyToWindowSize(txtEnd, width, charsetRenderSize);
        txtEnd.setLocalTranslation(appSettings.getWidth()*0.5f - txtEnd.getLineWidth() / 2.0f, 
                appSettings.getHeight()*0.50f + txtEnd.getLineHeight() / 2.0f, 0);
        
        txtWinner = new BitmapText(guiFont, false);
    }
    
    private void scaleProperlyToWindowSize(BitmapText text, float widthWindow, float charsetRenderSize){
        float size = widthWindow / charsetRenderSize;
        text.setSize(size);
    }
    
    public void update(float tpf){
        animationTime += tpf;
        
        if(state == GameState.SETUP || state == GameState.END){
            txtPlayerHint.setAlpha(FastMath.abs(FastMath.sin(animationTime/0.5f)));
        }else if( state == GameState.PLAY){
            gameTime -= tpf;
            if(gameTime <= 0){
                gameTime = 0;
            }
            notifyTime(gameTime);
        }
    }
    
    @Override
    public void notifyTime(float time) {
        gameTime = time;
        
        int sec = (int) (time % 100f);
        int hundreds = (int) (time * 100f) % 100;
        
        if(hundreds < 10){
            txtTime.setText("TIME: " + sec + ":0" + hundreds);
        }else{
            txtTime.setText("TIME: " + sec + ":" + hundreds);
        }    
    }

    @Override
    public void notifyScore(Map<Integer, Integer> scores) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Integer, Integer> s : scores.entrySet()){
            String row = GameConstants.PLAYER_NAMES[s.getKey()] + ":" + s.getValue() + "\n";
            sb.append(row);
            if(winner == null || s.getValue() > winner.getValue()){
                winner = s;
            }
        }
        txtPoint.setText(sb); 
    }

    @Override
    public void notifyGameState(GameState state) {
        this.state = state;
        switch(state){
            case PLAY:
                super.attachChild(txtTime);
                super.attachChild(txtPoint);
                super.detachChild(txtSetupText);
                super.detachChild(txtPlayerHint);
                break;
            case END:
                notifyTime(0);
                if(winner != null){
                    setWinnerText();
                    super.detachChild(txtSetupText);
                    super.attachChild(txtWinner);
                    super.attachChild(txtPlayerHint);
                }else {
                    super.attachChild(txtEnd);
                    super.detachChild(txtSetupText);
                }
                break;
            case SETUP:
                super.attachChild(txtSetupText);
                super.attachChild(txtPlayerHint);
                super.detachChild(txtTime);
                super.detachChild(txtPoint);
                super.detachChild(txtEnd);
                super.detachChild(txtWinner);
                txtPlayerHint.setAlpha(1f);
                winner = null;
                break;
        } 
    }
    
    public void setWinnerText(){
        txtWinner.setText("PLAYER " + GameConstants.PLAYER_NAMES[winner.getKey()] + " IS THE WINNER!");
        scaleProperlyToWindowSize(txtWinner, appSettings.getWidth(), guiFont.getCharSet().getRenderedSize());
        txtWinner.setLocalTranslation(appSettings.getWidth()*0.5f - txtWinner.getLineWidth() / 2.0f, 
                appSettings.getHeight()*0.50f + txtWinner.getLineHeight() / 2.0f, 0);
    }
    
}
