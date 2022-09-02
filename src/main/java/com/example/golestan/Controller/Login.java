package com.example.golestan.Controller;

import com.example.golestan.Account.ProfessorAccount;
import com.example.golestan.Account.StudentAccount;
import com.example.golestan.Account.UniversityAccount;
import com.example.golestan.Box.Job;
import com.example.golestan.Box.Jobs;
import com.example.golestan.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.SQLException;

public class Login {

    @FXML
    private ComboBox<String> jobInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField usernameInput;

    @FXML
    void loginClicked() throws SQLException, IOException {
        login();
    }

    @FXML
    void quitClicked() {
        SceneController control = new SceneController();
        control.closeProgram(MainApplication.window);
    }

    @FXML
    void signupClicked() throws IOException {
        signup();
    }

    public void initialize() {
        ObservableList<String> jobs = FXCollections.observableArrayList("University", "Professor", "Student");
        jobInput.setItems(jobs);
    }

    public void login() throws IOException, SQLException {
        SceneController control = new SceneController();
        boolean valid;

        if (usernameInput.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("PLEASE ENTER YOUR USERNAME !!");
            alert.show();
        } else if (passwordInput.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("PLEASE ENTER YOUR PASSWORD !!");
            alert.show();
        } else if (jobInput.getValue() == null) {
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
                        StuDashboard.setUsername(usernameInput.getText());
                        control.switchScene(MainApplication.window, "StudentDashboard.fxml");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void signup() throws IOException {
        SceneController control = new SceneController();
        Jobs job = Job.display("Job", "PLEASE SELECT YOUR JOB :");
        if (job != null) {
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
    }
}
