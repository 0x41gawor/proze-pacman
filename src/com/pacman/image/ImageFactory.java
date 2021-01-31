package com.pacman.image;

public class ImageFactory {

    public enum Images {MENU_BG, MENU_POINTER};

    public static Image getImage(Images image)
    {
        String filepath = switch (image) {
            case MENU_BG -> "res/img/menu_bg.png";
            case MENU_POINTER -> "res/img/menu_pointer.png";
        };

        return new Image(filepath);
    }
}
