package com.example.golestan.Controller;

import com.example.golestan.Account.StudentAccount;
import com.example.golestan.Database.CollegeDB;
import com.example.golestan.Database.CourseDB;
import com.example.golestan.Database.SemesterDB;
import com.example.golestan.Database.StudentDB;
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

public class StuDashboard {

    private static String username;
    public static String getUsername() {
        return username;
    }
    public static void setUsername(String username) {
        StuDashboard.username = username;
    }

    // Edit Info
    @FXML
    private TextField enteringYearInput;

    @FXML
    private ComboBox<String> collegeInput;

    @FXML
    private TextField firstnameInput;

    @FXML
    private TextField lastnameInput;

    @FXML
    private TextField majorInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField studentidInput;

    @FXML
    private TextField usernameInput;

    // Register
    @FXML
    private TableView<CourseDB> courseList;

    @FXML
    private TableColumn<CourseDB, String> courseName;

    @FXML
    private TableColumn<CourseDB, String> courseProf;

    @FXML
    private TableColumn<CourseDB, Integer> courseVahed;

    @FXML
    private TableColumn<CourseDB, String> courseCode;

    @FXML
    private TableColumn<CourseDB, String> courseSemester;

    // Schedule
    @FXML
    private TableView<CourseDB> courseDate;

    @FXML
    private TableColumn<CourseDB, String> courseColumn;

    @FXML
    private TableColumn<CourseDB, String> daysColumn;

    @FXML
    private TableColumn<CourseDB, String> timeColumn;

    @FXML
    private ComboBox<String> semesterSelect;

    // Score
    @FXML
    private TableView<StudentDB> scoreTable;

    @FXML
    private TableColumn<StudentDB, Integer> vahedColumn;

    @FXML
    private TableColumn<StudentDB, String> nameColumn;

    @FXML
    private TableColumn<StudentDB, Float> scoreColumn;

    // Label
    @FXML
    private Label showName;

    @FXML
    private Label max;

    @FXML
    private Label min;

    @FXML
    private Label received;

    @FXML
    private Label semester;

    @FXML
    private Label totalAverage;

    @FXML
    void addClicked(ActionEvent event) throws NullPointerException, SQLException {
        CourseDB course = courseList.getSelectionModel().getSelectedItem();
        StudentDB student = new StudentDB();
        student.setUsername(username);
        ResultSet resultSet = student.findStuWithUsername();
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
        student.setSemester(course.getSemester());
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
    void editClicked(ActionEvent event) throws SQLException, IOException {
        StudentDB studentDB = new StudentDB();
        StudentAccount studentAccount = new StudentAccount();
        boolean valid = studentAccount.checkValid(studentidInput.getText(),
                usernameInput.getText(),
                passwordInput.getText(),
                firstnameInput.getText(),
                lastnameInput.getText(),
                majorInput.getText(),
                collegeInput.getValue(),
                enteringYearInput.getText());

        boolean flag = false;
        if (valid) {
            studentDB.setStudentId(Integer.parseInt(studentidInput.getText()));
            studentDB.setUsername(usernameInput.getText());
            studentDB.setPassword(passwordInput.getText());
            studentDB.setFirstName(firstnameInput.getText());
            studentDB.setLastName(lastnameInput.getText());
            studentDB.setMajorSubject(majorInput.getText());
            studentDB.setCollege(collegeInput.getValue());
            studentDB.setEnteringYear(Integer.parseInt(enteringYearInput.getText()));
            flag = studentDB.editInfo(username);
        }

        if (flag) {
            logoutClicked(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS INFORMATION IS INVALID OR AlREADY EXIST !!");
            alert.show();
        }
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
        score();
    }

    @FXML
    void finishClicked(ActionEvent event) throws SQLException {
        StudentDB studentDB = new StudentDB();
        if (Integer.parseInt(received.getText()) < Integer.parseInt(min.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("YOU MUST SELECT MORE THAN " + min.getText() + " VAHED");
            alert.show();
        } else if (Integer.parseInt(received.getText()) > Integer.parseInt(max.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("YOU MUST SELECT LESS THAN " + max.getText() + " VAHED");
            alert.show();
        } else {
            studentDB.setUsername(username);
            studentDB.setStatus("END");
            studentDB.changeStatus(semester.getText());
        }
        initialize();
    }

    public void initialize() throws SQLException {
        StudentDB studentDB = new StudentDB();
        SemesterDB semesterDB = new SemesterDB();
        studentDB.setUsername(username);
        showName.setText(studentDB.findName());
        semester.setText(semesterDB.currentSemester());
        register();
        schedule();
        maxAndMin();

        ObservableList<String> existSemester = FXCollections.observableArrayList();
        ResultSet resultSet4 = semesterDB.semesterList();
        if (resultSet4.isBeforeFirst()) {
            while (resultSet4.next()) {
                String name = resultSet4.getString("Name");
                int number = resultSet4.getInt("Year");
                existSemester.add(name + number);
            }
            semesterSelect.setItems(existSemester);
        }

        ObservableList<String> existCollege = FXCollections.observableArrayList();
        CollegeDB collegeDB = new CollegeDB();
        existCollege.addAll(collegeDB.collegeNames());
        collegeInput.setItems(existCollege);
    }

    void register() throws SQLException {
        ObservableList<CourseDB> registerList = FXCollections.observableArrayList();

        StudentDB studentDB = new StudentDB();
        studentDB.setUsername(username);
        CourseDB courseDB = new CourseDB();
        ResultSet resultSet = courseDB.findCourse();
        SemesterDB semesterDB = new SemesterDB();

        if (resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String code = resultSet.getString("Code");
                String professor = resultSet.getString("ProfessorFirstName") + " " + resultSet.getString("ProfessorLastName");
                String semester = resultSet.getString("Semester");
                int vahed = resultSet.getInt("Vahed");
                courseDB.setCode(code);
                studentDB.setCourseCode(code);
                if (studentDB.findStatus().equals("END")) {
                    break;
                }

                if (semester.equals(semesterDB.currentSemester()) && courseDB.findYear() >= studentDB.findYear()) {
                    registerList.add(new CourseDB(name, code, professor, vahed, semester));
                }
            }

            courseName.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("name"));
            courseCode.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("code"));
            courseProf.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("professor"));
            courseVahed.setCellValueFactory(new PropertyValueFactory<CourseDB, Integer>("vahed"));
            courseSemester.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("semester"));
            courseList.setItems(registerList);
        }
    }

    void score() throws SQLException {
        StudentDB studentDB = new StudentDB();
        CourseDB courseDB = new CourseDB();
        ObservableList<StudentDB> scoreList = FXCollections.observableArrayList();
        studentDB.setUsername(username);
        ResultSet resultSet = studentDB.findStuWithUsername();
        float totalAve = 0;
        if (resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                String code = resultSet.getString("CourseCode");

                if (!code.equals("")) {
                    courseDB.setCode(code);
                    ResultSet resultSet1 = courseDB.findCourseWithCode();
                    studentDB.setCourseCode(code);
                    ResultSet resultSet3 = studentDB.findScoreWithCode();
                    float score = 0;
                    while (resultSet3.next()) {
                        score = resultSet3.getFloat("Score");
                    }

                    while (resultSet1.next()) {
                        String name = resultSet1.getString("Name");
                        String semester = resultSet1.getString("Semester");
                        int vahed = resultSet1.getInt("Vahed");

                        if (semesterSelect.getValue().equals(semester)) {
                            scoreList.add(new StudentDB(name, vahed, score));
                            totalAve += score * vahed;
                        }
                    }
                }
            }

            nameColumn.setCellValueFactory(new PropertyValueFactory<StudentDB, String>("name"));
            vahedColumn.setCellValueFactory(new PropertyValueFactory<StudentDB, Integer>("vahed"));
            scoreColumn.setCellValueFactory(new PropertyValueFactory<StudentDB, Float>("score"));
            scoreTable.setItems(scoreList);
        }
        totalAve /= Integer.parseInt(received.getText());
        totalAverage.setText(String.format("%.2f", totalAve));
        studentDB.setSemester(semesterSelect.getValue());
        studentDB.setTotalAverage(Float.parseFloat(totalAverage.getText()));
        studentDB.addTotalAverage();
    }

    void schedule() throws SQLException {
        StudentDB studentDB = new StudentDB();
        CourseDB courseDB = new CourseDB();
        ObservableList<CourseDB> date = FXCollections.observableArrayList();
        studentDB.setUsername(username);
        ResultSet resultSet1 = studentDB.findStuWithUsername();
        if (resultSet1.isBeforeFirst()) {
            int vahed = 0;
            while (resultSet1.next()) {
                String code = resultSet1.getString("CourseCode");
                if (!code.equals("")) {
                    courseDB.setCode(code);
                    ResultSet resultSet2 = courseDB.findCourseWithCode();
                    while (resultSet2.next()) {
                        String name = resultSet2.getString("Name");
                        String term = resultSet2.getString("Semester");
                        String time = resultSet2.getString("StartClass") + " - " + resultSet2.getString("EndClass");
                        String days = resultSet2.getString("Day");
                        if (term.equals(semester.getText())) {
                            date.add(new CourseDB(name, days, time));
                            vahed += resultSet2.getInt("Vahed");
                        }
                    }
                }
            }

            received.setText(String.valueOf(vahed));
            courseColumn.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("name"));
            daysColumn.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("day"));
            timeColumn.setCellValueFactory(new PropertyValueFactory<CourseDB, String>("time"));
            courseDate.setItems(date);
        }
    }

    void maxAndMin() throws SQLException {
        StudentDB studentDB = new StudentDB();
        studentDB.setUsername(username);
        boolean flag = semester.getText().contains("Summer");
        if (flag) {
            max.setText(String.valueOf(6));
            min.setText(String.valueOf(3));
        } else {
            String term = semester.getText();
            String name = term.substring(0, term.length() - 4);
            int year = Integer.parseInt(term.substring(term.length() - 4, term.length()));
            if (name.equals("Fall")) {
                studentDB.setSemester("Winter" + (year - 1));
            } else if (name.equals("Winter")) {
                studentDB.setSemester("Fall" + year);
            }

            if (studentDB.findTotalAve() >= 17) {
                max.setText(String.valueOf(24));
            } else {
                max.setText(String.valueOf(20));
            }

            min.setText(String.valueOf(12));
        }
    }
}

