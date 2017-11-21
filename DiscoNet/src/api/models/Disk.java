/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.models;

/**
 *
 * @author truls
 */
public interface Disk {
    
    /**
     * Return the radius of this disk
     * @return radius
     */
    float getRadius();
    
    /**
     * Return the reward associated with this disk
     * @return reward
     */
    int reward();
}
