package com.pacman.game;

import com.pacman.map.Map;
import com.pacman.model.Collectable;
import com.pacman.model.Player;
import com.pacman.model.managers.CollectableManager;
import com.pacman.ui.GamePanel;
import com.pacman.util.Vector;


/**
 Class responsible for reacting to player colliding with
 collectable items and ghosts.

 Class detects such events and take actions
 linked to the game logic that has to be done after them.
 */
public class GameLogic {
    /**
     Class needs some reference to GamePanel.collectableManager
     */
    CollectableManager collectableManager;
    /**
     Class needs some reference to GamePanel.player
     */
    Player player;
    /**
     Class needs some reference to GamePanel.map
     */
    Map map;
    /**
     Counter of dots collected by player.
     Class keeps track of it, because after player collects all dots
     we need to unlock the cup
     */
    int dotCounter;
    /**
     Number of dots to collect on single level.
     When dotCounter is equal to this number we need to unlock the cup.
     */
    int maxDotCounter;
    /**
     Position of cup.
     Class store this value, because it needs to destroy tiles around the cup.
     */
    Vector<Integer> cupPosition;
    /**
     Constructor
     */
    public GameLogic(CollectableManager collectableManager, Player player, Map map) {
        this.collectableManager = collectableManager;
        this.player = player;
        this.map = map;
        dotCounter = 0;
        maxDotCounter = map.get_maxDotCounter();
        cupPosition = map.get_cupPosition();
    }
    /**
     Checks collision with collectable items and ghost.
     Then take action if such event happens.
     */
    public void _update() {
        // Get type of collectable item that player collides in this frame
        // Class CollectableManager itself takes care of removing item after collision
        Collectable.Type type = collectableManager.checkCollision(player.getHitBox(),map);
        if(type!=null){
            switch (type) {
                case DOT -> {
                    System.out.println("GameLogic._update: DOT  collected");
                    // Increment number of collected dots
                    // Unlock the cup if all Dots are collected
                    if(++dotCounter>=maxDotCounter) {
                        unlockTheCup();
                    }
                }
                case GUN -> System.out.println("GameLogic._update: GUN collected");
                case CHERRIES -> System.out.println("GameLogic._update: CHERRIES collected");
                case BERRIES -> System.out.println("GameLogic._update: BERRIES collected");
                case CUP -> {
                    System.out.println("GameLogic._update: CUP collected");
                    GamePanel.isGameOver = GamePanel.GameState.WIN;
                }
            }
        }
    }
    /**
    Sets mapArray around the cup to zeros.
     */
    private void unlockTheCup() {
        map.setMapArray(cupPosition.x-1, cupPosition.y-2,0);
        map.setMapArray(cupPosition.x-1, cupPosition.y-1,0);
        map.setMapArray(cupPosition.x-1, cupPosition.y,0);
        map.setMapArray(cupPosition.x-1, cupPosition.y+1,0);
        map.setMapArray(cupPosition.x-1, cupPosition.y+2,0);
        map.setMapArray(cupPosition.x+1, cupPosition.y-2,0);
        map.setMapArray(cupPosition.x+1, cupPosition.y-1,0);
        map.setMapArray(cupPosition.x+1, cupPosition.y,0);
        map.setMapArray(cupPosition.x+1, cupPosition.y+1,0);
        map.setMapArray(cupPosition.x+1, cupPosition.y+2,0);
        map.setMapArray(cupPosition.x, cupPosition.y-2,0);
        map.setMapArray(cupPosition.x, cupPosition.y+2,0);
    }
}
