package com.ae2dms;

import com.ae2dms.Main;
import com.ae2dms.model.GameEngine;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;
import java.net.URL;

import static org.testfx.api.FxAssert.verifyThat;

/**
 * Test Main class.
 *
 * @author Sijin WANG
 * @since 20 November 2020
 */
public class MainTest extends ApplicationTest {

    /**
     * Release key press and mouse click.
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    /**
     * Test start button.
     */
    @Test
    public void startBtnTest() {
        verifyThat("#btnStart", NodeMatchers.isNotNull());
        clickOn("#btnStart");
    }

    /**
     * Test info button.
     */
    @Test
    public void infoBtnTest() {
        verifyThat("#btnInfo", NodeMatchers.isNotNull());
        clickOn("#btnInfo");
    }

    /**
     * Test setting button.
     *
     * <p>Ensure all the labels and radio buttons have the correct value.</p>
     */
    @Test
    public void settingBtnTest() {
        verifyThat("#setting", NodeMatchers.isNotNull());
        clickOn("#setting");
    }

    /**
     * Show the start page.
     * @param primaryStage current stage
     * @throws IOException fxml may not exist
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Main main = new Main();
        URL location = getClass().getClassLoader().getResource("fxml/start.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        Group group = new Group();
        Node animation = main.animation();
        group.getChildren().addAll(root, animation);

        primaryStage.setTitle(GameEngine.GAME_NAME);
        Scene scene = new Scene(group, Color.rgb(171, 229, 243));
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("startPage.css").toExternalForm());
        primaryStage.show();
    }

    /**
     * helper method to retrieve JavaFX GUI components
     * @param query fx:id
     * @param <T> Node
     * @return specified node
     */
    public <T extends Node> T find(final String query) {
        return (T) lookup(query).queryAll().iterator().next();
        //return lookup(query).query();
    }
}