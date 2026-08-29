module com.example.regattend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Para la base de datos SQLite

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.regattend to javafx.fxml;
    opens com.example.regattend.controller to javafx.fxml;
    opens com.example.regattend.model.entity to javafx.base; // Para el TableView
    
    exports com.example.regattend;
    exports com.example.regattend.controller;
}