package com.example.golestan.Database;

import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourseDB extends Database {
    private String professor;
    private String name;
    private String code;
    private int vahed;
    private String profFirstName;
    private String profLastName;
    private String college;
    private String semester;
    private int startClass;
    private int endClass;
    private String day;
    private String time;

    public CourseDB() {
        this.name = "";
        this.code = "";
        this.vahed = 0;
        this.profFirstName = "";
        this.profLastName = "";
        this.college = "";
        this.semester = "";
        this.startClass = 0;
        this.endClass = 0;
        this.day = "";
    }

    public CourseDB(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public CourseDB(String name, String day, String time) {
        this.name = name;
        this.day = day;
        this.time = time;
    }

    public CourseDB(String name, String code, int vahed, String profFirstName, String profLastName, String college, String semester, int startClass, int endClass, String day) {
        this.name = name;
        this.code = code;
        this.vahed = vahed;
        this.profFirstName = profFirstName;
        this.profLastName = profLastName;
        this.college = college;
        this.semester = semester;
        this.startClass = startClass;
        this.endClass = endClass;
        this.day = day;
    }

    public CourseDB(String name, String code, String professor, int vahed, String semester) {
        this.name = name;
        this.code = code;
        this.professor = professor;
        this.vahed = vahed;
        this.semester = semester;
    }

    public boolean addCourse() throws SQLException {
        if (checkCourse()) {
            return false;
        } else {
            super.setQuery("INSERT INTO Courses (Name, Code, Vahed, ProfessorFirstName, ProfessorLastName, College, " +
                    "Semester, StartClass, EndClass, Day) VALUES ('" + name + "', '" + code + "', " + vahed +
                    ", '" + profFirstName + "', '" + profLastName + "', '" + college + "', '" + semester + "', " + startClass +
                    ", " + endClass + ", '" + day + "')");
            super.write();
        }

        super.disconnect();
        return true;
    }

    public boolean checkCourse() throws SQLException {
        super.setQuery("SELECT * FROM Courses WHERE Code = '" + code + "'");
        if (super.isExist()) {
            super.disconnect();
            return true;
        }

        super.disconnect();
        return false;
    }

    public ResultSet findCourse() throws SQLException {
        super.setQuery("SELECT * FROM Courses");
        return super.read();
    }

    public ResultSet findCourseWithCode() throws SQLException {
        super.setQuery("SELECT * FROM Courses WHERE Code = '" + code + "'");
        return super.read();
    }

    public ResultSet findCourseWithStu(String username) throws SQLException {
        super.setQuery("SELECT * FROM Students WHERE Username = '" + username + "'");
        return super.read();
    }

    public ResultSet findCourseWithProf(String firstName, String lastName) throws SQLException {
        super.setQuery("SELECT Name, Code FROM Courses WHERE ProfessorFirstName = '" + firstName + "' AND " +
                "ProfessorLastName = '" + lastName + "'");
        return super.read();
    }

    public boolean checkValid(String name, String code, String vahed, String college, String semester, int startClass, int endClass) {
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("^[A-za-z]+[1-9]*$");
        matcher = pattern.matcher(name);
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS NAME IS INVALID !!");
            alert.show();
            return false;
        }

        pattern = Pattern.compile("^[1-9]+$");
        matcher = pattern.matcher(code);
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS CODE IS INVALID !!");
            alert.show();
            return false;
        }

        pattern = Pattern.compile("^[1-9]{1}$");
        matcher = pattern.matcher(vahed);
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS VAHED IS INVALID !!");
            alert.show();
            return false;
        }

        if (college.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS COLLEGE IS INVALID !!");
            alert.show();
            return false;
        }

        if (semester.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS SEMESTER IS INVALID !!");
            alert.show();
            return false;
        }

        if (startClass >= endClass) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("START AND END CLASS IS INVALID !!");
            alert.show();
            return false;
        }

        return true;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getVahed() {
        return vahed;
    }

    public void setVahed(int vahed) {
        this.vahed = vahed;
    }

    public String getProfFirstName() {
        return profFirstName;
    }

    public void setProfFirstName(String profFirstName) {
        this.profFirstName = profFirstName;
    }

    public String getProfLastName() {
        return profLastName;
    }

    public void setProfLastName(String profLastName) {
        this.profLastName = profLastName;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
