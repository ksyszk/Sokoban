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
 * Test settingInfoController.
 *
 * @author Sijin WANG
 * @since 20 November 2020
 */
public class settingInfoControllerTest extends ApplicationTest {

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
            return text.contains("Interesting features (3)");
        });

        verifyThat("#label", (Label label) -> {
            String text = label.getText();
            return text.contains("If you achieve some specified conditions,\n" +
                    "you will get an Achievement!\n" +
                    "\n" +
                    "More important, a golden CUP \n" +
                    "will be shown on the top of the page.\n" +
                    "\n" +
                    "You can also click these cup to view\n" +
                    "which achievement you have already got.\n" +
                    "Just like this ←\n" +
                    "\n" +
                    "There are THREE achievements in total.\n" +
                    "Try your best to collect all the cups!");
        });
    }

    /**
     * Test switch to the next page.
     *
     * <p>Test whether buttons are correct.</p>
     */
    @Test
    public void next() {
        clickOn("#next");
        sleep(1000);
        verifyThat("#topic", (Label label) -> {
            String text = label.getText();
            return text.contains("Interesting features (5)");
        });

        verifyThat("#label", (Label label) -> {
            String text = label.getText();
            return text.contains("Every level has a secret point.\n" +
                    "An invisible PORTAL is settled on this point.\n" +
                    "When you reach this point, it will take you to \n" +
                    "the end of current level !\n" +
                    "In the other word, you just skip this level !!");
        });
    }

    /**
     * Show the setting info page.
     * @param stage current stage
     * @throws IOException fxml may not exist
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent mainNode = FXMLLoader.load(Main.class.getClassLoader().getResource("fxml/settingInfo.fxml"));
        Scene scene = new Scene(mainNode);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }
}