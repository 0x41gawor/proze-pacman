package com.pacman.ui.jpanels;

import com.pacman.config.Config;
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
     * Determines if the gameplay is paused
     */
    boolean isClicked = false;
    /**
     * Deafault Constructor
     */
    /**
     Measure time between frames.
     Used to keep constant speed of objects independently from FPS.
     */
    Clock clock;

    public RulesPanel(Thread thread) {
        screensize = new Dimension(Config.WINDOW_SIZE_X, Config.WINDOW_SIZE_Y)
        this.setPreferredSize();
        clock = new Clock();
        Image backgroundImage = ImageFactory.getImage(Images.Rules);
        backgroundLabel = new JLabel(backgroundImage.get_imageIcon());
        thread = new Thread(this)
        thread.start();
    }
    @Override
    public void run(){
        double dt;
        dt = clock.restart();
        while(!isClicked) {
            _update(dt);
            repaint();
            sleep();
        }
        StateManager.changeState(0);
    }

    private void _update(double dt){

        }

    private void paint(Graphics g) {
        backgroundImage.getImageIcon(this, g, 0, 0);
    }
    private void sleep() {
        try {
            Thread.sleep((long)(1000.f/Config.FPS));
        } catch (InterruptedException ignored) {
        }
    }

    public class KeyboardHandler extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                isClicked = true;
            }
        }

    }
}
