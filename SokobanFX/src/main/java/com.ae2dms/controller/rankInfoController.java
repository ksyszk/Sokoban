package com.ae2dms.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Control the page which introduces rank in showInfo.
 *
 * <p>This class loads a page on the primary stage.
 * Images and texts about rank will be shown in this page.</p>
 *
 * @author Sijin WANG
 * @since 26 October 2020
 */
public class rankInfoController {
    Stage primaryStage;

    @FXML // fx:id="goBack"
    private ImageView goBack; // Value injected by FXMLLoader

    @FXML // fx:id="next"
    private ImageView next; // Value injected by FXMLLoader

    /**
     * Switch to the previous page,
     * which shows the basic features.
     *
     * @param event mouse click
     * @throws IOException loader may not exist
     */
    @FXML
    void back(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/basicFeatures.fxml"));
        Parent info = loader.load();
        primaryStage = (Stage) goBack.getScene().getWindow();
        // load page
        primaryStage.setTitle("Some basic features");
        Scene scene = new Scene(info);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Switch to the next page,
     * which shows the functionality of destroying walls.
     *
     * @param event mouse click
     * @throws IOException loader may not exist
     */
    @FXML
    void next(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/destroyWall.fxml"));
        Parent info = loader.load();
        primaryStage = (Stage) goBack.getScene().getWindow();
        // load page
        primaryStage.setTitle("Destroy wall");
        Scene scene = new Scene(info);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
