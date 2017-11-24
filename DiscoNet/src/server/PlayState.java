/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import api.DiskState;
import api.models.Disk;
import api.physics.CollisionDetector;
import api.physics.CollisionResult;
import api.physics.CollisionSolver;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import java.util.List;
import models.DiskConverter;
import models.DiskImpl;
import physics.CollisionDetectorImpl;
import physics.CollisionResultImpl;
import physics.CollisionSolverImpl;
import physics.DiskUpdate;

/**
 *
 * @author hannes
 */
public class PlayState extends BaseAppState {
    private ServerModule app;
    List<DiskImpl> disks;
    CollisionDetector collisionDetector;
    CollisionSolver collisionSolver;
    
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
        app.afterCollisions(collisions);
    }
    
    @Override
    protected void cleanup(Application app) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void onEnable() {
        disks = app.getInitDisks();
    }

    @Override
    protected void onDisable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
