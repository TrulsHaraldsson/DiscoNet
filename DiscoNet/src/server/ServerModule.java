/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import api.DiskState;
import api.DiskStateEmitter;
import api.DiskStateListener;
import api.GameState;
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
import com.jme3.app.SimpleApplication;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import models.DiskConverter;
import models.DiskImpl;
import models.PlayerDisk;

/**
 *
 * @author truls
 */
public class ServerModule extends SimpleApplication implements GameStateEmitter, DiskStateEmitter, ScoreEmitter, 
        TimeEmitter, PlayerMoveListener, IDProvider {
    
    private final List<DiskStateListener> diskStateListeners = new ArrayList<>();
    
    private final PlayState playState;
    //private final EndState endState;
    private final SetupState setupState;
    
    
    public ServerModule(){
        playState = new PlayState();
        setupState = new SetupState();
    }
    
    private ArrayList<Integer> connections = new ArrayList<>();
    private ArrayList<Integer> ready = new ArrayList<>();
    
    private GameStateListener gameStateListener;
    
    /**
     * Creates a player disk and gives it a id.
     * returns the id.
     * @return 
     */
    public int initId(){
        return setupState.initId();
    }
    
    public List<DiskImpl> getInitDisks(){
        return setupState.getDisks();
    }
    
    @Override
    public void addGameStateListener(GameStateListener gameStateListener) {
        this.gameStateListener = gameStateListener;
    }

    @Override
    public void addDiskStateListener(DiskStateListener diskStateListener) {
        this.diskStateListeners.add(diskStateListener);
    }
    
    public void notifyDiskStateListeners(List<DiskState> diskStates){
        for (DiskStateListener diskStateListener : diskStateListeners) {
            diskStateListener.notifyDiskState(diskStates);
        }
    }

    @Override
    public void addScoreListener(ScoreListener scoreListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTimeListener(TimeListener timeListener) {
        playState.addTimeListener(timeListener);
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
        PlayerDisk disk = (PlayerDisk)this.playState.getDisk(diskID);
        disk.accelerate(direction, isPressed); 
        notifyDiskStateListeners(DiskConverter.convertDisksToDiskStates(new ArrayList<DiskImpl> ((Collection<? extends DiskImpl>) disk)));
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

    public synchronized boolean isPlaying(){
        return playState.isEnabled();
    }
    
    public void afterCollisions(final List<DiskImpl> disks) {
        notifyDiskStateListeners(DiskConverter.convertDisksToDiskStates(disks));   
    }
    
    public void onPlayerReady(int id){
        if(!ready.contains(id)){
            System.out.println("Player ready " + id);
            ready.add(id);
        }
        
        if(ready.size() == connections.size()){
            setupState.setEnabled(false);
            playState.setEnabled(true);
            ready.clear();
            gameStateListener.notifyGameState(GameState.PLAY);
        }
    }
    
    public void onPlayerJoined(int id){
        if(!connections.contains(id)){
            System.out.println("Player joined id " + id);
            connections.add(id);
        }
    }
    
    
}
