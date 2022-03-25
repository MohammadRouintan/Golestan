package com.example.golestan.Controller;

import com.example.golestan.Database.*;
import com.example.golestan.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UniDashboard {

    private static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UniDashboard.username = username;
    }

    @FXML
    private TextField codeInput;

    @FXML
    private ComboBox<String> collegeInput;

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
    private TableView<CollegeDB> facultyList;

    @FXML
    private ComboBox<String> profName;

    @FXML
    private TextField firstnameProfInput;

    @FXML
    private TextField lastnameProfInput;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField nameCourseInput;

    @FXML
    private TableColumn<CollegeDB, String> nameFaculty;

    @FXML
    private TextField nameInput;

    @FXML
    private TableColumn<SemesterDB, String> nameSemester;

    @FXML
    private TextField newName;

    @FXML
    private PasswordField newPassword;

    @FXML
    private TextField newUsername;

    @FXML
    private Button quitButton;

    @FXML
    private ChoiceBox<String> semesterInput;

    @FXML
    private TableView<SemesterDB> semesterList;

    @FXML
    private ChoiceBox<Integer> semesterYearInput;

    @FXML
    private ChoiceBox<String> semsterNameInput;

    @FXML
    private TextField startClassInput;

    @FXML
    private TextField vahedInput;

    @FXML
    private Label nameLabel;

    @FXML
    private TableColumn<SemesterDB, Integer> yearSemester;

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
    void collegeSelected(ActionEvent event) throws SQLException {
        ProfessorDB professorDB = new ProfessorDB();
        professorDB.setCollege(collegeInput.getValue());
        ResultSet resultSet1 = professorDB.findProfWithCollege();
        ObservableList<String> profList = FXCollections.observableArrayList();
        if (resultSet1.isBeforeFirst()) {
            while (resultSet1.next()) {
                String firstname = resultSet1.getString("Firstname");
                String lastname = resultSet1.getString("Lastname");
                profList.add(firstname + " " + lastname);
            }
            profName.setItems(profList);
        } else {
            profList.removeAll();
            profName.setItems(profList);
        }
    }

    @FXML
    void createCourseClicked(ActionEvent event) throws SQLException {
        CourseDB courseDB = new CourseDB();
        boolean valid = courseDB.checkValid(nameCourseInput.getText(),
                codeInput.getText(),
                vahedInput.getText(),
                collegeInput.getValue(),
                semesterInput.getValue(),
                startClassInput.getText(),
                endClassInput.getText());

        String day = "";
        if (sat.isSelected()) {
            day += sat.getText();
        }
        if (sun.isSelected()) {
            day += sun.getText();
        }
        if (mon.isSelected()) {
            day += mon.getText();
        }
        if (tue.isSelected()) {
            day += tue.getText();
        }
        if (wed.isSelected()) {
            day += wed.getText();
        }
        boolean notExist = false;
        if (valid) {
            String[] split = profName.getValue().split(" ");
            courseDB.setName(nameCourseInput.getText());
            courseDB.setCode(codeInput.getText());
            courseDB.setVahed(Integer.parseInt(vahedInput.getText()));
            courseDB.setProfFirstName(split[0]);
            courseDB.setProfLastName(split[1]);
            courseDB.setCollege(collegeInput.getValue());
            courseDB.setSemester(semesterInput.getValue());
            courseDB.setStartClass(Integer.parseInt(startClassInput.getText()));
            courseDB.setEndClass(Integer.parseInt(endClassInput.getText()));
            courseDB.setDay(day);
            notExist = courseDB.addCourse();
        }

        if (notExist) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("COURSE ADDED.");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS CODE ALREADY EXIST !!");
            alert.show();
        }
    }

    @FXML
    void createFacultyClicked(ActionEvent event) throws SQLException {
        CollegeDB collegeDB = new CollegeDB();
        collegeDB.setName(nameInput.getText());
        collegeDB.addCollege();
        initialize();
    }

    @FXML
    void createSemesterClicked(ActionEvent event) throws SQLException {
        SemesterDB semesterDB = new SemesterDB();
        semesterDB.setName(semsterNameInput.getValue());
        semesterDB.setYear(semesterYearInput.getValue());
        semesterDB.addSemester();
        initialize();
    }

    @FXML
    void editClicked(ActionEvent event) throws IOException, SQLException {
        UniversityDB universityDB = new UniversityDB();
        universityDB.setName(newName.getText());
        universityDB.setUsername(username);
        universityDB.setPassword(newPassword.getText());
        universityDB.updateUni(newUsername.getText());
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

    public void initialize() throws SQLException {
        ObservableList<String> semesterName = FXCollections.observableArrayList("Fall", "Winter", "Summer");
        semsterNameInput.setItems(semesterName);

        ObservableList<Integer> semesterYear = FXCollections.observableArrayList();
        for (int i = 0; i < 50; i++) {
            semesterYear.add(1385 + i);
        }
        semesterYearInput.setItems(semesterYear);


        CollegeDB collegeDB = new CollegeDB();
        ObservableList<String> existCollege = FXCollections.observableArrayList();
        ObservableList<CollegeDB> collegeNames = FXCollections.observableArrayList();
        for (String name:collegeDB.collegeNames()) {
            collegeNames.add(new CollegeDB(name));
            existCollege.add(name);
        }
        collegeInput.setItems(existCollege);
        nameFaculty.setCellValueFactory(new PropertyValueFactory<CollegeDB, String>("name"));
        facultyList.setItems(collegeNames);

        SemesterDB semesterDB = new SemesterDB();
        ObservableList<String> existSemester = FXCollections.observableArrayList();
        ObservableList<SemesterDB> semesters = FXCollections.observableArrayList();
        ResultSet resultSet = semesterDB.semesterList();
        if (resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                int year = resultSet.getInt("Year");
                semesters.add(new SemesterDB(name, year));
                existSemester.add(name + year);
            }

            nameSemester.setCellValueFactory(new PropertyValueFactory<SemesterDB, String>("name"));
            yearSemester.setCellValueFactory(new PropertyValueFactory<SemesterDB, Integer>("year"));
            semesterList.setItems(semesters);
        }
        semesterInput.setItems(existSemester);
    }
}
