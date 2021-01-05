package com.pacman.ui;

import com.pacman.config.Config;

import javax.swing.*;
import java.awt.*;

/**
 * JFrame class from Swing
 *
 * We use it to create a system window.
 */
public class GameFrame extends JFrame {

    GamePanel panel;
    /**
     Default Constructor
     Which as for now creates GamePanel() and the gameplay runs
     */
    public GameFrame() {
        panel = new GamePanel();
        this.add(panel);
        this.setTitle(Config.TITLE);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(100,100));
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
