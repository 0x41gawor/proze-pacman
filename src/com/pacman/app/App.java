package com.pacman.app;

import com.pacman.config.Config;
import com.pacman.ui.GameFrame;
import com.pacman.ui.StateManager;

import java.lang.reflect.InvocationTargetException;

/**
 * Main class for our app
 *
 * Entry point for the program.
 */
public class App {

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {

        Config.parseConfig("config.properties");
        StateManager stateManager = new StateManager();
        stateManager.changeState(1);


    }
}
