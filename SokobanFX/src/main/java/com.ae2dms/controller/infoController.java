package com.ae2dms.controller;

import com.ae2dms.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Control the page which introduces how to play this game in showInfo.
 *
 * <p>This class loads a page on the primary stage.
 * Images and texts about how to play this game will be shown in this page.</p>
 *
 * @author Sijin WANG
 * @since 26 October 2020
 */
public class infoController {
    Stage primaryStage;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="goBack"
    private ImageView goBack; // Value injected by FXMLLoader
    @FXML // fx:id="next"
    private ImageView next; // Value injected by FXMLLoader

    /**
     * Switch to the start page.
     *
     * @param event mouse click
     * @throws IOException loader may not exist
     */
    @FXML
    void backStart(MouseEvent event) throws IOException {
        Main main = new Main();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/start.fxml"));
        Parent start = loader.load();
        primaryStage = (Stage) goBack.getScene().getWindow();
        Group group = new Group();
        Node animation = main.animation();
        group.getChildren().addAll(start, animation);
        // load page
        primaryStage.setTitle("Information");
        Scene scene = new Scene(group, Color.rgb(171, 229, 243));
        scene.getStylesheets().add(getClass().getClassLoader().getResource("startPage.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Switch to the next page,
     * which shows the basic features.
     *
     * @param event mouse click
     * @throws IOException loader may not exist
     */
    @FXML
    void next(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/basicFeatures.fxml"));
        Parent info = loader.load();
        primaryStage = (Stage) next.getScene().getWindow();
        // load page
        primaryStage.setTitle("Some basic features");
        Scene scene = new Scene(info);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This method is called by the FXMLLoader when initialization is complete.
     */
    @FXML
    void initialize() {
        assert goBack != null : "fx:id=\"goBack\" was not injected: check your FXML file 'info.fxml'.";
        assert next != null : "fx:id=\"next\" was not injected: check your FXML file 'info.fxml'.";
    }
}


