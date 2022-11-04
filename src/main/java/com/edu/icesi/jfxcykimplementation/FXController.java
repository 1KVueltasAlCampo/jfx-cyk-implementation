package com.edu.icesi.jfxcykimplementation;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FXController {

    @FXML
    private BorderPane mainPanel;

    @FXML
    private TableView<Production> tvMainTable;

    @FXML
    private TextField tfStringToEvaluate;

    @FXML
    private Label lbTitle;

    @FXML
    private TextField tfHeadOfProduction;

    @FXML
    private TextField tfBodyOfProduction;

    @FXML
    private TableView<BodyP> tvBodies;

    @FXML
    private Button btnAddProduction;

    @FXML
    private Button btnEditProduction;

    private ObservableList<BodyP> tempBodies;

    private Production tempProduction;

    private ObservableList<Production> productions;

    public FXController(){
        List<BodyP> tempList = new ArrayList<>();
        setTempBodies(tempList);
        List<Production> productionsList = new ArrayList<>();
        productions = FXCollections.observableArrayList(productionsList);
    }

    private void setTempBodies(List<BodyP> bodies){
        tempBodies = FXCollections.observableArrayList(bodies);
    }

    public void loadInitialTable() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("initial-table.fxml"));
        fxmlLoader.setController(this);
        Parent managerPane = fxmlLoader.load();
        createTableOfProductions();
        mainPanel.setCenter(managerPane);
    }

    @FXML
    void loadInitialTable(ActionEvent event) throws IOException {
        loadInitialTable();
    }

    public void loadBodiesOfProduction(int option, String headOfProduction) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-edit-production.fxml"));
        fxmlLoader.setController(this);
        Parent managerPane = fxmlLoader.load();
        fillHeadTextField(option, headOfProduction);
        changeTitle(option);
        showButton(option);
        createTableOfBodies();
        mainPanel.setCenter(managerPane);
    }

    private void createTableOfBodies(){
        List<TableColumn<BodyP, String>> columns = new ArrayList<>();
        TableColumn<BodyP, String> bodies = new TableColumn<>("Bodies of Production");
        bodies.setCellValueFactory(
                new PropertyValueFactory<BodyP,String>("bodyOfProduction")
        );
        columns.add(bodies);
        ObservableList<TableColumn<BodyP, String>> observableList = FXCollections.observableArrayList(columns);
        tvBodies.getColumns().addAll(observableList);
        tvBodies.setItems(tempBodies);
    }

    private void createTableOfProductions(){
        List<TableColumn<Production, String>> columns = new ArrayList<>();
        TableColumn<Production, String> heads = new TableColumn<>("Head");
        heads.setCellValueFactory(
                new PropertyValueFactory<Production, String>("head")
        );
        columns.add(heads);
        TableColumn<Production, String> bodies = new TableColumn<>("Bodies");
        bodies.setCellValueFactory(
                new PropertyValueFactory<Production, String>("bodiesString")
        );
        columns.add(bodies);
        ObservableList<TableColumn<Production, String>> observableList = FXCollections.observableArrayList(columns);
        tvMainTable.getColumns().addAll(observableList);
        tvMainTable.setItems(productions);
    }

    private void showButton(int option){
        switch (option){
            case 1: //Add production
                btnAddProduction.setDisable(false);
                btnAddProduction.setVisible(true);
                break;
            case 2: //Edit production
                btnEditProduction.setDisable(false);
                btnEditProduction.setVisible(true);
                break;
        }
    }
    private void changeTitle(int option){
        switch (option){
            case 1: //Add production
                lbTitle.setText("Add Production");
                break;
            case 2: //Edit production
                lbTitle.setText("Edit Production");
                break;
        }
        lbTitle.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(lbTitle, 0.0);
        AnchorPane.setRightAnchor(lbTitle, 0.0);
        lbTitle.setAlignment(Pos.CENTER);
    }

    private void fillHeadTextField(int option, String headOfProduction){
        if(option == 2){ //If the user wants to edit the production
            tfHeadOfProduction.setText(headOfProduction);
        }
    }

    @FXML
    void addPro(ActionEvent event) throws IOException {
        List<BodyP> tempList = new ArrayList<>();
        setTempBodies(tempList);
        loadBodiesOfProduction(1, null);
    }

    @FXML
    void deleteProduction(ActionEvent event) {

    }

    @FXML
    void editPro(ActionEvent event) throws IOException {
        if(tvMainTable.getSelectionModel().getSelectedItems().size() == 0) {
            showWarningAlert(null, null, "Select the production you want to edit");
        }else{
            Production productionToEdit = tvMainTable.getSelectionModel().getSelectedItems().get(0);
            setTempBodies(productionToEdit.getBodies());
            tempProduction = productionToEdit;
            loadBodiesOfProduction(2, productionToEdit.getHead());
        }
    }

    @FXML
    void runCYK(ActionEvent event) {

    }

    @FXML
    void addBodyOfProduction(ActionEvent event) {
        String bodyOfProduction = tfBodyOfProduction.getText();
        if(bodyOfProduction.equals("")){ //Checks if the production's body is empty
            showWarningAlert(null, null, "Please enter the body of the production");
        }else if(searchBodyOfProduction(bodyOfProduction) != -1){ //Checks if the production's body exists
            showWarningAlert(null, null, "You have already entered the body of production " + bodyOfProduction);
        }else if(bodyOfProduction.contains(" ")){ //Checks if there are a blank space
            showWarningAlert(null, null, "Enter the production's body without blank spaces");
        }else{
            tempBodies.add(
                    new BodyP(bodyOfProduction)
            );
            tfBodyOfProduction.clear();
        }
    }

    @FXML
    void deleteBodyOfProduction(ActionEvent event) {
        if(tvBodies.getSelectionModel().getSelectedItems().size() == 0) {
            showWarningAlert(null, null, "Select the production's body you want to delete");
        }else {
            tvBodies.getItems().removeAll(
                    tvBodies.getSelectionModel().getSelectedItems()
            );
        }
    }

    @FXML
    void addProduction(ActionEvent event) throws IOException {
        String headOfProduction = tfHeadOfProduction.getText();
        if(checkProduction(headOfProduction)){
            Production production = new Production(headOfProduction, tempBodies);
            productions.add(production);
            loadInitialTable();
        }
    }

    @FXML
    void editProduction(ActionEvent event) {

    }

    private boolean checkProduction(String headOfproduction){
        boolean allIsFine = true;
        System.out.println(headOfproduction);
        if(headOfproduction.equals("")){ //Checks if the production's name is empty
            showWarningAlert(null, null, "Please enter the head of the production");
            allIsFine = false;
        }else if(searchProduction(headOfproduction) != -1){ //Checks if the production's name is unique
            showWarningAlert(null, null, "There is already a production with the head " + headOfproduction + ". Please enter another");
            allIsFine = false;
        }else if(tempBodies.size() == 0){ //Checks if there is at least one production's body
            showWarningAlert(null, null, "Please enter at least one production's body");
            allIsFine = false;
        }
        return allIsFine;
    }

    private int searchProduction(String headOfProduction){
        int index = -1;
        boolean found = false;
        for(int i = 0; i < productions.size() && !found; i++){
            if(headOfProduction.equals(productions.get(i).getHead())){
                index = i;
                found = true;
            }
        }
        return index;
    }

    private int searchBodyOfProduction(String bodyOfProduction){
        int index = -1;
        boolean found = false;
        for(int i = 0; i < tempBodies.size() && !found; i++){
            if(bodyOfProduction.equals(tempBodies.get(i).getBodyOfProduction())){
                index = i;
                found = true;
            }
        }
        return index;
    }

    /**
     * Shows a warning alert <br>
     * @param title The title of the warning
     * @param header The header of the warning
     * @param content The content of the warning
     */
    public void showWarningAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Shows an information alert
     * @param title The title of the information alert
     * @param header The header of the information alert
     * @param content The content of the information alert
     */
    public void showInformationAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static class Production{

        private String head;

        private List<BodyP> bodies;

        private String bodiesString;

        private Production(String headP, List<BodyP> bodiesP){
            head = headP;
            bodies = bodiesP;
            createRepresentationOfBodies();
        }

        private void createRepresentationOfBodies(){
            bodiesString = "";
            for(int i = 0; i < bodies.size()-1; i++){
                bodiesString += bodies.get(i).getBodyOfProduction() + " | ";
            }
            bodiesString += bodies.get(bodies.size()-1).getBodyOfProduction();
        }

        public List<BodyP> getBodies() {
            return bodies;
        }

        public String getBodiesString() {
            return bodiesString;
        }

        public String getHead() {
            return head;
        }
    }

    public static class BodyP{
        private SimpleStringProperty bodyOfProduction;

        private BodyP(String bodyOfP){
            this.bodyOfProduction = new SimpleStringProperty(bodyOfP);
        }

        public String getBodyOfProduction() {
            return bodyOfProduction.get();
        }
    }

}
