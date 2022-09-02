package com.example.golestan;

import com.example.golestan.Controller.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApplication extends Application {
    public static Stage window;
    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        SceneController control = new SceneController();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Golestan");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            event.consume();
            control.closeProgram(stage);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}