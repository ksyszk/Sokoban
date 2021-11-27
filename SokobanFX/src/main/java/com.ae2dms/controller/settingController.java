package com.ae2dms.controller;

import com.ae2dms.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * User can modify settings here.
 *
 * <p>Every kind of object has two choices.
 * Player can choose one he/she prefer
 * and save the setting.
 * Then the objects in the game will be change into the image he/she choose.l</p>
 */
public class settingController {
    Stage primaryStage;
    private static int chosenFloor = 1;
    private static int chosenWall = 1;
    private static int chosenKeeper= 1;
    private static int chosenCrate = 1;

    @FXML // fx:id="floor1"
    private RadioButton floor1; // Value injected by FXMLLoader

    @FXML // fx:id="floorGroup"
    private ToggleGroup floorGroup; // Value injected by FXMLLoader

    @FXML // fx:id="floor2"
    private RadioButton floor2; // Value injected by FXMLLoader

    @FXML // fx:id="wall1"
    private RadioButton wall1; // Value injected by FXMLLoader

    @FXML // fx:id="wallGroup"
    private ToggleGroup wallGroup; // Value injected by FXMLLoader

    @FXML // fx:id="wall2"
    private RadioButton wall2; // Value injected by FXMLLoader

    @FXML // fx:id="cancel"
    private Button cancel; // Value injected by FXMLLoader

    @FXML // fx:id="save"
    private Button save; // Value injected by FXMLLoader

    @FXML // fx:id="keeperGroup"
    private ToggleGroup keeperGroup; // Value injected by FXMLLoader

    @FXML // fx:id="crateGroup"
    private ToggleGroup crateGroup; // Value injected by FXMLLoader

    /**
     * Get chosen keeper.
     *
     * @return {@code chosenKeeper}
     */
    public static int getChosenKeeper() {
        return chosenKeeper;
    }

    /**
     * Set chosen keeper.
     *
     * @param chosenKeeper no. of keeper object chosen by player
     */
    public static void setChosenKeeper(int chosenKeeper) {
        settingController.chosenKeeper = chosenKeeper;
    }

    /**
     * Get chosen crate.
     *
     * @return chosen crate
     */
    public static int getChosenCrate() {
        return chosenCrate;
    }

    /**
     * Set chosen crate.
     *
     * @param chosenCrate no. of crate object chosen by player
     */
    public static void setChosenCrate(int chosenCrate) {
        settingController.chosenCrate = chosenCrate;
    }

    @FXML
    private void initialize() {
    }

    /**
     * Get chosen floor.
     *
     * @return chosen floor
     */
    public static int getChosenFloor() {
        return chosenFloor;
    }

    /**
     * Get chosen wall.
     *
     * @return chosen wall
     */
    public static int getChosenWall() {
        return chosenWall;
    }

    /**
     * Switch to the start page.
     *
     * <p>After clicking 'cancel' button,
     * go back to start page.</p>
     *
     * @throws IOException file may not exist
     */
    public void goBack() throws IOException {
        Main main = new Main();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/start.fxml"));
        Parent start = loader.load();
        primaryStage = (Stage) save.getScene().getWindow();
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
     * Save the settings modified by player.
     *
     * <p>After clicking 'save' button, settings will be saved
     * and stage will show start page.</p>
     *
     * @param actionEvent mouse click
     * @throws IOException player may not choose
     */
    public void saveSetting(ActionEvent actionEvent) throws IOException {
        RadioButton selectedFloor = (RadioButton) floorGroup.getSelectedToggle();
        setChosenFloor(Integer.parseInt(selectedFloor.getText()));

        RadioButton selectedWall = (RadioButton) wallGroup.getSelectedToggle();
        setChosenWall(Integer.parseInt(selectedWall.getText()));

        RadioButton selectedKeeper = (RadioButton) keeperGroup.getSelectedToggle();
        setChosenKeeper(Integer.parseInt(selectedKeeper.getText()));

        RadioButton selectedCrate = (RadioButton) crateGroup.getSelectedToggle();
        setChosenCrate(Integer.parseInt(selectedCrate.getText()));

        goBack();
    }

    /**
     * Set chosen floor.
     *
     * @param chosenFloor chosen floor
     */
    public void setChosenFloor(int chosenFloor) {
        settingController.chosenFloor = chosenFloor;
    }

    /**
     * Set chosen wall.
     *
     * @param chosenWall chosen wall
     */
    public void setChosenWall(int chosenWall) {
        settingController.chosenWall = chosenWall;
    }

}

