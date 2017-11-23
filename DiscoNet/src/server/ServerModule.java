/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import api.DiskState;
import api.DiskStateEmitter;
import api.DiskStateListener;
import api.GameStateEmitter;
import api.GameStateListener;
import api.IDProvider;
import api.IDRequester;
import api.MoveDirection;
import api.PlayerMoveEmitter;
import api.PlayerMoveListener;
import api.ScoreEmitter;
import api.ScoreListener;
import api.TimeEmitter;
import api.TimeListener;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import models.DiskImpl;
import models.GameConstants;
import models.PlayerDisk;
import models.SetupInitiater;

/**
 *
 * @author truls
 */
public class ServerModule extends SimpleApplication implements GameStateEmitter, DiskStateEmitter, ScoreEmitter, TimeEmitter, PlayerMoveListener, IDProvider{
    
    private final Random random = new Random();
    List<DiskImpl> disks;
    List<PlayerDisk> players;
    List<Integer> occupiedPositions = new ArrayList<>();
    
    public int initId(){
        int id = players.size();
        int pos = random.nextInt(9);
        while (occupiedPositions.contains(pos)){
            pos = random.nextInt(9);
        }
        occupiedPositions.add(pos);
        Material m = SetupInitiater.setupMaterial(assetManager, GameConstants.PLAYER_COLORS[id]);
        PlayerDisk p = new PlayerDisk(m, id);
        p.setLocalTranslation(GameConstants.PLAYER_POSITIONS[pos]);
        players.add(p);
        return id;
    }
    
    @Override
    public void addGameStateListener(GameStateListener gameStateListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addDiskStateListener(DiskStateListener diskStateListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addScoreListener(ScoreListener scoreListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTimeListener(TimeListener timeListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void simpleInitApp() {
        disks = new ArrayList();
        players = new ArrayList();
    }

    PlayerMoveListener getPlayerMoveListener() {
        return this;
    }

    @Override
    public void notifyPlayerMove(int diskID, MoveDirection direction, boolean isPressed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void requestID(IDRequester idr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<DiskState> getDiskStates() {
        List<DiskState> diskStates = new ArrayList();
        for (DiskImpl disk: disks) {
            diskStates.add(new DiskState(disk));
        }
        return diskStates;
    }
    
    public List<DiskState> getPlayerDiskStates() {
        List<DiskState> diskStates = new ArrayList();
        for (DiskImpl disk: players) {
            diskStates.add(new DiskState(disk));
        }
        return diskStates;
    }
    
    
}
