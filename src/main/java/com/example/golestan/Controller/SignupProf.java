package com.example.golestan.Controller;

import com.example.golestan.Account.ProfessorAccount;
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

public class SignupProf {

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<String> collegeInput;

    @FXML
    private TextField firstnameInput;

    @FXML
    private TextField gruopInput;

    @FXML
    private TextField lastnameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Button quitButton;

    @FXML
    private Button signupButton;

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
        ProfessorAccount professorAccount = new ProfessorAccount();
        boolean valid = professorAccount.signup(usernameInput.getText(),
                passwordInput.getText(),
                firstnameInput.getText(),
                lastnameInput.getText(),
                gruopInput.getText(),
                collegeInput.getValue());
        if (valid) {
            control.switchScene(MainApplication.window, "ProfessorDashboard.fxml");
        }
    }

    public void initialize() throws SQLException {
        ObservableList<String> existCollege = FXCollections.observableArrayList();
        CollegeDB collegeDB = new CollegeDB();
        existCollege.addAll(collegeDB.collegeNames());
        collegeInput.setItems(existCollege);
    }
}
