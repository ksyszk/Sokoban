package com.ae2dms.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;

import static org.testfx.api.FxAssert.verifyThat;

/**
 * Test settingController.
 *
 * @author Sijin WANG
 * @since 3 December 2020
 */
public class settingControllerTest extends ApplicationTest {

    /**
     * Test back to the start page.
     *
     * <p>Test whether buttons are correct.</p>
     */
    @Test
    public void goBack() {
        clickOn("#cancel");
        sleep(1000);
        verifyThat("#btnStart", (Button button) -> {
            String text = button.getText();
            return text.contains("Start");
        });

        verifyThat("#btnInfo", (Button button) -> {
            String text = button.getText();
            return text.contains("Info");
        });

        verifyThat("#setting", (Button button) -> {
            String text = button.getText();
            return text.contains("Setting");
        });
    }

    /**
     * Test whether get the initial value of radio buttons.
     */
    @Test
    public void saveSettingInit() {
        verifyThat("#save", NodeMatchers.isNotNull());

        clickOn("#save");
        sleep(1000);

        Assert.assertEquals(1, com.ae2dms.controller.settingController.getChosenFloor());
        Assert.assertEquals(1, com.ae2dms.controller.settingController.getChosenWall());
        Assert.assertEquals(1, com.ae2dms.controller.settingController.getChosenKeeper());
        Assert.assertEquals(1, com.ae2dms.controller.settingController.getChosenCrate());
    }


    /**
     * Test whether set the correct value.
     *
     * <p>Player choose the second floor and keeper,
     * so the value of floor and keeper will be set to 2.</p>
     */
    @Test
    public void saveSettingFK2() {
        verifyThat("#save", NodeMatchers.isNotNull());

        clickOn("#floor2");
        sleep(1000);
        clickOn("#keeper2");
        sleep(1000);
        clickOn("#save");
        sleep(1000);

        Assert.assertEquals(2, com.ae2dms.controller.settingController.getChosenFloor());
        Assert.assertEquals(1, com.ae2dms.controller.settingController.getChosenWall());
        Assert.assertEquals(2, com.ae2dms.controller.settingController.getChosenKeeper());
        Assert.assertEquals(1, com.ae2dms.controller.settingController.getChosenCrate());
    }

    /**
     * Show the setting page.
     * @param primaryStage current stage
     * @throws IOException fxml may not exist
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/setting.fxml"));
        Parent info = loader.load();
        // load page
        primaryStage.setTitle("Setting");
        Scene scene = new Scene(info);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("startPage.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}