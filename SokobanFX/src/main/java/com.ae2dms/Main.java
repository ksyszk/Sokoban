package com.ae2dms;

import com.ae2dms.model.GameEngine;
import com.ae2dms.model.ImageLoader;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;

/**
 * Main function
 *
 * <p>This class applies the start page,
 * which leads to game, setting and info</p>
 *
 * <p>This class also design an animation on the start page</p>
 *
 * @author Sijin WANG
 * @since 26 October 2020
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Load the start page
     * and call animation() to make an interesting 'Sokoban'(Additional feature).
     *
     * @param primaryStage the stage which will show the start page
     * @throws IOException fxml may not exist
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        URL location = getClass().getClassLoader().getResource("fxml/start.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        Group group = new Group();
        Node animation = animation();
        group.getChildren().addAll(root, animation);
        
        primaryStage.setTitle(GameEngine.GAME_NAME);
        Scene scene = new Scene(group, Color.rgb(171, 229, 243));
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("startPage.css").toExternalForm());
        primaryStage.show();
    }


    /**
     * Make an animation using image of Sokoban
     *
     * @return the animation
     */
    public Node animation() {
        ImageView iv = new ImageView();
        iv.setImage(ImageLoader.getImageTitle());

        Path path = new Path();
        path.getElements().add(new MoveTo(300,100));
        path.getElements().add(new CubicCurveTo(350, 120, 400, 120, 450, 100));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(iv);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();

        return iv;
    }
}
