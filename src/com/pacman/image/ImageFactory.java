package com.pacman.image;

public class ImageFactory {

    public enum Images {MENU_BG, MENU_POINTER, RULES, INTERLEVEL1, INTERLEVEL2, INTERLEVEL3, ENTERYOURNICKNAME_BG};

    public static Image getImage(Images image)
    {
        String filepath = switch (image) {
            case MENU_BG -> "res/img/menu_bg.png";
            case MENU_POINTER -> "res/img/menu_pointer.png";
            case RULES -> "res/img/rules.png";
            case ENTERYOURNICKNAME_BG -> "res/img/enteryournickname.png";
            case INTERLEVEL1 -> "res/img/interlevel1.png";
            case INTERLEVEL2 -> "res/img/interlevel2.png";
            case INTERLEVEL3 -> "res/img/interlevel3.png";
        };

        return new Image(filepath);
    }
}
