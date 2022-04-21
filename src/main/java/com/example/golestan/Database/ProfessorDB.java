package com.example.golestan.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorDB extends Database implements Users {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String group;
    private String college;

    public ProfessorDB() {
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.group = "";
        this.college = "";
    }

    @Override // This function add professor to table of Professors in database.
    public void addToDatabase() throws SQLException {
        super.setQuery("INSERT INTO Professors (Username, Password, Firstname, Lastname, Goroh, College) VALUES " +
                "('" + username + "', '" + password + "', '" + firstName + "', '" + lastName + "', '" +
                group + "', '" + college + "')");
        super.write();
        super.disconnect();
    }

    @Override // This function check that username exists or not.
    public boolean checkExistUsername(String username) throws SQLException {
        super.setQuery("SELECT * FROM Professors WHERE Username = '" + username + "'");
        if (super.read().isBeforeFirst()) {
            super.disconnect();
            return true;
        }

        super.disconnect();
        return false;
    }

    @Override
    public ResultSet findWithUsername() throws SQLException {
        super.setQuery("SELECT * FROM Professors WHERE Username = '" + username + "'");
        return super.read();
    }

    // This function find professors of special college.
    public ResultSet findProfWithCollege() throws SQLException {
        super.setQuery("SELECT * FROM Professors WHERE College = '" + college + "'");
        return super.read();
    }

    @Override // This function update information when professor edit his information.
    public void updateDatabase(String oldUsername) throws SQLException {
        super.setQuery("UPDATE Courses SET ProfessorFirstName = '" + firstName + "', ProfessorLastName = '" + lastName + "'"
                + " WHERE ProfessorFirstName = '" + findOldName(oldUsername)[0] + "' AND ProfessorLastName = '" + findOldName(oldUsername)[1] + "'");
        super.write();

        super.setQuery("UPDATE Professors SET Username = '" + username + "', Password = '" + password +
                "', Firstname = '" + firstName + "', Lastname = '" + lastName + "', Goroh = '" + group +
                "', College = '" + college + "' WHERE Username = '" + oldUsername + "'");
        super.write();


        super.disconnect();
    }

    @Override
    public String findName() throws SQLException {
        ResultSet resultSet = findWithUsername();
        String firstname = "", lastname = "";
        while (resultSet.next()) {
            firstname = resultSet.getString("Firstname");
            lastname = resultSet.getString("Lastname");
        }
        return firstname + " " + lastname;
    }

    private String[] findOldName(String oldUsername) throws SQLException {
        String user = getUsername();
        setUsername(oldUsername);
        String[] name = findName().split(" ");
        setUsername(user);
        return name;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String goroh) {
        this.group = goroh;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
