package com.pacman.app;

import com.pacman.config.Config;
import com.pacman.map.Map;

/**
 * Main class for our app
 *
 * Entry point for the program.
 */
public class App {

    public static void main(String[] args) {
        Config.parseConfig("config.properties");

        Map maze = new Map();
        maze.loadFromFile("level1.txt");
        maze.showMapOnConsole();
    }
}
