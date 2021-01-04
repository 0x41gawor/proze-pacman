package com.pacman.ui;

import com.pacman.config.Config;
import com.pacman.model.Player;
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
     Measure time between frames.
     Used to keep constant speed of objects independently from FPS.
     */
    Clock clock;

    //------------------------------------------------------------------------------------------------------------------ C O N S T R U C T O R
    /**
     Default Constructor
     Sets JPanel and run the gameplay thread
     */
    GamePanel() {
        // Setting up the Game
        screenSize = new Dimension(Config.WINDOW_SIZE_X, Config.WINDOW_SIZE_Y);
        player = new Player(100,100,Config.PLAYER_SIZE,Config.PLAYER_SIZE,Config.PLAYER_MOVEMENT_SPEED_X,Config.PLAYER_MOVEMENT_SPEED_Y);
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
            _update(dt);
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
        player._update(dt);
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

        // Objects
        // Every object should have it's draw() method called here
        player.draw(g);

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

    //------------------------------------------------------------------------------------------------------------------ N E S T E D   C L A S S E S
    /**
     * This class handles every keyboard input during the game.
     * KeyAdapter implements KeyListener interface.
     */
    public class KeyboardHandler extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
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
            screenSize = e.getComponent().getSize();
            System.out.println("Resized to " + screenSize);
        }
    }
}
