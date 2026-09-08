module com.example.regattend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.regattend to javafx.fxml;
    opens com.example.regattend.controller to javafx.fxml;
    opens com.example.regattend.model.entity to javafx.base;

    exports com.example.regattend;
    exports com.example.regattend.controller;
    exports com.example.regattend.model.entity;
}