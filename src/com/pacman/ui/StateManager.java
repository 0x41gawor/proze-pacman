package com.pacman.ui;

import javax.swing.*;
import java.awt.*;

public class StateManager {
    public  static int sel = 1;
    static GameFrame frame;
    static Thread thread;

    public StateManager() {
        frame = new GameFrame();
        thread = new Thread();
    }

    public static void changeState(int sel) {
        switch (sel) {
            case 1 -> {
                frame.game(thread);
            }
            case 2 -> {
                frame.menu(thread);
            }
            case 0 -> {
                System.exit(0);
            }
            }
    }
}
