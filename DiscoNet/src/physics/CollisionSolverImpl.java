/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import api.physics.CollisionResult;
import api.physics.CollisionSolver;
import api.physics.RigidBody;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import java.util.List;
import models.DiskImpl;

/**
 *
 * @author truls
 */
public class CollisionSolverImpl implements CollisionSolver{

    @Override
    public void resolveCollisions(List<CollisionResult> collisions, float tpf) {
        for(CollisionResult result : collisions){
            RigidBody bodyA = result.getFirst();
            RigidBody bodyB = result.getSecond();
            Vector3f normal = result.getCollisionNormal();
            Vector3f tangential = new Vector3f(-normal.y, normal.x, 0);
            
            // Rewinding
            float rewindTime = tpf - result.getTimeOfContact();
            Vector3f velA = bodyA.getVelocity();
            Vector3f velB = bodyB.getVelocity();
            bodyA.translate(new Vector3f(-velA.x * rewindTime, -velA.y * rewindTime, 0));
            bodyB.translate(new Vector3f(-velB.x * rewindTime, -velB.y * rewindTime, 0));
            
            // Calculate collision resolution
            float velAnormal = normal.dot(bodyA.getVelocity());
            float velAtan = tangential.dot(bodyA.getVelocity());
            float velBnormal = normal.dot(bodyB.getVelocity());
            float velBtan = tangential.dot(bodyB.getVelocity());
            
            float combinedMass = bodyA.getMass() + bodyB.getMass();
            
            float vAnPrime = velAnormal * ( bodyA.getMass() - bodyB.getMass() ) + 2 * bodyB.getMass() * velBnormal;
            vAnPrime = vAnPrime / combinedMass;
            
            float vBnPrime = velBnormal * ( bodyB.getMass() - bodyA.getMass() ) + 2 * bodyA.getMass() * velAnormal;
            vBnPrime = vBnPrime / combinedMass;
            
            Vector3f newVelAnormal = new Vector3f(vAnPrime * normal.x, vAnPrime * normal.y, 0f);
            Vector3f newVelAtan = new Vector3f(velAtan * tangential.x, velAtan * tangential.y, 0f);
            Vector3f newVelBnormal = new Vector3f(vBnPrime * normal.x, vBnPrime * normal.y, 0f);
            Vector3f newVelBtan = new Vector3f(velBtan * tangential.x, velBtan * tangential.y, 0f);
            
            // Update to new velocities
            bodyA.setVelocity(newVelAnormal.add(newVelAtan));
            bodyB.setVelocity(newVelBnormal.add(newVelBtan));
            
            // Integrate rest of the time 
            bodyA.integrate(rewindTime);
            bodyB.integrate(rewindTime);
        }
    }
    
    /**
     * checks and adjusts disks velocity and position for a collision with a wall.
     * @param d
     * @param edgeSize 
     * @return  
     */
    @Override
    public boolean collisionWithWall(DiskImpl d, float edgeSize){
        Vector3f pos = d.getLocalTranslation();
        Vector3f v = d.getVelocity();
        float radius = d.getRadius();
        float maxDistance = edgeSize - radius;
        boolean collision = false;
        if (FastMath.abs(pos.x) > maxDistance){
            // Collision with east/west wall
            
            d.setVelocity(new Vector3f(-v.x, v.y, 0));
            float moveBack = FastMath.abs(pos.x) - maxDistance;
            if (pos.x > 0){
                // east wall
                d.move(- moveBack, 0, 0);
            }else {
                // west wall
                d.move(moveBack, 0, 0);
            }
            collision = true;
        }
        if (FastMath.abs(pos.y) > maxDistance) {
            // collision with south/north wall
            d.setVelocity(new Vector3f(v.x, - v.y, 0));
            float moveBack = FastMath.abs(pos.y) - maxDistance;
            if (pos.y > 0){
                // north wall
                d.move(0, - moveBack, 0);
            }else {
                // south wall
                d.move(0, moveBack, 0);
            }
            collision = true;
        }
        return collision;
    }
    
}
