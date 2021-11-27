package com.ae2dms.controller;

import com.ae2dms.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Show information of portal feature.
 *
 * @author Sijin WANG
 * @since 3 December 2020
 */
public class portalInfoController {
    Stage primaryStage;

    @FXML // fx:id="goBack"
    private ImageView goBack; // Value injected by FXMLLoader

    @FXML // fx:id="topic"
    private Label topic; // Value injected by FXMLLoader

    @FXML // fx:id="label"
    private Label label; // Value injected by FXMLLoader

    @FXML // fx:id="next"
    private ImageView next; // Value injected by FXMLLoader

    @FXML
    void back(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/settingInfo.fxml"));
        Parent info = loader.load();
        primaryStage = (Stage) goBack.getScene().getWindow();
        // load page
        primaryStage.setTitle("Setting");
        Scene scene = new Scene(info);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    void next(MouseEvent event) throws IOException {
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

}

