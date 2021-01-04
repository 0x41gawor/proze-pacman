package com.pacman.model;

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
    /**
     Default Constructor
     */
    public Player(int posX, int posY, int width, int height, int movementSpeedX, int movementSpeedY) {
        super(posX,posY,width,height);
        this.posX = posX;
        this.posY = posY;
        this.movementSpeedX = movementSpeedX;
        this.movementSpeedY = movementSpeedY;
    }
    /**
     * Draws player with given Graphics
     * Should be called in GamePanel.paint()
     */
    public void draw(Graphics g) {
        g.setColor(Color.green);
        g.fillRect((int)posX,(int)posY,width,height);
    }
    /**
     * Update player positions
     * Should be called in GamePanel.paint()
     */
    public void _update(double dt) {
        posX = posX + movementX * movementSpeedX * dt;
        posY = posY + movementY * movementSpeedY * dt;

    }
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
        if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            movementY = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
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
