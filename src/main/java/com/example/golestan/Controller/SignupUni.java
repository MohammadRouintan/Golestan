package com.example.golestan.Controller;

import com.example.golestan.Account.UniversityAccount;
import com.example.golestan.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class SignupUni {

    @FXML
    private Button backButton;

    @FXML
    private TextField nameInput;

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
        UniversityAccount universityAccount = new UniversityAccount();
        boolean valid = universityAccount.signup(nameInput.getText(),
                usernameInput.getText(),
                passwordInput.getText());
        if (valid) {
            control.switchScene(MainApplication.window, "UniversityDashboard.fxml");
        }
    }

}
