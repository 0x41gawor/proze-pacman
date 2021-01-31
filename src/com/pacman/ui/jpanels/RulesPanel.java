package com.pacman.ui.jpanels;

import com.pacman.config.Config;
import com.pacman.image.Image;
import com.pacman.image.ImageFactory;
import com.pacman.ui.StateManager;
import com.pacman.ui.util.Clock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RulesPanel extends JPanel implements Runnable {
    /**
     * Size of the screen
     */
    Dimension screensize;
    /**
     * Determines if the enter is clicked
     */
    boolean isClicked ;
    /**
     * Represent background image
     */
    Image backgroundImage;
    /**
     Measure time between frames.
     Used to keep constant speed of objects independently from FPS.
     */
    Clock clock;
    /**
     * Deafault Constructor
     */
    public RulesPanel(Thread thread) {
        screensize = new Dimension(Config.WINDOW_SIZE_X, Config.WINDOW_SIZE_Y);
        this.setPreferredSize(screensize);
        clock = new Clock();
        backgroundImage = ImageFactory.getImage(ImageFactory.Images.RULES);
        isClicked = false;
        this.addKeyListener(new RulesPanel.KeyboardHandler());
        thread = new Thread(this);
        thread.start();
    }
    @Override
    public void run(){
        double dt;
        dt = clock.restart();
        System.out.println("costam");
        while(!isClicked) {
            _update(dt);
            repaint();
            sleep();
        }
        StateManager.changeState(0);
    }
    /**
     Update is empty .
     */
    private void _update(double dt){

        }
    /**
     Paint the frame.
     This method is called by Swing update() method, which we call in
     repaint() in MAIN LOOP.
     */
    public void paint(Graphics g) {
        backgroundImage.get_imageIcon().paintIcon(this, g, 0, 0);
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
    public class KeyboardHandler extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                isClicked = true;
            }
        }
    }
}
