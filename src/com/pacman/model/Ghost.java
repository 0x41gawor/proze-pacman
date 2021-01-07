package com.pacman.model;

import com.pacman.config.Config;
import com.pacman.map.Map;

import java.awt.*;
import java.util.Random;

/**
 * Class representing ghost
 */
public class Ghost extends Rectangle {
    /**
     Vertical Movement speed in pixels per second [px/s]
     Horizontal and Vertical speed may differ because of window resizing,
     User can resize the window not into a square shape
     */
    double movementSpeedX;
    /**
     Horizontal Movement speed in pixels per second [px/s]
     Horizontal and Vertical speed may differ because of window resizing,
     User can resize the window not into a square shape
     */
    double movementSpeedY;
    /**
     Position X
     */
    double posX;
    /**
     Position Y
     */
    double posY;
    /**
     Last position X where ghost changed dir
     */
    double posXprev;
    /**
     Last position Y where ghost changed dir
     */
    double posYprev;
    /**
     Direction in which ghost moves
     */
    public enum Direction {LEFT,RIGHT,UP,DOWN};
    Direction dir;
    /**
     Random generator for generating direction
     */
    Random random;
    /**
     Color of displayed ghost
     */
    Color color;

    //------------------------------------------------------------------------------------------------------------------ C O N S T R U C T O R
    /**
     Constructor
     */
    public Ghost(int posX, int posY, int width, int height, int movementSpeedX, int movementSpeedY, Direction dir, Color color) {
        super(posX,posY,width,height);
        this.posX = posX;
        this.posY = posY;
        this.posXprev = posX;
        this.posYprev = posY;
        this.movementSpeedX = movementSpeedX;
        this.movementSpeedY = movementSpeedY;
        this.dir = dir;
        this.color = color;
        random = new Random();
    }
    //------------------------------------------------------------------------------------------------------------------ U P D A T E
    /**
     * Update ghost positions
     * <p> How does it work? </p>
     * <p>
     *     At first frame ghost has some given Direction and simply moves towards it,
     *     within the next frames ghost checks if he has moved <code>GRID</code> pixels.
     *     Every <code>GRID</code> pixels, which mean every time ghost is on a new tile, ghost has
     *     an opportunity to change the direction.
     *     He scans area around him finding out the tile values up,down,left and right to his current tile.
     *     Then he randomizes a direction.
     *     Additionally ghost does not turn back until he finds a wall against him.
     * </p>
     */
    public void _update(double dt, Map map) {
        // If ghost entered new GRID, save his position for later checks and choose new dir.
        if(Math.abs(posX - posXprev) >= Config.GRID_X || Math.abs(posY - posYprev) >= Config.GRID_Y ) {
            posXprev = posX;
            posYprev = posY;
            _update_chooseDir(map);
        }
        // Move ghost
        _update_move(dt);
        // Set ghost at the centre of a tile
        _update_moveCorrection(map);
    }
    //------------------------------------------------------------------------------------------------------------------ D R A W
    /**
     * Draws ghost with given Graphics
     * Should be called in GamePanel.paint() in GhostManager.draw()
     */
    public void draw(Graphics g) {
        g.setColor(color);
        x = (int)posX-width/2;
        y = (int)posY-height/2;
        g.fillRect(x,y,width,height);
    }
    /**
     * Scans area around ghost and chooses direction.
     * The only not wall directions can be chosen.
     */
    private void _update_chooseDir(Map map) {
        // Get tile position of a ghost
        int[] pos = map.getTileCords(posX,posY);
        // Save tile values around the ghost
        int[] dirs = {0, 0, 0, 0};
        dirs[0] = map.getTile(pos[0] - 1, pos[1]);     // left
        dirs[1] = map.getTile(pos[0] + 1, pos[1]);     // right
        dirs[2] = map.getTile(pos[0], pos[1] - 1);     // up
        dirs[3] = map.getTile(pos[0], pos[1] + 1);     // down

        // Block opposite direction to the current one
        // Because we want ghost only to change direction after meeting a wall or a turn
        if(dir == Direction.LEFT) dirs[1] = 1;
        else if(dir == Direction.RIGHT) dirs[0]=1;
        else if(dir == Direction.UP) dirs[3]=1;
        else if(dir == Direction.DOWN) dirs[2]=1;

        // Check if by chance all direction are now blocked
        boolean allDirsAreOne = true;
        for(int i=0; i<4;i++) {
            if (dirs[i] == 0) {
                allDirsAreOne = false;
                break;
            }
        }
        // If yes we need to take the opposite direction
        // Because it could only happen if ghost is in the dead end
        if(allDirsAreOne){
            dir = switch (dir) {
                case LEFT  ->  Direction.RIGHT;
                case RIGHT ->   Direction.LEFT;
                case UP    ->   Direction.DOWN;
                case DOWN  ->   Direction.UP;
            };
        }
        else {
            int result;
            do {
                result = random.nextInt(4);
            } while (dirs[result] != 0);
            dir = switch (result) {
                case 0 -> Direction.LEFT;
                case 1 -> Direction.RIGHT;
                case 2 -> Direction.UP;
                case 3 -> Direction.DOWN;
                default -> throw new IllegalStateException("Unexpected value: " + result);
            };
        }
    }
    /**
     * Move ghost accordingly to the direction
     */
    private void _update_move(double dt) {
        switch (dir) {
            case LEFT  -> posX = posX - movementSpeedX * dt;
            case RIGHT -> posX = posX + movementSpeedX * dt;
            case UP    -> posY = posY - movementSpeedY * dt;
            case DOWN  -> posY = posY + movementSpeedY * dt;
        }
    }
    /**
     * Center ghost on a tile
     */
    private void _update_moveCorrection(Map map) {
        if(dir == Direction.UP || dir == Direction.DOWN){
            int[] pos = map.getTileCords(posX,posY);
            posX = pos[0] * Config.GRID_X + Config.GRID_X/2;
        }
        if(dir == Direction.LEFT || dir == Direction.RIGHT){
            int[] pos = map.getTileCords(posX,posY);
            posY = pos[1] * Config.GRID_Y + Config.GRID_Y/2;
        }
    }
    //------------------------------------------------------------------------------------------------------------------ G E T T E R S
    /**
     * posX getter used in resizeHandler
     */
    public double get_posX() { return posX; }
    /**
     * posY getter used in resizeHandler
     */
    public double get_posY() { return posY; }
    //------------------------------------------------------------------------------------------------------------------ S E T T E R S
    /**
     * posX setter used in resizeHandler
     */
    public void set_posX(double x) { this.posX = x; }
    /**
     * posX setter used in resizeHandler
     */
    public void set_posY(double y) { this.posY = y; }
    /**
     * movementSpeedX setter used in resizeHandler
     */
    public void set_movementSpeedX(int movementSpeedX) { this.movementSpeedX = movementSpeedX; }
    /**
     * movementSpeedY setter used in resizeHandler
     */
    public void set_movementSpeedY(int movementSpeedY) { this.movementSpeedY = movementSpeedY; }
}
