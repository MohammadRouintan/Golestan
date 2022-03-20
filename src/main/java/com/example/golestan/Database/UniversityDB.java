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

    public void signup() throws SQLException {
        super.setQuery("SELECT * FROM Universities WHERE Username = " + username);
        boolean user = super.isExist();
        super.setQuery("SELECT * FROM Universities WHERE Name = " + name);
        boolean tempName = super.isExist();
        if (user) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER ALREADY EXIST.\nYOU CANNOT USE THIS USERNAME !!");
            alert.show();
        } else if (tempName) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER ALREADY EXIST.\nYOU CANNOT USE THIS NAME !!");
            alert.show();
        } else {
            super.setQuery("INSERT INTO Universities (Name, Username, Password) VALUES " +
                    "(" + name + ", " + username + ", " + password + ")");
            super.write();
        }
        super.disconnect();
    }

    public boolean login() throws SQLException {
        super.setQuery("SELECT * FROM Universities WHERE Username = " + username);
        if (!super.isExist()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER NOT FOUND !!\nPLEASE ENTER CORRECT USERNAME OR PASSWORD.");
            alert.show();
        } else {
            while (findUniversities().next()) {
                String recievePassword = findUniversities().getString("Password");
                if (recievePassword.equals(password)) {
                    super.disconnect();
                    return true;
                }
            }
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
