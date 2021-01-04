package com.pacman.app;

import com.pacman.config.Config;
import com.pacman.map.Map;
import com.pacman.ui.GameFrame;

/**
 * Main class for our app
 *
 * Entry point for the program.
 */
public class App {

    public static void main(String[] args) {

        Config.parseConfig("config.properties");
        GameFrame frame = new GameFrame();
    }
}
