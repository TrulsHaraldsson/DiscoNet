/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.physics;

import java.util.List;

/**
 *
 * @author truls
 */
public interface CollisionSolver {
    
    void resolveCollisions(List<CollisionResult> collisions, float tpf);
    
}
