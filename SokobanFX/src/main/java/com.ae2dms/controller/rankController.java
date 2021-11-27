package com.ae2dms.controller;

import com.ae2dms.model.GameEngine;
import com.ae2dms.model.record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Show rank after each level.
 *
 * @author Sijin WANG
 * @since 3 December 2020
 */
public class rankController {
    private GameEngine gameEngine = gameController.getGameEngine();

    @FXML // fx:id="table"
    private TableView<record> table; // Value injected by FXMLLoader

    @FXML // fx:id="label"
    private Label label; // Value injected by FXMLLoader

    @FXML // fx:id="num"
    private TableColumn num; // Value injected by FXMLLoader

    @FXML // fx:id="step"
    private TableColumn step; // Value injected by FXMLLoader

    @FXML // fx:id="score"
    private TableColumn score; // Value injected by FXMLLoader

    /**
     * Initialize the stage.
     */
    @FXML
    void initialize() {
        label.setText("You moved " + gameEngine.getEachLevelCount() + " steps!\n"
                + "Your rank is No. " + gameEngine.rankNum);
        label.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20));

        ObservableList<record> list = FXCollections.observableArrayList();

        for(int i = 0; i < gameEngine.finalRank.size(); i++) {
            if(i < 20) {
                list.add(new record(Integer.toString(i+1), gameEngine.finalRank.get(i), Integer.toString(10000 - Integer.parseInt(gameEngine.finalRank.get(i)))));
            } else {
                break;
            }
        }

        num.setCellValueFactory(new PropertyValueFactory<>("num"));
        step.setCellValueFactory(new PropertyValueFactory<>("move"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));

        table.setItems(list);
    }

}

