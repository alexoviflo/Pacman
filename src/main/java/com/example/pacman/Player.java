package com.example.pacman;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.example.pacman.main.*;

public class Player extends ImageView {

    public static Image PACMAN_RIGHT;
    public static Image PACMAN_UP;
    public static Image PACMAN_DOWN;
    public static Image PACMAN_LEFT;

    public static int speed = 10;


    public Player() {
        super();
        setImage(PACMAN_RIGHT);
        setFitHeight(getX_Size() - 8);
        setFitWidth(getX_Size() - 8);

    }

    static {
        try {
            PACMAN_RIGHT = new Image(Player.class.getResourceAsStream("/img/pacmanRight.gif"));
            PACMAN_UP = new Image(Player.class.getResourceAsStream("/img/pacmanUp.gif"));
            PACMAN_DOWN = new Image(Player.class.getResourceAsStream("/img/pacmanDown.gif"));
            PACMAN_LEFT = new Image(Player.class.getResourceAsStream("/img/pacmanLeft.gif"));
        } catch (Exception e) {
            System.out.println("Failed to load images.");
        }
    }

    public void update(Scene scene){


    scene.setOnKeyPressed(event -> {
        double nextX = getTranslateX();
        double nextY = getTranslateY();


        if(nextX < 0){
            nextX = 0;
        } else if(nextX + getFitWidth() > scene.getWidth()){
            nextX = scene.getWidth() - getFitWidth();
        }

        switch (event.getCode()) {
            case W:
                nextY -= speed;
                setImage(PACMAN_UP);
                setTranslateY(nextY);
                if (main.c.isCollisionDetected(this, veggListe, nextX, nextY)) {
                    setTranslateY(getTranslateY() + speed);
                }
                break;
            case S:
                nextY += speed;
                setImage(PACMAN_DOWN);
                setTranslateY(nextY);
                if (main.c.isCollisionDetected(this, veggListe, nextX, nextY)) {
                    setTranslateY(getTranslateY() - speed);
                }
                break;
            case A:
                nextX -= speed;
                setImage(PACMAN_LEFT);
                setTranslateX(nextX);
                if (main.c.isCollisionDetected(this, veggListe, nextX, nextY)) {
                    setTranslateX(getTranslateX() + speed);
                }
                break;
            case D:
                nextX += speed;
                setImage(PACMAN_RIGHT);
                setTranslateX(nextX);
                if (main.c.isCollisionDetected(this, veggListe, nextX, nextY)) {
                    setTranslateX(getTranslateX() - speed);
                }
                break;
        }

    });

        }

}












