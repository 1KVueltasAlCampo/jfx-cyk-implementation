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
        testALaFuerzaBorrarDespues2();
        launch(args);
    }

    private static void testALaFuerzaBorrarDespues(){
        ArrayList<String[]> grammar = new ArrayList<>();
        ArrayList<String> heads = new ArrayList<>();
        heads.add("S");
        grammar.add(new String[]{"BA", "AC"});
        heads.add("A");
        grammar.add(new String[]{"CC", "b"});
        heads.add("B");
        grammar.add(new String[]{"AB", "a"});
        heads.add("C");
        grammar.add(new String[]{"BA", "a"});
        String string = "bbab";
        CYKAlgorithm algorithm = new CYKAlgorithm(grammar,heads,'S',string);
        algorithm.watchMatrix();
        System.out.println("La cadena esta: "+algorithm.confirmStringInGrammar());
    }

    private static void testALaFuerzaBorrarDespues2(){
        ArrayList<String[]> grammar = new ArrayList<>();
        ArrayList<String> heads = new ArrayList<>();
        heads.add("S");
        grammar.add(new String[]{"IB"});
        heads.add("I");
        grammar.add(new String[]{"AX", "AJ","a"});
        heads.add("J");
        grammar.add(new String[]{"YZ", "BZ","AK","a"});
        heads.add("K");
        grammar.add(new String[]{"AK", "a"});
        heads.add("A");
        grammar.add(new String[]{"a"});
        heads.add("B");
        grammar.add(new String[]{"b"});
        heads.add("C");
        grammar.add(new String[]{"c"});
        heads.add("X");
        grammar.add(new String[]{"IA"});
        heads.add("Y");
        grammar.add(new String[]{"BJ"});
        heads.add("Z");
        grammar.add(new String[]{"CC"});
        String string = "aaaabbaccccaaab";
        //String string = "abccb";
        CYKAlgorithm algorithm = new CYKAlgorithm(grammar,heads,'S',string);
        algorithm.watchMatrix();
        System.out.println("La cadena esta: "+algorithm.confirmStringInGrammar());
    }
}