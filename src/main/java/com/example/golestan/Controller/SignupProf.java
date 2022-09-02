package com.example.golestan.Controller;

import com.example.golestan.Account.ProfessorAccount;
import com.example.golestan.Database.CollegeDB;
import com.example.golestan.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.SQLException;

public class SignupProf {

    @FXML
    private ComboBox<String> collegeInput;

    @FXML
    private TextField firstnameInput;

    @FXML
    private TextField groupInput;

    @FXML
    private TextField lastnameInput;

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
        ProfessorAccount professorAccount = new ProfessorAccount();
        professorAccount.setUsername(usernameInput.getText());
        professorAccount.setPassword(passwordInput.getText());
        professorAccount.setFirstName(firstnameInput.getText());
        professorAccount.setLastName(lastnameInput.getText());
        professorAccount.setGroup(groupInput.getText());
        if (collegeInput.getValue() != null) {
            professorAccount.setCollege(collegeInput.getValue());
        }
        if (professorAccount.checkValid()) {
            boolean valid = professorAccount.signup();
            if (valid) {
                ProfDashboard.setUsername(usernameInput.getText());
                control.switchScene(MainApplication.window, "ProfessorDashboard.fxml");
            }
        }
    }

    public void initialize() throws SQLException {
        setCollegeBox();
    }

    public void setCollegeBox() throws SQLException {
        ObservableList<String> existCollege = FXCollections.observableArrayList();
        CollegeDB collegeDB = new CollegeDB();
        existCollege.addAll(collegeDB.collegeNames());
        collegeInput.setItems(existCollege);
    }
}
