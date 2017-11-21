/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jme3.material.Material;

/**
 *
 * @author truls
 */
public class PlayerDisk extends DiskImpl{
    
    public PlayerDisk(Material material) {
        super(DiskImpl.PLAYER_R, material);
    }

    @Override
    public int reward() {
        return 0;
    }
    
}
