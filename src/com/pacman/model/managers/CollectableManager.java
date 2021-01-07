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
        // Create Cup
        //TODO hardcoded values !!!
        collectableList.add(collectableFactory(Collectable.Type.CUP, 9, 9));
        // Create Dots
        //TODO hardcoded values !!!
        collectableList.add(collectableFactory(Collectable.Type.DOT, 5, 1));
        collectableList.add(collectableFactory(Collectable.Type.DOT, 19, 17));
        collectableList.add(collectableFactory(Collectable.Type.DOT, 1, 17));
        collectableList.add(collectableFactory(Collectable.Type.DOT, 19, 1));
        // Create Gun
        collectableList.add(collectableFactory(Collectable.Type.GUN, map));
        // Create Cherries
        collectableList.add(collectableFactory(Collectable.Type.CHERRIES, map));
        // Create Berries
        collectableList.add(collectableFactory(Collectable.Type.BERRIES, map));
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
    public void _update(double dt){

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
