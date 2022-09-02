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
    private int unit;
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
        this.unit = 0;
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

    public CourseDB(String name, String code, String professor, int unit, String semester) {
        this.name = name;
        this.code = code;
        this.professor = professor;
        this.unit = unit;
        this.semester = semester;
    }

    @Override // This function add course to table of Courses in database.
    public void addToDatabase() throws SQLException {
        super.setQuery("INSERT INTO Courses (Name, Code, Vahed, ProfessorFirstName, ProfessorLastName, College, " +
                "Semester, StartClass, EndClass, Day) VALUES ('" + name + "', '" + code + "', " + unit +
                ", '" + profFirstName + "', '" + profLastName + "', '" + college + "', '" + semester + "', " + startClass +
                ", " + endClass + ", '" + day + "')");
        super.write();
        super.disconnect();
    }

    // This function check that course with this code exists or not.
    public boolean checkCourse() throws SQLException {
        if (findCourseWithCode().isBeforeFirst()) {
            super.disconnect();
            return true;
        }

        super.disconnect();
        return false;
    }

    // This function find Courses of special semester.
    public ResultSet findCourseWithSemester() throws SQLException {
        super.setQuery("SELECT * FROM Courses WHERE Semester = '" + semester + "'");
        return super.read();
    }

    // This function find course with special code.
    public ResultSet findCourseWithCode() throws SQLException {
        super.setQuery("SELECT * FROM Courses WHERE Code = '" + code + "'");
        return super.read();
    }

    // This function find courses with special professor.
    public ResultSet findCourseWithProf(String firstName, String lastName) throws SQLException {
        super.setQuery("SELECT * FROM Courses WHERE ProfessorFirstName = '" + firstName + "' AND " +
                "ProfessorLastName = '" + lastName + "'");
        return super.read();
    }

    public int findCourseYear() throws SQLException {
        ResultSet resultSet = findCourseWithCode();
        int year = 0;
        while (resultSet.next()) {
            String semester = resultSet.getString("Semester");
            year = Integer.parseInt(semester.substring(semester.length() - 4, semester.length()));
        }
        return year;
    }

    public boolean checkValid(String name, String code, String unit, int startClass, int endClass, String day) {
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("^[A-Za-z]+[1-9]*$");
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
        matcher = pattern.matcher(unit);
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS VAHED IS INVALID !!");
            alert.show();
            return false;
        }

        if (startClass >= endClass) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("START AND END CLASS IS INVALID !!");
            alert.show();
            return false;
        }

        if (day.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("PLEASE SELECT DAYS OF CLASS !!");
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

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getUnit() {
        return unit;
    }

    public void setProfFirstName(String profFirstName) {
        this.profFirstName = profFirstName;
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

    public void setStartClass(int startClass) {
        this.startClass = startClass;
    }

    public void setEndClass(int endClass) {
        this.endClass = endClass;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
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
}
