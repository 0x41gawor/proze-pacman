package com.pacman.game;

import com.pacman.config.Config;
import com.pacman.map.Map;
import com.pacman.model.Collectable;
import com.pacman.model.Player;
import com.pacman.model.managers.CollectableManager;
import com.pacman.model.managers.GhostManager;
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
     Class needs some reference to GamePanel.ghostManager
     */
    GhostManager ghostManager;
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
     * How many points player scored
     */
    int score;
    /**
     * How many retries to complete level player have
     */
    int lives;
    /**
     * True if player is immortal
     */
    boolean isPlayerImmortal;
    /**
     * How long player is immortal
     *
     * Applies to immortality after respawn
     */
    double RESPAWN_IMMORTALITY_TIME;
    /**
     * How long player is immortal
     *
     * Applies to immortality after collecting berries
     */
    double BERRIES_IMMORTALITY_TIME;
    /**
     * Timer of player immortality
     *
     * Applies to both immortalities
     */
    double timerImmortality;
    /**
     Constructor
     */
    public GameLogic(CollectableManager collectableManager, Player player, Map map, GhostManager ghostManager) {
        this.collectableManager = collectableManager;
        this.player = player;
        this.map = map;
        this.ghostManager = ghostManager;
        dotCounter = 0;
        maxDotCounter = map.get_maxDotCounter();
        cupPosition = map.get_cupPosition();
        score = 0;
        lives = Config.LIVES;
        isPlayerImmortal = false;
        RESPAWN_IMMORTALITY_TIME = Config.RESPAWN_IMMORTALITY_TIME;
        BERRIES_IMMORTALITY_TIME = Config.BERRIES_IMMORTALITY_TIME;
        timerImmortality = 0;
    }
    /**
     Checks collision with collectable items and ghost.
     Then take action if such event happens.
     */
    public void _update(double dt) {
        // Get type of collectable item that player collides in this frame
        // Class CollectableManager itself takes care of removing item after collision
        Collectable.Type type = collectableManager.checkCollision(player.getHitBox(),map);
        if(type!=null){
            switch (type) {
                case DOT -> {
                    // Increment number of collected dots
                    // Unlock the cup if all Dots are collected
                    if(++dotCounter>=maxDotCounter) {
                        unlockTheCup();
                    }
                }
                case GUN -> System.out.println("GameLogic._update: GUN collected");
                case CHERRIES -> System.out.println("GameLogic._update: CHERRIES collected");
                case BERRIES -> {
                    // Set player immortality to true for a few seconds
                    isPlayerImmortal = true;
                    timerImmortality = BERRIES_IMMORTALITY_TIME;
                }
                case CUP -> {
                    // Change isGameOver to true (WIN state)
                    GamePanel.isGameOver = GamePanel.GameState.WIN;
                }
            }
        }
        // If player is immortal we need to count down the remaining time of immortality
        if (isPlayerImmortal) {
            if( (timerImmortality -= dt) < 0) {
                isPlayerImmortal = false;
            }
        }
        // Collision with ghosts
        if(ghostManager.checkCollision(player.getHitBox()) && !isPlayerImmortal) {
            System.out.println("GameLogic._update: Lives left: " + lives);
            // Set immortality for the moment after respawn
            isPlayerImmortal = true;
            timerImmortality = RESPAWN_IMMORTALITY_TIME;
            // Respawn player
            player.set_GridPos(map.get_playerSpawnPosition());
            // If no lives are left change isGameOver to true (LOSE state)
            if(--lives < 0) {
                GamePanel.isGameOver = GamePanel.GameState.LOSE;
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
