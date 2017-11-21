/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import api.physics.CollisionResult;
import api.physics.RigidBody;
import com.jme3.math.Vector3f;

/**
 *
 * @author truls
 */
public class CollisionResultImpl implements CollisionResult{

    private final RigidBody A;
    private final RigidBody B;
    private final Vector3f normal;
    private final float toc;
    
    public CollisionResultImpl(RigidBody A, RigidBody B, Vector3f normal, float toc){
        this.A = A;
        this.B = B;
        this.normal = normal;
        this.toc = toc;
    }
    
    @Override
    public Vector3f getCollisionNormal() {
        return normal;
    }

    @Override
    public RigidBody getFirst() {
        return A;
    }

    @Override
    public RigidBody getSecond() {
        return B;
    }

    @Override
    public float getTimeOfContact() {
        return toc;
    }
    
}
