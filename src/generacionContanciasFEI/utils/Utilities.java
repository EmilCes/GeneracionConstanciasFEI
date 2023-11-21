package utils;

import controllers.FXMLLoginController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;
import java.net.URL;

public class Utilities {

    public static Scene initializeScene(String path){
        Scene scene = null;
        try {
            //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resourceUrl = FXMLLoginController.class.getResource(path);
            Parent vista = FXMLLoader.load(resourceUrl);
            scene = new Scene(vista);
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }
        return scene;
    }

    public static void centerStage(Stage stage) {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((visualBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((visualBounds.getHeight() - stage.getHeight()) / 2);
    }
}
