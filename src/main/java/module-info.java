module com.example.regattend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.regattend to javafx.fxml;
    exports com.example.regattend;
}