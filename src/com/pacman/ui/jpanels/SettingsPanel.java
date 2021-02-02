package com.pacman.ui.jpanels;

import com.pacman.config.Config;
import com.pacman.image.Image;
import com.pacman.image.ImageFactory;
import com.pacman.ui.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.pacman.image.ImageFactory.Images.SETTINGS;

public class SettingsPanel extends JPanel{
    /**
     * Deafault Constructor
     */
    public SettingsPanel() {
        Dimension screenSize = new Dimension(Config.WINDOW_SIZE_X, Config.WINDOW_SIZE_Y);
        this.setPreferredSize(screenSize);
        Image backgroundImage;
        backgroundImage = ImageFactory.getImage(SETTINGS);
        JLabel backgroundLabel = new JLabel(backgroundImage.get_imageIcon());
        this.add(backgroundLabel);
        this.addKeyListener(new SettingsPanel.KeyboardHandler());
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
            StateManager.changeState(0);
        }
    }
}
}


