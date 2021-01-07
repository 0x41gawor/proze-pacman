package com.pacman.model.managers;

import com.pacman.config.Config;
import com.pacman.map.Map;
import com.pacman.model.Collectable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class used to manage collectable items.
 *
 * Collectable items are {DOT, GUN, CHERRIES, BERRIES, CUP}
 *
 * Creates item.
 * Stores items in a list.
 * Calls it draw() method
 * Calls it checkCollision() method to detect
 * collision with player and take proper action.
 */
public class CollectableManager {
    /**
     * List of collectables
     */
    private final ArrayList<Collectable> collectableList;
    /**
     * Random generator for generating random position
     */
    Random random;
    /**
     * Time between spawning next gun
     */
    double GUN_RESPAWN_TIME;
    /**
     * How long gun stays on map
     */
    double GUN_LIFE_TIME;
    /**
     * Time between spawning next cherries
     */
    double CHERRIES_RESPAWN_TIME;
    /**
     * Time between spawning next berries
     */
    double BERRIES_RESPAWN_TIME;
    /**
     * How long berries stays on map
     */
    double BERRIES_LIFE_TIME;
    /**
     * Timer for cherries respawning
     */
    double timerCherries;
    /**
     * Timer for gun respawning
     */
    double timerGun;
    /**
     * Check if gun is spawned
     */
    boolean isRespawningGun;
    /**
     * Timer for berries respawning
     */
    double timerBerries;
    /**
     * Check if berries is spawned
     */
    boolean isRespawningBerries;
    /**
     * Constructor
     */
    public CollectableManager(Map map) {
        collectableList = new ArrayList<Collectable>();
        random = new Random();
        GUN_RESPAWN_TIME = Config.GUN_RESPAWN_TIME;
        GUN_LIFE_TIME = Config.GUN_LIFE_TIME;
        CHERRIES_RESPAWN_TIME = Config.CHERRIES_RESPAWN_TIME;
        BERRIES_RESPAWN_TIME = Config.BERRIES_RESPAWN_TIME;
        BERRIES_LIFE_TIME = Config.BERRIES_LIFE_TIME;
        timerCherries = 0.0;
        timerGun = 0.0;
        isRespawningGun = true;
        timerBerries = 0.0;
        isRespawningBerries = true;
        // Create Cup
        //TODO hardcoded values !!!
        collectableList.add(collectableFactory(Collectable.Type.CUP, 9, 9));
        // Create Dots
        //TODO hardcoded values !!!
        collectableList.add(collectableFactory(Collectable.Type.DOT, 5, 1));
        collectableList.add(collectableFactory(Collectable.Type.DOT, 19, 17));
        collectableList.add(collectableFactory(Collectable.Type.DOT, 1, 17));
        collectableList.add(collectableFactory(Collectable.Type.DOT, 19, 1));
        // Create Cherries
        collectableList.add(collectableFactory(Collectable.Type.CHERRIES, map));
    }
    /**
     * Creates collectable item with random position.
     * Used to create GUN, CHERRIES, BERRIES
     */
    public Collectable collectableFactory(Collectable.Type type, Map map) {
        // Random collectable position
        int posX;
        int posY;
        do {
            posX = random.nextInt(Config.MAP_SIZE_X);
            posY = random.nextInt(Config.MAP_SIZE_Y);
        }while (map.getTile(posX,posY) != 0);
        return new Collectable(posX, posY, Config.COLLECTABLE_SIZE_X, Config.COLLECTABLE_SIZE_Y, type);
    }
    /**
     * Creates collectable item with fixed position.
     * Used to create DOT, CUP
     */
    public Collectable collectableFactory(Collectable.Type type, int posX, int posY) {
        return new Collectable(posX,posY, Config.COLLECTABLE_SIZE_X, Config.COLLECTABLE_SIZE_Y,type);
    }
    /**
     * Update collectables list
     *
     * Should be called in GamePanel._update()
     */
    public void _update(double dt, Map map){
        // Cherries
        timerCherries += dt;
        if (timerCherries > CHERRIES_RESPAWN_TIME) {
            timerCherries = 0;
            remove(Collectable.Type.CHERRIES);
            collectableList.add(collectableFactory(Collectable.Type.CHERRIES, map));
        }
        // Gun
        timerGun += dt;
        if (isRespawningGun) {
            if(timerGun > GUN_RESPAWN_TIME) {
                timerGun = 0;
                collectableList.add(collectableFactory(Collectable.Type.GUN, map));
                isRespawningGun = false;
            }
        }
        else {
            if(timerGun > GUN_LIFE_TIME) {
                timerGun = 0;
                remove(Collectable.Type.GUN);
                isRespawningGun = true;
            }
        }
        // Berries
        timerBerries += dt;
        if (isRespawningBerries) {
            if(timerBerries > BERRIES_RESPAWN_TIME) {
                timerBerries = 0;
                collectableList.add(collectableFactory(Collectable.Type.BERRIES, map));
                isRespawningBerries = false;
            }
        }
        else {
            if(timerBerries > BERRIES_LIFE_TIME) {
                timerBerries = 0;
                remove(Collectable.Type.BERRIES);
                isRespawningBerries = true;
            }
        }
    }
    /**
     * Calls draw for all collectable items
     *
     * Should be called in GamePanel.draw()
     */
    public void draw(Graphics g) {
        for (Collectable collectable: collectableList) {
            collectable.draw(g);
        }
    }
    /**
     * Delete first appearance of element with given type in list
     */
    private void remove(Collectable.Type type) {
        int removeAt = 0;
        for (int i=0; i<collectableList.size(); i++) {
            if(collectableList.get(i).get_type()==type) {
             removeAt=i;
             break;
            }
        }
        collectableList.remove(removeAt);
    }
    /**
     * Resize all collectable items one by one
     *
     * Should be called in GamePanel.ResizeHandler.componentResized()
     */
    public void resize(Dimension oldScreenSize, Dimension screenSize, Map map) {
        for (Collectable collectable: collectableList) {
            collectable.setSize((int)((double)Config.COLLECTABLE_SIZE_X/(double)Config.WINDOW_SIZE_X * screenSize.width), (int)((double)Config.COLLECTABLE_SIZE_Y/(double)Config.WINDOW_SIZE_Y * screenSize.height));
        }
    }


}
