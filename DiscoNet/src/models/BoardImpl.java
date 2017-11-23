/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import api.models.Board;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author truls
 */
public class BoardImpl extends Node implements Board {
    
    // thickness of the sides of the frame
    public static final float FRAME_THICKNESS = 24f; 
    // width (and height) of the free area inside the frame, where disks move
    public static final float FREE_AREA_WIDTH = 492f / 2f; 
    public static final float FREE_AREA_THICKNESS = 10f;
    // total outer width (and height) of the frame
    public static final float FRAME_SIZE = FREE_AREA_WIDTH + 2f * FRAME_THICKNESS; 
    
    public static final String NAME = "BOARD";
    
    private final Geometry board;
    
    public BoardImpl(AssetManager assetManager) {
        board = new Geometry(NAME, new Box(FREE_AREA_WIDTH, FREE_AREA_WIDTH, FREE_AREA_THICKNESS));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        board.setMaterial(mat);
        board.setLocalTranslation(new Vector3f(0,0,FRAME_THICKNESS/2));
        super.attachChild(board);
        createFrame(assetManager);
    }
    
    private void createFrame(AssetManager assetManager){
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Brown);
        
        Box box = new Box(FRAME_SIZE, FRAME_THICKNESS, FRAME_THICKNESS);
        
        Geometry top = new Geometry("topFrame", box);
        top.move(new Vector3f(0f, FREE_AREA_WIDTH + FRAME_THICKNESS, 0f));
        top.setMaterial(mat);
        this.attachChild(top);
        
        Geometry bot = new Geometry("botFrame", box);
        bot.move(new Vector3f(0f, -FREE_AREA_WIDTH - FRAME_THICKNESS, 0f));
        bot.setMaterial(mat);
        this.attachChild(bot);
        
        Geometry left = new Geometry("leftFrame", box);
        left.rotate(0f, 0f, FastMath.PI/2.0f);
        left.move(new Vector3f(-FREE_AREA_WIDTH - FRAME_THICKNESS, 0f, 0f));
        left.setMaterial(mat);
        this.attachChild(left);
        
        Geometry right = new Geometry("leftFrame", box);
        right.rotate(0f, 0f, FastMath.PI/2.0f);
        right.move(new Vector3f(FREE_AREA_WIDTH + FRAME_THICKNESS, 0f, 0f));
        right.setMaterial(mat);
        this.attachChild(right);
    }

    @Override
    public float getBottomExtent() {
        return -FREE_AREA_WIDTH;
    }

    @Override
    public float getTopExtent() {
        return FREE_AREA_WIDTH;
    }

    @Override
    public float getLeftExtent() {
        return -FREE_AREA_WIDTH;
    }

    @Override
    public float getRightExtent() {
        return FREE_AREA_WIDTH;
    }
    
}
