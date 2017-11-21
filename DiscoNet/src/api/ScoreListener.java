/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.util.Map;

/**
 *
 * @author truls
 */
public interface ScoreListener {
    
    void notifyScore(Map<String, Integer> scores);
}
