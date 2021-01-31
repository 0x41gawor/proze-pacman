package com.pacman.image;

public class ImageFactory {

    public enum Images {MENU_BG, MENU_POINTER,RULES};

    public static Image getImage(Images image)
    {
        String filepath = switch (image) {
            case MENU_BG -> "res/img/menu_bg.png";
            case MENU_POINTER -> "res/img/menu_pointer.png";
            case RULES -> "res/img/rules.png";
        };

        return new Image(filepath);
    }
}
