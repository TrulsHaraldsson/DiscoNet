/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.physics;

/**
 *
 * @author truls
 */
public interface CollisionDetectionListener {
    
    void onCollision(CollisionResult collision);
    
}
