package com.ae2dms.controller;

import com.ae2dms.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
 * Test portalInfoController.
 *
 * @author Sijin WANG
 * @since 29 November 2020
 */
public class portalInfoControllerTest extends ApplicationTest {

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
            return text.contains("Interesting features (4)");
        });

        verifyThat("#label", (Label label) -> {
            String text = label.getText();
            return text.contains("You can choose your own setting here!\n" +
                    "Various objects can be set in the game.\n" +
                    "Just choose the image you prefer =_=");
        });
    }

    /**
     * Test switch to the start page.
     *
     * <p>Test whether buttons are correct.</p>
     */
    @Test
    public void next() {
        clickOn("#next");
        sleep(1000);
        verifyThat("#btnStart", (Button button) -> {
            String text = button.getText();
            return text.contains("Start");
        });

        verifyThat("#btnInfo", (Button button) -> {
            String text = button.getText();
            return text.contains("Info");
        });
    }

    /**
     * Show the portal info page.
     * @param stage current stage
     * @throws IOException fxml may not exist
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent mainNode = FXMLLoader.load(Main.class.getClassLoader().getResource("fxml/portalInfo.fxml"));
        Scene scene = new Scene(mainNode);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }
}