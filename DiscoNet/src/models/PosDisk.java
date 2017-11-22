/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Cylinder;

/**
 *
 * @author truls
 */
public class PosDisk extends DiskImpl{
    
    private int pointsLeft = 5;
    
    public PosDisk(Material material, Material dotMaterial, int id) {
        super(DiskImpl.POSDISK_R, material, id);
        Cylinder cyl = new Cylinder(32, 32, 2.5f, DISK_HEIGHT, true);
        Geometry temp = new Geometry("dot1", cyl);
        
        temp.move(0, 0, DISK_HEIGHT);
        temp.setMaterial(dotMaterial);
        super.attachChild(temp);
        
        float offset = POSDISK_R / 2.0f * 0.7f;
        temp = new Geometry("dot2", cyl);
        temp.move(offset, offset, DISK_HEIGHT);
        temp.setMaterial(dotMaterial);
        super.attachChild(temp);
        
        temp = new Geometry("dot3", cyl);
        temp.move(offset, -offset, DISK_HEIGHT);
        temp.setMaterial(dotMaterial);
        super.attachChild(temp);
        
        temp = new Geometry("dot4", cyl);
        temp.move(-offset, -offset, DISK_HEIGHT);
        temp.setMaterial(dotMaterial);
        super.attachChild(temp);
        
        temp = new Geometry("dot5", cyl);
        temp.move(-offset, offset, DISK_HEIGHT);
        temp.setMaterial(dotMaterial);
        super.attachChild(temp);
    }
    
    @Override
    public int reward() {
        if(pointsLeft == 0){
            return 0;
        }else {
            super.detachChildNamed("dot" + pointsLeft);
            return pointsLeft--;
        }
    }
    
    @Override
    public Vector3f getAcceleration(){
        return Vector3f.ZERO;
    }
    
}
