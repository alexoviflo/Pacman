package com.example.pacman;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import static com.example.pacman.main.*;

import javafx.scene.media.*;

public class BigDot extends sDot {

    private final Sound sound = new Sound("pacman_intermission.wav");

    public static Image dott1;

    public BigDot(){
        super();
        setImage(dott1);
        setFitHeight(getX_Size());
        setFitWidth(getX_Size());
    }
    
    static {
        try {
            dott1 = new Image(BigDot.class.getResourceAsStream("/img/cherry.png"));


        } catch (Exception e) {
            System.out.println("Failed to load images.");
        }
    }

   public boolean eat(){
        ImageView collideDot1 = eatDot(bigDottListe);

        if(collideDot1 != null){
            group.getChildren().remove(collideDot1);
            bigDottListe.remove(collideDot1);
            sound.play();

        cherryEaten = true;
       Timer timer = new Timer();
       timer.schedule(new TimerTask() {
           @Override
           public void run() {
               cherryEaten = false;
               Platform.runLater(() ->{
                   sound.stop();
               //    point = 0;
                 //  pointLabel.setText("points "+ point);
               });
               timer.cancel();
           }
       }, 10000);
        }

       
       return true;

   }
}
