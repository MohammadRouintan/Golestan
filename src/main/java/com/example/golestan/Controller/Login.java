package com.example.golestan.Controller;

import com.example.golestan.Box.Job;
import com.example.golestan.Box.Jobs;
import com.example.golestan.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    ObservableList<String> jobs = FXCollections
            .observableArrayList("University", "Professor", "Student");

    @FXML
    private ChoiceBox<String> jobInput;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Button quitButton;

    @FXML
    private Button signupButton;

    @FXML
    private TextField usernameInput;

    @FXML
    void loginClicked(ActionEvent event) {

    }

    @FXML
    void quitClicked(ActionEvent event) {
        SceneController control = new SceneController();
        control.closeProgram(MainApplication.window);
    }

    @FXML
    void signupClicked(ActionEvent event) throws IOException {
        SceneController control = new SceneController();
        Jobs job = Job.display("Job", "PLEASE SELECT YOUR JOB :");
        switch (job) {
            case UNIVERSITY:
                control.switchScene(MainApplication.window,"SignupUni.fxml");
                break;
            case PROFESSOR:
                control.switchScene(MainApplication.window,"SignupProf.fxml");
                break;
            case STUDENT:
                control.switchScene(MainApplication.window,"SignupStu.fxml");
                break;
            default:
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        jobInput.setItems(jobs);
    }
}
