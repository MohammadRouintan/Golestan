package com.example.golestan.Database;

import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UniversityDB extends Database{
    private String name;
    private String username;
    private String password;

    public UniversityDB() {
        this.name = "";
        this.username = "";
        this.password = "";
    }

    public UniversityDB(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public boolean addUni() throws SQLException {
        if (checkUniWithName() || checkUniWithUsername()) {
            return false;
        } else {
            super.setQuery("INSERT INTO Universities (Name, Username, Password) VALUES " +
                    "('" + name + "', '" + username + "', '" + password + "')");
            super.write();
        }

        super.disconnect();
        return true;
    }

    public boolean updateUni() throws SQLException {
        if (!checkUniWithUsername() || !checkUniWithName()) {
            return false;
        } else {
            super.setQuery("UPDATE Universities SET Name = '" + name + "', Username = '" + username + "', Password = '" +
                    password + "' WHERE Name = '" + name + "' OR Username = '" + username + "'");
            super.write();
        }

        super.disconnect();
        return true;
    }

    public boolean removeUni() throws SQLException {
        if (!checkUniWithName() || !checkUniWithUsername()) {
            return false;
        } else {
            super.setQuery("DELETE FROM Universities WHERE Name = '" + name + "' OR Username = '" + username + "'");
            super.write();
        }

        super.disconnect();
        return true;
    }

    public boolean checkUniWithName() throws SQLException {
        super.setQuery("SELECT * FROM Universities WHERE Name = '" + name + "'");
        if (super.isExist()) {
            super.disconnect();
            return true;
        }

        super.disconnect();
        return false;
    }

    public boolean checkUniWithUsername() throws SQLException {
        super.setQuery("SELECT * FROM Universities WHERE Username = '" + username + "'");
        if (super.isExist()) {
            super.disconnect();
            return true;
        }

        super.disconnect();
        return false;
    }

    public ResultSet findUniversities() throws SQLException {
        ResultSet resultSet = super.read();
        super.disconnect();
        return resultSet;
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
