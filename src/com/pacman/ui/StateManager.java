package com.pacman.ui;

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
            case 0 -> {
                frame.menu(thread);
            }
            case 1 -> {
                frame.game(thread);
            }
            default -> {
                System.exit(0);
            }
            }
    }
}
