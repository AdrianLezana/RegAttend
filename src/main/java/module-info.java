module com.example.regattend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.ooxml;

    opens com.example.regattend.controller to javafx.fxml;
    exports com.example.regattend;
    opens com.example.regattend.model.entity to javafx.base;
}