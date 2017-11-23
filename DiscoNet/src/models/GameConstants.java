/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author hannes
 */
public class GameConstants {
    // thickness of the sides of the frame
    static final float FRAME_THICKNESS = 24f;
    // width (and height) of the free area inside the frame, where disks move
    static final float FREE_AREA_WIDTH = 492f; 
    // total outer width (and height) of the frame
    static final float FRAME_SIZE = FREE_AREA_WIDTH + 2f * FRAME_THICKNESS; 

    // next three constants define initial positions for disks
    static final float PLAYER_COORD = FREE_AREA_WIDTH / 6;
    static final float POSNEG_MAX_COORD = FREE_AREA_WIDTH / 3;
    static final float POSNEG_BETWEEN_COORD = PLAYER_COORD;
    
    static final float DISK_HEIGHT = FRAME_THICKNESS;

    static final float PLAYER_R = 20f; // radius of a player's disk
    static final float POSDISK_R = 16f; // radius of a positive disk
    static final float NEGDISK_R = 16f; // radius of a negative disk
    
    static final int MAX_PLAYERS = 9;
    
    // positions for the players.
    public static Vector3f[] PLAYER_POSITIONS = {new Vector3f(PLAYER_COORD, PLAYER_COORD, 0), new Vector3f(0, PLAYER_COORD, 0),
        new Vector3f( - PLAYER_COORD, PLAYER_COORD, 0), new Vector3f( - PLAYER_COORD, 0, 0), new Vector3f( - PLAYER_COORD, - PLAYER_COORD, 0),
        new Vector3f(0, - PLAYER_COORD, 0), new Vector3f(PLAYER_COORD, - PLAYER_COORD, 0), new Vector3f(PLAYER_COORD, 0, 0),
        new Vector3f(0, 0, 0)
    };
    
    // colors to distinguishe the players.
    public static final ColorRGBA[] PLAYER_COLORS = {ColorRGBA.Blue, ColorRGBA.Black, ColorRGBA.Yellow, 
        ColorRGBA.Orange, ColorRGBA.Cyan, ColorRGBA.DarkGray,
         ColorRGBA.Pink, ColorRGBA.Brown, ColorRGBA.LightGray
    };
}
