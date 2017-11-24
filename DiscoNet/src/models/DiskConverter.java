/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import api.DiskState;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hannes
 */
public class DiskConverter {
    public static List<DiskState> convertDisksToDiskStates(List<DiskImpl> disks){
        List<DiskState> diskStates = new ArrayList();
        for (DiskImpl disk: disks) {
            diskStates.add(new DiskState(disk));
        }
        return diskStates;
    }
}
