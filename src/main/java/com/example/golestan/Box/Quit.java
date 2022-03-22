package com.example.golestan.Box;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Quit {
    private boolean answer;

    public boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);
        label.setTextFill(Paint.valueOf("#0c31a1"));

        Button yes = new Button("Yes");
        Button no = new Button("No");
        yes.setTextFill(Paint.valueOf("#ffffff"));
        yes.setStyle("-fx-background-color: #0c31a1");
        no.setTextFill(Paint.valueOf("#ffffff"));
        no.setStyle("-fx-background-color: #0c31a1");

        yes.setOnAction(event -> {
            answer = true;
            window.close();
        });

        no.setOnAction(event -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yes, no);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();

        return answer;
    }
}
