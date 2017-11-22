/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;

/**
 *
 * @author truls
 */
public class PlayerDisk extends DiskImpl{
    static final float ACCELERATION_MULTIPLIER = 5f;
    private Vector3f acceleration;
    
    public PlayerDisk(Material material) {
        super(DiskImpl.PLAYER_R, material);
    }

    @Override
    public int reward() {
        return 0;
    }
    
    public void accelerateX(boolean pressed){
        float a = (pressed) ? ACCELERATION_MULTIPLIER : - ACCELERATION_MULTIPLIER;
        this.acceleration.addLocal(a, 0, 0);
    }
    
    public void accelerateY(boolean pressed){
        float a = (pressed) ? ACCELERATION_MULTIPLIER : - ACCELERATION_MULTIPLIER;
        this.acceleration.addLocal(0, a, 0);
    }

    @Override
    public Vector3f getAcceleration() {
        return acceleration;
    }
    
}
