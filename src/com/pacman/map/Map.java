package com.pacman.map;

import com.pacman.config.Config;
import com.pacman.ui.StateManager;
import com.pacman.util.Vector;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <code>Map</code> class represents the maze loaded from file for specific level.
 *
 * Map uses 1 to store a tile being wall, and 0 as blank tile. Such data is stored
 * in 2-dimensional array.
 */
public class Map {
    /**
     2D array with 0 and 1 values used to store single tile type.
     */
    private final int[][] mapArray = new int[Config.MAP_SIZE_X][Config.MAP_SIZE_Y];
    /**
     Player spawn position
     */
    Vector<Integer> playerSpawnPosition;
    /**
    Cup position
     */
    Vector<Integer> cupPosition;
    /**
     Array list with dot positions
     */
    ArrayList<Vector<Integer>> dotPositions;
    /**
     * Number of ghosts
     */
    int ghostNumber;
    /**
     * Number of dots player needs to collect to unlock the cup
     */
    int maxDotCounter;
    /**
     Default constructor
     */
    public Map(int level){
        cupPosition = new Vector<Integer>(0,0);
        dotPositions = new ArrayList<>();
        ghostNumber = 0;
        maxDotCounter = 0;
        loadFromFile("levels/level" + level + ".txt");
    }
    /**
     * Loads map from file
     *
     * Also sets value of <code>positions</code> list, which we will use to store coordinates of balls.
     * As for now we don't use it and just temporarily print it on the screen.
     *
     * @param fileName - name of file, that stores the level
     */
    public void loadFromFile(String fileName) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            List<String> positions = new ArrayList<String>(Arrays.asList(line.split(" ")));
            // Assign player spawn position
            playerSpawnPosition = new Vector<Integer>(Integer.parseInt(positions.get(0)),Integer.parseInt(positions.get(1)));
            // Assign cup position
            cupPosition = new Vector<Integer>(Integer.parseInt(positions.get(2)),Integer.parseInt(positions.get(3)));
            // Assign dots positions
            for (int i = 4; i<positions.size()-1; i+=2) {
                dotPositions.add(new Vector<Integer>(Integer.parseInt(positions.get(i)),Integer.parseInt(positions.get(i+1))));
            }
            // Assign ghost number
            line = reader.readLine();
            ghostNumber = Integer.parseInt(line);
            // Assign maxDotCounter
            maxDotCounter = positions.size()/2-2;
            // Assign mapArray
            int x=0;
            int y=0;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                mapArray[x][y] = Integer.parseInt(line);
                x++;
                if (x > Config.MAP_SIZE_X - 1) {
                    x = 0;
                    y++;
                }
            }
        }
        catch(IOException e) {
                e.printStackTrace();
                System.out.println("No level file");
                StateManager.changeState(0);
        }
    }
    /**
     * Draws map.
     * Drawing map means to draw all of the map tiles being a wall.
     * Should be called in GamePanel.paint()
     *
     * @param g - Graphic context to draw on
     */
    public void draw(Graphics g) {
        g.setColor(Color.blue);
        for(int x = 0; x<Config.MAP_SIZE_X; x++) {
            for (int y = 0; y<Config.MAP_SIZE_Y; y++) {
                if(mapArray[x][y] == 1) {
                    g.fillRect(x*Config.GRID_X, y*Config.GRID_Y,Config.GRID_X-1, Config.GRID_Y-1);
                }
            }
        }
    }
    /**
     * Method name could also be get mapArray
     * Returns the tile (0 OR 1) with given pos in GRIDs
     */
    public int getTile(int x, int y) {
        if(x < 0 || x > Config.MAP_SIZE_X - 1 || y < 0 || y > Config.MAP_SIZE_Y - 1) return  1;
        return mapArray[x][y];
    }
    /**
     * Return GRID pos from given px pos.
     */
    public int[] getTileCords(double posX, double posY) {
        return new int[]{(int)posX/Config.GRID_X,(int)posY/Config.GRID_Y};
    }
    /**
     * Set tile of pos x,y as the given value
     */
    public void setMapArray(int x, int y, int value) {
       mapArray[x][y] = value;
    }
    /**
     Return vector with player spawn position
     */
    public Vector<Integer> get_playerSpawnPosition() {
        return playerSpawnPosition;
    }
    /**
     Return vector with cup position
     */
    public Vector<Integer> get_cupPosition() {
        return cupPosition;
    }
    /**
     Return ArrayList of dots positions
     */
    public  ArrayList<Vector<Integer>> get_dotPositions() {
        return dotPositions;
    }
    /**
     Return ghost number
     */
    public int get_ghostNumber() {
        return ghostNumber;
    }
    /**
     Return maxDotCounter
     */
    public int get_maxDotCounter() {
        return maxDotCounter;
    }
}
