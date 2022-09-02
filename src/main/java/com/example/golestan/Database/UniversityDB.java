package com.example.golestan.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UniversityDB extends Database implements Users{
    private String name;
    private String username;
    private String password;

    public UniversityDB() {
        this.name = "";
        this.username = "";
        this.password = "";
    }

    @Override // This function add university to table of Universities in database.
    public void addToDatabase() throws SQLException {
        super.setQuery("INSERT INTO Universities (Name, Username, Password) VALUES " +
                "('" + name + "', '" + username + "', '" + password + "')");
        super.write();
        super.disconnect();
    }

    @Override // This function update information when university edit his information.
    public void updateDatabase(String oldUsername) throws SQLException {
        super.setQuery("UPDATE Universities SET Name = '" + name + "', Username = '" + username + "', Password = '" +
                password + "' WHERE Username = '" + oldUsername + "'");
        super.write();
        super.disconnect();
    }

    // This function check that university with this name exists or not.
    public boolean checkUniWithName() throws SQLException {
        super.setQuery("SELECT * FROM Universities WHERE Name = '" + name + "'");
        if (super.read().isBeforeFirst()) {
            super.disconnect();
            return true;
        }

        super.disconnect();
        return false;
    }

    @Override // This function check that username exists or not.
    public boolean checkExistUsername(String username) throws SQLException {
        super.setQuery("SELECT * FROM Universities WHERE Username = '" + username + "'");
        if (super.read().isBeforeFirst()) {
            super.disconnect();
            return true;
        }

        super.disconnect();
        return false;
    }

    @Override
    public ResultSet findWithUsername() throws SQLException {
        super.setQuery("SELECT * FROM `Universities` WHERE Username = '" + username + "'");
        return super.read();
    }

    @Override
    public String findName() throws SQLException {
        ResultSet resultSet = findWithUsername();
        String name = "";
        while (resultSet.next()) {
            name = resultSet.getString("Name");
        }
        return name;
    }

    // This function return number of universities.
    public int numberOfUni () throws SQLException {
        super.setQuery("SELECT * FROM Universities");
        ResultSet resultSet = super.read();
        int counter = 0;
        while (resultSet.next()) {
            counter++;
        }

        return counter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
