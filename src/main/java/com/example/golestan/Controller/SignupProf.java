package com.example.golestan.Controller;

import com.example.golestan.Account.ProfessorAccount;
import com.example.golestan.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class SignupProf {

    @FXML
    private Button backButton;

    @FXML
    private TextField collegeInput;

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
                collegeInput.getText());
        if (valid) {
            control.switchScene(MainApplication.window, "ProfessorDashboard.fxml");
        }
    }

}
