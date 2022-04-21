package com.example.golestan.Controller;

import com.example.golestan.Account.StudentAccount;
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

public class SignupStu {

    @FXML
    private ComboBox<String> collegeInput;

    @FXML
    private ComboBox<Integer> enteringYearInput;

    @FXML
    private TextField firstnameInput;

    @FXML
    private TextField lastnameInput;

    @FXML
    private TextField majorInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField studentIdInput;

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
        StudentAccount studentAccount = new StudentAccount();
        studentAccount.setUsername(usernameInput.getText());
        studentAccount.setPassword(passwordInput.getText());
        studentAccount.setFirstName(firstnameInput.getText());
        studentAccount.setLastName(lastnameInput.getText());
        studentAccount.setMajorSubject(majorInput.getText());

        try {
            studentAccount.setStudentId(Integer.parseInt(studentIdInput.getText()));
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        if (collegeInput.getValue() != null && enteringYearInput.getValue() != null) {
            studentAccount.setCollege(collegeInput.getValue());
            studentAccount.setEnteringYear(Integer.parseInt(String.valueOf(enteringYearInput.getValue())));
        }

        boolean valid = studentAccount.signup();
        if (valid) {
            StuDashboard.setUsername(usernameInput.getText());
            control.switchScene(MainApplication.window, "StudentDashboard.fxml");
        }
    }

    public void initialize() throws SQLException {
        setCollegeBox();
        setEnteringYearBox();
    }

    public void setCollegeBox() throws SQLException {
        ObservableList<String> collegeList = FXCollections.observableArrayList();
        CollegeDB college = new CollegeDB();
        collegeList.addAll(college.collegeNames());
        collegeInput.setItems(collegeList);
    }

    public void setEnteringYearBox() {
        ObservableList<Integer> yearList = FXCollections.observableArrayList();
        for (int i = 1385; i <= 1450; i++) {
            yearList.add(i);
        }
        enteringYearInput.setItems(yearList);
    }
}
