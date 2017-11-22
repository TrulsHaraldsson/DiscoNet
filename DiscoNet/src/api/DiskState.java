/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import api.models.Player;
import com.jme3.math.Vector3f;
import models.DiskImpl;

/**
 *
 * @author truls
 */
public class DiskState {
    private final Vector3f pos;
    private final Vector3f velocity;
    private final Vector3f acceleration;
    private final Player player;

    public DiskState(DiskImpl d, Player p) {
        this.pos = d.getCenterOfMass();
        this.velocity = d.getVelocity();
        this.acceleration = null;
        this.player = p;
    }
    
    public Vector3f getPosition(){
        return pos;
    }
    
    public Vector3f getVelocity(){
        return velocity;
    }
    
    public Vector3f getAcceleration(){
        return acceleration;
    }
    
    public Player getPlayer(){
        return player;
    }
}
