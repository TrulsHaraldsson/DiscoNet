/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import api.DiskState;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ted
 */
public class SetupInitiater {    
    private static final Random RANDOM = new Random();
    private static final float INIT_VELCOITY_MULTIPLIER = 5f;
    
    static final float PLAYER_COORD = GameConstants.PLAYER_COORD;
    static final float POSNEG_MAX_COORD = GameConstants.POSNEG_MAX_COORD;
    static final float POSNEG_BETWEEN_COORD = GameConstants.POSNEG_BETWEEN_COORD;

    static float[] positiveX = {-POSNEG_MAX_COORD, -POSNEG_MAX_COORD, -POSNEG_MAX_COORD, 0, POSNEG_MAX_COORD, POSNEG_MAX_COORD, POSNEG_MAX_COORD, 0};
    static float[] positiveY = {-POSNEG_MAX_COORD, 0, POSNEG_MAX_COORD, POSNEG_MAX_COORD, POSNEG_MAX_COORD, 0, -POSNEG_MAX_COORD, -POSNEG_MAX_COORD};

    static float[] negativeX = {-POSNEG_MAX_COORD, -POSNEG_MAX_COORD, -PLAYER_COORD, -PLAYER_COORD, PLAYER_COORD, PLAYER_COORD, POSNEG_MAX_COORD, POSNEG_MAX_COORD};
    static float[] negativeY = {-PLAYER_COORD, PLAYER_COORD, -POSNEG_MAX_COORD, POSNEG_MAX_COORD, -POSNEG_MAX_COORD, POSNEG_MAX_COORD, -PLAYER_COORD, PLAYER_COORD};
       
    public static BoardImpl getBoard(AssetManager asset){        
        return new BoardImpl(asset);
    }
    
    public static Material setupMaterial(AssetManager assetManager, ColorRGBA color){
        /*Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        m.setColor("Color", color);
        return m;*/
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors",true);
        material.setColor("Diffuse", color);
        material.setFloat("Shininess", 64f);  // [0,128]
        material.setColor("Specular", color);
        return material;
    }
    
    /**
     * Creates playerdisks from diskStates.
     * @param diskStates
     * @param assetManager
     * @return 
     */
    public static List<PlayerDisk> createPlayerDisks(List<DiskState> diskStates, AssetManager assetManager) {
        List<PlayerDisk> players = new ArrayList<>();
        for (int i = 0; i< diskStates.size(); i++) {
            DiskState player = diskStates.get(i);
            System.out.println("ID = " + player.getID());
            Material m = setupMaterial(assetManager, GameConstants.PLAYER_COLORS[player.getID()]);
            PlayerDisk p = new PlayerDisk(m, player.getID());
            p.setLocalTranslation(player.getPosition());
            p.setAcceleration(player.getAcceleration());
            p.setVelocity(player.getVelocity());
            players.add(p);
        }
        return players;
    }
    
    public static List<DiskImpl> getPassiveDisks(AssetManager assetManager){
        Material negM = setupMaterial(assetManager, ColorRGBA.Red);
        Material posM = setupMaterial(assetManager, ColorRGBA.Green);
        Material dotM = setupMaterial(assetManager, ColorRGBA.White);
        List<DiskImpl> list = new ArrayList<>();
        list.addAll(getNegative(negM));
        list.addAll(getPositive(posM, dotM));
        return list; 
    }
    
    private static List<DiskImpl> getNegative(Material m){
        List<DiskImpl> negDisks = new ArrayList<>();
        DiskImpl negDisk;  
        
        for(int i = 0; i < 8; i++){
            negDisk = new NegDisk(m, i + GameConstants.MAX_PLAYERS);                
            negDisk.setLocalTranslation(negativeX[i], negativeY[i], 0);
            negDisks.add(negDisk);
        }
        return negDisks;
    }
    
    private static List<DiskImpl> getPositive(Material diskM, Material dotM){
        List<DiskImpl> pDisks = new ArrayList<>();
        DiskImpl pDisk;  
        
        for(int i = 0; i < 8; i++){
            pDisk = new PosDisk(diskM, dotM, i + GameConstants.MAX_PLAYERS + negativeX.length);                
            pDisk.setLocalTranslation(positiveX[i],positiveY[i], 0);
            pDisks.add(pDisk);
        }
        return pDisks;     
    }
    
    public static void setInitSpeed(DiskImpl disk){
        float xInit = (RANDOM.nextFloat() * 2 - 1) * INIT_VELCOITY_MULTIPLIER;
        float yInit = (RANDOM.nextFloat() * 2 - 1) * INIT_VELCOITY_MULTIPLIER;
        
        disk.setVelocity(new Vector3f(xInit, yInit, 0));
    }
    
}
