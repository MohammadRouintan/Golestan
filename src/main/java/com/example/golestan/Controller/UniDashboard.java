package com.example.golestan.Controller;

import com.example.golestan.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UniDashboard implements Initializable {

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
    private ComboBox<String> dayInput;

    @FXML
    private Button editandrestart;

    @FXML
    private TextField endClassInput;

    @FXML
    private TextField examDate;

    @FXML
    private TableView<?> facultyList;

    @FXML
    private TextField firstnameProfInput;

    @FXML
    private TextField lastnameProfInput;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField nameCourseInput;

    @FXML
    private TableColumn<?, ?> nameFaculty;

    @FXML
    private TextField nameInput;

    @FXML
    private TableColumn<?, ?> nameSemester;

    @FXML
    private TextField newName;

    @FXML
    private TextField newPassword;

    @FXML
    private TextField newUsername;

    @FXML
    private Button quitButton;

    @FXML
    private ChoiceBox<?> semesterInput;

    @FXML
    private TableView<?> semesterList;

    @FXML
    private ChoiceBox<Integer> semesterYearInput;

    @FXML
    private ChoiceBox<String> semsterNameInput;

    @FXML
    private TextField startClassInput;

    @FXML
    private TextField vahedInput;

    @FXML
    private TableColumn<?, ?> yearSemester;

    @FXML
    private CheckBox sat;

    @FXML
    private CheckBox sun;

    @FXML
    private CheckBox mon;

    @FXML
    private CheckBox tue;

    @FXML
    private CheckBox wed;

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
    void editClicked(ActionEvent event) throws IOException {
        SceneController control = new SceneController();
        control.switchScene(MainApplication.window, "Login.fxml");
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> semesterName = FXCollections.observableArrayList("Fall", "Winter", "Summer");
        semsterNameInput.setItems(semesterName);
        ObservableList<Integer> semesterYear = FXCollections.observableArrayList();
        for (int i = 0; i < 50; i++) {
            semesterYear.add(1385 + i);
        }
        semesterYearInput.setItems(semesterYear);
    }
}
