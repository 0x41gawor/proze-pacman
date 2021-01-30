package com.pacman.ui;

import com.pacman.config.Config;
import com.pacman.ui.util.Panel;

import javax.swing.*;
import java.awt.*;

/**
 * JFrame class from Swing
 *
 * We use it to create a system window.
 */
public class GameFrame extends JFrame {

    Panel panel;
    /**
     Default Constructor
     Which as for now creates GamePanel() and the gameplay runs
     */
    public GameFrame() {
        this.setTitle(Config.TITLE);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(100,100));
        this.setLocationRelativeTo(null);
    }

    void game(Thread thread) {
        //panel = null;
        panel = new GamePanel(thread);
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }

    void menu(Thread thread) {
        //panel = null;
        panel = new MenuPanel(thread);
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }
}
