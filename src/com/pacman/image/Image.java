package com.pacman.image;

import javax.swing.*;

public class Image {

    ImageIcon imageIcon;


    public Image(String filename) {
        imageIcon = new ImageIcon(filename);
    }

    public ImageIcon get_imageIcon() {
        return imageIcon;
    }
}
