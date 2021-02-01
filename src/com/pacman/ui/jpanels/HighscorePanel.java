package com.pacman.ui.jpanels;

import com.pacman.config.Config;
import com.pacman.image.Image;
import com.pacman.image.ImageFactory;
import com.pacman.ui.StateManager;
import com.pacman.ui.jpanels.model.TopScorer;
import com.pacman.ui.util.Clock;
import com.pacman.util.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HighscorePanel extends JPanel implements Runnable {
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
     * List of topscorers
     */
    List<TopScorer> topScorers;
    /**
     * Font of string
     */
    Font font;
    /**
     * Deafault Constructor
     */
    public HighscorePanel(Thread thread) {
        screensize = new Dimension(Config.WINDOW_SIZE_X, Config.WINDOW_SIZE_Y);
        this.setPreferredSize(screensize);
        clock = new Clock();
        backgroundImage = ImageFactory.getImage(ImageFactory.Images.HIGHSCORES_BG);
        isClicked = false;
        topScorers = new ArrayList<TopScorer>();
        font = new Font(Font.MONOSPACED, Font.BOLD, 30);
        LoadHighscoreFromFile("highscores.txt");
        this.addKeyListener(new HighscorePanel.KeyboardHandler());
        thread = new Thread(this);
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
        drawTopScorers(g);
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
     Load Highscore from file
     */
    private void LoadHighscoreFromFile(String filename) {
        List<String> names = new ArrayList<String>();
        List<Integer> points = new ArrayList<Integer>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            String line = "";
            int i = 0;
            while(line!=null) {
                line= reader.readLine();
                if(line != null)
                {
                    if(i++ % 2 == 0) {
                        names.add(line);
                    }
                    else {
                        points.add(Integer.parseInt(line));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i< Math.min(names.size(), points.size()); i++) {
            this.topScorers.add(new TopScorer(names.get(i), points.get(i)));
        }
    }
    /**
     Draw highscorers
     */
    private void drawTopScorers(Graphics g) {
        g.setColor(Color.white);
        g.setFont(font);
        int iterator = 1;
        Vector<Integer> pos = new Vector<Integer>(300,150);
        for (TopScorer topScorer : topScorers) {
            g.drawString(iterator++ + ". " + topScorer.name + " " + topScorer.points, pos.x, pos.y);
            pos.y += 50;
        }
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
