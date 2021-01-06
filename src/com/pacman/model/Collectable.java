package com.pacman.model;

import java.awt.*;

/**
 Class representing collectable item e.g. dot, gun, cherries ...
 */
public class Collectable extends Rectangle {
    /**
     Position X
     */
    double posX;
    /**
     Position Y
     */
    double posY;
    /**
     Type of collectable item
     */
    public enum Type {DOT, GUN, CHERRIES,BERRIES,CUP};
    Type type;
    /**
     Constructor
     */
    public Collectable (int posX, int posY, int width, int height, Type type ) {
        super(posX,posY,width,height);
        this.posX = posX;
        this.posY = posY;
        this.type = type;
    }
    /**
     Draw collectable item with given Graphics
     Should be called in GamePanel.paint() in CollectablesManager.draw()
     */
    public void draw(Graphics g) {
        Color color = switch(type) {
            case DOT -> Color.decode("#ffffff");
            case GUN -> Color.decode("#423f3f");
            case CHERRIES -> Color.decode("#8a1640");
            case BERRIES -> Color.decode("#4e1fb5");
            case CUP -> Color.decode("#ffff00");
        };
        g.setColor(color);
        x = (int)posX-width/2;
        y = (int)posY-height/2;
        g.fillOval(x,y,width,height);
    }
    /**
     Check collision with given rectangle
     <returns>True if hitBox intersects with this object</returns>
     */
    public boolean checkCollision(Rectangle hitBox) {
        return this.intersects(hitBox);
    }
    /**
     Return type of collectable item
     */
    public Type get_type() {
        return type;
    }
}