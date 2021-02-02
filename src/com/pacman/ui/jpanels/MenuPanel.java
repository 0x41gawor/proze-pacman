package com.pacman.ui.jpanels;

import com.pacman.config.Config;
import com.pacman.image.Image;
import com.pacman.image.ImageFactory;
import com.pacman.ui.StateManager;
import com.pacman.ui.util.Clock;
import com.pacman.util.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * JPanel presenting the menu
 *
 *
 */
public class MenuPanel extends JPanel implements Runnable {
    /**
     * Size of the screen
     */
    Dimension screenSize;
    /**
     Decides whether user made his decision or not
     Initially is set as false, and while loop in run() method iterates,
     until user change its value to true by pressing enter
     */
    boolean isSelectionDone;
    /**
     Current option pointed with pointerImage
     */
    int selection;
    /**
     Number of menu options
     */
    int selectionMax;
    /**
    Measure time between frames.
    Used to keep constant speed of objects independently from FPS.
     */
    Clock clock;
    /**
     * Image representing the pac-man pointer
     */
    Image pointerImage;
    /**
     * Background image
     */
    Image backgroundImage;
    /**
     * Position of pointerImage
     */
    Vector<Integer> pointerImagePos;
    /**
     Default Constructor
     Sets JPanel and run the menu thread
     */
    public MenuPanel(Thread thread) {
        // Setting up the menu display and logic
        screenSize = new Dimension(Config.WINDOW_SIZE_X, Config.WINDOW_SIZE_Y);
        clock = new Clock();
        isSelectionDone = false;
        selection = 1;
        selectionMax = 5;
        backgroundImage = ImageFactory.getImage(ImageFactory.Images.MENU_BG);
        pointerImage = ImageFactory.getImage(ImageFactory.Images.MENU_POINTER);
        pointerImagePos = new Vector<Integer>(310,310);

        // Setting up JPanel
        this.setPreferredSize(screenSize);
        this.setFocusable(true); //they say it is focusable by default
        this.addKeyListener(new MenuPanel.KeyboardHandler());
        // Starting thread, this will fire run() method
        thread = new Thread(this);
        thread.start();
    }
    /**
     Run method from Runnable interface
     */
    @Override
    public void run() {
        double dt;
        while(!isSelectionDone) {
            dt = clock.restart();
            _update(dt);
            repaint();
            sleep();
        }
            int nextState = switch (selection) {
                case 1 -> 6;
                case 2 -> 2;
                case 3 -> 3;
                case 4 -> 4;
                default -> 5;
            };
        StateManager.changeState(nextState);
    }
    /**
     Update the displayed menu.
     Compute new positions for pointerImage etc.
     Every object should have it's _update() method to be called here.
     */
    private void _update(double dt) {
        switch (selection) {
            case 1 -> {
                pointerImagePos.x = 312;
                pointerImagePos.y = 399;
            }
            case 2 -> {
                pointerImagePos.x = 305;
                pointerImagePos.y = 438;
            }
            case 3 -> {
                pointerImagePos.x = 344;
                pointerImagePos.y = 476;
            }
            case 4 -> {
                pointerImagePos.x = 330;
                pointerImagePos.y = 510;
            }
            case 5 -> {
                pointerImagePos.x = 356;
                pointerImagePos.y = 548;
            }

        }
    }
    /**
     Paint the frame.
     This method is called by Swing update() method, which we call in
     repaint() in MAIN LOOP.
     Here painting all objects takes place.
     */
    public void paint(Graphics g) {
            backgroundImage.get_imageIcon().paintIcon(this, g, 0, 0);
            pointerImage.get_imageIcon().paintIcon(this, g, pointerImagePos.x, pointerImagePos.y);
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
    /**
     * This class handles every keyboard input.
     * It handles arrow keys and Enter key.
     * KeyAdapter implements KeyListener interface.
     */
    public class KeyboardHandler extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                isSelectionDone = true;
            }
            else {
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    selection --;
                    if(selection < 1) {
                        selection = selectionMax;
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    selection ++;
                    if(selection > selectionMax ) {
                        selection = 1;
                    }
                }
            }
        }
        public void keyReleased(KeyEvent e){
        }
    }
}
