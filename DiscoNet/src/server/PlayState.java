/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import api.DiskState;
import api.TimeEmitter;
import api.TimeListener;
import api.models.Disk;
import api.physics.CollisionDetector;
import api.physics.CollisionResult;
import api.physics.CollisionSolver;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import java.util.ArrayList;
import java.util.List;
import models.DiskConverter;
import models.DiskImpl;
import models.PlayerDisk;
import physics.CollisionDetectorImpl;
import physics.CollisionResultImpl;
import physics.CollisionSolverImpl;
import physics.DiskUpdate;

/**
 *
 * @author hannes
 */
public class PlayState extends BaseAppState implements TimeEmitter{
    private ServerModule app;
    List<DiskImpl> disks;
    CollisionDetector collisionDetector;
    CollisionSolver collisionSolver;
    
    private TimeListener timeListener;
    private final float notifyGameTimeInterval = 1.0f;
    private float gameTime;
    private float timeSinceLastTimeUpdate;
    
    @Override
    protected void initialize(Application app) {
        this.app = (ServerModule) app;
        this.collisionDetector = new CollisionDetectorImpl();
        this.collisionSolver = new CollisionSolverImpl();
    }
    
    /**
     * returns all disks as diskStates.
     * @return 
     */
    public synchronized List<DiskState> getDiskStates() {
        return DiskConverter.convertDisksToDiskStates(disks);
    }

    @Override
    public void update(float tpf){
        DiskUpdate.updateDisks(disks, tpf);
        List<CollisionResult> collisions = collisionDetector.getCollisions((List<Disk>)(List<?>) disks, tpf);
        collisionSolver.resolveCollisions(collisions, tpf);
        // List that holds the disks that collided
        final List<DiskImpl> collidedDisks = new ArrayList<>();
        // Calculate the new points
        for (CollisionResult collision : collisions) {
            DiskImpl d1 = (DiskImpl) collision.getFirst();
            DiskImpl d2 = (DiskImpl) collision.getSecond();
            if (d1 instanceof PlayerDisk){
                ((PlayerDisk)d1).addPoints(d2.reward());
            } else if (d2 instanceof PlayerDisk){
                ((PlayerDisk)d2).addPoints(d1.reward());
            }
            disks.add(d1);
            disks.add(d2);
        }
        // notify network a collision occured
        app.afterCollisions(collidedDisks);
        
        // Updating game time
        gameTime -= tpf;
        
        timeSinceLastTimeUpdate += tpf;
        if(timeSinceLastTimeUpdate > notifyGameTimeInterval){
            timeListener.notifyTime(gameTime);
        }
        
    }
    
    @Override
    protected void cleanup(Application app) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void onEnable() {
        disks = app.getInitDisks();
        gameTime = 30.0f;
        timeSinceLastTimeUpdate = 1.0f;
    }

    @Override
    protected void onDisable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTimeListener(TimeListener timeListener) {
        this.timeListener = timeListener;
    }
    
}
