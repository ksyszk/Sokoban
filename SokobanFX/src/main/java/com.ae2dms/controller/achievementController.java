package com.ae2dms.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * Show the overall achievements player has got.
 *
 * @author Sijin WANG
 * @since 3 December 2020
 */
public class achievementController {
    gameController gameController = new gameController();

    @FXML // fx:id="achieve1"
    private VBox achieve1; // Value injected by FXMLLoader

    @FXML // fx:id="achieve2"
    private VBox achieve2; // Value injected by FXMLLoader

    @FXML // fx:id="achieve3"
    private VBox achieve3; // Value injected by FXMLLoader

    /**
     * Initialize the dialog.
     */
    @FXML
    void initialize() {
        if(com.ae2dms.controller.gameController.isAchieveDestroy) {
            achieve1.setVisible(true);
        }

        if(com.ae2dms.controller.gameController.isAchieveUndo) {
            achieve2.setVisible(true);
        }

        if(com.ae2dms.controller.gameController.isAchieveReset) {
            achieve3.setVisible(true);
        }
    }

}

