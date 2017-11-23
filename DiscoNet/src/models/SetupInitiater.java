/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author ted
 */
public class SetupInitiater {    
    
    static final float FREE_AREA_WIDTH = 492f / 2;
    static final float PLAYER_COORD = FREE_AREA_WIDTH / 6;
    static final float POSNEG_MAX_COORD = FREE_AREA_WIDTH / 3;
    static final float POSNEG_BETWEEN_COORD = PLAYER_COORD;

    static float[] positiveX = {-POSNEG_MAX_COORD, -POSNEG_MAX_COORD, -POSNEG_MAX_COORD, 0, POSNEG_MAX_COORD, POSNEG_MAX_COORD, POSNEG_MAX_COORD, 0};
    static float[] positiveY = {-POSNEG_MAX_COORD, 0, POSNEG_MAX_COORD, POSNEG_MAX_COORD, POSNEG_MAX_COORD, 0, -POSNEG_MAX_COORD, -POSNEG_MAX_COORD};

    static float[] negativeX = {-POSNEG_MAX_COORD, -POSNEG_MAX_COORD, -PLAYER_COORD, -PLAYER_COORD, PLAYER_COORD, PLAYER_COORD, POSNEG_MAX_COORD, POSNEG_MAX_COORD};
    static float[] negativeY = {-PLAYER_COORD, PLAYER_COORD, -POSNEG_MAX_COORD, POSNEG_MAX_COORD, -POSNEG_MAX_COORD, POSNEG_MAX_COORD, -PLAYER_COORD, PLAYER_COORD};
       
    public static BoardImpl getBoard(AssetManager asset){        
        return new BoardImpl(asset);
    }
    
    public static List<DiskImpl> getDisks(Material m, Material n){
        List<DiskImpl> list = new ArrayList<>();
        list.addAll(getNegative(m));
        list.addAll(getPositive(n));
        return list; 
    }
    
    private static List<DiskImpl> getNegative(Material m){
        List<DiskImpl> negDisks = new ArrayList<>();
        DiskImpl negDisk;  
        
        for(int i = 0; i < 8; i++){
            negDisk = new NegDisk(m, i);                
            negDisk.setLocalTranslation(negativeX[i], negativeY[i], 0);
            negDisks.add(negDisk);
        }
        return negDisks;
    }
    
    private static List<DiskImpl> getPositive(Material m){
        List<DiskImpl> pDisks = new ArrayList<>();
        DiskImpl pDisk;  
        
        for(int i = 0; i < 8; i++){
            pDisk = new NegDisk(m, i);                
            pDisk.setLocalTranslation(positiveX[i],positiveY[i], 0);
            pDisks.add(pDisk);
        }
        return pDisks;     
    }
    
}