/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import api.DiskState;
import api.DiskStateListener;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import models.BoardImpl;
import models.DiskImpl;
import models.GameConstants;
import models.PlayerDisk;
import models.SetupInitiater;

/**
 *
 * @author truls
 */
public class SetupState extends BaseAppState implements DiskStateListener{

    ClientModule app;
    List<DiskImpl> disks;
    Node board;
    
    @Override
    protected void initialize(Application app) {
        this.app = (ClientModule) app;
        disks = new ArrayList<>();
    }

    @Override
    protected void cleanup(Application app) {
        System.out.println("SetupState cleanup");
    }
    
    public List<DiskImpl> getInitiateDisks(){
        return disks;
    }
    
    @Override
    protected void onEnable() {
        System.out.println("SetupState enabled");
        Material mNeg = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Material mPos = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Material mDot = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mDot.setColor("Color", ColorRGBA.White);
        mNeg.setColor("Color", ColorRGBA.Red);
        mPos.setColor("Color", ColorRGBA.Green);
        disks = SetupInitiater.getDisks(mNeg, mPos, mDot);
        
        Node root = app.getRootNode();
        // Create empty board
        board = SetupInitiater.getBoard(app.getAssetManager());
        root.attachChild(board);
        
        // Set board invisible
        //board.setCullHint(Spatial.CullHint.Always);
        
        //Add key bindings
        app.getInputManager().addMapping("RequestStart", new KeyTrigger(KeyInput.KEY_RETURN));
        // Add them as listeners
        app.getInputManager().addListener(app.getActionListener(),"RequestStart");
            
    }

    @Override
    protected void onDisable() {
        // remove key mappings
        app.getInputManager().deleteMapping("RequestStart");
        System.out.println("SetupState disabled");
    }
    
    /**
     * Creates playerdisks from diskStates.
     * TODO: move to models package.
     * @param diskStates
     * @param materials
     * @return 
     */
    public static List<PlayerDisk> createPlayerDisks(List<DiskState> diskStates, List<Material> materials) {
        List<PlayerDisk> players = new ArrayList<>();
        for (int i = 0; i< diskStates.size(); i++) {
            DiskState player = diskStates.get(i);
            Material m = materials.get(i);
            m.setColor("Color", GameConstants.PLAYER_COLORS[player.getID()]);
            PlayerDisk p = new PlayerDisk(m, player.getID());
            p.setLocalTranslation(player.getPosition());
            p.setAcceleration(player.getAcceleration());
            p.setVelocity(player.getVelocity());
            players.add(p);
        }
        return players;
    }
    
    /**
     * only player diskStates
     * @param diskStates 
     */
    @Override
    public void notifyDiskState(List<DiskState> diskStates) {
        List<Material> materials = new ArrayList<>();
        for (int i = 0; i< diskStates.size(); i++){
            Material m = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            materials.add(m);
        }
        disks.addAll(createPlayerDisks(diskStates, materials));
        // Add all disks to board.
        for (DiskImpl disk : disks) {
            board.attachChild(disk);
            //disk.setLocalTranslation(0, 0, 100);
            
        }
        System.out.println(disks.size() + " Disks added to board.");
    }
    
}
