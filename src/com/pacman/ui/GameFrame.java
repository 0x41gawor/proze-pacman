package com.pacman.ui;

import com.pacman.config.Config;
import com.pacman.ui.jpanels.*;
import com.pacman.ui.jpanels.InterLevelPanel;

import javax.swing.*;
import java.awt.*;

/**
 * JFrame class from Swing
 *
 * We use it to create a system window.
 */
public class GameFrame extends JFrame {

    JPanel panel;
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
    /**
     Switch JPanel to GamePanel
     */
    void game(Thread thread) {
        panel = new GamePanel(thread);
        this.add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    /**
     Switch JPanel to MenuPanel
     */
    void menu(Thread thread) {
        panel = new HighscorePanel(thread);
        this.add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    /**
     Switch JPanel to RulesPanel
     */
    void rules(Thread thread) {
        panel = new RulesPanel(thread);
        this.add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    /**
     Switch JPanel to EnterNickNamePanel
     */
    void enterNickName(int score) {
        panel = new EnterNickNamePanel(score);
        this.add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
