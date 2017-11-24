/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import models.DiskImpl;

/**
 *
 * @author truls
 */
@Serializable
public class DiskState {
    private Vector3f pos;
    private Vector3f velocity;
    private Vector3f acceleration;
    private int id;
    private int points;
    
    public DiskState(){
    }

    public DiskState(DiskImpl d) {
        this.pos = d.getPosition();
        this.velocity = d.getVelocity();
        this.acceleration = d.getAcceleration();
        this.id = d.getID();
        this.points = d.getPoints();
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
    
    public int getID(){
        return id;
    }
    
    public int getPoints(){
        return points;
    }
}
