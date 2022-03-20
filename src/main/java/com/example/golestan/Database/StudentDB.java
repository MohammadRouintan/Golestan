package com.example.golestan.Database;

import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDB extends Database {
    private int studentId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String majorSubject;
    private String college;
    private int enteringYear;
    private String semester;
    private String professor;
    private String course;
    private float score;
    private float totalAverage;
    private int startClass;
    private int endClass;

    public StudentDB() {
        this.studentId = 0;
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.majorSubject = "";
        this.college = "";
        this.enteringYear = 0;
        this.semester = "";
        this.professor = "";
        this.course = "";
        this.score = 0;
        this.totalAverage = 0;
        this.startClass = 0;
        this.endClass = 0;
    }

    public StudentDB(int studentId, String username, String password, String firstName, String lastName, String majorSubject, String college, int enteringYear) {
        this.studentId = studentId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.majorSubject = majorSubject;
        this.college = college;
        this.enteringYear = enteringYear;
        this.semester = "";
        this.professor = "";
        this.course = "";
        this.score = 0;
        this.totalAverage = 0;
        this.startClass = 0;
        this.endClass = 0;
    }

    public StudentDB(int studentId, String username, String password, String firstName, String lastName, String majorSubject, String college, int enteringYear, String semester, String professor, String course, float score, float totalAverage, int startClass, int endClass) {
        this.studentId = studentId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.majorSubject = majorSubject;
        this.college = college;
        this.enteringYear = enteringYear;
        this.semester = semester;
        this.professor = professor;
        this.course = course;
        this.score = score;
        this.totalAverage = totalAverage;
        this.startClass = startClass;
        this.endClass = endClass;
    }

    public void signUpStudent() throws SQLException {
        super.setQuery("SELECT * FROM Students WHERE Username = " + username);
        boolean user = isExist();
        super.setQuery("SELECT * FROM Students WHERE StudentId = " + studentId);
        boolean id = isExist();

        if (user) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER ALREADY EXIST.\nYOU CANNOT USE THIS USERNAME !!");
            alert.show();
        } else if (id) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER ALREADY EXIST.\nYOU CANNOT USE THIS STUDENT ID !!");
            alert.show();
        } else {
            super.setQuery("INSERT INTO Students (StudentId, Username, Password, Firstname, Lastname, MajorSubject, College, EnteringYear, " +
                    "Semester, Professor, Course, Score, TotalAverage, StartClass, EndClass) VALUES " +
                    "(" + studentId + ", " + username + ", " + password + ", " + firstName + ", " + lastName + ", " +
                    majorSubject + ", " + college + ", " + enteringYear + ", " + semester + ", " + professor + ", " +
                    course + ", " + score + ", " + totalAverage + ", " + startClass + ", " + endClass + ")");
            super.write();
        }
    }
    
    public boolean loginStudent() throws SQLException {
        super.setQuery("SELECT * FROM Students WHERE Username = " + username);
        if (!isExist()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER NOT FOUND !!\nPLEASE ENTER CORRECT USERNAME OR PASSWORD.");
            alert.show();
        } else {
            while (findStudent().next()) {
                String recievePassword = findStudent().getString("Password");
                if (recievePassword.equals(password)) {
                    return true;
                }
            }
        }
        
        return false;
    }

    @Override
    public boolean isExist() throws SQLException {
        ResultSet resultSet = super.read();
        super.disconnect();
        return resultSet.isBeforeFirst();
    }
    
    public ResultSet findStudent() throws SQLException {
        ResultSet resultSet = super.read();
        super.disconnect();
        return resultSet;
    }
    
    
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMajorSubject() {
        return majorSubject;
    }

    public void setMajorSubject(String majorSubject) {
        this.majorSubject = majorSubject;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public int getEnteringYear() {
        return enteringYear;
    }

    public void setEnteringYear(int enteringYear) {
        this.enteringYear = enteringYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float getTotalAverage() {
        return totalAverage;
    }

    public void setTotalAverage(float totalAverage) {
        this.totalAverage = totalAverage;
    }

    public int getStartClass() {
        return startClass;
    }

    public void setStartClass(int startClass) {
        this.startClass = startClass;
    }

    public int getEndClass() {
        return endClass;
    }

    public void setEndClass(int endClass) {
        this.endClass = endClass;
    }
}
