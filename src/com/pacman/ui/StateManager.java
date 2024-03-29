package com.pacman.ui;

import com.pacman.config.Config;

/**
    Each game state is presented as different JPanel.
    States are:
        - menu 0
        - gameplay 1
        - highscores 2
        - rules 3
        - settings 4
        - quit 5
        - interlevel 6
        - enter nickname 7

    And StateManager manages transitions between them.
 */
public class StateManager {
    /**
     Swing Jframe in where game will be presented.
     */
    static GameFrame frame;
    /**
     Every JPanel need to be in the same thread.
     We cannot end one and start new thread each transitions,
     because event listeners are attached to a thread, not a Panel
     */
    static Thread thread;
    /**
     Score for the EnterNickNamePanel and GamePanel for the next level
     */
    static int score;
    /**
     * Number of levels
     */
    public static int numberOfLevels;
    /**
     * Next level that should be passed to GamePanel
     */
    public static int nextLevel;
    /**
     * Lives left for one game
     */
    public static int lives;

    /**
     Default constructor
     */
    public StateManager() {
        frame = new GameFrame();
        thread = new Thread();
        score = 0;
        nextLevel = 1;
        numberOfLevels = Config.NUMBER_OF_LEVELS;
        lives = Config.LIVES;
    }
    /**
     Each JPanel has its run() method.
     */
    public static void changeState(int sel) {
        switch (sel) {
            case 0 -> {
                frame.menu(thread);
            }
            case 1 -> {
                frame.game(thread, nextLevel, score, lives);
            }
            case 2 -> {
                frame.highscores(thread);
            }
            case 3 -> {
                frame.rules(thread);
            }
            case 4 -> {
                frame.settings();
            }
            case 6 -> {
                frame.interlevel(nextLevel);
            }
            case 7 -> {
                frame.enterNickName(score);
            }
            default -> {
                System.exit(0);
            }
            }
    }
    /**
     * Used to set score from GamePanel, that will be later displayed
     * in the EnterNickNamePanel
     *
     * @param sc - score passed from GamePanel to EnterNickNamePanel
     */
    public static void setScore(int sc) {
        score = sc;
    }

}
