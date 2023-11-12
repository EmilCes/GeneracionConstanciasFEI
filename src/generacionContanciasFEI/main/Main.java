package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args)
    {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/FXMLLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("GCPPFEI");
        stage.setScene(scene);
        stage.show();
    }

}
