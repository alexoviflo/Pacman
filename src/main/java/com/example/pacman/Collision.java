package com.example.pacman;

import javafx.animation.AnimationTimer;
import javafx.fxml.Initializable;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import static com.example.pacman.main.*;

public class Collision {

    private static final double ROM = 5;

    public boolean isCollisionDetected(ImageView pacman, List<ImageView> walls, double nextX, double nextY) {
        pacman.setTranslateX(nextX);
        pacman.setTranslateY(nextY);
        for (ImageView wall : walls) {
            Bounds wallBounds = wall.getBoundsInParent();
            Bounds playerBounds = pacman.getBoundsInParent();
            double playerLeft = playerBounds.getMinX();
            double playerRight = playerBounds.getMaxX();
            double playerTop = playerBounds.getMinY();
            double playerBottom = playerBounds.getMaxY();

            if (playerBounds.intersects(wallBounds)) {
                double wallLeft = wallBounds.getMinX() + ROM;
                double wallRight = wallBounds.getMaxX() - ROM;
                double wallTop = wallBounds.getMinY() + ROM;
                double wallBottom = wallBounds.getMaxY() - ROM;

                if (playerLeft < wallRight && playerRight > wallLeft && playerTop < wallBottom && playerBottom > wallTop) {
                    return true;
                }
            }
        }
        return false;
    }
}









