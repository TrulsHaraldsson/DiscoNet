/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import api.MoveDirection;
import com.jme3.material.Material;

/**
 *
 * @author truls
 */
public class PlayerDisk extends DiskImpl{
    static final float ACCELERATION_MULTIPLIER = 80f;
    
    public PlayerDisk(Material material, int id) {
        super(GameConstants.PLAYER_R, material, id);
        points = 0;
    }

    @Override
    public int reward() {
        return 0;
    }
    
    public void addPoints(int reward){
        points += reward;
    }
    
    public void accelerateX(boolean right){
        float a = (right) ? ACCELERATION_MULTIPLIER : - ACCELERATION_MULTIPLIER;
        this.acceleration.addLocal(a, 0, 0);
    }
    
    public void accelerateY(boolean up){
        float a = (up) ? ACCELERATION_MULTIPLIER : - ACCELERATION_MULTIPLIER;
        this.acceleration.addLocal(0, a, 0);
    }
    
    public void accelerate(MoveDirection direction, boolean b){        
        switch(direction){
            case UP : 
                accelerateY(b);
                break;
            case DOWN : 
                accelerateY(!b);
                break;
            case LEFT :
                accelerateX(!b);
                break;
            case RIGHT : 
                accelerateX(b);
                break;
            default :
                break;
        }
    }
}
