package com.mycompany.pongrecreacion;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;


/**
 * JavaFX App
 */
public class App extends Application {

    int ballCenterX = 10;
    int ballCurrentSpeedX = 8;
    int ballCenterY = 30;
    int ballCurrentSpeedY = 8;
    final int SCENE_TAM_X = 600;
    final int SCENE_TAM_Y = 400;
    final int STICK_WIDTH = 20;
    final int STICK_HEIGHT = 200;
    int stickPosY = (SCENE_TAM_Y - STICK_HEIGHT) / 2;
    int stickCurrentSpeed = 0;
    int score;
    int TEXT_SIZE = 20;
    Text textScore;
    
    @Override
    public void start(Stage stage) {
        
        

        Pane root = new Pane();
        var scene = new Scene(root, SCENE_TAM_X, SCENE_TAM_Y, Color.BLACK);
        stage.setTitle("pongFX");
        stage.setScene(scene);
        stage.show();
        
        //layout principal
        HBox paneScores = new HBox();
        paneScores.setTranslateY(20);
        paneScores.setMinWidth(SCENE_TAM_X);
        paneScores.setAlignment(Pos.CENTER);
        paneScores.setSpacing(100);
        root.getChildren().add(paneScores);
        
        HBox paneCurrentScore = new HBox();
        paneCurrentScore.setSpacing(10);
        paneScores.getChildren().add(paneCurrentScore);
        
        HBox paneHighScore = new HBox();
        paneHighScore.setSpacing(10);
        paneScores.getChildren().add(paneHighScore);
        
        Text textTitleScore = new Text("Score:");
        textTitleScore.setFont(Font.font(TEXT_SIZE));
        textTitleScore.setFill(Color.WHITE);
        
        textScore = new Text("0");
        textScore.setFont(Font.font(TEXT_SIZE));
        textScore.setFill(Color.WHITE);
        
        resetGame();
        
        paneCurrentScore.getChildren().add(textTitleScore);
        paneCurrentScore.getChildren().add(textScore);
        
        Circle circleBall = new Circle(ballCenterX, ballCenterY, 7, Color.WHITE);
        root.getChildren().add(circleBall);
        Rectangle rectStick = new Rectangle(SCENE_TAM_X*0.9, stickPosY, STICK_WIDTH, STICK_HEIGHT);
        rectStick.setFill(Color.WHITE);
        root.getChildren().add(rectStick);
        
        Timeline animationBall = new Timeline(
            new KeyFrame(Duration.seconds(0.017), (var ae) -> {
                  circleBall.setCenterX(ballCenterX);
                  ballCenterX += ballCurrentSpeedX;
                  if(ballCenterX >= SCENE_TAM_X) {
                      ballCurrentSpeedX = -8;
                  }
                  if(ballCenterX <= 0) {
                      ballCurrentSpeedX = 8;
                  }
                  circleBall.setCenterY(ballCenterY);
                  ballCenterY += ballCurrentSpeedY;
                  if(ballCenterY >= SCENE_TAM_Y) {
                      ballCurrentSpeedY = -8;
                  }
                  if(ballCenterY <= 0) {
                      ballCurrentSpeedY = 8;
                  }
                  stickPosY += stickCurrentSpeed;
                  if(stickPosY < 0) {
                      stickPosY = 0;
                  } else {
                      if(stickPosY > SCENE_TAM_Y - STICK_HEIGHT) {
                          stickPosY = SCENE_TAM_Y - STICK_HEIGHT;
                      }
                  }
                  rectStick.setY(stickPosY);
                  
                  Shape shapeColision = Shape.intersect(circleBall, rectStick);
                  boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
                  if(colisionVacia == false && ballCurrentSpeedX > 0 ) {
                      
                    ballCurrentSpeedX = -8;
                    score++;
                    textScore.setText(String.valueOf(score));
                    }
                  if(ballCenterX >= SCENE_TAM_X) {
                    resetGame();
                    System.out.println("reiniciando");
                    }
            })
            
        );

        
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch(event.getCode()) {
                case UP:
                    stickCurrentSpeed = -8;
                    break;
                case DOWN:
                    stickCurrentSpeed = 8;
                    break;
            }
        });
        scene.setOnKeyReleased((KeyEvent event) -> {
            stickCurrentSpeed = 0;
        });
        
        for(int i=0; i<SCENE_TAM_Y; i+=30) {
            Line line = new Line(SCENE_TAM_X/2, i, SCENE_TAM_X/2, i+10);
            line.setStroke(Color.WHITE);
            line.setStrokeWidth(4);
            root.getChildren().add(line);
        }
        animationBall.setCycleCount(Timeline.INDEFINITE);
        animationBall.play();

        
    }

    private void resetGame() {
        score = 0;
        textScore.setText(String.valueOf(score));
        ballCenterX = 10;
        ballCurrentSpeedY = 8;
    }
    
    public static void main(String[] args) {
        launch();
    }

}