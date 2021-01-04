package com.pacman.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * <code>Config</code> class reads "config.properties" file and store its content is static fields, which gives
 * possibility to read config file content in any time in any place of code
 */
public class Config {
    /**
     Window title
     */
    public static String TITLE;
    /**
     Window width in pixels
     */
    public static int WINDOW_SIZE_X;
    /**
     Window height in pixels
     */
    public static int WINDOW_SIZE_Y;
    /**
     Width of map in tiles
     */
    public static int MAP_SIZE_X;
    /**
     Height of map in tiles
     */
    public static int MAP_SIZE_Y;
    /**
     Horizontal size of a single tile
     */
    public static int GRID_X;
    /**
     Vertical size of a single tile
     */
    public static int GRID_Y;
    /**
     Frames per second
     */
    public static float FPS;
    /**
     Pac-man size. Only one value because pac-man is circle shaped.
     */
    public static int PLAYER_SIZE;
    /**
     * player movement speed is expressed in px/s
     * In game as a speed reference we will use GRID size
     */
    public static int PLAYER_MOVEMENT_SPEED_X;
    public static int PLAYER_MOVEMENT_SPEED_Y;
    /**
     Default Constructor
     */
    private Config() {

    }

    /**
     Returns FileInputStream from file with given fileName
     @param fileName - name of  file
     @return FileInputStream
     */
    private static InputStream loadConfig(String fileName)  {
        InputStream is=null;
        try{
            is = new FileInputStream(fileName);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return is;
    }

    /**
     Parse config file content to Config class fields
     @param fileName - file to read config from
     */
    public static void parseConfig(String fileName) {
        Properties properties = new Properties();
        try{
            properties.load(loadConfig(fileName));
        } catch (Exception e){
            e.printStackTrace();
        }

        TITLE = properties.getProperty("TITLE");
        WINDOW_SIZE_X = Integer.parseInt(properties.getProperty("WINDOW_SIZE_X"));
        WINDOW_SIZE_Y = Integer.parseInt(properties.getProperty("WINDOW_SIZE_Y"));
        MAP_SIZE_X = Integer.parseInt(properties.getProperty("MAP_SIZE_X"));
        MAP_SIZE_Y = Integer.parseInt(properties.getProperty("MAP_SIZE_Y"));
        GRID_X = Integer.parseInt(properties.getProperty("GRID_X"));
        GRID_Y = Integer.parseInt(properties.getProperty("GRID_Y"));
        FPS = Float.parseFloat(properties.getProperty("FPS"));
        PLAYER_SIZE = Integer.parseInt(properties.getProperty("PLAYER_SIZE"));

        PLAYER_MOVEMENT_SPEED_X = 6*GRID_X;
        PLAYER_MOVEMENT_SPEED_Y = 6*GRID_Y;

    }
}
