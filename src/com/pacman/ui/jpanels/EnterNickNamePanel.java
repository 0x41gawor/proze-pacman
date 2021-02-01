package com.pacman.ui.jpanels;

import com.pacman.config.Config;
import com.pacman.image.Image;
import com.pacman.image.ImageFactory;
import com.pacman.ui.StateManager;
import com.pacman.ui.jpanels.model.SortByPoints;
import com.pacman.ui.jpanels.model.TopScorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnterNickNamePanel extends JPanel{
    /**
     Background image
     */
    Image imageBackground;
    /**
     Font for nickname
     */
    Font fontNickName;
    /**
     Font for score
     */
    Font fontScore;
    /**
     NickName string
     */
    String nickNameText;
    /**
     Score string
     */
    String scoreText;
    /**
     Constructor
     Sets JPanel and two string displayed in it
     */
    public EnterNickNamePanel(int score) {
        Dimension screenSize = new Dimension(Config.WINDOW_SIZE_X, Config.WINDOW_SIZE_Y);
        this.setPreferredSize(screenSize);
        fontNickName = new Font(Font.MONOSPACED, Font.BOLD, 30);
        fontScore = new Font(Font.MONOSPACED, Font.BOLD, 80);
        nickNameText = "";
        this.scoreText = String.valueOf(score);
        imageBackground =  ImageFactory.getImage(ImageFactory.Images.ENTERYOURNICKNAME_BG);
        this.addKeyListener(new KeyboardHandler());
    }
    /**
     Paint the frame after every key pressed or at the beginning
     */
    public void paint(Graphics g) {
        imageBackground.get_imageIcon().paintIcon(this, g, 0, 0);
        g.setColor(Color.white);
        g.setFont(fontNickName);
        g.drawString(nickNameText, 210, 540);
        g.setFont(fontScore);
        g.drawString(scoreText, 300, 380);
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
     * KeyAdapter implements KeyListener interface.
     */
    public class KeyboardHandler extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            // If we press VK_ENTER save score to file and change state to main menu
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    FileWriter fw = new FileWriter("highscores.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    //bw.newLine();
                    bw.write(nickNameText);
                    bw.newLine();
                    bw.write(scoreText);
                    bw.newLine();
                    bw.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                sortHighscores("highscores.txt");
                StateManager.setScore(0);
                StateManager.changeState(0);
            }
            inputEngine(e);
            repaint();
        }
        /**
         * Dumbest things i've ever created
         */
        private void inputEngine(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (nickNameText.length() > 0) {
                    nickNameText = nickNameText.substring(0, nickNameText.length() - 1);
                }
            }else if(e.getKeyCode() == KeyEvent.VK_A) {
                nickNameText += "A";
            }else if(e.getKeyCode() == KeyEvent.VK_B) {
                nickNameText += "B";
            }else if(e.getKeyCode() == KeyEvent.VK_C) {
                nickNameText += "C";
            }else if(e.getKeyCode() == KeyEvent.VK_D) {
                nickNameText += "D";
            }else if(e.getKeyCode() == KeyEvent.VK_E) {
                nickNameText += "E";
            }else if(e.getKeyCode() == KeyEvent.VK_F) {
                nickNameText += "F";
            }else if(e.getKeyCode() == KeyEvent.VK_G) {
                nickNameText += "G";
            }else if(e.getKeyCode() == KeyEvent.VK_H) {
                nickNameText += "H";
            }else if(e.getKeyCode() == KeyEvent.VK_I) {
                nickNameText += "I";
            }else if(e.getKeyCode() == KeyEvent.VK_J) {
                nickNameText += "J";
            }else if(e.getKeyCode() == KeyEvent.VK_K) {
                nickNameText += "K";
            }else if(e.getKeyCode() == KeyEvent.VK_L) {
                nickNameText += "L";
            }else if(e.getKeyCode() == KeyEvent.VK_M) {
                nickNameText += "M";
            }else if(e.getKeyCode() == KeyEvent.VK_N) {
                nickNameText += "N";
            }else if(e.getKeyCode() == KeyEvent.VK_O) {
                nickNameText += "O";
            }else if(e.getKeyCode() == KeyEvent.VK_P) {
                nickNameText += "P";
            }else if(e.getKeyCode() == KeyEvent.VK_R) {
                nickNameText += "R";
            }else if(e.getKeyCode() == KeyEvent.VK_S) {
                nickNameText += "S";
            }else if(e.getKeyCode() == KeyEvent.VK_T) {
                nickNameText += "T";
            }else if(e.getKeyCode() == KeyEvent.VK_U) {
                nickNameText += "U";
            }else if(e.getKeyCode() == KeyEvent.VK_W) {
                nickNameText += "W";
            }else if(e.getKeyCode() == KeyEvent.VK_Q) {
                nickNameText += "Q";
            }else if(e.getKeyCode() == KeyEvent.VK_X) {
                nickNameText += "X";
            } else if(e.getKeyCode() == KeyEvent.VK_Y) {
                nickNameText += "Y";
            }else if(e.getKeyCode() == KeyEvent.VK_Z) {
                nickNameText += "Z";
            }else if(e.getKeyCode() == KeyEvent.VK_0) {
                nickNameText += "0";
            }else if(e.getKeyCode() == KeyEvent.VK_1) {
                nickNameText += "1";
            }else if(e.getKeyCode() == KeyEvent.VK_2) {
                nickNameText += "2";
            }else if(e.getKeyCode() == KeyEvent.VK_3) {
                nickNameText += "3";
            }else if(e.getKeyCode() == KeyEvent.VK_4) {
                nickNameText += "4";
            }else if(e.getKeyCode() == KeyEvent.VK_5) {
                nickNameText += "5";
            }else if(e.getKeyCode() == KeyEvent.VK_6) {
                nickNameText += "6";
            }else if(e.getKeyCode() == KeyEvent.VK_7) {
                nickNameText += "7";
            }else if(e.getKeyCode() == KeyEvent.VK_8) {
                nickNameText += "8";
            }else if(e.getKeyCode() == KeyEvent.VK_9) {
                nickNameText += "9";
            }else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                nickNameText += " ";
            }
        }
        public void keyReleased(KeyEvent e) {

        }
        private void sortHighscores(String filename){
            java.util.List<String> names = new ArrayList<>();
            List<Integer> points = new ArrayList<>();
            List<TopScorer> topScorers = new ArrayList<>();
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
                topScorers.add(new TopScorer(names.get(i), points.get(i)));
            }
            Collections.sort(topScorers, new SortByPoints());
            int maxIndex = 10;
            if(topScorers.size() < 10){
                maxIndex = topScorers.size();
            }
            topScorers = topScorers.subList(0,maxIndex);
            try{
               FileWriter writer = new FileWriter("highscores.txt");
                for (TopScorer ts: topScorers) {
                    writer.write(ts.name + "\n");
                    writer.write(ts.points + "\n");
                }
               writer.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
        }
    }
}
