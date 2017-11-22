/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import api.models.Disk;
import api.physics.RigidBody;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Cylinder;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;

/**
 *
 * @author truls
 */
public abstract class DiskImpl extends Node implements Disk, RigidBody{
    
    protected static final float PLAYER_R = 20f; // radius of a player's disk
    protected static final float POSDISK_R = 16f; // radius of a positive disk
    protected static final float NEGDISK_R = 16f; // radius of a negative disk
    protected static final int DISK_HEIGHT = 1;
    private static final float MAX_SPEED = 300.0f;
    private static final float FRICTION = 0.9985f;
    
    private static int numberOfDisks = 0;
    
    private static final float DENSITY = 1.0f; 
    
    private final float mass;
    private final float radius;
    private final Geometry disk;
    
    private Vector3f velocity = new Vector3f(0.0f, 0.0f, 0.0f);
    
    public DiskImpl(float radius, Material material){
        super.setName("disk#" + numberOfDisks++);
        this.radius = radius;
        this.mass = radius * radius * FastMath.PI * DENSITY;
        Cylinder cyl = new Cylinder(32, 32, radius, DISK_HEIGHT, true);
        disk = new Geometry(super.getName() + "geometry", cyl);
        disk.setMaterial(material);
        super.attachChild(disk);
    }
    
    @Override
    public float getRadius() {
        return radius;
    }

    @Override
    public float getMass() {
        return mass;
    }

    @Override
    public void applyImpulse(Vector3f impulse) {
        velocity = impulse.scaleAdd(1.0f / getMass(), velocity);
        // Apply impulse relative to the mass of the object
        
        if(velocity.length() > MAX_SPEED){
            // Limit the velocity to MAX_SPEED
            velocity = velocity.normalize().scaleAdd(MAX_SPEED, Vector3f.ZERO);
        }
    }

    @Override
    public void integrate(float tpf) {
        Vector3f t = new Vector3f(velocity.x * tpf, velocity.y * tpf, 0);
        // Calculate translation 
        
        velocity.mult(FRICTION);
        // Simulate friction by reducing velocity by some fraction
        
        super.move(t);
        // Translate the position
    }

    @Override
    public Vector3f getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }
    
    @Override
    public void translate(Vector3f offset){
        super.move(offset);
    }
    
    @Override
    public Vector3f getCenterOfMass(){
        return this.getWorldTranslation();
    }
    
}