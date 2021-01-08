package com.pacman.model.managers;

import com.pacman.config.Config;
import com.pacman.map.Map;
import com.pacman.model.Ghost;
import com.pacman.model.Missile;
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
    ArrayList<Missile> missileList;

    /**
     * Constructor
     */
    public Gun() {
        missileList = new ArrayList<Missile>();
    }
    /**
     * Updates position of fired missiles
     *
     * Should be called in GamePanel._update
     */
    public void _update(double dt, Map map) {
        for (Missile missile: missileList) {
            missile._update(dt);
            if(map.getTile(map.getTileCords(missile.get_pos().x, missile.get_pos().y)[0], map.getTileCords(missile.get_pos().x, missile.get_pos().y)[1])==1){
                missileList.remove(missile);
                break;
            }
        }
    }
    /**
     * Draw all  fired missiles
     *
     * Should be called in GamePanel.draw
     */
    public void draw(Graphics g) {
        for (Missile missile: missileList) {
            missile.draw(g);
        }
    }
    /**
     * Add new missile to list
     */
    public void fire(Vector<Double> pos, int width, int height, Ghost.Direction dir) {
        missileList.add(new Missile(pos, width, height, dir));
    }
    /**
     * Check if any missile collides with given hitBox if so return true and destroy missile that collided
     */
    public boolean checkCollision(Rectangle hitBox) {
        for (Missile missile: missileList) {
            if (missile.checkCollision(hitBox)) {
                // Remove missile after collision
                missileList.remove(missile);
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
        for (Missile missile: missileList) {
            missile.setSize((int)((double) Config.GRID_X/(double)Config.WINDOW_SIZE_X * screenSize.width)/10, (int)((double)Config.GRID_Y/(double)Config.WINDOW_SIZE_Y * screenSize.height)/10);
            missile.set_pos(new Vector<Double>(missile.get_pos().x / (double)oldScreenSize.width * (double)screenSize.width, missile.get_pos().y / (double)oldScreenSize.height * (double)screenSize.height ));
            missile.set_movementSpeed(new Vector<Double>((double)Config.BULLET_SPEED*Config.GRID_X, (double)Config.BULLET_SPEED*Config.GRID_Y));
        }
    }
}
