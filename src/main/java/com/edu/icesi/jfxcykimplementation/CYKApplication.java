package com.edu.icesi.jfxcykimplementation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CYKApplication extends Application {

    private FXController guiController;

    /**
     * The constructor method of the CYKApplication class <br>
     */
    public CYKApplication(){
        guiController = new FXController();
    }

    /**
     * Starts the GUI
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CYKApplication.class.getResource("main-scene.fxml"));
        fxmlLoader.setController(guiController);
        Parent root;
        try{
            root = fxmlLoader.load();
            guiController.loadInitialTable();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("CYK Implementation");
            stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param args The command line parameters
     */
    public static void main(String[] args) {
        launch(args);
    }
}