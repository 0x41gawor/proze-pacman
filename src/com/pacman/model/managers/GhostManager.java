package com.pacman.model.managers;


import com.pacman.config.Config;
import com.pacman.map.Map;
import com.pacman.model.Ghost;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class used to manage ghost.
 *
 * Creates ghosts (randoms empty tile, random color)
 * Store ghost in a list.
 * Calls their update and draw methods.
 */
public class GhostManager {
    /**
     * List of ghosts
     */
    private final ArrayList<Ghost> ghostList;
    /**
     * Random generator for generating position, direction and color of ghost
     */
    Random random;
    /**
     * Array with available colors for ghosts
     */
    Color[] colors;
    /**
     * Constructor.
     *
     * Initializes members and creates ghosts with ghostFactory method.
     */
    public GhostManager(Map map) {
        // TODO Read number of ghosts from map
        int ghostNumber = 5;
        // Initialize members
        ghostList = new ArrayList<Ghost>();
        random = new Random();
        colors = new Color[]{Color.WHITE, Color.PINK, Color.CYAN, Color.GREEN, Color.RED, Color.MAGENTA, Color.YELLOW, Color.gray};
        // Add ghosts to the list
        for(int i=0; i<ghostNumber; i++) {
            ghostList.add(ghostFactory(map));
        }
    }
    /**
     * Used to create a single ghost.
     *
     * Random its position (checking if it is not a wall), direction and color.
     * <returns>Created ghost</returns>
     */
    private Ghost ghostFactory(Map map) {
        // Random ghost position
        int posX;
        int posY;
        do {
            posX = random.nextInt(Config.MAP_SIZE_X);
            posY = random.nextInt(Config.MAP_SIZE_Y);
        }while (map.getTile(posX,posY) != 0);
        // Random ghost color
        int sel = random.nextInt(colors.length);
        Color color = colors[sel];
        // Choose direction for Ghost
        Ghost.Direction dir;
        int[] dirs = {0, 0, 0, 0};
        dirs[0] = map.getTile(posX - 1, posY);     // left
        dirs[1] = map.getTile(posX + 1, posY);     // right
        dirs[2] = map.getTile(posX, posY - 1);     // up
        dirs[3] = map.getTile(posX, posY + 1);     // down
        int result;
        do {
            result = random.nextInt(4);
        } while (dirs[result] != 0);
        dir = switch (result) {
            case 0 -> Ghost.Direction.LEFT;
            case 1 -> Ghost.Direction.RIGHT;
            case 2 -> Ghost.Direction.UP;
            case 3 -> Ghost.Direction.DOWN;
            default -> throw new IllegalStateException("Unexpected value: " + result);
        };
        //Create ghost
        return new Ghost(Config.GRID_X*posX + Config.GRID_X/2, Config.GRID_Y*posX + Config.GRID_Y/2,
                Config.GHOST_SIZE_X,Config.GHOST_SIZE_Y, Config.GHOST_MOVEMENT_SPEED_X, Config.GHOST_MOVEMENT_SPEED_Y,
                dir, color);
    }
    /**
     * Calls _update for all ghost
     *
     * Should be called in GamePanel._update()
     */
    public void _update(double dt, Map map) {
        for (Ghost ghost: ghostList) {
            ghost._update(dt, map);
        }
    }
    /**
     * Calls draw for all ghost
     *
     * Should be called in GamePanel.draw()
     */
    public void draw(Graphics g) {
        for (Ghost ghost: ghostList) {
            ghost.draw(g);
        }
    }
    /**
     * Resize all ghost one by one
     *
     * Should be called in GamePanel.ResizeHandler.componentResized()
     */
    public void resize(Dimension oldScreenSize, Dimension screenSize) {
        for (Ghost ghost: ghostList) {
            ghost.setSize((int)((double)Config.GHOST_SIZE_X/(double)Config.WINDOW_SIZE_X * screenSize.width), (int)((double)Config.GHOST_SIZE_Y/(double)Config.WINDOW_SIZE_Y * screenSize.height));
            ghost.set_posX(ghost.get_posX() / (double)oldScreenSize.width * (double)screenSize.width);
            ghost.set_posY(ghost.get_posY() / (double)oldScreenSize.height * (double)screenSize.height);
            ghost.set_movementSpeedX( Config.GHOST_MOVEMENT_SPEED_X );
            ghost.set_movementSpeedY( Config.GHOST_MOVEMENT_SPEED_Y );
        }
    }
}
