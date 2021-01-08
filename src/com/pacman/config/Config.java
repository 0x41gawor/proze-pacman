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
     Pac-man size horizontally.
     */
    public static int PLAYER_SIZE_X;
    /**
     Pac-man size vertically.
     */
    public static int PLAYER_SIZE_Y;
    /**
     Ghost size horizontally.
     */
    public static int GHOST_SIZE_X;
    /**
     Ghost size vertically.
     */
    public static int GHOST_SIZE_Y;
    /**
     * Player movement speed is expressed in px/s
     * In game as a speed reference we will use GRID size
     */
    public static int PLAYER_MOVEMENT_SPEED_X;
    public static int PLAYER_MOVEMENT_SPEED_Y;
    /**
     * Ghost movement speed is expressed in px/s
     * In game as a speed reference we will use GRID size
     */
    public static int GHOST_MOVEMENT_SPEED_X;
    public static int GHOST_MOVEMENT_SPEED_Y;
    /**
     Ghost size horizontally.
     */
    public static int COLLECTABLE_SIZE_X;
    /**
     Ghost size vertically.
     */
    public static int COLLECTABLE_SIZE_Y;
    /**
     * Time between spawning next gun
     */
    public static double GUN_RESPAWN_TIME;
    /**
     * How long gun stays on map
     */
    public static double GUN_LIFE_TIME;
    /**
     * Time between spawning next cherries
     */
    public static double CHERRIES_RESPAWN_TIME;
    /**
     * Time between spawning next berries
     */
    public static double BERRIES_RESPAWN_TIME;
    /**
     * How long berries stays on map
     */
    public static double BERRIES_LIFE_TIME;
    /**
     * How many life player have on level
     */
    public  static int LIVES;
    /**
     * Player movement speeed as multiplier of GRID/s
     */
    public static int PLAYER_MOVEMENT_SPEED;
    /**
     * Player movement speeed as
     */
    public static int GHOST_MOVEMENT_SPEED;
    /**
     * How long player is immortal after collecting berries
     */
    public static double BERRIES_IMMORTALITY_TIME;
    /**
     * How long player is immortal after respawn
     */
    public static double RESPAWN_IMMORTALITY_TIME;
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

        // Read config values from file
        TITLE = properties.getProperty("TITLE");
        WINDOW_SIZE_X = Integer.parseInt(properties.getProperty("WINDOW_SIZE_X"));
        WINDOW_SIZE_Y = Integer.parseInt(properties.getProperty("WINDOW_SIZE_Y"));
        MAP_SIZE_X = Integer.parseInt(properties.getProperty("MAP_SIZE_X"));
        MAP_SIZE_Y = Integer.parseInt(properties.getProperty("MAP_SIZE_Y"));
        GRID_X = Integer.parseInt(properties.getProperty("GRID_X"));
        GRID_Y = Integer.parseInt(properties.getProperty("GRID_Y"));
        FPS = Float.parseFloat(properties.getProperty("FPS"));
        GUN_RESPAWN_TIME = Double.parseDouble(properties.getProperty("GUN_RESPAWN_TIME"));
        GUN_LIFE_TIME = Double.parseDouble(properties.getProperty("GUN_LIFE_TIME"));
        CHERRIES_RESPAWN_TIME = Double.parseDouble(properties.getProperty("CHERRIES_RESPAWN_TIME"));
        BERRIES_RESPAWN_TIME = Double.parseDouble(properties.getProperty("BERRIES_RESPAWN_TIME"));
        BERRIES_LIFE_TIME = Double.parseDouble(properties.getProperty("BERRIES_LIFE_TIME"));
        LIVES = Integer.parseInt(properties.getProperty("LIVES"));
        PLAYER_MOVEMENT_SPEED = Integer.parseInt(properties.getProperty("PLAYER_MOVEMENT_SPEED"));
        GHOST_MOVEMENT_SPEED = Integer.parseInt(properties.getProperty("GHOST_MOVEMENT_SPEED"));
        BERRIES_IMMORTALITY_TIME = Double.parseDouble(properties.getProperty("BERRIES_IMMORTALITY_TIME"));
        RESPAWN_IMMORTALITY_TIME = Double.parseDouble(properties.getProperty("RESPAWN_IMMORTALITY_TIME"));

        // Compute config values from the one read from file
        PLAYER_MOVEMENT_SPEED_X = PLAYER_MOVEMENT_SPEED*GRID_X;
        PLAYER_MOVEMENT_SPEED_Y = PLAYER_MOVEMENT_SPEED*GRID_Y;
        PLAYER_SIZE_X = GRID_X;
        PLAYER_SIZE_Y = GRID_Y;
        GHOST_MOVEMENT_SPEED_X = GHOST_MOVEMENT_SPEED* GRID_X;
        GHOST_MOVEMENT_SPEED_Y = GHOST_MOVEMENT_SPEED* GRID_Y;
        GHOST_SIZE_X = GRID_X * 4/5;
        GHOST_SIZE_Y = GRID_Y * 4/5;
        COLLECTABLE_SIZE_X = GRID_X * 3/5;
        COLLECTABLE_SIZE_Y = GRID_Y * 3/5;
    }
}
