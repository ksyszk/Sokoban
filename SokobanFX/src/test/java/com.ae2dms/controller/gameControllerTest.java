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
public class gameControllerTest extends ApplicationTest {
    MainTest mainTest = new MainTest();

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
     * Ensure that the number of menu items is correct.
     */
    @Test
    public void ensureMenuBar() {
        verifyThat("#menuBar", NodeMatchers.isNotNull());

        MenuBar menu = mainTest.find("#menuBar");
        WaitForAsyncUtils.waitForFxEvents();
        Assert.assertEquals(4, menu.getMenus().size());
    }

    /**
     * Test whether menuItems in File are exist.
     */
    @Test
    public void ensureMenuItemFile() {
        clickOn("#menuFile");
        sleep(1000);
        verifyThat("#exit", NodeMatchers.isNotNull());
        verifyThat("#saveGame", NodeMatchers.isNotNull());
        verifyThat("#loadGame", NodeMatchers.isNotNull());
    }

    /**
     * Test whether menuItems in Level are exist.
     */
    @Test
    public void ensureMenuItemLevel() {
        clickOn("#menuLevel");
        sleep(1000);
        verifyThat("#undo", NodeMatchers.isNotNull());
        verifyThat("#music", NodeMatchers.isNotNull());
        verifyThat("#debug", NodeMatchers.isNotNull());
    }

    /**
     * Test whether menuItems in About are exist.
     */
    @Test
    public void ensureMenuItemAbout() {
        clickOn("#menuAbout");
        sleep(1000);
        verifyThat("#about", NodeMatchers.isNotNull());
    }

    /**
     * Test whether menuItems in More are exist.
     */
    @Test
    public void ensureMenuItemMore() {
        clickOn("#menuMore");
        sleep(1000);
        verifyThat("#reverse", NodeMatchers.isNotNull());
    }

    /**
     * Ensure that the initial value of move count is correct.
     */
    @Test
    public void updateMoveCountTest() {
        Label moves = mainTest.find("#moves");
        WaitForAsyncUtils.waitForFxEvents();
        Assert.assertEquals("Moves: ", moves.getText());

        Label count = mainTest.find("#moveCount");
        WaitForAsyncUtils.waitForFxEvents();
        Assert.assertEquals("  ", count.getText());
    }
}