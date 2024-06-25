package com.example.pacman;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import java.io.File;



public class Sound {
    private MediaPlayer mediaPlayer;

    public Sound(String fileName) {
        try {
            String filnavn = new File(fileName).getAbsolutePath();
            Media media = new Media(new File(filnavn).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        } catch (MediaException e) {
            System.out.println("feil i å lage lyden: " + e.getMessage());
            mediaPlayer = null;
        }
    }


    public void play() {
        mediaPlayer.play();
    }

    public void stop() {
        mediaPlayer.stop();
    }
}

