package com.pacman.ui;

import com.pacman.config.Config;
import com.pacman.ui.util.Panel;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class MenuPanel extends Panel implements Runnable {

    Dimension screenSize;

    public MenuPanel(Thread thread) {
        screenSize = new Dimension(Config.WINDOW_SIZE_X, Config.WINDOW_SIZE_Y);
        this.setPreferredSize(screenSize);
        thread = new Thread(this);
        thread.start();
    }
    @Override
    public void run() {

            for(int i=0; i<5; i++) {
                System.out.println(i);
            }

            StateManager.changeState(1);
    }
}
