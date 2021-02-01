package com.pacman.model;

import com.pacman.config.Config;
import com.pacman.util.Vector;

import java.awt.*;

public class Score {
    /**
     * Positions of top left corner of the the score
     */
    Vector<Integer> pos;
    /**
     * Positions of string
     */
    int points;
    /**
     * Size of dtring
     */
    int size;
    /**
     * Font of string
     */
    Font font;
    public Score (Vector<Integer> pos,  int size, int points) {
        this.pos = pos;
        this.size = size;
        font = new Font(Font.MONOSPACED, Font.BOLD, size);
        this.points = points;
    }
    /**
     * Draw score
     *
     * Should be called in GamePanel.draw
     */
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.setFont(font);
        g.drawString("Score : " + points, pos.x, pos.y);
    }
    /**
     * Add points to score
     */
    public void increment (int points) {
        this.points += points;
    }
    /**
     * Used in resizeHandler
     */
    public void resize(Dimension screenSize, Dimension oldScreenSize) {
        size = (int)((15.0/40.0*(double)Config.GRID_X)+(15.0/40.0*(double)Config.GRID_Y))/2;
        font = new Font(Font.MONOSPACED, Font.BOLD, size);
        pos.x = (Config.MAP_SIZE_X-3) * Config.GRID_X;
        pos.y = Config.GRID_Y/2;
    }
    /**
     * points getter
     */
    public int get_points() {
        return points;
    }
}
