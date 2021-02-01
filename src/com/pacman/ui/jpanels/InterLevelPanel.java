package com.pacman.ui.jpanels;

import com.pacman.config.Config;
import com.pacman.image.Image;
import com.pacman.image.ImageFactory;
import com.pacman.ui.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InterLevelPanel extends JPanel {
    /**
     * Deafault Constructor
     */
    public InterLevelPanel(int nextLevel) {
        Dimension screenSize = new Dimension(Config.WINDOW_SIZE_X, Config.WINDOW_SIZE_Y);
        this.setPreferredSize(screenSize);
        Image backgroundImage;
        switch (nextLevel) {
          case 1 ->  backgroundImage = ImageFactory.getImage(ImageFactory.Images.INTERLEVEL1);
          case 2 ->   backgroundImage = ImageFactory.getImage(ImageFactory.Images.INTERLEVEL2);
          case 3 ->   backgroundImage = ImageFactory.getImage(ImageFactory.Images.INTERLEVEL3);
          default  -> backgroundImage = ImageFactory.getImage(ImageFactory.Images.INTERLEVEL4);
        }
        JLabel backgroundLabel = new JLabel(backgroundImage.get_imageIcon());
        this.add(backgroundLabel);
        this.addKeyListener(new InterLevelPanel.KeyboardHandler());
    }
    /**
     Swing method
     We needed to add because new Panel need to be notified before
     handling keyboard.
     */
    @Override
    public void addNotify() {
        super.addNotify();
        this.requestFocusInWindow();
    }
    /**
     * This class handles every keyboard input.
     * It handles arrow keys and Enter key.
     */
    public class KeyboardHandler extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
               StateManager.changeState(1);
               System.out.println("elo 420");
            }
        }
    }
}


