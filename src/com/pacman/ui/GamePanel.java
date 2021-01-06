package com.pacman.ui;

import com.pacman.config.Config;
import com.pacman.map.Map;
import com.pacman.model.Player;
import com.pacman.model.managers.CollectableManager;
import com.pacman.model.managers.GhostManager;
import com.pacman.ui.util.Clock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * GamePanel class from Swing
 *
 * We use it as a container for graphics components during gameplay.
 * Here is the Main Game Loop.
 *
 * Class methods are divided in 4 parts:
 * - Constructor
 * - Game Main Loop
 *      - Containing run method and 3 steps of Game Main Loop -> update, repaint, sleep
 * - Aux Game
 *      - Auxiliary methods used by Game Main Loop methods
 * - Aux Swing
 *      - Auxiliary methods used by Swing framework
 * - Nested classes
 *      - In general EventListeners with easy access to this class members (that's why nested)
 *          - KeyboardHandler
 *          - ResizeHandler
 */
public class GamePanel extends JPanel implements Runnable {

    /**
     Size of a screen
     GameFrame uses pack() method so window size is same as this one.
     We use it also for computing resizing graphics components.
     */
    Dimension screenSize;
    /**
     Thread of gameplay.
     GamePanel constructor creates new thread for gameplay.
     And the GameFrame thread, the parent of this one continues to live alongside.
     */
    Thread gameThread;
    /**
     Object representing pac-man
     */
    Player player;
    /**
     Object representing map.
     */
    Map map;
    /**
     Measure time between frames.
     Used to keep constant speed of objects independently from FPS.
     */
    Clock clock;
    /**
     * Determines if the gameplay is paused
     */
    boolean isPause = false;
    /**
     GhostManager
     */
    GhostManager ghostManager;
    /**
     CollectableManager
     */
    CollectableManager collectableManager;

    //------------------------------------------------------------------------------------------------------------------ C O N S T R U C T O R
    /**
     Default Constructor
     Sets JPanel and run the gameplay thread
     */
    GamePanel() {
        // Setting up the Game
        screenSize = new Dimension(Config.WINDOW_SIZE_X, Config.WINDOW_SIZE_Y);
        player = new Player(Config.GRID_X*9 + Config.GRID_X/2,Config.GRID_Y + Config.GRID_Y/2,Config.PLAYER_SIZE_X,Config.PLAYER_SIZE_Y,Config.PLAYER_MOVEMENT_SPEED_X,Config.PLAYER_MOVEMENT_SPEED_Y);
        map = new Map();
        ghostManager = new GhostManager(map);
        collectableManager = new CollectableManager(map);
        // Setting up JPanel
        clock = new Clock();
        this.setFocusable(true); //they say it is focusable by default
        this.addKeyListener(new KeyboardHandler());
        this.addComponentListener(new ResizeHandler());
        this.setPreferredSize(screenSize);

        // Starting thread for gameplay, this will fire run() method
        gameThread = new Thread(this);
        gameThread.start();
    }
    //------------------------------------------------------------------------------------------------------------------ G A M E   M A I N   L O O P
    /**
     Run method from Runnable interface
     Contains the GAME MAIN LOOP
     */
    @Override
    public void run() {
        double dt;
        while(true) {
            dt = clock.restart();
            if(!isPause) _update(dt);
            repaint();
            sleep();
        }
    }
    /**
     Update the game.
     Compute new positions for graphic components, check collisions etc...
     Every object should have it's _update() method to be called here.
     */
    private void _update(double dt) {
        player._update(dt,map);
        ghostManager._update(dt,map);
    }
    /**
     Paint the frame.
     This method is called by Swing update() method, which we call in
     repaint() in GAME MAIN LOOP.
     Here painting all objects takes place.
     */
    public void paint(Graphics g) {
        // Background
        g.setColor(Color.black);
        g.fillRect(0,0,screenSize.width,screenSize.height);
        if(isPause) {
            g.setColor(Color.red);                              /// todo: change into inscription
            g.fillOval(200,200,200,200);
        }
        // Objects
        // Every object should have it's draw() method called here
        map.draw(g);
        player.draw(g);
        ghostManager.draw(g);
        collectableManager.draw(g);

        // IDK if that's necessary
        g.dispose();
    }
    /**
     Sleep between frames.
     This is preventing our game from CPU abusing.
     */
    private void sleep() {
        try {
            Thread.sleep((long)(1000.f/Config.FPS));
        } catch (InterruptedException ignored) {
        }
    }
    //------------------------------------------------------------------------------------------------------------------ A U X   G A M E
    //------------------------------------------------------------------------------------------------------------------ A U X   S W I N G
    /**
     Swing framework calls this method for GameFrame in pack(),
     this way GameFrame knows what size to take
     */
    public Dimension getPreferredSize() {
        return screenSize;
    }
    /**
     * Set game into pause mode or resume the game
     */
    public void pause() {
        isPause = !isPause;
    }
    //------------------------------------------------------------------------------------------------------------------ N E S T E D   C L A S S E S
    /**
     * This class handles every keyboard input during the game.
     * KeyAdapter implements KeyListener interface.
     */
    public class KeyboardHandler extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_P) {
                pause();
            }
            else {
                player.keyPressed(e);
            }
        }
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
    }
    /**
     * This class handles window resizing during gameplay.
     */
    public class ResizeHandler extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            Dimension oldScreenSize = screenSize;
            screenSize = e.getComponent().getSize();
            newConfigValues();
            resizePlayer(oldScreenSize);
            resizeGhosts(oldScreenSize);
            resizeCollectables(oldScreenSize);
        }
        /**
         * Resizing window means to resize GRID, and all things that depend on this
         * Because we need to keep constant ratio between WINDOW_SIZE and GRID (Which is btw. MAP_SIZE)
         */
        private void newConfigValues() {
            Config.WINDOW_SIZE_X = screenSize.width;
            Config.WINDOW_SIZE_Y = screenSize.height;
            Config.GRID_X = Config.WINDOW_SIZE_X / Config.MAP_SIZE_X;
            Config.GRID_Y = Config.WINDOW_SIZE_Y / Config.MAP_SIZE_Y;
            Config.PLAYER_MOVEMENT_SPEED_X = 6 * Config.GRID_X;
            Config.PLAYER_MOVEMENT_SPEED_Y = 6 * Config.GRID_Y;
            Config.PLAYER_SIZE_X = (int)(0.8*Config.GRID_X);
            Config.PLAYER_SIZE_Y = (int)(0.8*Config.GRID_Y);
            Config.GHOST_MOVEMENT_SPEED_X = 4 * Config.GRID_X;
            Config.GHOST_MOVEMENT_SPEED_Y = 4 * Config.GRID_Y;
            Config.GHOST_SIZE_X = (int)(0.8*Config.GRID_X);
            Config.GHOST_SIZE_Y = (int)(0.8*Config.GRID_Y);
            Config.COLLECTABLE_SIZE_X = Config.GRID_X * 3/5;
            Config.COLLECTABLE_SIZE_Y = Config.GRID_Y * 3/5;
        }
        /**
         * Resizing window means to resize PLAYER_SIZE and change his position
         * Because if player stands 100px from the left and user resize window to be double sized
         * now player should stand 200px from the left border.
         * Also player movementSpeed changes because player should always
         * need the same amount of time to cover the distance of the entire map
         */
        private void resizePlayer(Dimension oldScreenSize) {
            player.setSize((int)((double)Config.PLAYER_SIZE_X/(double)Config.WINDOW_SIZE_X * screenSize.width), (int)((double)Config.PLAYER_SIZE_Y/(double)Config.WINDOW_SIZE_Y * screenSize.height));
            player.set_posX(player.get_posX() / (double)oldScreenSize.width * (double)screenSize.width);
            player.set_posY(player.get_posY() / (double)oldScreenSize.height * (double)screenSize.height);
            player.set_movementSpeedX( Config.PLAYER_MOVEMENT_SPEED_X );
            player.set_movementSpeedY( Config.PLAYER_MOVEMENT_SPEED_Y );
        }
        /**
         * Resizing window means to resize GHOST_SIZE and change his position
         */
        private void resizeGhosts(Dimension oldScreenSize) {
            ghostManager.resize(oldScreenSize,screenSize);
        }
        private void resizeCollectables(Dimension oldScreenSize) {
            collectableManager.resize(oldScreenSize,screenSize,map);
        }
    }
}
