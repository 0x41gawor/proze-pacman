package com.pacman.image;

public class ImageFactory {

    public enum Images {MENU};

    public static Image getImage(Images image)
    {
        String filepath = switch (image) {
            case MENU -> "res/img/menu.png";
        };

        return new Image(filepath);
    }
}
