package com.edu.icesi.jfxcykimplementation;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FXController {

    @FXML
    private BorderPane mainPanel;

    @FXML
    private Button btnDelete;
    @FXML
    private TableView<Production> tvMainTable;

    @FXML
    private TextField tfStringToEvaluate;

    @FXML
    private TextField tfHeadOfProduction;

    private Production tempProduction;

    private ObservableList<Production> productions;

    public FXController(){
        List<Production> productionsList = new ArrayList<>();
        productions = FXCollections.observableArrayList(productionsList);
    }



    public void loadInitialTable() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("initial-table.fxml"));
        fxmlLoader.setController(this);
        Parent managerPane = fxmlLoader.load();
        createTableOfProductions();
        btnDelete.getStyleClass().add("critical_button");
        mainPanel.setCenter(managerPane);
    }

    private void createTableOfProductions(){
        double prefWithOfTV = tvMainTable.getPrefWidth();
        double percentageH = 0.09;
        double percentageB = 0.9;
        List<TableColumn<Production, String>> columns = new ArrayList<>();
        TableColumn<Production, String> heads = new TableColumn<>("Head");
        heads.setCellValueFactory(
                new PropertyValueFactory<Production, String>("representationHead")
        );
        heads.setStyle("-fx-alignment: CENTER-RIGHT;");
        heads.setPrefWidth(percentageH*prefWithOfTV);
        columns.add(heads);
        TableColumn<Production, String> bodies = new TableColumn<>("Bodies");
        bodies.setCellValueFactory(
                new PropertyValueFactory<Production, String>("bodies")
        );
        bodies.setStyle("-fx-alignment: CENTER-LEFT;");
        bodies.setPrefWidth(percentageB*prefWithOfTV);
        columns.add(bodies);

        ObservableList<TableColumn<Production, String>> observableList = FXCollections.observableArrayList(columns);
        tvMainTable.getStyleClass().add("no_header");
        tvMainTable.getColumns().addAll(observableList);
        tvMainTable.setItems(productions);
    }

    @FXML
    void addPro(ActionEvent event) throws IOException {
        String headOfProduction = tfHeadOfProduction.getText().toUpperCase();
        if(headOfProduction.equals("")){ //Checks if the production head is empty
            showWarningAlert(null, null, "Please enter the head of the production");
        }else if(searchProduction(headOfProduction) != -1){
            showWarningAlert(null, null, "The " + headOfProduction + " production head already exists");
        }else if(headOfProduction.contains(" ")){
            showWarningAlert(null, null, "Enter the production head without blanket spaces");
        }else{
            Production temp = new Production(headOfProduction);
            productions.add(temp);
        }

    }

    @FXML
    void deleteProduction(ActionEvent event) {
        if(tvMainTable.getSelectionModel().getSelectedItems().size() == 0) {
            showWarningAlert(null, null, "Select the production you want to delete");
        }else{
            tvMainTable.getItems().removeAll(
                    tvMainTable.getSelectionModel().getSelectedItems()
            );
        }
    }

    @FXML
    void runCYK(ActionEvent event) {
        String[] headsOfProductions = new String[productions.size()];
        ArrayList<String[]> bodiesOfProductions = new ArrayList<>();

        for(int i = 0; i < productions.size(); i++){
            headsOfProductions[i] = productions.get(i).getHead();
            String[] tempBodies = productions.get(i).getBodies().getText().split("|");
            for(int j = 0; j < tempBodies.length; j++){
                tempBodies[j] = tempBodies[j].trim();
            }
            bodiesOfProductions.add(tempBodies);
        }
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

        private String representationHead;

        private TextField bodies;

        private Production(String headP){
            head = headP.toUpperCase();
            representationHead = head + " ->";
            bodies = new TextField();
        }

        public TextField getBodies() {
            return bodies;
        }

        public String getHead() {
            return head;
        }

        public String getRepresentationHead() {
            return representationHead;
        }
    }

}
