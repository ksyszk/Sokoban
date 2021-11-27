package com.ae2dms.controller;

import com.ae2dms.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;

import static org.testfx.api.FxAssert.verifyThat;

/**
 * Test basicFeatureController.
 *
 * @author Sijin WANG
 * @since 20 November 2020
 */
public class basicFeatureControllerTest extends ApplicationTest {

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
     * Test back to previous page.
     *
     * <p>Test whether topic and text in label are correct.</p>
     */
    @Test
    public void back() {
        clickOn("#goBack");
        sleep(1000);
        verifyThat("#topic", (Label label) -> {
            String text = label.getText();
            return text.contains("How to play");
        });

        verifyThat("#label", (Label label) -> {
            String text = label.getText();
            return text.contains("You are the lovely DOG! You can control it by keyboard.\n" +
                    "You task it to push the closed box to the island, \n" +
                    "and you will get a opened box full of treasure!");
        });
    }

    /**
     * Test switch to next page.
     *
     * <p>Test whether topic and text in label are correct.</p>
     */
    @Test
    public void next() {
        clickOn("#next");
        sleep(1000);
        verifyThat("#topic", (Label label) -> {
            String text = label.getText();
            return text.contains("High score");
        });

        verifyThat("#label", (Label label) -> {
            String text = label.getText();
            return text.contains("After complete each level, \n" +
                    "a high score pop-up will be shown.\n" +
                    "\n" +
                    "It will tell you \n" +
                    "how many steps you have moved,\n" +
                    "your rank, \n" +
                    "and the score of \n" +
                    "the first twenty players.");
        });
    }

    /**
     * Show the basic features info page.
     * @param stage current stage
     * @throws IOException fxml may not exist
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent mainNode = FXMLLoader.load(Main.class.getClassLoader().getResource("fxml/basicFeatures.fxml"));
        Scene scene = new Scene(mainNode);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }
}