package com.example.golestan.Controller;

import com.example.golestan.Box.Quit;
import com.example.golestan.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneController {
    public void switchScene(Stage window, String fxml) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource(fxml)));
        window.getScene().setRoot(pane);
    }

    public void closeProgram(Stage window) {
        Quit quit = new Quit();
        boolean flag = quit.display("Quit", "DO YOU WANT TO EXIT ?");
        if (flag) {
            window.close();
        }
    }
}
