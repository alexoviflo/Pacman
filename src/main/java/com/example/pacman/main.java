package com.example.pacman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.example.pacman.Enemy.hastighet;


public class main extends Application {

    public static BorderPane pane = new BorderPane();
    public static Group group = new Group();

    public static double TILE_WIDTH = 600;
    public static double TILE_HEIGHT = 600;


    public static String[] mapFilePath = {"level.txt", "level2.txt", "level3.txt"};

    public static List<ImageView> veggListe = new ArrayList<>();

    public static List<sDot> dottListe = new ArrayList<>();
    public static List<BigDot> bigDottListe = new ArrayList<>();

    public static boolean isRunning = true;


    static int  x_size = getX_Size();
    static int y_size = x_size;
    public static Player player = new Player();
    public static Wall w = new Wall();
    public static Collision c = new Collision();
    public static sDot s = new sDot();
    public static BigDot bi = new BigDot();
    public static Enemy e1 = new Enemy(0);
    public static  Enemy e2 = new Enemy(1);
    public static Enemy e3 = new Enemy(2);
    public static Enemy e4 = new Enemy(3);

    public static Label label = new Label("lives 3");
    public static Label pointLabel = new Label("points 0");
    public static Label overLabel = new Label("YOU WON");

    public static int x = 0;


   public static Sound sound = new Sound("C:/Users/alexa/OneDrive/Dokumenter/JAVA/PacMan/chomp.wav");

  public static   List<Node> nodes = scanMap(mapFilePath[x]);

    public static int point = 0;


    public static int lives = 3;

    public static int playerStartX;
    public static int playerStartY;
    public static int enemyStartX;
    public static int enemyStartY;

    public static boolean cherryEaten = false;

    public Timeline timeLine;

    public static Scene scene = new Scene(pane );


    @Override
    public void start(Stage stage) throws IOException {

        pane.setStyle("-fx-background-color: #000000;");


        sound.play();

       VBox lablBox = new VBox();

        label.setVisible(true);
        pointLabel.setVisible(true);

        // move label 10 pixels to the right
        label.setTranslateY(10);

         // move label 10 pixels to the right
        pointLabel.setTranslateY(50);

        lablBox.getChildren().addAll(label,pointLabel);
        
        group.getChildren().addAll(nodes);
        pane.setLeft(lablBox);
        pane.setCenter(group);

       pane.setPrefSize(TILE_WIDTH, TILE_HEIGHT);

        stage.setFullScreen(true);

        label.setFont(new Font(20));
        label.setTextFill(Color.WHITE);

        pointLabel.setFont(new Font(20));
        pointLabel.setTextFill(Color.RED);

        overLabel.setStyle("-fx-font-size: 48pt;");
        overLabel.setTextFill(Color.RED);


        player.toFront();
        Duration frameDuration = Duration.millis(24);
         timeLine = new Timeline(
                new KeyFrame(frameDuration, event -> {
                    if (!isRunning) {
                        timeLine.stop();
                        return;
                    }

                    bi.eat();
                    player.update(scene);
                    e1.move();
                    e2.move();
                    e3.move();
                    e4.move();
                    e1.update(0);
                    e2.update(1);
                    e3.update(2);
                    e4.update(3);
                    s.eat();

                })
        );

        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();

        stage.setTitle("Pacman");
            stage.setScene(scene);
            stage.show();
    }

    public static List<Node> scanMap(String filnavn) {
        List<Node> nodes = new ArrayList<>();

        try {
            int lineNumber = 0;
            Scanner scan = new Scanner(new File(filnavn));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                line = line.replaceAll(" ",  "");

                for (int i = 0; i < line.length(); i++) {
                    int x = i * x_size ;
                    int y = lineNumber * y_size;
                    char c = line.charAt(i);
                    if (c == 'W') {
                        w = new Wall();
                        w.setTranslateX(x );
                        w.setTranslateY(y);
                        nodes.add(w);
                        veggListe.add(w);
                    } else if (c == 'B') {
                        bi = new BigDot();
                        bi.setTranslateX(x);
                        bi.setTranslateY(y);
                      nodes.add(bi);
                      bigDottListe.add(bi);
                    } else if (c == 'S') {
                        s = new sDot();
                        s.setTranslateX(x);
                        s.setTranslateY(y);
                       nodes.add(s);
                        dottListe.add(s);
                    } else if (c == 'L') {
                        e1.setTranslateX(x);
                        e1.setTranslateY(y);
                        enemyStartX = x;
                        enemyStartY = y;
                        nodes.add(e1);
                    } else if (c == 'R') {
                        e2.setTranslateX(x);
                        e2.setTranslateY(y);
                        enemyStartX = x;
                        enemyStartY = y;
                       nodes.add(e2);
                    } else if (c == 'J') {
                        e3.setTranslateX(x);
                        e3.setTranslateY(y);
                        enemyStartX = x;
                        enemyStartY = y;
                       nodes.add(e3);
                    } else if (c == 'T') {
                        e4.setTranslateX(x);
                        e4.setTranslateY(y);
                        enemyStartX = x;
                        enemyStartY = y;
                       nodes.add(e4);
                    }else if (c == 'P') {
                        player.setTranslateX(x);
                        player.setTranslateY(y);
                        playerStartX = x;
                        playerStartY = y;
                       nodes.add(player);
                    }
                }
                lineNumber++;
            }
            scan.close();

        } catch(Exception e){
            e.printStackTrace();
        }

        return nodes;
    }

    public static int getX_Size() {
        try {
            Scanner scanner = new Scanner(new File(mapFilePath[x]));
            String linje = scanner.nextLine();
            return linje.length();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void nyttLevel(){
        x++;
      if(x < mapFilePath.length){
          lives = 3;
          point = 0;
          pointLabel.setText("Points 0");
          label.setText("Lives 3");
          cherryEaten = false;
          hastighet += 1;

          nodes = scanMap(mapFilePath[x]);
          group.getChildren().setAll(nodes);

          veggListe = new ArrayList<>();
          for(Node node : nodes){
              if(node instanceof Wall){
                  veggListe.add((Wall) node);
              }
          }

      } else{
          sound.stop();
          sound = new Sound("C:/Users/alexa/OneDrive/Dokumenter/JAVA/PacMan/gameover.wav");
          isRunning = false;
          group.getChildren().add(overLabel);

      }


    }



    public static void main(String[] args) {
        launch();
    }
}