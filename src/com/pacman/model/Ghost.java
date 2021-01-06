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

    int[] lastPosChangedDir = new int[2];

    /**
     Constructor
     */
    public Ghost(int posX, int posY, int width, int height, int movementSpeedX, int movementSpeedY, Direction dir) {
        super(posX,posY,width,height);
        this.posX = posX;
        this.posY = posY;
        this.movementSpeedX = movementSpeedX;
        this.movementSpeedY = movementSpeedY;
        this.dir = dir;
        random = new Random();
    }
    /**
     * Draws ghost with given Graphics
     * Should be called in GamePanel.paint() in GhostManager.draw()
     */
    public void draw(Graphics g) {
        g.setColor(Color.white);
        x = (int)posX-width/2;
        y = (int)posY-height/2;
        g.fillRect(x,y,width,height);
    }
    /**
     * Update ghost positions
     * Should be called in GamePanel._update() in GhostManager._update()
     */
    public void _update(double dt, Map map) {
        // Losujemy kierunek
        // Zapisujemy aktualną pozycję
        // Zaczynamy iść w danym kierunku
        // I sprawdzamy czy nie przeszczliśmy już GRID pixeli
        // Jeśli tak, to losujemy nowy kierunek

        //if(abs(posXnowy-posXstary > GRID || )
        if(Math.abs(posX - posXprev) >= Config.GRID_X || Math.abs(posY - posYprev) >= Config.GRID_Y ){
            posXprev = posX;
            posYprev = posY;

            int[] pos = map.getTileCords(posX,posY);
            int[] dirs = {0, 0, 0, 0};
            dirs[0] = map.getTile(pos[0] - 1, pos[1]);     // left
            dirs[1] = map.getTile(pos[0] + 1, pos[1]);     // right
            dirs[2] = map.getTile(pos[0], pos[1] - 1);     // up
            dirs[3] = map.getTile(pos[0], pos[1] + 1);     // down

            if(dir == Direction.LEFT) dirs[1] = 1;
            else if(dir == Direction.RIGHT) dirs[0]=1;
            else if(dir == Direction.UP) dirs[3]=1;
            else if(dir == Direction.DOWN) dirs[2]=1;

            boolean allDirsAreOne = true;
            for(int i=0; i<4;i++) {
                if(dirs[i] == 0 ) {
                    allDirsAreOne = false;
                }
            }
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
                switch (result) {
                    case 0 -> dir = Direction.LEFT;
                    case 1 -> dir = Direction.RIGHT;
                    case 2 -> dir = Direction.UP;
                    case 3 -> dir = Direction.DOWN;
                }
            }
        }
        // RUCH TO ZDROWIE
        switch (dir) {
            case LEFT  -> posX = posX - movementSpeedX * dt;
            case RIGHT -> posX = posX + movementSpeedX * dt;
            case UP    -> posY = posY - movementSpeedY * dt;
            case DOWN  -> posY = posY + movementSpeedY * dt;
        }
        // KOREKTRA RUCHU
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
