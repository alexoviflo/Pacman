package com.example.pacman;

import javafx.animation.PathTransition;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.*;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.Random;

import static com.example.pacman.main.*;

public class Enemy extends ImageView {

    public static double hastighet = 2;
    private static double vanligHastighet = 2;
    public static double cherryHastighet = 1;

    public int pos;
    public enum Direction {
        LEFT, RIGHT, UP, DOWN, NONE
    }

    public Direction direction;

    public Enemy(int pos) {
        super();
        this.pos = pos;
        setImage(getGhostImage(pos));
        direction = Direction.NONE;
        setFitHeight(main.getX_Size());
        setFitWidth(main.getX_Size());
    }

    public void update(int ghostIndex) {
        Bounds enemyB = this.getBoundsInParent();
        Bounds playerBounds = player.getBoundsInParent();

        if(!cherryEaten){
            hastighet = vanligHastighet;
        }

                if(cherryEaten){
                    setImage(getGhostImage(pos));
                   hastighet = cherryHastighet;
                    if (enemyB.intersects(playerBounds)) {
                        points();
                      resetEnemy();
                }
            }
         else {
             if (enemyB.intersects(playerBounds)) {
                    lives--;
                    if (lives > -1) {
                        resetGame();

                    } else {
                        endGame();
                    }
                } else{
                setImage(ghostImages[ghostIndex]);
            }
        }
    }

    public void resetEnemy(){

        setTranslateX(pane.getBoundsInParent().getMinX() + enemyStartX);
        setTranslateY(pane.getBoundsInParent().getMinY() + enemyStartY);
    }

    public void resetGame(){
        player.setTranslateX(playerStartX);
        player.setTranslateY(playerStartY);

        label.setText("Lives: "+ lives);
    }

    public void points(){

            if(!cherryEaten){
                point = 0;
            }
            point++;
            pointLabel.setText("points "+  point);

    }
    public void endGame(){

        isRunning = false;
        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setStyle("-fx-font-size: 48pt;");
        gameOverLabel.setTextFill(Color.RED);

        group.getChildren().add(gameOverLabel);



    }
    public void move() {
        int currentX = (int) getTranslateX();
        int currentY = (int) getTranslateY();

        int nextX = currentX;
        int nextY = currentY;

        //bevege fienden i den nåværende retning til den treffer en vegg
        switch (direction) {
            case LEFT:
                nextX -= hastighet;
                break;
            case RIGHT:
              nextX += hastighet;
                break;
            case UP:
                nextY -= hastighet;
                break;
            case DOWN:
                 nextY += hastighet;
                break;
                case NONE:
                    direction = getNewRandomDirection(direction, currentX, currentY);
                break;
        }

        if(!main.c.isCollisionDetected(this,veggListe, nextX,nextY)){
            setTranslateX(nextX);
            setTranslateY(nextY);
        } else{
            direction = getNewRandomDirection(direction, currentX, currentY);
        }
    }

    private Direction getNewRandomDirection(Direction currentDirection, int currentX, int currentY) {
        //lager en ny liste av retninger eksludert motsatt av retningen av den nåværende retningnen
        List<Direction> validDirections = new ArrayList<>(Arrays.asList(Direction.values()));
        if (currentDirection != Direction.NONE) {
            validDirections.remove(currentDirection);
        }

        // bruker shuffle, for at spøkelse ikke går frem og tilbake
        Collections.shuffle(validDirections);


        //velger den første retningen som ikke leder tilbake til den tidligere posisjonen
        for (Direction direction : validDirections) {
            int nextX = currentX;
            int nextY = currentY;

            if(nextX < 0){
                nextX = 0;
            } else if(nextX + getFitWidth() > scene.getWidth()){
                nextX = (int) (scene.getWidth() - getFitWidth());
            }

            switch (direction) {
                case LEFT:
                    nextX -= hastighet;
                    break;
                case RIGHT:
                    nextX += hastighet;
                    break;
                case UP:
                    nextY -= hastighet;
                    break;
                case DOWN:
                    nextY += hastighet;
                    break;
                default:
                    break;
            }
            if (!main.c.isCollisionDetected(this, veggListe, nextX, nextY)) {
                return direction;
            }
        }
        // viss ingen gyldig retning så retning none
        return Direction.NONE;
    }


    private static final Image ghostScared = new Image(Enemy.class.getResourceAsStream("/img/scared.gif"));
   private static final Image[] ghostImages = new Image[] {

            new Image(Enemy.class.getResourceAsStream("/img/blue.gif")),
            new Image(Enemy.class.getResourceAsStream("/img/ghost2.gif")),
            new Image(Enemy.class.getResourceAsStream("/img/pink.gif")),
           new Image(Enemy.class.getResourceAsStream("/img/red.gif")),
           ghostScared,

    };

    private Image getGhostImage(int pos) {

        if (pos >= 0 && pos < ghostImages.length) {
            if (cherryEaten) {

                return ghostScared;
            } else {

                return ghostImages[pos];
            }

        } else {
            return ghostScared;
        }
    }


}








