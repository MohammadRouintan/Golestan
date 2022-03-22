package com.example.golestan.Box;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Job {
    static Jobs job;
    public static Jobs display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);
        label.setTextFill(Paint.valueOf("#0c31a1"));

        Button uni = new Button("University");
        Button prof = new Button("Professor");
        Button stu = new Button("Student");
        uni.setTextFill(Paint.valueOf("#ffffff"));
        uni.setStyle("-fx-background-color: #0c31a1");
        prof.setTextFill(Paint.valueOf("#ffffff"));
        prof.setStyle("-fx-background-color: #0c31a1");
        stu.setTextFill(Paint.valueOf("#ffffff"));
        stu.setStyle("-fx-background-color: #0c31a1");

        uni.setOnAction(event -> {
            job = Jobs.UNIVERSITY;
            window.close();
        });

        prof.setOnAction(event -> {
            job = Jobs.PROFESSOR;
            window.close();
        });

        stu.setOnAction(event -> {
            job = Jobs.STUDENT;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, uni, prof, stu);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();

        return job;
    }
}
