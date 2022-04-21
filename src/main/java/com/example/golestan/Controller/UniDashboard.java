package com.example.golestan.Controller;

import com.example.golestan.Account.UniversityAccount;
import com.example.golestan.Database.*;
import com.example.golestan.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UniDashboard {

    private static String username;
    public static void setUsername(String username) {
        UniDashboard.username = username;
    }

    // Make Faculty
    @FXML
    private TextField facultyNameInput;

    @FXML
    private TableView<CollegeDB> facultyList;

    @FXML
    private TableColumn<CollegeDB, String> facultyNames;

    // Make Semester
    @FXML
    private ComboBox<String> semesterNameInput;

    @FXML
    private ComboBox<Integer> semesterYearInput;

    @FXML
    private TableView<SemesterDB> semesterList;

    @FXML
    private TableColumn<SemesterDB, String> semesterNames;

    @FXML
    private TableColumn<SemesterDB, Integer> semesterYears;

    // Make Course
    @FXML
    private TextField courseNameInput;

    @FXML
    private TextField courseCodeInput;

    @FXML
    private TextField courseUnitInput;

    @FXML
    private ComboBox<String> courseFacultyInput;

    @FXML
    private ComboBox<String> courseSemesterInput;

    @FXML
    private ComboBox<Integer> courseStartInput;

    @FXML
    private ComboBox<Integer> courseEndInput;

    @FXML
    private ComboBox<String> courseProfInput;

    @FXML
    private CheckBox sat, sun, mon, tue, wed;

    // Edit Info
    @FXML
    private TextField newNameInput;

    @FXML
    private TextField newUsernameInput;

    @FXML
    private PasswordField newPasswordInput;

    // Labels
    @FXML
    private Label showName;

    @FXML
    private Label showSemester;

    @FXML // This function find professor of selected faculty.
    void facultySelected() throws SQLException {
        ProfessorDB professorDB = new ProfessorDB();
        professorDB.setCollege(courseFacultyInput.getValue());
        ObservableList<String> profList = FXCollections.observableArrayList();
        ResultSet resultSet = professorDB.findProfWithCollege();

        if (resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                String firstname = resultSet.getString("Firstname");
                String lastname = resultSet.getString("Lastname");
                profList.add(firstname + " " + lastname);
            }

            courseProfInput.setItems(profList);
        } else {
            profList.removeAll();
            courseProfInput.setItems(profList);
        }
    }

    @FXML // This function create a faculty.
    void createFacultyClicked() throws SQLException {
        CollegeDB collegeDB = new CollegeDB();
        collegeDB.setName(facultyNameInput.getText());

        if (collegeDB.checkValid()) { collegeDB.addToDatabase(); }
        initialize();
    }

    @FXML // This function create a semester with name and year.
    void createSemesterClicked() throws SQLException {
        SemesterDB semesterDB = new SemesterDB();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (semesterNameInput.getValue() != null && semesterYearInput.getValue() != null) {
            semesterDB.setName(semesterNameInput.getValue());
            semesterDB.setYear(semesterYearInput.getValue());
            if (!semesterDB.checkSemester()) { semesterDB.addToDatabase(); }
        } else if (semesterNameInput.getValue() == null && semesterYearInput.getValue() == null) {
            alert.setContentText("PLEASE ENTER NAME AND YEAR !!");
            alert.show();
        } else if (semesterNameInput.getValue() == null) {
            alert.setContentText("PLEASE ENTER NAME !!");
            alert.show();
        } else {
            alert.setContentText("PLEASE ENTER YEAR !!");
            alert.show();
        }

        initialize();
    }

    @FXML // This function set current semester.
    void currentClicked() throws SQLException {
        SemesterDB semesterDB = semesterList.getSelectionModel().getSelectedItem();
        semesterDB.setCurrent();
        showSemester.setText(semesterDB.currentSemester());
        initialize();
    }

    @FXML // This function create a course with information.
    void createCourseClicked() throws SQLException {
        CourseDB courseDB = new CourseDB();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (courseFacultyInput.getValue() == null || courseSemesterInput.getValue() == null ||
                courseStartInput.getValue() == null || courseEndInput.getValue() == null ||
                courseProfInput.getValue() == null) {
            alert.setContentText("PLEASE ENTER CORRECT INFORMATION !!");
            alert.show();
            return;
        }

        String day = "";
        if (sat.isSelected()) { day += sat.getText() + " "; }
        if (sun.isSelected()) { day += sun.getText() + " "; }
        if (mon.isSelected()) { day += mon.getText() + " "; }
        if (tue.isSelected()) { day += tue.getText() + " "; }
        if (wed.isSelected()) { day += wed.getText(); }

        boolean valid = courseDB.checkValid(courseNameInput.getText(),
                        courseCodeInput.getText(),
                        courseUnitInput.getText(),
                        courseStartInput.getValue(),
                        courseEndInput.getValue(),
                        day);

        if (valid) {
            String[] names = courseProfInput.getValue().split(" ");
            courseDB.setName(courseNameInput.getText());
            courseDB.setCode(courseCodeInput.getText());
            courseDB.setUnit(Integer.parseInt(courseUnitInput.getText()));
            courseDB.setProfFirstName(names[0]);
            courseDB.setProfLastName(names[1]);
            courseDB.setCollege(courseFacultyInput.getValue());
            courseDB.setSemester(courseSemesterInput.getValue());
            courseDB.setStartClass(courseStartInput.getValue());
            courseDB.setEndClass(courseEndInput.getValue());
            courseDB.setDay(day);
        } else {
            return;
        }

        boolean notExist = false;
        if (!courseDB.checkCourse()) {
            courseDB.addToDatabase();
            notExist = true;
        }

        if (notExist) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("COURSE ADDED.");
            alert.show();
        } else {
            alert.setContentText("THIS COURSE ALREADY EXIST !!");
            alert.show();
        }
    }

    @FXML // This function edit information of university.
    void editClicked() throws IOException, SQLException {
        UniversityAccount universityAccount = new UniversityAccount();
        universityAccount.setName(newNameInput.getText());
        universityAccount.setUsername(newUsernameInput.getText());
        universityAccount.setPassword(newPasswordInput.getText());

        if (universityAccount.checkValid()) {
            universityAccount.editInfo(username);
            logoutClicked();
        }
    }

    @FXML
    void logoutClicked() throws IOException {
        SceneController control = new SceneController();
        control.switchScene(MainApplication.window, "Login.fxml");
    }

    @FXML
    void quitClicked() {
        SceneController control = new SceneController();
        control.closeProgram(MainApplication.window);
    }

    public void initialize() throws SQLException {
        UniversityDB universityDB = new UniversityDB();
        CollegeDB collegeDB = new CollegeDB();
        SemesterDB semesterDB = new SemesterDB();
        universityDB.setUsername(username);
        showName.setText(universityDB.findName());
        showSemester.setText(semesterDB.currentSemester());

        setMakeSemesterBoxes();
        setCollegeTable(collegeDB);
        setCollegeBox(collegeDB);
        setSemesterTable(semesterDB);
        setSemesterBox(semesterDB);
        setTimeBoxes();
    }

    public void setMakeSemesterBoxes() {
        ObservableList<String> semesterName = FXCollections.observableArrayList("Fall", "Winter", "Summer");
        semesterNameInput.setItems(semesterName);

        ObservableList<Integer> semesterYear = FXCollections.observableArrayList();
        for (int i = 0; i < 50; i++) { semesterYear.add(1385 + i); }
        semesterYearInput.setItems(semesterYear);
    }

    public void setCollegeTable(CollegeDB collegeDB) throws SQLException {
        ObservableList<CollegeDB> collegeNames = FXCollections.observableArrayList();
        for (String name : collegeDB.collegeNames()) {
            collegeNames.add(new CollegeDB(name));
        }
        facultyNames.setCellValueFactory(new PropertyValueFactory<>("name"));
        facultyList.setItems(collegeNames);
    }

    public void setCollegeBox(CollegeDB collegeDB) throws SQLException {
        ObservableList<String> existCollege = FXCollections.observableArrayList();
        existCollege.addAll(collegeDB.collegeNames());
        courseFacultyInput.setItems(existCollege);
    }

    public void setSemesterTable(SemesterDB semesterDB) throws SQLException {
        ObservableList<SemesterDB> semesters = FXCollections.observableArrayList();
        for (String name : semesterDB.semesterList()) {
            String[] names = name.split(" ");
            semesters.add(new SemesterDB(names[0], Integer.parseInt(names[1])));
        }
        semesterNames.setCellValueFactory(new PropertyValueFactory<>("name"));
        semesterYears.setCellValueFactory(new PropertyValueFactory<>("year"));
        semesterList.setItems(semesters);
    }

    public void setSemesterBox(SemesterDB semesterDB) throws SQLException {
        ObservableList<String> existSemester = FXCollections.observableArrayList();
        existSemester.add(semesterDB.currentSemester());
        courseSemesterInput.setItems(existSemester);
    }

    public void setTimeBoxes() {
        ObservableList<Integer> times = FXCollections.observableArrayList();
        for (int i = 1; i <= 24; i++) {
            times.add(i);
        }
        courseStartInput.setItems(times);
        courseEndInput.setItems(times);
    }
}
