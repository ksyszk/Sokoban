package com.ae2dms.controller;

import com.ae2dms.MainTest;
import com.ae2dms.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;
import java.io.IOException;

import static org.testfx.api.FxAssert.verifyThat;

/**
 * Test gameController.
 *
 * @author Sijin WANG
 * @since 20 November 2020
 */
public class gameControllerTest2 extends ApplicationTest {
    /**
     * Show the game page.
     * @param stage current stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent mainNode = FXMLLoader.load(Main.class.getClassLoader().getResource("fxml/game.fxml"));
        Scene scene = new Scene(mainNode);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
        gameController gameController = new gameController();
        gameController.showBuff();
        gameController.showAchievementDestroy();
        gameController.showAchievementUndo();
        gameController.showAchievementReset();
    }

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
     * Test {@code showBuff()}.
     *
     * <p>Check whether texts are correct.</p>
     */
    @Test
    public void testShowBuff() {
        verifyThat("#topic", (Label label) -> {
            String text1 = label.getText();
            return text1.contains("You find the secret Portal !");
        });

        verifyThat("#text", (Label label) -> {
            String reason = label.getText();
            return reason.contains("Complete the level!");
        });
    }

    /**
     * Test {@code showAchievementDestroy()}.
     *
     * <p>Check whether texts are correct.</p>
     */
    @Test
    public void testShowAchievementDestroy() {
        verifyThat("#topicDestroy", (Label label) -> {
            String text1 = label.getText();
            return text1.contains("Congratulations!!!\n" +
                    "New Achievement");
        });

        verifyThat("#nameDestroy", (Label label) -> {
            String name = label.getText();
            return name.contains("Wall Terminator");
        });

        verifyThat("#reasonDestroy", (Label label) -> {
            String reason = label.getText();
            return reason.contains("Destroy 10 walls");
        });
    }

    /**
     * Test {@code showAchievementUndo()}.
     *
     * <p>Check whether texts are correct.</p>
     */
    @Test
    public void testShowAchievementUndo() {
        verifyThat("#topicUndo", (Label label) -> {
            String text1 = label.getText();
            return text1.contains("Congratulations!!!\n" +
                    "New Achievement");
        });

        verifyThat("#nameUndo", (Label label) -> {
            String name = label.getText();
            return name.contains("No regret medicine in real life :)");
        });

        verifyThat("#reasonUndo", (Label label) -> {
            String reason = label.getText();
            return reason.contains("Undo 10 times");
        });
    }

    /**
     * Test {@code showAchievementReset()}.
     *
     * <p>Check whether texts are correct.</p>
     */
    @Test
    public void testShowAchievementReset() {
        verifyThat("#topicReset", (Label label) -> {
            String text1 = label.getText();
            return text1.contains("Congratulations!!!\n" +
                    "New Achievement");
        });

        verifyThat("#nameReset", (Label label) -> {
            String name = label.getText();
            return name.contains("Always Restart =_=");
        });

        verifyThat("#reasonReset", (Label label) -> {
            String reason = label.getText();
            return reason.contains("Reset three times");
        });
    }
}