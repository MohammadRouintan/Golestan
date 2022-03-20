package com.example.golestan.Database;

import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorDB extends Database {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int goroh;
    private String college;
    private String semester;
    private String course;
    private int startClass;
    private int endClass;

    public ProfessorDB() {
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.goroh = 0;
        this.college = "";
        this.semester = "";
        this.course = "";
        this.startClass = 0;
        this.endClass = 0;
    }

    public ProfessorDB(String username, String password, String firstName, String lastName, int goroh, String college) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.goroh = goroh;
        this.college = college;
        this.semester = "";
        this.course = "";
        this.startClass = 0;
        this.endClass = 0;
    }

    public ProfessorDB(String username, String password, String firstName, String lastName, int goroh, String college, String semester, String course, int startClass, int endClass) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.goroh = goroh;
        this.college = college;
        this.semester = semester;
        this.course = course;
        this.startClass = startClass;
        this.endClass = endClass;
    }

    public void signup() throws SQLException {
        super.setQuery("SELECT * FROM Professors WHERE Username = " + username);
        if (super.isExist()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER ALREADY EXIST.\nYOU CANNOT USE THIS USERNAME !!");
            alert.show();
        } else {
            super.setQuery("INSERT INTO Professors (Username, Password, Firstname, Lastname, Goroh, College, Semester, Course, StartClass, EndClass) VALUES " +
                    "(" + username + ", " + password + ", " + firstName + ", " + lastName + ", " +
                    goroh + ", " + college + ", " + semester + ", " + course + ", " + startClass + ", " + endClass + ")");
            super.write();
        }
        super.disconnect();
    }

    public boolean login() throws SQLException {
        super.setQuery("SELECT * FROM Professors WHERE Username = " + username);
        if (!super.isExist()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER NOT FOUND !!\nPLEASE ENTER CORRECT USERNAME OR PASSWORD.");
            alert.show();
        } else {
            while (findProfessor().next()) {
                String recievePassword = findProfessor().getString("Password");
                if (recievePassword.equals(password)) {
                    super.disconnect();
                    return true;
                }
            }
        }

        super.disconnect();
        return false;
    }

    public ResultSet findProfessor() throws SQLException {
        ResultSet resultSet = super.read();
        super.disconnect();
        return resultSet;
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

    public int getGoroh() {
        return goroh;
    }

    public void setGoroh(int goroh) {
        this.goroh = goroh;
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
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
