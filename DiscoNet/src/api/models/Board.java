/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.models;

/**
 * An axis aligned box
 * @author truls
 */
public interface Board {
   
    /**
     * Return bottom side boundary
     * @return 
     */
    float getBottomExtent();
    
    /**
     * Return top side boundary
     * @return 
     */
    float getTopExtent();
    
    /**
     * Return left side boundary
     * @return 
     */
    float getLeftExtent();
    
    /**
     * Return right side boundary
     * @return 
     */
    float getRightExtent();
}
