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
 * Test reverseController.
 *
 * @author Sijin WANG
 * @since 20 November 2020
 */
public class reverseControllerTest extends ApplicationTest {

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
            return text.contains("Interesting features (1)");
        });

        verifyThat("#label", (Label label) -> {
            String text = label.getText();
            return text.contains("You can use 'W' 'A' 'S' 'D' to destroy walls.\n" +
                    "\n" +
                    "If there is no wall in your moving direction, \n" +
                    "just perform as MOVE.\n" +
                    "\n" +
                    "ATTENTION !!!!\n" +
                    "After destroying one wall,\n" +
                    "your step will add 20!");
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
     * Show the reverse info page.
     * @param stage current stage
     * @throws IOException fxml may not exist
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent mainNode = FXMLLoader.load(Main.class.getClassLoader().getResource("fxml/reverse.fxml"));
        Scene scene = new Scene(mainNode);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }
}