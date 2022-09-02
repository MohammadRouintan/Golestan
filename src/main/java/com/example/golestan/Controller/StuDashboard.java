package com.example.golestan.Controller;

import com.example.golestan.Account.StudentAccount;
import com.example.golestan.Database.CollegeDB;
import com.example.golestan.Database.CourseDB;
import com.example.golestan.Database.SemesterDB;
import com.example.golestan.Database.StudentDB;
import com.example.golestan.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StuDashboard {

    private static String username;
    public static void setUsername(String username) {
        StuDashboard.username = username;
    }

    // Register
    @FXML
    private TableView<CourseDB> courseList;

    @FXML
    private TableColumn<CourseDB, String> courseName;

    @FXML
    private TableColumn<CourseDB, String> courseCode;

    @FXML
    private TableColumn<CourseDB, String> courseProf;

    @FXML
    private TableColumn<CourseDB, Integer> courseUnit;

    @FXML
    private TableColumn<CourseDB, String> courseSemester;

    // Score
    @FXML
    private ComboBox<String> semesterSelect;

    @FXML
    private TableView<StudentDB> scoreTable;

    @FXML
    private TableColumn<StudentDB, String> nameColumn;

    @FXML
    private TableColumn<StudentDB, Integer> unitColumn;

    @FXML
    private TableColumn<StudentDB, Float> scoreColumn;

    // Schedule
    @FXML
    private TableView<CourseDB> courseDate;

    @FXML
    private TableColumn<CourseDB, String> courseColumn;

    @FXML
    private TableColumn<CourseDB, String> daysColumn;

    @FXML
    private TableColumn<CourseDB, String> timeColumn;

    // Edit Info
    @FXML
    private TextField firstnameInput;

    @FXML
    private TextField lastnameInput;

    @FXML
    private TextField majorInput;

    @FXML
    private TextField studentIdInput;

    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private ComboBox<String> collegeInput;

    @FXML
    private ComboBox<Integer> enteringYearInput;

    // Label
    @FXML
    private Label showName;

    @FXML
    private Label showMax;

    @FXML
    private Label showMin;

    @FXML
    private Label showReceived;

    @FXML
    private Label showSemester;

    @FXML
    private Label showTotalAverage;

    @FXML // This function receive course.
    void addClicked() throws NullPointerException, SQLException {
        CourseDB courseDB = courseList.getSelectionModel().getSelectedItem();
        if (courseDB == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("PLEASE SELECT A COURSE !!");
            alert.show();
            return;
        }

        StudentDB studentDB = new StudentDB();
        studentDB.setUsername(username);
        ResultSet resultSet = studentDB.findWithUsername();
        while (resultSet.next()) {
            studentDB.setStudentId(resultSet.getInt("StudentId"));
            studentDB.setUsername(resultSet.getString("Username"));
            studentDB.setPassword(resultSet.getString("Password"));
            studentDB.setFirstName(resultSet.getString("Firstname"));
            studentDB.setLastName(resultSet.getString("Lastname"));
            studentDB.setMajorSubject(resultSet.getString("MajorSubject"));
            studentDB.setCollege(resultSet.getString("College"));
            studentDB.setEnteringYear(resultSet.getInt("EnteringYear"));
        }
        studentDB.setCourseCode(courseDB.getCode());
        studentDB.setSemester(courseDB.getSemester());
        studentDB.addStuWithCourse();
        initialize();
    }

    @FXML // This function delete course from list of selected courses.
    void removeClicked() throws SQLException {
        StudentDB studentDB = new StudentDB();
        CourseDB courseDB = courseList.getSelectionModel().getSelectedItem();
        if (courseDB == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("PLEASE SELECT A COURSE !!");
            alert.show();
            return;
        }
        studentDB.setUsername(username);
        studentDB.setCourseCode(courseDB.getCode());
        studentDB.removeCourse();
        initialize();
    }

    @FXML // This function finish selecting course and after click this button you cannot receive or delete courses.
    void finishClicked() throws SQLException {
        StudentDB studentDB = new StudentDB();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        studentDB.setUsername(username);
        studentDB.setSemester(showSemester.getText());
        if (studentDB.findStatusOfRegister().equals("END")) {
            alert.setContentText("Registration is over !!");
            alert.show();
            return;
        }

        if (Integer.parseInt(showReceived.getText()) < Integer.parseInt(showMin.getText())) {
            alert.setContentText("YOU MUST SELECT MORE THAN " + showMin.getText() + " UNIT");
            alert.show();
        } else if (Integer.parseInt(showReceived.getText()) > Integer.parseInt(showMax.getText())) {
            alert.setContentText("YOU MUST SELECT LESS THAN " + showMax.getText() + " UNIT");
            alert.show();
        } else {
            studentDB.setUsername(username);
            studentDB.setStatus("END");
            studentDB.changeStatus(showSemester.getText());
        }
        initialize();
    }

    @FXML // This function show score table by semester.
    void semesterClicked() throws SQLException {
        StudentDB studentDB = new StudentDB();
        CourseDB courseDB = new CourseDB();
        score(studentDB, courseDB);
    }

    @FXML // This function edit information of student.
    void editClicked() throws SQLException, IOException {
        StudentAccount studentAccount = new StudentAccount();

        if (studentIdInput.getText().equals("")) {
            studentAccount.setStudentId(0);
        } else {
            studentAccount.setStudentId(Integer.parseInt(studentIdInput.getText()));
        }

        if (enteringYearInput.getValue() == null) {
            studentAccount.setEnteringYear(0);
        } else {
            studentAccount.setEnteringYear(enteringYearInput.getValue());
        }

        studentAccount.setUsername(usernameInput.getText());
        studentAccount.setPassword(passwordInput.getText());
        studentAccount.setFirstName(firstnameInput.getText());
        studentAccount.setLastName(lastnameInput.getText());
        studentAccount.setMajorSubject(majorInput.getText());
        studentAccount.setCollege(collegeInput.getValue());
        boolean valid = studentAccount.checkValid();

        boolean flag;
        if (valid) {
            flag = studentAccount.editInfo(username);
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

    public void initialize() throws SQLException {
        StudentDB studentDB = new StudentDB();
        SemesterDB semesterDB = new SemesterDB();
        CollegeDB collegeDB = new CollegeDB();
        CourseDB courseDB = new CourseDB();

        studentDB.setUsername(username);
        showName.setText(studentDB.findName());
        showSemester.setText(semesterDB.currentSemester());
        setSemesterBox(semesterDB);
        setCollegeBox(collegeDB);
        setEnteringYearBox();
        register(studentDB, courseDB, semesterDB);
        schedule(studentDB, courseDB);
        maxAndMin(studentDB);
    }

    // This function create table of register.
    public void register(StudentDB studentDB, CourseDB courseDB, SemesterDB semesterDB) throws SQLException {
        ObservableList<CourseDB> registerList = FXCollections.observableArrayList();
        studentDB.setUsername(username);
        courseDB.setSemester(showSemester.getText());
        ResultSet resultSet = courseDB.findCourseWithSemester();

        if (resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String code = resultSet.getString("Code");
                String professor = resultSet.getString("ProfessorFirstName") + " " + resultSet.getString("ProfessorLastName");
                String semester = resultSet.getString("Semester");
                int unit = resultSet.getInt("Vahed");
                courseDB.setCode(code);
                studentDB.setCourseCode(code);
                if (studentDB.findStatus().equals("END")) {
                    break;
                }

                if (semester.equals(semesterDB.currentSemester()) && courseDB.findCourseYear() >= studentDB.findEnteringYear()) {
                    registerList.add(new CourseDB(name, code, professor, unit, semester));
                }
            }

            courseName.setCellValueFactory(new PropertyValueFactory<>("name"));
            courseCode.setCellValueFactory(new PropertyValueFactory<>("code"));
            courseProf.setCellValueFactory(new PropertyValueFactory<>("professor"));
            courseUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
            courseSemester.setCellValueFactory(new PropertyValueFactory<>("semester"));
            courseList.setItems(registerList);
        }
    }

    // This function create table of score.
    public void score(StudentDB studentDB, CourseDB courseDB) throws SQLException {
        ObservableList<StudentDB> scoreList = FXCollections.observableArrayList();
        studentDB.setUsername(username);
        studentDB.setSemester(semesterSelect.getValue());
        ResultSet resultSet = studentDB.findCourseWithSemester();

        if (resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                String code = resultSet.getString("CourseCode");
                if (!code.equals("")) {
                    courseDB.setCode(code);
                    studentDB.setCourseCode(code);

                    ResultSet resultSet1 = courseDB.findCourseWithCode();
                    ResultSet resultSet2 = studentDB.findScoreWithCode();
                    float score = 0;
                    while (resultSet2.next()) {
                        score = resultSet2.getFloat("Score");
                    }

                    while (resultSet1.next()) {
                        String name = resultSet1.getString("Name");
                        String semester = resultSet1.getString("Semester");
                        int unit = resultSet1.getInt("Vahed");

                        if (semesterSelect.getValue().equals(semester)) {
                            scoreList.add(new StudentDB(name, unit, score));
                        }
                    }
                }
            }

            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
            scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
            scoreTable.setItems(scoreList);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("YOU HAVE NOT RECEIVED ANY LESSONS !!");
            alert.show();
            semesterSelect.setValue(semesterSelect.getItems().get(0));
            return;
        }

        // set totalAverage
        studentDB.setSemester(semesterSelect.getValue());
        float totalAverage = studentDB.findTotalAve();
        if (totalAverage != 0) {
            showTotalAverage.setText(String.valueOf(totalAverage));
        } else {
            float total = calculateTotalAverage();
            showTotalAverage.setText(String.format("%.2f", total));
            studentDB.setTotalAverage(Float.parseFloat(showTotalAverage.getText()));
            studentDB.addTotalAverage();
        }
    }

    // This function create table of schedule.
    public void schedule(StudentDB studentDB, CourseDB courseDB) throws SQLException {
        ObservableList<CourseDB> date = FXCollections.observableArrayList();
        studentDB.setUsername(username);
        ResultSet resultSet1 = studentDB.findWithUsername();
        if (resultSet1.isBeforeFirst()) {
            int vahed = 0;
            while (resultSet1.next()) {
                String code = resultSet1.getString("CourseCode");
                if (!code.equals("")) {
                    courseDB.setCode(code);
                    ResultSet resultSet2 = courseDB.findCourseWithCode();
                    while (resultSet2.next()) {
                        String name = resultSet2.getString("Name");
                        String semester = resultSet2.getString("Semester");
                        String time = resultSet2.getString("StartClass") + " - " + resultSet2.getString("EndClass");
                        String days = resultSet2.getString("Day");
                        if (semester.equals(showSemester.getText())) {
                            date.add(new CourseDB(name, days, time));
                            vahed += resultSet2.getInt("Vahed");
                        }
                    }
                }
            }

            showReceived.setText(String.valueOf(vahed));
            courseColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            daysColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
            timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
            courseDate.setItems(date);
        }
    }

    // This function set max and min unit that student can receive.
    public void maxAndMin(StudentDB studentDB) throws SQLException {
        studentDB.setUsername(username);
        if (showSemester.getText().equals("")) {
            return;
        }

        boolean flag = showSemester.getText().contains("Summer");
        if (flag) {
            showMax.setText(String.valueOf(6));
            showMin.setText(String.valueOf(3));
        } else {
            String semester = showSemester.getText();
            String name = semester.substring(0, semester.length() - 4);
            int year = Integer.parseInt(semester.substring(semester.length() - 4));
            if (name.equals("Fall")) {
                studentDB.setSemester("Winter" + (year - 1));
            } else if (name.equals("Winter")) {
                studentDB.setSemester("Fall" + year);
            }

            if (studentDB.findTotalAve() >= 17) {
                showMax.setText(String.valueOf(24));
            } else {
                showMax.setText(String.valueOf(20));
            }

            showMin.setText(String.valueOf(12));
        }
    }

    // This function calculate total average.
    public float calculateTotalAverage() {
        float total = 0;
        ObservableList<StudentDB> studentScores = scoreTable.getItems();
        for (StudentDB student : studentScores) {
            total += student.getScore() * student.getUnit();
        }
        total /= Integer.parseInt(showReceived.getText());
        return total;
    }

    public void setSemesterBox(SemesterDB semesterDB) throws SQLException {
        ObservableList<String> existSemester = FXCollections.observableArrayList();
        for (String names : semesterDB.semesterList()) {
            String[] split = names.split(" ");
            existSemester.add(split[0] + split[1]);
        }
        semesterSelect.setItems(existSemester);
    }

    public void setCollegeBox(CollegeDB collegeDB) throws SQLException {
        ObservableList<String> existCollege = FXCollections.observableArrayList();
        existCollege.addAll(collegeDB.collegeNames());
        collegeInput.setItems(existCollege);
    }

    public void setEnteringYearBox() {
        ObservableList<Integer> yearList = FXCollections.observableArrayList();
        for (int i = 1385; i <= 1450; i++) {
            yearList.add(i);
        }
        enteringYearInput.setItems(yearList);
    }
}

