package com.edu.icesi.jfxcykimplementation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class FXController {

    @FXML
    private BorderPane mainPanel;

    @FXML
    private TableView<?> tbMainTable;

    @FXML
    private TextField tfStringToEvaluate;

    @FXML
    private Label lbTitle;

    @FXML
    private TextField tfHeadOfProduction;

    @FXML
    private TextField txBodyOfProduction;

    public FXController(){

    }

    public void loadInitialTable() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("initial-table.fxml"));
        fxmlLoader.setController(this);
        Parent managerPane = fxmlLoader.load();
        mainPanel.setCenter(managerPane);
    }

    @FXML
    void loadInitialTable(ActionEvent event) throws IOException {
        loadInitialTable();
    }

    public void loadBodiesOfProduction() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-edit-production.fxml"));
        fxmlLoader.setController(this);
        Parent managerPane = fxmlLoader.load();
        mainPanel.setCenter(managerPane);
    }


    @FXML
    void addProduction(ActionEvent event) throws IOException {
        loadBodiesOfProduction();
    }

    @FXML
    void deleteProduction(ActionEvent event) {

    }

    @FXML
    void editProduction(ActionEvent event) throws IOException {
        loadBodiesOfProduction();
    }

    @FXML
    void runCYK(ActionEvent event) {

    }

    @FXML
    void addBodyOfProduction(ActionEvent event) {

    }

    @FXML
    void deleteBodyOfProduction(ActionEvent event) {

    }


}
