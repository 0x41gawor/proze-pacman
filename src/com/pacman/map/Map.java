package com.pacman.map;

import com.pacman.config.Config;

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
     Default constructor
     */
    public Map(){
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


            // <   temporarily here >
            System.out.print("Positions: ");
            for (String pos : positions){
                System.out.print(pos + " ");
            }
            // < / temporarily here >

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
        }
    }

    /**
     * Print mapArray as a matrix on the screen
     */
    public void showMapOnConsole(){
        System.out.println();
        System.out.println("Map loaded from file:");
        int x = 0;
        int y = 0;
        for (y=0; y<Config.MAP_SIZE_Y;y++){
            for(x=0; x<Config.MAP_SIZE_X;x++)
                System.out.print(mapArray[x][y] + " ");
            System.out.println("");
        }
    }
}
