package com.example.golestan.Controller;

import com.example.golestan.Account.UniversityAccount;
import com.example.golestan.MainApplication;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.SQLException;

public class SignupUni {

    @FXML
    private TextField nameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField usernameInput;

    @FXML
    void backClicked() throws IOException {
        SceneController control = new SceneController();
        control.switchScene(MainApplication.window, "Login.fxml");
    }

    @FXML
    void quitClicked() {
        SceneController control = new SceneController();
        control.closeProgram(MainApplication.window);
    }

    @FXML
    void signupClicked() throws SQLException, IOException {
        SceneController control = new SceneController();
        UniversityAccount universityAccount = new UniversityAccount();
        universityAccount.setName(nameInput.getText());
        universityAccount.setUsername(usernameInput.getText());
        universityAccount.setPassword(passwordInput.getText());
        boolean valid = universityAccount.signup();

        if (valid) {
            UniDashboard.setUsername(usernameInput.getText());
            control.switchScene(MainApplication.window, "UniversityDashboard.fxml");
        }
    }
}
