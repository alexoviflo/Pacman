package com.example.pacman;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class Wall extends ImageView {



    public Wall() {
        setImage(vegg);
        setFitHeight(main.getX_Size());
        setFitWidth(main.getX_Size());
    }

    public  ImageView wallView = getWallView();

    public static Image vegg;

    public ImageView getWallView() {
        return wallView;
    }


    static {
        try {
            vegg = new Image(Wall.class.getResourceAsStream("/img/wall.png"));


        } catch (Exception e) {
            System.out.println("Failed to load images.");
        }
    }


}
