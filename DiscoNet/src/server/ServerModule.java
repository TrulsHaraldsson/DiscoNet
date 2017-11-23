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
import java.util.ArrayList;
import java.util.List;
import models.DiskImpl;
import models.PlayerDisk;

/**
 *
 * @author truls
 */
public class ServerModule extends SimpleApplication implements GameStateEmitter, DiskStateEmitter, ScoreEmitter, TimeEmitter, PlayerMoveListener, IDProvider{
    
    List<DiskImpl> disks;
    List<PlayerDisk> players;
    
    public int initId(){
        Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        int id = players.size();
        players.add(new PlayerDisk(m, id));
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
