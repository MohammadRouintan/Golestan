package com.example.golestan.Controller;

import com.example.golestan.Account.ProfessorAccount;
import com.example.golestan.Database.*;
import com.example.golestan.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.FloatStringConverter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfDashboard {

    private static String username;
    public static void setUsername(String username) {
        ProfDashboard.username = username;
    }

    // Courses
    @FXML
    private TableView<CourseDB> courseList;

    @FXML
    private TableColumn<CourseDB, String> nameColumn;

    @FXML
    private TableColumn<CourseDB, String> codeColumn;

    // ------------------
    @FXML
    private TableView<StudentDB> studentList;

    @FXML
    private TableColumn<StudentDB, Integer> idColumn;

    @FXML
    private TableColumn<StudentDB, String> nameStuColumn;

    @FXML
    private TableColumn<StudentDB, Float> scoreColumn;

    // Edit Info
    @FXML
    private TextField firstnameInput;

    @FXML
    private TextField lastnameInput;

    @FXML
    private ComboBox<String> collegeInput;

    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField groupInput;

    // Labels
    @FXML
    private Label showName;

    @FXML
    private Label showSemester;

    @FXML // This function edit information of professor.
    void editClicked() throws SQLException, IOException {
        ProfessorAccount professorAccount = new ProfessorAccount();
        professorAccount.setUsername(usernameInput.getText());
        professorAccount.setPassword(passwordInput.getText());
        professorAccount.setFirstName(firstnameInput.getText());
        professorAccount.setLastName(lastnameInput.getText());
        professorAccount.setGroup(groupInput.getText());
        professorAccount.setCollege(collegeInput.getValue());
        boolean valid = professorAccount.checkValid();

        boolean flag = false;
        if (valid) {
            flag = professorAccount.editInfo(username);
        } else {
            return;
        }

        if (flag) {
            logoutClicked();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS INFORMATION IS INVALID OR AlREADY EXIST !!");
            alert.show();
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

    @FXML // This function edit score of student
    void editScore(TableColumn.CellEditEvent<StudentDB, Float> event) throws SQLException {
        StudentDB studentDB = studentList.getSelectionModel().getSelectedItem();
        studentDB.setCourseCode(courseList.getSelectionModel().getSelectedItem().getCode());
        studentDB.setScore(event.getNewValue());
        studentDB.editScore();
    }

    public void initialize() throws SQLException {
        ProfessorDB professorDB = new ProfessorDB();
        CollegeDB collegeDB = new CollegeDB();
        SemesterDB semesterDB = new SemesterDB();
        CourseDB courseDB = new CourseDB();

        professorDB.setUsername(username);
        showName.setText(professorDB.findName());
        showSemester.setText(semesterDB.currentSemester());

        setCollegeBox(collegeDB);
        setCourseTable(professorDB, courseDB);
        courseSelected();
    }

    public void setCollegeBox(CollegeDB collegeDB) throws SQLException {
        ObservableList<String> existCollege = FXCollections.observableArrayList();
        existCollege.addAll(collegeDB.collegeNames());
        collegeInput.setItems(existCollege);
    }

    // This function show students of course.
    public void courseSelected() {
        courseList.getSelectionModel().selectedItemProperty().addListener(event -> {
            String currentCourse = courseList.getSelectionModel().getSelectedItem().getCode();
            StudentDB studentDB = new StudentDB();
            try {
                ObservableList<StudentDB> students = FXCollections.observableArrayList();
                studentDB.setCourseCode(currentCourse);
                ResultSet resultSet = studentDB.findCourseWithCode();
                if (resultSet.isBeforeFirst()) {
                    while (resultSet.next()) {
                        int studentId = resultSet.getInt("StudentId");
                        String name = resultSet.getString("Firstname") + " " + resultSet.getString("Lastname");
                        float score = resultSet.getFloat("Score");
                        students.add(new StudentDB(studentId, name, score));
                    }
                    idColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
                    nameStuColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                    scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
                    studentList.setItems(students);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        scoreColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
    }

    public void setCourseTable(ProfessorDB professorDB, CourseDB courseDB) throws SQLException {
        ObservableList<CourseDB> courseNames = FXCollections.observableArrayList();
        String[] names = professorDB.findName().split(" ");
        ResultSet resultSet = courseDB.findCourseWithProf(names[0], names[1]);
        if (resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String code = resultSet.getString("Code");
                String semester = resultSet.getString("Semester");
                if (semester.equals(showSemester.getText())) {
                    courseNames.add(new CourseDB(name, code));
                }
            }

            nameColumn.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("name"));
            codeColumn.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("code"));
            courseList.setItems(courseNames);
        }
    }
}