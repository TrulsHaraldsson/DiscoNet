/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.physics;

import com.jme3.math.Vector3f;

/**
 *
 * @author truls
 */
public interface CollisionResult {
    
    Vector3f getCollisionNormal();
    
    RigidBody getFirst();
    
    RigidBody getSecond();
    
    float getTimeOfContact();
}
