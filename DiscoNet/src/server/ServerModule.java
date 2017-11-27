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
        TimeEmitter, TimeListener, PlayerMoveListener, IDProvider {
    
    private final List<DiskStateListener> diskStateListeners = new ArrayList<>();
    private final List<GameStateListener> gameStateListeners = new ArrayList<>();
    
    private ArrayList<Integer> connections = new ArrayList<>();
    private ArrayList<Integer> ready = new ArrayList<>();
    
    
    
    private final PlayState playState;
    private final EndState endState;
    private final SetupState setupState;
    
    
    public ServerModule(){
        playState = new PlayState();
        setupState = new SetupState();
        endState = new EndState();
    }
    
    
    public void clearConnections(){
        connections.clear();
        ready.clear();
    }
    
    /**
     * Creates a player disk and gives it a id.
     * returns the id.
     * @return 
     */
    public int initId(){
        if (setupState.isEnabled()) {
            return setupState.initId();
        } else {
            return -1;
        }
    }
    
    public List<DiskImpl> getInitDisks(){
        return setupState.getDisks();
    }
    
    private void changeGameState(GameState newState){
        switch(newState){
            case SETUP:
                playState.setEnabled(false);
                endState.setEnabled(false);
                setupState.setEnabled(true);
                break;
            case PLAY:
                endState.setEnabled(false);
                setupState.setEnabled(false);
                playState.setEnabled(true);
                break;
            case END:
                playState.setEnabled(false);
                setupState.setEnabled(false);
                endState.setEnabled(true);
                break;
            default:
                break;
        }
        notifyGameStateListeners(newState);
    }
    
    @Override
    public void addGameStateListener(GameStateListener gameStateListener) {
        this.gameStateListeners.add(gameStateListener);
    }
    
    public void notifyGameStateListeners(GameState gs){
        for (GameStateListener gameStateListener : gameStateListeners) {
            gameStateListener.notifyGameState(gs);
        }
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
        endState.setEnabled(false);
        setupState.setEnabled(true);
        
        stateManager.attach(playState);
        stateManager.attach(endState);
        stateManager.attach(setupState);
        
        
        playState.addTimeListener(this);
        
    }

    PlayerMoveListener getPlayerMoveListener() {
        return this;
    }

    @Override
    public void notifyPlayerMove(int diskID, MoveDirection direction, boolean isPressed) {
        PlayerDisk disk = (PlayerDisk)this.playState.getDisk(diskID);
        disk.accelerate(direction, isPressed); 
        //ArrayList<DiskImpl> list = new ArrayList<>();
        //list.add(disk);
        
        notifyDiskStateListeners(DiskConverter.convertDisksToDiskStates(this.playState.getDisks()));
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
        if (disks.size() > 0) {
            notifyDiskStateListeners(DiskConverter.convertDisksToDiskStates(this.playState.getDisks())); 
        }  
    }
    
    public void onPlayerReady(int id){
        if(!ready.contains(id)){
            System.out.println("Player ready " + id);
            ready.add(id);
        }
        
        if(ready.size() == connections.size()){
            changeGameState(GameState.PLAY);
            ready.clear();
        }
    }
    
    public void onPlayersReady(){
        changeGameState(GameState.PLAY);
    }
    
    public void onPlayerJoined(int id){
        if(!connections.contains(id)){
            System.out.println("Player joined id " + id);
            connections.add(id);
        }
    }
    

    @Override
    public void notifyTime(float time) {
        if (time <= 0) {
            changeGameState(GameState.END); // Currently does nothing.
            System.out.println("Time is over!");
            changeGameState(GameState.SETUP);
        }
    }
    
    
}
