package com.example.golestan.Controller;

import com.example.golestan.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class UniDashboard {

    @FXML
    private TextField codeInput;

    @FXML
    private ChoiceBox<?> collegeInput;

    @FXML
    private Button createCourseButton;

    @FXML
    private Button createFacultyButton;

    @FXML
    private Button createSemesterButton;

    @FXML
    private ChoiceBox<?> dayInput;

    @FXML
    private TextField endClassInput;

    @FXML
    private TextField firstnameProfInput;

    @FXML
    private TextField lastnameProfInput;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField nameCourseInput;

    @FXML
    private TextField nameInput;

    @FXML
    private Button quitButton;

    @FXML
    private ChoiceBox<?> semesterInput;

    @FXML
    private ChoiceBox<?> semesterYearInput;

    @FXML
    private ChoiceBox<?> semsterNameInput;

    @FXML
    private TextField startClassInput;

    @FXML
    private TextField vahedInput;

    @FXML
    void createCourseClicked(ActionEvent event) {

    }

    @FXML
    void createFacultyClicked(ActionEvent event) {

    }

    @FXML
    void createSemesterClicked(ActionEvent event) {

    }

    @FXML
    void logoutClicked(ActionEvent event) throws IOException {
        SceneController control = new SceneController();
        control.switchScene(MainApplication.window, "Login.fxml");
    }

    @FXML
    void quitClicked(ActionEvent event) {
        SceneController control = new SceneController();
        control.closeProgram(MainApplication.window);
    }

}
