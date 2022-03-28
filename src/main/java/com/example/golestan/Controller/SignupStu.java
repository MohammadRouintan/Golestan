package com.example.golestan.Controller;

import com.example.golestan.Account.StudentAccount;
import com.example.golestan.Database.CollegeDB;
import com.example.golestan.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class SignupStu {

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<String> collegeInput;

    @FXML
    private TextField enteringYearInput;

    @FXML
    private TextField firstnameInput;

    @FXML
    private TextField lastnameInput;

    @FXML
    private TextField majorSubjectInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Button quitButton;

    @FXML
    private Button signupButton;

    @FXML
    private TextField studentIdInput;

    @FXML
    private TextField usernameInput;

    @FXML
    void backClicked(ActionEvent event) throws IOException {
        SceneController control = new SceneController();
        control.switchScene(MainApplication.window, "Login.fxml");
    }

    @FXML
    void quitClicked(ActionEvent event) {
        SceneController control = new SceneController();
        control.closeProgram(MainApplication.window);
    }

    @FXML
    void signupClicked(ActionEvent event) throws SQLException, IOException {
        SceneController control = new SceneController();
        StudentAccount studentAccount = new StudentAccount();
        boolean valid = studentAccount.signup(studentIdInput.getText(),
                usernameInput.getText(),
                passwordInput.getText(),
                firstnameInput.getText(),
                lastnameInput.getText(),
                majorSubjectInput.getText(),
                collegeInput.getValue(),
                enteringYearInput.getText());
        if (valid) {
            StuDashboard.setUsername(usernameInput.getText());
            control.switchScene(MainApplication.window, "StudentDashboard.fxml");
        }
    }

    public void initialize() throws SQLException {
        ObservableList<String> collegeList = FXCollections.observableArrayList();
        CollegeDB college = new CollegeDB();
        collegeList.addAll(college.collegeNames());
        collegeInput.setItems(collegeList);
    }

}
