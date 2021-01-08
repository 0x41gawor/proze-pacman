package com.pacman.model;

import com.pacman.config.Config;
import com.pacman.map.Map;
import com.pacman.util.Vector;

import java.awt.*;
import java.util.ArrayList;
/**
 * Gun could be also named MissileManager
 */
public class Gun {
    /**
     * List of fired missiles
     */
    ArrayList<Missile> missiles;

    /**
     * Constructor
     */
    Gun() {
        missiles = new ArrayList<Missile>();
    }
    /**
     * Updates position of fired missiles
     *
     * Should be called in GamePanel._update
     */
    public void _update(double dt) {
        for (Missile missile: missiles) {
            missile._update(dt);
        }
    }
    /**
     * Draw all  fired missiles
     *
     * Should be called in GamePanel.draw
     */
    public void draw(Graphics g) {
        for (Missile missile: missiles) {
            missile.draw(g);
        }
    }
    /**
     * Add new missile to list
     */
    public void fire(Vector<Double> pos, int width, int height, Ghost.Direction dir) {
        missiles.add(new Missile(pos, width, height, dir));
    }
    /**
     * Check if any missile collides with given hitBox if so return true and destroy missile that collided
     */
    public boolean checkCollision(Rectangle hitBox) {
        for (Missile missile: missiles) {
            if (missile.checkCollision(hitBox)) {
                // Remove missile after collision
                missiles.remove(missile);
                return true;
            }
        }
        return false;
    }
    /**
     * Resize fired missiles
     *
     * Used in resizeHandler
     */
    public void resize(Dimension oldScreenSize, Dimension screenSize, Map map) {
        for (Missile missile: missiles) {
            //TODO Change values
            missile.setSize((int)((double) Config.COLLECTABLE_SIZE_X/(double)Config.WINDOW_SIZE_X * screenSize.width), (int)((double)Config.COLLECTABLE_SIZE_Y/(double)Config.WINDOW_SIZE_Y * screenSize.height));
            missile.set_pos(new Vector<Double>(missile.get_pos().x / (double)oldScreenSize.width * (double)screenSize.width, missile.get_pos().y / (double)oldScreenSize.height * (double)screenSize.height ));
            //TODO hardcoded values!!! Add fields to Config class
            missile.set_movementSpeed(new Vector<Double>((double)6*Config.GRID_X, (double)6*Config.GRID_Y));
        }
    }
}
