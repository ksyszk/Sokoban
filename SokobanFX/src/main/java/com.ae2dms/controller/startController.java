package com.ae2dms.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.ae2dms.model.GameEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Show the start page.
 *
 * <p>This class is the controller of start.fxml.
 * Player can start playing game, read infomation and modify settings on this page.</p>
 *
 * @author Sijin WANG
 * @since 26 October 2020
 */
public class startController {
    private Stage primaryStage;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnStart"
    private Button btnStart; // Value injected by FXMLLoader

    public startController() {
    }

    /**
     * Start play game.
     *
     * <p>Load view of game and show.
     * Call {@code loadDefaultSaveFile} to start game.</p>
     *
     * @param event mouse click
     * @throws IOException loader may not exist
     */
    @FXML
    void startGame(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game.fxml"));
        Parent game = loader.load();
        primaryStage = (Stage) btnStart.getScene().getWindow();
        GridPane gameGrid = (GridPane) game.lookup("#gameGrid");
        // game page visible
        game.setVisible(true);
        // start load game
        primaryStage.setTitle(GameEngine.GAME_NAME);
        Scene scene = new Scene(game);
        primaryStage.setScene(scene);
        primaryStage.show();
        gameController gc = loader.getController();
        gc.loadDefaultSaveFile(primaryStage, gameGrid);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnStart != null : "fx:id=\"btnStart\" was not injected: check your FXML file 'start.fxml'.";

    }

    /**
     * Show information of this game.
     *
     * <p>Load info.fxml and switch to the new page.
     * Information including how to use basic features,
     * how to access to interesting features...</p>
     *
     * @throws IOException fxml may not exist
     */
    public void showInfo() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/info.fxml"));
        Parent info = loader.load();
        primaryStage = (Stage) btnStart.getScene().getWindow();
        // load page
        primaryStage.setTitle("Information");
        Scene scene = new Scene(info);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Show settings player can modify (Additional feature).
     *
     * <p>Load setting.fxml and switch to the new page.
     * Player can random match images for objects.</p>
     *
     * @throws IOException fxml may not exist
     * @param actionEvent mouse click
     */
    public void gameSetting(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/setting.fxml"));
        Parent info = loader.load();
        primaryStage = (Stage) btnStart.getScene().getWindow();
        // load page
        primaryStage.setTitle("Setting");
        Scene scene = new Scene(info);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("startPage.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

