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
public interface RigidBody {
    
    float getMass();
    
    void applyImpulse(Vector3f impulse);
    
    void integrate(float tpf);
    
    Vector3f getVelocity();
    
    void setVelocity(Vector3f velocity);
    
    void translate(Vector3f offset);
    
    Vector3f getCenterOfMass();
    
    Vector3f getAcceleration();
}
