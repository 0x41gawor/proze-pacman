package com.pacman.ui;

/**
    Each game state is presented as different JPanel.
    States are:
        - menu
        - gameplay
        - highscore
        - credits
        - settings
        - quit

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
     Default constructor
     */
    public StateManager() {
        frame = new GameFrame();
        thread = new Thread();
    }
    /**
     Each JPanel has its run() method.
     When state is
     */
    public static void changeState(int sel) {
        switch (sel) {
            case 1 -> {
                frame.game(thread);
            }
            case 2 -> {
                frame.menu(thread);
            }
            case 0 -> {
                System.exit(0);
            }
            }
    }
}
