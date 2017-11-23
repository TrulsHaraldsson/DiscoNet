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
    
    private static final int REWARD = -3;
    
    public NegDisk(Material material, int id) {
        super(GameConstants.NEGDISK_R, material, id);
    }

    @Override
    public int reward() {
        return REWARD;
    }
    
}
