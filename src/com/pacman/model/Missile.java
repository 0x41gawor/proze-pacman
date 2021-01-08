package com.pacman.model;

import com.pacman.config.Config;
import com.pacman.util.Vector;

import java.awt.*;

/**
 * Class representing fired missile
 */
public class Missile extends Rectangle {
    /**
     * Position
     */
    Vector<java.lang.Double> pos;
    /**
     * Tells direction in which missile moves
     *
     * Same mechanics as in the player
     */
    Vector<java.lang.Integer> movement;
    /**
     * Movement speed of missile expressed in GRIDS per seconds [GRID/s]
     */
    Vector<java.lang.Double> movementSpeed;

    /**
     * Constructor
     *
     * While constructing missile we need to pass it:
     * - player position
     * - size of missile
     * - direction in which missile is fired
     */
    public Missile(Vector<java.lang.Double> pos, int width, int height, Ghost.Direction dir) {
        super(pos.x.intValue(), pos.y.intValue(), width, height);
        this.pos = pos;
        movement = new Vector<Integer>(0,0);
        movementSpeed = new Vector<java.lang.Double>((double)Config.BULLET_SPEED* Config.GRID_X,(double)Config.BULLET_SPEED* Config.GRID_Y);
        switch (dir) {
            case LEFT -> {
                movement.x = -1;
                movement.y = 0;
            }
            case RIGHT -> {
                movement.x = 1;
                movement.y = 0;
            }
            case UP -> {
                movement.x = 0;
                movement.y = -1;
            }
            case DOWN -> {
                movement.x = 0;
                movement.y = 1;
            }
        }
    }
    /**
     * Update a missile positions
     *
     * Should be called in Gun._update
     */
    public void _update(double dt) {
        pos.x += movement.x * movementSpeed.x * dt;
        pos.y += movement.y * movementSpeed.y * dt;
    }
    /**
     * Draw a missile using given Graphics
     *
     * Should be called in Gun.draw
     */
    public void draw(Graphics g) {
        g.setColor(Color.cyan);
        x = pos.x.intValue()-width/2;
        y = pos.y.intValue()-height/2;
        g.fillOval(x,y,width,height);
    }
    /**
     * Returns true if missile intersects with given hitBox
     */
    public boolean checkCollision(Rectangle hitBox) {
        return this.intersects(hitBox);
    }
    /**
     * pos setter used in resizeHandler
     */
    public void set_pos(Vector<java.lang.Double> pos) {
        this.pos = pos;
    }
    /**
     * movementSpeed setter used in resizeHandler
     */
    public void set_movementSpeed(Vector<java.lang.Double> movementSpeed) {
        this.movementSpeed = movementSpeed;
    }
    /**
     * pos getter used in resizeHandler
     */
    public Vector<java.lang.Double> get_pos() { return pos; }
}
