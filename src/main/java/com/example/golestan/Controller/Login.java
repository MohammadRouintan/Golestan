package com.example.golestan.Controller;

import com.example.golestan.Account.ProfessorAccount;
import com.example.golestan.Account.StudentAccount;
import com.example.golestan.Account.UniversityAccount;
import com.example.golestan.Box.Job;
import com.example.golestan.Box.Jobs;
import com.example.golestan.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    void loginClicked(ActionEvent event) throws SQLException, IOException {
        SceneController control = new SceneController();
        boolean valid;

        if (jobInput.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("PLEASE SELECT YOUR JOB !!");
            alert.show();
        } else {
            switch (jobInput.getValue()) {
                case "University":
                    UniversityAccount account1 = new UniversityAccount();
                    valid = account1.login(usernameInput.getText(), passwordInput.getText());
                    if (valid) {
                        UniDashboard.setUsername(usernameInput.getText());
                        control.switchScene(MainApplication.window, "UniversityDashboard.fxml");
                    }
                    break;
                case "Professor":
                    ProfessorAccount account2 = new ProfessorAccount();
                    valid = account2.login(usernameInput.getText(), passwordInput.getText());
                    if (valid) {
                        ProfDashboard.setUsername(usernameInput.getText());
                        control.switchScene(MainApplication.window, "ProfessorDashboard.fxml");
                    }
                    break;
                case "Student":
                    StudentAccount account3 = new StudentAccount();
                    valid = account3.login(usernameInput.getText(), passwordInput.getText());
                    if (valid) {
                        control.switchScene(MainApplication.window, "StudentDashboard.fxml");
                    }
                    break;
                default:
                    break;
            }
        }
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
