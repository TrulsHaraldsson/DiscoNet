/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import java.util.List;
import models.DiskImpl;

/**
 *
 * @author hannes
 */
public class DiskUpdate {
    public static void updateDisks(List<DiskImpl> disks, float tpf){
        for (DiskImpl disk : disks) {
            disk.integrate(tpf);
        }
        
    }
}
