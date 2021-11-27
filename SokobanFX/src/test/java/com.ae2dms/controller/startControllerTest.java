package com.ae2dms.controller;

import com.ae2dms.MainTest;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.matcher.base.NodeMatchers;

import java.util.function.Predicate;

import static org.testfx.api.FxAssert.verifyThat;

/**
 * Test startController.
 *
 * @author Sijin WANG
 * @since 20 November 2020
 */
public class startControllerTest extends MainTest {

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
    public void startGameTest() {
        verifyThat("#btnStart", NodeMatchers.isNotNull());
        clickOn("#btnStart");
    }

    /**
     * Test info button.
     */
    @Test
    public void showInfoTest() {
        clickOn("#btnInfo");
        sleep(1000);
        verifyThat("#label", (Label label) -> {
            String text = label.getText();
            return text.contains("You are the lovely DOG! You can control it by keyboard.\n" +
                    "You task it to push the closed box to the island, \n" +
                    "and you will get a opened box full of treasure!");
        });
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
        sleep(1000);

        verifyThat("#floor", (Label label) -> {
            String text = label.getText();
            return text.contains("Floor");
        });

        verifyThat("#wall", (Label label) -> {
            String text = label.getText();
            return text.contains("Wall");
        });

        verifyThat("#keeper", (Label label) -> {
            String text = label.getText();
            return text.contains("Keeper");
        });

        verifyThat("#crate", (Label label) -> {
            String text = label.getText();
            return text.contains("Crate");
        });

        //Boolean chosen = radioButton.isSelected();
        verifyThat("#floor1", (Predicate<RadioButton>) ToggleButton::isSelected);
        verifyThat("#floor2", (RadioButton radioButton) -> !radioButton.isSelected());

        verifyThat("#wall1", (Predicate<RadioButton>) ToggleButton::isSelected);
        verifyThat("#wall2", (RadioButton radioButton) -> !radioButton.isSelected());

        verifyThat("#keeper1", (Predicate<RadioButton>) ToggleButton::isSelected);
        verifyThat("#keeper2", (RadioButton radioButton) -> !radioButton.isSelected());

        verifyThat("#crate1", (Predicate<RadioButton>) ToggleButton::isSelected);
        verifyThat("#crate2", (RadioButton radioButton) -> !radioButton.isSelected());
    }
}