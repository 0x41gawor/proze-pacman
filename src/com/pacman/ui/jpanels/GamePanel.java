package com.pacman.ui.jpanels;

import com.pacman.config.Config;
import com.pacman.game.GameLogic;
import com.pacman.map.Map;
import com.pacman.model.*;
import com.pacman.model.managers.CollectableManager;
import com.pacman.model.managers.GhostManager;
import com.pacman.model.managers.Gun;
import com.pacman.ui.StateManager;
import com.pacman.ui.util.Clock;
import com.pacman.util.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * JPanel presenting the gameplay
 *
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
    //Thread gameThread;
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
    /**
     GameLogic
     */
    GameLogic gameLogic;
    /**
     Lives
     */
    Lives lives;
    /**
     Score
     */
    Score score;
    /**
     Gun
     */
    Gun gun;
    /**
     if isGameOver is false it means game is not over, we can still play
     if game is over we need to distinguish if player won or lose the game
     thats why this member is not boolean
     */
    public enum GameState {FALSE, LOSE, WIN};
    public static GameState isGameOver;
    //------------------------------------------------------------------------------------------------------------------ C O N S T R U C T O R
    /**
     Default Constructor
     Sets JPanel and run the gameplay thread
     */
    public GamePanel(Thread thread, int nextLevel, int scoreFromPreviousLevel, int livesLeft) {
        // Setting up the Game
        screenSize = new Dimension(Config.WINDOW_SIZE_X, Config.WINDOW_SIZE_Y);
        clock = new Clock();
        isGameOver = GameState.FALSE;
        map = new Map(nextLevel);
        player = new Player(map.get_playerSpawnPosition(), Config.PLAYER_SIZE_X, Config.PLAYER_SIZE_Y, Config.PLAYER_MOVEMENT_SPEED_X, Config.PLAYER_MOVEMENT_SPEED_Y);
        ghostManager = new GhostManager(map);
        collectableManager = new CollectableManager(map);
        lives = new Lives(new Vector<>(0, 0), Config.GRID_X, Config.GRID_Y, livesLeft);
        score = new Score(new Vector<>(18 * Config.GRID_X, Config.GRID_Y / 2), 15, scoreFromPreviousLevel);
        gun = new Gun();
        gameLogic = new GameLogic(collectableManager, player, map, ghostManager, lives, score, gun);
        // Setting up JPanel
        this.setFocusable(true); //they say it is focusable by default
        this.addKeyListener(new KeyboardHandler());
        this.addComponentListener(new ResizeHandler());
        this.setPreferredSize(screenSize);

        // Starting thread for gameplay, this will fire run() method
        thread = new Thread(this);
        thread.start();
    }

    //------------------------------------------------------------------------------------------------------------------ G A M E   M A I N   L O O P
    /**
     Run method from Runnable interface
     Contains the GAME MAIN LOOP
     */
    @Override
    public void run() {
        double dt;
        while(isGameOver == GameState.FALSE) {
            dt = clock.restart();
            if(!isPause) _update(dt);
            repaint();
            sleep();
        }
        switch(isGameOver) {
            case WIN -> {
                StateManager.nextLevel+=1;
                StateManager.setScore(score.get_points());
                StateManager.lives = lives.get_lives() + 1;
                if(StateManager.nextLevel > StateManager.numberOfLevels) {
                    StateManager.nextLevel = 1;
                    StateManager.changeState(7);
                }
                else{
                    StateManager.changeState(1);
                }
            }
            case LOSE -> {
                StateManager.setScore(score.get_points()); StateManager.changeState(7);
                StateManager.lives = Config.LIVES;
            }
        }
    }
    /**
     Update the game.
     Compute new positions for graphic components, check collisions etc...
     Every object should have it's _update() method to be called here.
     */
    private void _update(double dt) {
        player._update(dt, map);
        ghostManager._update(dt, map);
        collectableManager._update(dt, map);
        gameLogic._update(dt);
        gun._update(dt, map);
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
        lives.draw(g);
        score.draw(g);
        gun.draw(g);
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
    /**
     * Set game into pause mode or resume the game
     */
    public void pause() {
        isPause = !isPause;
    }
    //------------------------------------------------------------------------------------------------------------------ A U X   S W I N G
    /**
     Swing framework calls this method for GameFrame in pack(),
     this way GameFrame knows what size to take
     */
    public Dimension getPreferredSize() {
        return screenSize;
    }
    /**
     Swing method
     We needed to add because new Panel need to be notified before
     handling keyboard and resized events.
     */
    @Override
    public void addNotify() {
        super.addNotify();
        this.requestFocusInWindow();
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
            else if (e.getKeyCode()==KeyEvent.VK_SPACE) {
                gameLogic.shoot();
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
            resizeLives();
            resizeScore(oldScreenSize);
            resizeGun(oldScreenSize);
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
            Config.PLAYER_MOVEMENT_SPEED_X = Config.PLAYER_MOVEMENT_SPEED   * Config.GRID_X;
            Config.PLAYER_MOVEMENT_SPEED_Y = Config.PLAYER_MOVEMENT_SPEED * Config.GRID_Y;
            Config.PLAYER_SIZE_X = (int)(0.8*Config.GRID_X);
            Config.PLAYER_SIZE_Y = (int)(0.8*Config.GRID_Y);
            Config.GHOST_MOVEMENT_SPEED_X = Config.GHOST_MOVEMENT_SPEED * Config.GRID_X;
            Config.GHOST_MOVEMENT_SPEED_Y = Config.GHOST_MOVEMENT_SPEED* Config.GRID_Y;
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
        /**
         * Resize collectable items
         */
        private void resizeCollectables(Dimension oldScreenSize) {
            collectableManager.resize(oldScreenSize,screenSize,map);
        }
        /**
         * Resize lives
         */
        private void  resizeLives() {
            lives.resize(screenSize);
        }
        /**
         * Resize score
         */
        private void resizeScore(Dimension oldScreenSize) {
            score.resize(screenSize, oldScreenSize);
        }
        private void resizeGun(Dimension oldScreenSize) {
            gun.resize(oldScreenSize,screenSize,map);
        }
    }
}
