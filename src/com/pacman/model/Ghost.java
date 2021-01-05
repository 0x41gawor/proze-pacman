package com.pacman.model;

import com.pacman.map.Map;

import java.awt.*;
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
    /**
     Constructor
     */
    public Ghost(int posX, int posY, int width, int height, int movementSpeedX, int movementSpeedY) {
        super(posX,posY,width,height);
        this.posX = posX;
        this.posY = posY;
        this.movementSpeedX = movementSpeedX;
        this.movementSpeedY = movementSpeedY;
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
        //posX += 2.f;
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
