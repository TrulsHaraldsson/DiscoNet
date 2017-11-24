/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

/**
 *
 * @author truls
 */
public class NegDisk extends DiskImpl{
    
    
    public NegDisk(Material material, int id) {
        super(GameConstants.NEGDISK_R, material, id);
        points = -3;
    }

    @Override
    public int reward() {
        return points;
    }
    
}
