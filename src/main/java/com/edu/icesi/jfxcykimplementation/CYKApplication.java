package com.edu.icesi.jfxcykimplementation;

import com.edu.icesi.jfxcykimplementation.model.CYKAlgorithm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
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