/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.physics;

import java.util.List;
import models.DiskImpl;

/**
 *
 * @author truls
 */
public interface CollisionSolver {
    
    void resolveCollisions(List<CollisionResult> collisions, float tpf);
    boolean collisionWithWall(DiskImpl disk, float edgeSize);
}
