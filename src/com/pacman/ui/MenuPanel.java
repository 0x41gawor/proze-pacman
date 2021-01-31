package com.pacman.ui;

import com.pacman.config.Config;

import javax.swing.*;
import java.awt.*;
/**
 * JPanel presenting the menu
 */
public class MenuPanel extends JPanel implements Runnable {
    /**
     * Size of the screen
     */
    Dimension screenSize;
    /**
     Default Constructor
     Sets JPanel and run the menu thread
     */
    public MenuPanel(Thread thread) {
        screenSize = new Dimension(Config.WINDOW_SIZE_X, Config.WINDOW_SIZE_Y);
        this.setPreferredSize(screenSize);
        thread = new Thread(this);
        thread.start();
    }
    /**
     Run method from Runnable interface
     */
    @Override
    public void run() {

            for(int i=0; i<5; i++) {
                System.out.println(i);
            }

            StateManager.changeState(1);
    }
}
