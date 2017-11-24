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
import api.PlayerMoveListener;
import api.ScoreEmitter;
import api.ScoreListener;
import api.TimeEmitter;
import api.TimeListener;
import api.physics.CollisionDetectionListener;
import api.physics.CollisionResult;
import com.jme3.app.SimpleApplication;
import java.util.ArrayList;
import java.util.List;
import models.DiskConverter;
import models.DiskImpl;
import models.PlayerDisk;
import network.ServerHandler;

/**
 *
 * @author truls
 */
public class ServerModule extends SimpleApplication implements GameStateEmitter, DiskStateEmitter, ScoreEmitter, 
        TimeEmitter, PlayerMoveListener, IDProvider {
    
    private final PlayState playState;
    //private final EndState endState;
    private final SetupState setupState;
    
    private ServerHandler server;
    
    public ServerModule(ServerHandler server){
        this.server = server;
        playState = new PlayState();
        setupState = new SetupState();
    }
    
    public int initId(){
        return setupState.initId();
    }
    
    public List<DiskImpl> getInitDisks(){
        return setupState.getDisks();
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
        playState.setEnabled(false);
        //endState.setEnabled(false);
        setupState.setEnabled(true);
        
        stateManager.attach(playState);
        //stateManager.attach(endState);
        stateManager.attach(setupState);
        
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

    public synchronized List<DiskState> getDiskStates() {
        return playState.getDiskStates();
    }
    
    public synchronized List<DiskState> getPlayerDiskStates(){
        return setupState.getPlayerDiskStates();
    }

    public void afterCollisions(List<CollisionResult> collisions) {
        final List<DiskImpl> disks = new ArrayList<>();
        for (CollisionResult collision : collisions) {
            disks.add((DiskImpl) collision.getFirst());
            disks.add((DiskImpl) collision.getSecond());
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("lol");
                server.broadcastDiskStates(DiskConverter.convertDisksToDiskStates(disks));
            }
        }).start();
        
    }
    
    
}
