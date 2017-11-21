/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import api.GameState;
import api.GameStateEmitter;
import api.GameStateListener;
import api.ScoreEmitter;
import api.ScoreListener;
import api.TimeEmitter;
import api.TimeListener;
import java.util.Map;

/**
 *
 * @author truls
 */
public class GUIModule implements TimeListener, ScoreListener, GameStateListener{
    
    public GUIModule(TimeEmitter timeEmitter, ScoreEmitter scoreEmitter, GameStateEmitter gameStateEmitter){
        timeEmitter.addTimeListener(this);
        scoreEmitter.addScoreListener(this);
        gameStateEmitter.addGameStateListener(this);
    }

    @Override
    public void notifyTime(float time) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyScore(Map<String, Integer> scores) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyGameState(GameState state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
