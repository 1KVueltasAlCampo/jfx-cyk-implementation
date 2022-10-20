module com.edu.icesi.jfxcykimplementation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.edu.icesi.jfxcykimplementation to javafx.fxml;
    exports com.edu.icesi.jfxcykimplementation;
}