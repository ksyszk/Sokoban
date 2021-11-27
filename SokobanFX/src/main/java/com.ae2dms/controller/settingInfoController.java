/**
 * Sample Skeleton for 'settingInfo.fxml' Controller Class
 */

package com.ae2dms.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Control the page which introduces setting in showInfo.
 *
 * <p>This class loads a page on the primary stage.
 * Images and texts about setting will be shown in this page.</p>
 *
 * @author Sijin WANG
 * @since 26 October 2020
 */
public class settingInfoController {
    Stage primaryStage;

    @FXML // fx:id="goBack"
    private ImageView goBack; // Value injected by FXMLLoader

    @FXML // fx:id="topic"
    private Label topic; // Value injected by FXMLLoader

    @FXML // fx:id="label"
    private Label label; // Value injected by FXMLLoader

    @FXML // fx:id="next"
    private ImageView next; // Value injected by FXMLLoader

    /**
     * Switch to the previous page,
     * which shows the functionality of achievements.
     *
     * @param event mouse click
     * @throws IOException loader may not exist
     */
    @FXML
    void back(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/achievementInfo.fxml"));
        Parent info = loader.load();
        primaryStage = (Stage) goBack.getScene().getWindow();
        // load page
        primaryStage.setTitle("Achievement");
        Scene scene = new Scene(info);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Switch to the next page,
     * which shows the portal information page.
     *
     * @param event mouse click
     * @throws IOException loader may not exist
     */
    @FXML
    void next(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/portalInfo.fxml"));
        Parent info = loader.load();
        primaryStage = (Stage) goBack.getScene().getWindow();
        // load page
        primaryStage.setTitle("Achievement");
        Scene scene = new Scene(info);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

