package com.example.golestan.Controller;

import com.example.golestan.Database.CourseDB;
import com.example.golestan.Database.SemesterDB;
import com.example.golestan.Database.StudentDB;
import com.example.golestan.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StuDashboard {
    private static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        StuDashboard.username = username;
    }

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<CourseDB, String> courseCode;

    @FXML
    private TableView<CourseDB> courseList;

    @FXML
    private TableColumn<CourseDB, String> courseName;

    @FXML
    private TableColumn<CourseDB, String> courseProf;

    @FXML
    private TableColumn<CourseDB, Integer> courseVahed;

    @FXML
    private Button logoutButton;

    @FXML
    private TableColumn<CourseDB, String> courseColumn;

    @FXML
    private TableColumn<CourseDB, String> daysColumn;

    @FXML
    private TableView<CourseDB> courseDate;

    @FXML
    private TableColumn<CourseDB, String> timeColumn;

    @FXML
    private TableColumn<CourseDB, String> courseSemester;

    @FXML
    private Button quitButton;

    @FXML
    private Button removeButton;

    @FXML
    private TableView<StudentDB> scoreTable;

    @FXML
    private ComboBox<String> semesterSelect;

    @FXML
    private TableColumn<StudentDB, Integer> vahedColumn;

    @FXML
    private TableColumn<StudentDB, String> nameColumn;

    @FXML
    private TableColumn<StudentDB, Float> scoreColumn;

    @FXML
    void addClicked(ActionEvent event) throws NullPointerException, SQLException {
        CourseDB course = courseList.getSelectionModel().getSelectedItem();
        StudentDB student = new StudentDB();
        student.setUsername(username);
        ResultSet resultSet = student.findStu();
        while (resultSet.next()) {
            student.setStudentId(resultSet.getInt("StudentId"));
            student.setUsername(resultSet.getString("Username"));
            student.setPassword(resultSet.getString("Password"));
            student.setFirstName(resultSet.getString("Firstname"));
            student.setLastName(resultSet.getString("Lastname"));
            student.setMajorSubject(resultSet.getString("MajorSubject"));
            student.setCollege(resultSet.getString("College"));
            student.setEnteringYear(resultSet.getInt("EnteringYear"));
        }

        student.setCourseCode(course.getCode());
        if (student.getStudentId() != 0) {
            student.addStuWithCourse();
        }
        initialize();
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

    @FXML
    void removeClicked(ActionEvent event) throws SQLException {
        StudentDB student = new StudentDB();
        CourseDB course = courseList.getSelectionModel().getSelectedItem();
        student.setUsername(username);
        student.setCourseCode(course.getCode());
        student.removeStu();
        initialize();
    }

    @FXML
    void semesterClicked(ActionEvent event) throws SQLException {
        StudentDB studentDB = new StudentDB();
        CourseDB courseDB = new CourseDB();
        ObservableList<StudentDB> scores = FXCollections.observableArrayList();
        ResultSet resultSet1 = courseDB.findCourseWithStu(username);
        if (resultSet1.isBeforeFirst()) {
            while (resultSet1.next()) {
                String code = resultSet1.getString("CourseCode");
                if (!code.equals("")) {
                    courseDB.setCode(code);
                    ResultSet resultSet2 = courseDB.findCourseWithCode();
                    while (resultSet2.next()) {
                        String name = resultSet2.getString("Name");
                        int vahed = resultSet2.getInt("Vahed");
                        String semester = resultSet2.getString("Semester");
                        studentDB.setCourseCode(code);
                        ResultSet resultSet3 = studentDB.findScoreWithCode();
                        float score = 0;
                        while (resultSet3.next()) {
                            score = resultSet3.getFloat("Score");
                        }
                        if (semesterSelect.getValue().equals(semester)) {
                            scores.add(new StudentDB(name, vahed, score));
                        }
                    }
                }
            }

            nameColumn.setCellValueFactory(new PropertyValueFactory<StudentDB, String>("name"));
            vahedColumn.setCellValueFactory(new PropertyValueFactory<StudentDB, Integer>("vahed"));
            scoreColumn.setCellValueFactory(new PropertyValueFactory<StudentDB, Float>("score"));
            scoreTable.setItems(scores);
        }
    }

    public void initialize() throws SQLException {
        CourseDB courseDB = new CourseDB();
        StudentDB studentDB = new StudentDB();
        ObservableList<CourseDB> coursesName = FXCollections.observableArrayList();
        ResultSet resultSet = courseDB.findCourse();
        studentDB.setUsername(username);
        ResultSet resultSet3 = studentDB.findStu();
        int year = 0;
        while (resultSet3.next()) {
            year = resultSet3.getInt("EnteringYear");
        }
        if (resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String code = resultSet.getString("Code");
                String professor = resultSet.getString("ProfessorFirstName") + " " + resultSet.getString("ProfessorLastName");
                int vahed = resultSet.getInt("Vahed");
                String semester = resultSet.getString("Semester");
                int num = Integer.parseInt(semester.substring(semester.length() - 4, semester.length()));
                if (num >= year) {
                    coursesName.add(new CourseDB(name, code, professor, vahed, semester));
                }
            }

            courseName.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("name"));
            courseCode.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("code"));
            courseProf.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("professor"));
            courseVahed.setCellValueFactory(new PropertyValueFactory<CourseDB, Integer>("vahed"));
            courseSemester.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("semester"));
            courseList.setItems(coursesName);
        }

        ObservableList<CourseDB> date = FXCollections.observableArrayList();
        ResultSet resultSet1 = courseDB.findCourseWithStu(username);
        if (resultSet1.isBeforeFirst()) {
            while (resultSet1.next()) {
                String code = resultSet1.getString("CourseCode");
                if (!code.equals("")) {
                    courseDB.setCode(code);
                    ResultSet resultSet2 = courseDB.findCourseWithCode();
                    while (resultSet2.next()) {
                        String name = resultSet2.getString("Name");
                        String vahed = resultSet2.getString("Vahed");
                        String semester = resultSet2.getString("Semester");
                        int number =  Integer.parseInt(semester.substring(semester.length() - 4, semester.length()));
                        String time = resultSet2.getString("StartClass") + " - " + resultSet2.getString("EndClass");
                        String days = resultSet2.getString("Day");
                        date.add(new CourseDB(name, days, time));
                    }
                }
            }

            courseColumn.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("name"));
            daysColumn.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("day"));
            timeColumn.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("time"));
            courseDate.setItems(date);
        }

        ObservableList<String> existSemester = FXCollections.observableArrayList();
        SemesterDB semesterDB = new SemesterDB();
        ResultSet resultSet4 = semesterDB.semesterList();
        if (resultSet4.isBeforeFirst()) {
            while (resultSet4.next()) {
                String name = resultSet4.getString("Name");
                int number = resultSet4.getInt("Year");
                existSemester.add(name + number);
            }
            semesterSelect.setItems(existSemester);
        }
    }
}

