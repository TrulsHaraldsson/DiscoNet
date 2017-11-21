/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.physics;

import api.models.Disk;
import java.util.List;

/**
 *
 * @author truls
 */
public interface CollisionDetector {
    
    List<CollisionResult> getCollisions(List<Disk> disks, float tpf);
    
    void addCollisionDetectionListener(CollisionDetectionListener listener);
}
