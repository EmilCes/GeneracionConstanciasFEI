module com.uv.generacionConstanciasFEI {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    exports main;
    exports controllers;

    opens controllers to javafx.fxml;
}