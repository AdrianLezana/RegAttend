module com.example.regattend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.regattend.controller to javafx.fxml;
    exports com.example.regattend;
}