package com.pacman.app;

import com.pacman.config.Config;
import com.pacman.ui.StateManager;

import java.lang.reflect.InvocationTargetException;

/**
 * Main class for our app
 *
 * Entry point for the program.
 */
public class App {

    public static void main(String[] args) {

        Config.parseConfig("config.properties");
        StateManager stateManager = new StateManager();
        StateManager.changeState(1);
    }
}
