module com.uv.generacionConstanciasFEI {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires mysql.connector.java;
    requires org.apache.poi.ooxml;
    requires kernel;
    requires layout;
    requires itextpdf;

    exports main;
    exports controllers;

    opens controllers to javafx.fxml;
}