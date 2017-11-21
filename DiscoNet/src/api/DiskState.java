/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import com.jme3.math.Vector3f;

/**
 *
 * @author truls
 */
public interface DiskState {
    
    Vector3f getPosition();
    
    Vector3f getVelocity();
    
    Vector3f getAcceleration();
    
    Player getPlayer();
}
