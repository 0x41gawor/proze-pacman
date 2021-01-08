package com.pacman.model;

import com.pacman.config.Config;
import com.pacman.map.Map;
import com.pacman.util.Vector;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Class representing pac-man
 * We called it player, because we want to mark that,
 * this object is controlled by the player.
 * And btw. name pac-man appears too much in this project.
 */
public class Player extends Rectangle {
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
     Possible values are -1,0,1: {0 - player does not move horizontally, -1 - player moves left, 1 - player moves right}
     */
    int movementX;
    /**
     Possible values are -1,0,1: {0 - player does not move vertically, -1 - player moves up, 1 - player moves down}
     */
    int movementY;
    /**
     Position X
     */
    double posX;
    /**
     Position Y
     */
    double posY;

    //------------------------------------------------------------------------------------------------------------------ C O N S T R U C T O R
    /**
     Constructor
     */
    public Player(Vector<Integer> pos, int width, int height, int movementSpeedX, int movementSpeedY) {
        super(pos.x*Config.GRID_X + Config.GRID_X/2,pos.y*Config.GRID_Y + Config.GRID_Y/2,width,height);
        set_GridPos(pos);
        this.movementSpeedX = movementSpeedX;
        this.movementSpeedY = movementSpeedY;
    }
    //------------------------------------------------------------------------------------------------------------------ D R A W
    /**
     * Draws player with given Graphics
     * Should be called in GamePanel.paint()
     */
    public void draw(Graphics g) {
        g.setColor(Color.orange);
        x = (int)posX-width/2;
        y = (int)posY-height/2;
        g.fillOval(x,y,(int)(width*0.8),(int)(height*0.8));
    }
    //------------------------------------------------------------------------------------------------------------------ U P D A T E
    /**
     * Update player positions
     * Should be called in GamePanel._update()
     */
    public void _update(double dt, Map map) {
        ///TODO Improve collision!!!
        int[] tileCords = map.getTileCords(get_posX(), get_posY());
        int tileCordX = tileCords[0];
        int tileCordY = tileCords[1];
        boolean leftBanned = false;
        boolean rightBanned = false;
        boolean upBanned = false;
        boolean downBanned = false;
        // S I M P L E   C O L L I S I O N
        // In future move to method checkCollision
        x = (int)(posX + movementX * movementSpeedX * dt)-width/2;
        y = (int)(posY + movementY * movementSpeedY * dt)-height/2;
        // tileCordx-1, tileCordY ==== lewo
        if (map.getTile(tileCordX-1, tileCordY) == 1) {
            if(this.intersects(new Rectangle((tileCordX-1)* Config.GRID_X,tileCordY*Config.GRID_Y, Config.GRID_X, Config.GRID_Y))){
                leftBanned = true;
            }
        }
        // tileCordx+1, tileCordY ==== prawo
        if (map.getTile(tileCordX+1, tileCordY) == 1) {
            if(this.intersects(new Rectangle((tileCordX+1)* Config.GRID_X,tileCordY*Config.GRID_Y, Config.GRID_X, Config.GRID_Y))){
                rightBanned = true;
            }
        }
        // tileCordx, tileCordY-1 ==== góra
        if (map.getTile(tileCordX, tileCordY-1) == 1) {
            if(this.intersects(new Rectangle(tileCordX* Config.GRID_X,(tileCordY-1)*Config.GRID_Y, Config.GRID_X, Config.GRID_Y))){
                upBanned = true;
            }
        }
        // tileCordx, tileCordY+1 ==== dół
        if (map.getTile(tileCordX, tileCordY+1) == 1) {
            if(this.intersects(new Rectangle(tileCordX* Config.GRID_X,(tileCordY+1)*Config.GRID_Y, Config.GRID_X, Config.GRID_Y))){
                downBanned = true;
            }
        }
        if(movementX != -1 && !rightBanned || movementX !=1 && !leftBanned) {
            posX = posX + movementX * movementSpeedX * dt;
        }
        if(movementY != -1 && !downBanned || movementY !=1 && !upBanned) {
            posY = posY + movementY * movementSpeedY * dt;
        }
    }
    //------------------------------------------------------------------------------------------------------------------ K E Y   H A N D L E R
    /**
     * Set player direction depending on the keys pressed by the user
     * Should be called in GamePanel.InputHandler.keyPressed()
     */
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP) {
            movementY = -1;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN) {
            movementY = 1;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
            movementX = 1;
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT) {
            movementX = -1;
        }
    }
    /**
     * Brakes player movement in specific direction after specific key is released
     * Should be called in GamePanel.InputHandler.keyReleased()
     */
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && movementY == -1) {
            movementY = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN && movementY == 1) {
            movementY = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT && movementX == -1) {
            movementX = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT && movementX == 1) {
            movementX = 0;
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
    /**
     * Get player as Rectangle
     *
     * Used to check collision
     */
    public Rectangle getHitBox() {
        return new Rectangle(x,y,width,height);
    }
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
     * set grid position of player
     */
    public void set_GridPos(Vector<Integer> pos) {
        this.posX = Config.GRID_X * pos.x + Config.GRID_X*0.5;
        this.posY = Config.GRID_Y * pos.y + Config.GRID_Y*0.5;
    }
    /**
     * movementSpeedX setter used in resizeHandler
     */
    public void set_movementSpeedX(int movementSpeedX) { this.movementSpeedX = movementSpeedX; }
    /**
     * movementSpeedY setter used in resizeHandler
     */
    public void set_movementSpeedY(int movementSpeedY) { this.movementSpeedY = movementSpeedY; }
}
