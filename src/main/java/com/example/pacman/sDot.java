package com.example.pacman;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import static com.example.pacman.main.*;
public class sDot extends ImageView {

    public sDot() {
        super();
        setImage(dott);
        dottListe.add(this);
        setFitHeight(getX_Size());
        setFitWidth(getX_Size());
    }

    public ImageView dottView = getDottView();

    public static Image dott;

    public ImageView getDottView() {
        return dottView;
    }

    static {
        try {
            dott = new Image(sDot.class.getResourceAsStream("/img/smalldot.png"));


        } catch (Exception e) {
            System.out.println("Failed to load images.");
        }
    }

    public boolean eat(){
        ImageView collideDot = eatDot(dottListe);

        if(collideDot != null){
            group.getChildren().remove(collideDot);
            dottListe.remove(collideDot);

            if(dottListe.isEmpty()){
                nyttLevel();
            }
            return true;

        }
        return false;

    }

    public ImageView eatDot(List<? extends ImageView> dots){
        ImageView pacman = player;

        for(ImageView dot : dots){
            Bounds visualDot = dot.getBoundsInParent();
            Bounds visualPacman = pacman.getBoundsInParent();

            if(visualDot.intersects(visualPacman)){
            //    pane.getChildren().remove(dot);
                dots.remove(dot);

                return dot;
            }
        }
        return null;
    }

}











