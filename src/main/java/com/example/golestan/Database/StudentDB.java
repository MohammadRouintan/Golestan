package com.example.golestan.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDB extends Database {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String majorSubject;
    private String college;
    private String courseCode;
    private String name;
    private float score;
    private float totalAverage;
    private int studentId;
    private int enteringYear;
    private int vahed;

    public StudentDB() {
        this.studentId = 0;
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.majorSubject = "";
        this.college = "";
        this.enteringYear = 0;
        this.courseCode = "";
        this.score = 0;
        this.totalAverage = 0;
    }

    public StudentDB(String name, int vahed, float score) {
        this.name = name;
        this.vahed = vahed;
        this.score = score;
    }

    public StudentDB(int studentId, String name, float score) {
        this.studentId = studentId;
        this.name = name;
        this.score = score;
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
        this.courseCode = "";
        this.score = 0;
        this.totalAverage = 0;
    }

    public StudentDB(int studentId, String username, String password, String firstName, String lastName, String majorSubject, String college, int enteringYear, String courseCode, float score, float totalAverage) {
        this.studentId = studentId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.majorSubject = majorSubject;
        this.college = college;
        this.enteringYear = enteringYear;
        this.courseCode = courseCode;
        this.score = score;
        this.totalAverage = totalAverage;
    }

    public boolean addStu() throws SQLException {
        if (checkStuWithUsername(username) || checkStuWithStudentId()) {
            return false;
        } else {
            super.setQuery("INSERT INTO Students (StudentId, Username, Password, Firstname, Lastname, MajorSubject, College, EnteringYear, " +
                    "CourseCode, Score, TotalAverage) VALUES " + "(" + studentId + ", '" + username + "', '" + password +
                    "', '" + firstName + "', '" + lastName + "', '" + majorSubject + "', '" + college + "', " + enteringYear +
                    ", '" + courseCode + "', " + score + ", " + totalAverage + ")");
            super.write();
        }

        super.disconnect();
        return true;
    }

    public void addStuWithCourse() throws SQLException {
        super.setQuery("INSERT INTO Students (StudentId, Username, Password, Firstname, Lastname, MajorSubject, College, EnteringYear, " +
                "CourseCode, Score, TotalAverage) VALUES " + "(" + studentId + ", '" + username + "', '" + password + "', '" +
                firstName + "', '" + lastName + "', '" + majorSubject + "', '" + college + "', " + enteringYear + ", '" +
                courseCode + "', " + score + ", " + totalAverage + ")");
        super.write();
        super.disconnect();
    }

    public boolean checkStuWithUsername(String username) throws SQLException {
        super.setQuery("SELECT * FROM Students WHERE Username = '" + username + "'");
        if (super.isExist()) {
            super.disconnect();
            return true;
        }

        super.disconnect();
        return false;
    }

    public boolean checkStuWithStudentId() throws SQLException {
        super.setQuery("SELECT * FROM Students WHERE StudentId = " + studentId);
        if (super.isExist()) {
            super.disconnect();
            return true;
        }

        super.disconnect();
        return false;
    }

    public boolean editInfo(String oldUsername) throws SQLException {
        boolean flag = false;
        if (oldUsername.equals(username) || !checkStuWithUsername(username)) {
            flag = true;
        }

        if (checkStuWithUsername(oldUsername) && !checkStuWithStudentId() && flag) {
            super.setQuery("UPDATE Students SET StudentId = " + studentId + ", Username = '" + username + "', Password = '" + password +
                    "', Firstname = '" + firstName + "', Lastname = '" + lastName + "', MajorSubject = '" + majorSubject +
                    "', College = '" + college + "', EnteringYear = " + enteringYear + " WHERE Username = '" + oldUsername + "'");
            super.write();
            return true;
        }

        super.disconnect();
        return false;
    }

    public void removeStu() throws SQLException {
        if (checkStuWithUsername(username)) {
            super.setQuery("DELETE FROM Students WHERE Username = '" + username + "' AND CourseCode = '" + courseCode + "'");
            super.write();
            super.disconnect();
        }
    }

    public void addScore() throws SQLException {
        super.setQuery("UPDATE Students SET Score = " + score + " WHERE StudentId = " + studentId + " AND CourseCode = '" + courseCode + "'");
        super.write();
        super.disconnect();
    }

    public ResultSet findCourseWithCode() throws SQLException {
        super.setQuery("SELECT * FROM Students WHERE CourseCode = '" + courseCode + "'");
        return super.read();
    }

    public ResultSet findStuWithUsername() throws SQLException {
        super.setQuery("SELECT * FROM Students WHERE Username = '" + username + "'");
        return super.read();
    }

    public ResultSet findScoreWithCode() throws SQLException {
        super.setQuery("SELECT Score FROM Students WHERE CourseCode = '" + courseCode + "'");
        return super.read();
    }

    public String findName() throws SQLException {
        ResultSet resultSet = findStuWithUsername();
        String firstName = "", lastName = "";
        while (resultSet.next()) {
            firstName = resultSet.getString("Firstname");
            lastName = resultSet.getString("Lastname");
        }
        return firstName + " " + lastName;
    }

    public int findYear() throws SQLException {
        ResultSet resultSet = findStuWithUsername();
        int year = 0;
        while (resultSet.next()) {
            year = resultSet.getInt("EnteringYear");
        }
        return year;
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

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVahed() {
        return vahed;
    }

    public void setVahed(int vahed) {
        this.vahed = vahed;
    }
}
