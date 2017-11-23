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
    
    private static final float MAX_SPEED = 300.0f;
    private static final float FRICTION = 0.9985f;
    
    private static int numberOfDisks = 0;
    
    private static final float DENSITY = 1.0f; 
    
    private final float mass;
    private final float radius;
    private final Geometry disk;
    private final int id;
    
    private Vector3f velocity = new Vector3f(0.0f, 0.0f, 0.0f);
    protected Vector3f acceleration = new Vector3f(0.0f,0.0f, 0.0f);
    
    public DiskImpl(float radius, Material material, int id){
        super.setName("disk#" + numberOfDisks++);
        this.radius = radius;
        this.mass = radius * radius * FastMath.PI * DENSITY;
        this.id = id;
        Cylinder cyl = new Cylinder(32, 32, radius, GameConstants.DISK_HEIGHT, true);
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
        Vector3f dV = new Vector3f(acceleration.x * tpf, acceleration.y * tpf, 0);
        // Calculate a delta velocity based on acceleration
        
        velocity.addLocal(dV);
        // Add delta velocity to current velocity
        
        Vector3f t = new Vector3f(velocity.x * tpf, velocity.y * tpf, 0);
        // Calculate translation based on velocity
        
        velocity.mult(FRICTION);
        // Simulate friction by reducing velocity by some fraction
        
        super.move(t);
        // Translate the position
    }
    
    @Override
    public void translate(Vector3f offset){
        super.move(offset);
    }
    
    @Override
    public int getID() {
        return this.id;
    }
    
    @Override
    public Vector3f getPosition(){
        return this.getWorldTranslation();
    }
    
    @Override
    public void setPosition(Vector3f position){
        super.setLocalTranslation(position);
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
    public Vector3f getAcceleration(){
        return acceleration;
    }
    
    @Override
    public void setAcceleration(Vector3f acceleration){
        this.acceleration = acceleration;
    }
}
