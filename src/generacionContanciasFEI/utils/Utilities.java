package utils;

import controllers.FXMLLoginController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
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

    public static <T> T changePane(AnchorPane stage, String path) {
        stage.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(Utilities.class.getResource(path));
            AnchorPane newPane = loader.load();
            stage.getChildren().add(newPane);
            return loader.getController();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    public static void showSimpleDialog(String title, String message, Alert.AlertType alertType) {
        Alert simpleAlert = new Alert(alertType);
        simpleAlert.setTitle(title);
        simpleAlert.setContentText(message);
        simpleAlert.setHeaderText(null);
        simpleAlert.showAndWait();
    }

    public static int showQuestionDialog(String title, String message) {
        Alert questionDialog = new Alert(Alert.AlertType.CONFIRMATION);
        questionDialog.setTitle(title);
        questionDialog.setContentText(message);
        questionDialog.setHeaderText(null);

        ButtonType btnYes = new ButtonType("Yes");
        ButtonType btnNo = new ButtonType("No");

        questionDialog.getButtonTypes().setAll(btnYes, btnNo);

        questionDialog.showAndWait().ifPresentOrElse(
                response -> {
                    if (response == btnYes) {
                        questionDialog.setResult(ButtonType.OK);
                    } else if (response == btnNo) {
                        questionDialog.setResult(ButtonType.CANCEL);
                    }
                },
                () -> {
                    questionDialog.setResult(ButtonType.CLOSE);
                }
        );

        if (questionDialog.getResult() == ButtonType.OK) {
            return 1;
        } else if (questionDialog.getResult() == ButtonType.CANCEL) {
            return 0;
        } else {
            return -1;
        }
    }
}
