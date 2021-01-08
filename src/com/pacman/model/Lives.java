package com.pacman.model;

import com.pacman.config.Config;
import com.pacman.util.Vector;

import java.awt.*;
;
/**
 * Class drawing the remaining number of lives
 */
public class Lives {
    /**
     * Positions of top left corner of the the left heart
     */
    Vector<Integer> position;
    /**
     * Positions of hearts
     */
    int lives;
    /**
     * Width and height of hearts
     */
    int width;
    int height;
    /**
     * Constructor
     */
    public Lives (Vector<Integer> position,int  width,int height) {
        this.position = position;
        this.width = width;
        this.height = height;
        lives = Config.LIVES;
    }
    /**
     * Draw different number of hearts depending on value of parametr lives
     */
    public void draw(Graphics g) {
        g.setColor(Color.red);
        for (int i=0; i<lives; i++) {
            g.fillOval(position.x + i * Config.GRID_X, position.y, width-1, height-1);
        }
    }
    /**
     * Draw ine heart less
     */
    public void reduce(){
       this.lives--;
    }
    /**
     * Used in resizeHandler
     */
    public void resize(Dimension screenSize) {
         width =  Config.GRID_X;
         height =  Config.GRID_Y;
         System.out.println("Lives.resize width: " + width);
         System.out.println("Lives.resize height: " + height);
    }
}

