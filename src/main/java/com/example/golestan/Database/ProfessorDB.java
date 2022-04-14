package com.example.golestan.Database;

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

    public boolean addProf() throws SQLException {
        if (checkProfWithUsername()) {
            return false;
        } else {
            super.setQuery("INSERT INTO Professors (Username, Password, Firstname, Lastname, Goroh, College, " +
                    "Semester, Course, StartClass, EndClass) VALUES " +
                    "('" + username + "', '" + password + "', '" + firstName + "', '" + lastName + "', " +
                    goroh + ", '" + college + "', '" + semester + "', '" +
                    course + "', " + startClass + ", " + endClass + ")");
            super.write();
        }

        super.disconnect();
        return true;
    }

    public boolean removeProf() throws SQLException {
        if (!checkProfWithUsername()) {
            return false;
        } else {
            super.setQuery("DELETE FROM Professors WHERE Username = '" + username + "'");
            super.write();
        }

        super.disconnect();
        return true;
    }

    public boolean checkProfWithUsername() throws SQLException {
        super.setQuery("SELECT * FROM Professors WHERE Username = '" + username + "'");
        if (super.isExist()) {
            super.disconnect();
            return true;
        }

        super.disconnect();
        return false;
    }

    public ResultSet findProf() throws SQLException {
        super.setQuery("SELECT * FROM Professors WHERE Username = '" + username + "'");
        return super.read();
    }

    public ResultSet findProfWithCollege() throws SQLException {
        super.setQuery("SELECT * FROM Professors WHERE College = '" + college + "'");
        return super.read();
    }

    public boolean editInfo(String oldUsername) throws SQLException {
        boolean flag = false;
        if (oldUsername.equals(username) || !checkProfWithUsername()) {
            flag = true;
        }

        String user = getUsername();
        setUsername(oldUsername);
        String[] name = findName().split(" ");
        if (checkProfWithUsername() && flag) {
            super.setQuery("UPDATE Professors SET Username = '" + user + "', Password = '" + password +
                    "', Firstname = '" + firstName + "', Lastname = '" + lastName + "', Goroh = " + goroh +
                    ", College = '" + college + "' WHERE Username = '" + oldUsername + "'");
            super.write();
            super.setQuery("UPDATE Courses SET ProfessorFirstName = '" + firstName + "', ProfessorLastName = '" + lastName + "'"
                        + " WHERE ProfessorFirstName = '" + name[0] + "' AND ProfessorLastName = '" + name[1] + "'");
            super.write();
            super.disconnect();
            return true;
        }

        return false;
    }

    public String findName() throws SQLException {
        ResultSet resultSet = findProf();
        String firstname = "", lastname = "";
        while (resultSet.next()) {
            firstname = resultSet.getString("Firstname");
            lastname = resultSet.getString("Lastname");
        }
        return firstname + " " + lastname;
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
