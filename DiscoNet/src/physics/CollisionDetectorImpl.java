/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import api.models.Disk;
import api.physics.CollisionDetectionListener;
import api.physics.CollisionDetector;
import api.physics.CollisionResult;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;
import models.DiskImpl;

/**
 *
 * @author truls
 */
public class CollisionDetectorImpl implements CollisionDetector{
    
    ArrayList<CollisionDetectionListener> listeners = new ArrayList();

    @Override
    public List<CollisionResult> getCollisions(List<Disk> disks, float tpf) {
        ArrayList<CollisionResult> results = new ArrayList<>();
        for(int i = 0; i < disks.size(); i++){
            DiskImpl diskA = (DiskImpl)disks.get(i);
            for(int j = i+1; j < disks.size(); j++){
                DiskImpl diskB = (DiskImpl)disks.get(j);
                
                Vector3f posA = diskA.getWorldTranslation();
                Vector3f posB = diskB.getWorldTranslation();
                float distance = posA.distance(posB);
                float combinedRadius = diskA.getRadius() + diskB.getRadius();
                
                if(distance < combinedRadius){
                    Vector3f normal = posB.subtract(posA);
                    normal = normal.normalize();
                    float collisionDepth = combinedRadius - distance;
                    
                    //Velocities
                    Vector3f velA = diskA.getVelocity();
                    Vector3f velB = diskB.getVelocity();
                    
                    //Radius
                    float rA = diskA.getRadius();
                    float rB = diskB.getRadius();
                    
                    //Rewinding position to start of update
                    Vector3f startPosA = new Vector3f(posA.x - velA.x * tpf, posA.y - velA.y * tpf, 0);
                    Vector3f startPosB = new Vector3f(posB.x - velB.x * tpf, posB.y - velB.y * tpf, 0);
                    
                    float dPx = startPosA.x - startPosB.x;
                    float dPy = startPosA.y - startPosB.y;
                    float dVx = velA.x - velB.x;
                    float dVy = velA.y - velB.y;
                    
                    float q = dPx * dPx + dPy * dPy - (rA + rB) * (rA + rB);
                    
                    float temp = dVx * dVx + dVy * dVy;
                    
                    q = q / temp;
                    
                    float p = 2.0f * ( dPx * dVx + dPy * dVy);
                    
                    p = p / temp;
                    
                    float sqrt = FastMath.sqrt(p*p/4 - q);
                   
                    float t1 = - p / 2 + sqrt;
                    float t2 = - p / 2 - sqrt;
                    
                    float toc;
                    
                    if(t1 < tpf){
                        toc = t1;
                    }else{
                        toc = t2;
                    }
                    
                    results.add(new CollisionResultImpl(diskA, diskB, normal, toc));
                }
            }
        }
        
        for(CollisionResult collision : results){
            notifyListeners(collision);
        }
        
        return results;
    }

    @Override
    public void addCollisionDetectionListener(CollisionDetectionListener listener) {
        listeners.add(listener);
    }
    
    private void notifyListeners(CollisionResult collision){
        for(CollisionDetectionListener listener : listeners){
            listener.onCollision(collision);
        }
    }
    
}
