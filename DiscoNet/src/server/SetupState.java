/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import api.DiskState;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import models.DiskImpl;
import models.GameConstants;
import models.PlayerDisk;
import models.SetupInitiater;

/**
 *
 * @author hannes
 */
public class SetupState extends BaseAppState{
    private ServerModule app;
    
    
    private final Random random = new Random();
    List<DiskImpl> disks;
    List<PlayerDisk> players;
    List<Integer> occupiedPositions;
    
    @Override
    protected void initialize(Application app) {
        this.app = (ServerModule) app;
    }
    
    /**
     * Creates a player disk and gives it a id.
     * returns the id.
     * @return 
     */
    public int initId(){
        int id = players.size();
        if (id >= GameConstants.MAX_PLAYERS){
            return -1;
        }
        // Get random pos for available positions
        int pos = random.nextInt(GameConstants.MAX_PLAYERS);
        while (occupiedPositions.contains(pos)){
            pos = random.nextInt(GameConstants.MAX_PLAYERS);
        }
        occupiedPositions.add(pos);
        
        Material m = SetupInitiater.setupMaterial(app.getAssetManager(), GameConstants.PLAYER_COLORS[id]);
        PlayerDisk p = new PlayerDisk(m, id);
        p.setLocalTranslation(GameConstants.PLAYER_POSITIONS[pos]);
        players.add(p);
        return id;
    }
    
    /**
     * returns all player disks as diskStates.
     * @return 
     */
    public synchronized List<DiskState> getPlayerDiskStates() {
        List<DiskState> diskStates = new ArrayList();
        for (DiskImpl disk: players) {
            diskStates.add(new DiskState(disk));
        }
        return diskStates;
    }
    
    /**
     * call this when about to change to playState.
     */
    public void getReadyToStart(){
        disks.addAll(SetupInitiater.getPassiveDisks(app.getAssetManager()));
        disks.addAll(players);
    }
    
    public List<DiskImpl> getDisks(){
        return disks;
    }

    @Override
    protected void cleanup(Application app) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * things that should happen everytime setupState starts
     */
    @Override
    protected void onEnable() {
        disks = new ArrayList();
        players = new ArrayList(); 
        occupiedPositions = new ArrayList();
        app.clearConnections();
    }

    /**
     * things that should happen everytime setupState ends (and playState begins)
     */
    @Override
    protected void onDisable() {
        getReadyToStart(); //TODO: check if supposed to be here or called from servermodule
    }
    
}
