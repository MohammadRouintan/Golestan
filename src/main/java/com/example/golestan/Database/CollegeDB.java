package com.example.golestan.Database;

import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollegeDB extends Database {
    String name;

    public CollegeDB() {
        this.name = "";
    }
    public CollegeDB(String name) {
        this.name = name;
    }

    @Override // This function add college to table of Colleges in database.
    public void addToDatabase() throws SQLException {
        if (!checkCollegeWithName()) {
            super.setQuery("INSERT INTO Colleges (Name) VALUES ('" + name + "')");
            super.write();
            super.disconnect();
        }
    }

    // This function check that college with this name exists or not.
    public boolean checkCollegeWithName() throws SQLException {
        super.setQuery("SELECT * FROM Colleges WHERE Name = '" + name + "'");
        if (super.read().isBeforeFirst()) {
            super.disconnect();
            return true;
        }

        super.disconnect();
        return false;
    }

    // This function return list of college.
    public ArrayList<String> collegeNames() throws SQLException {
        super.setQuery("SELECT Name FROM Colleges");
        ResultSet resultSet = super.read();
        ArrayList<String> names = new ArrayList<>();

        while (resultSet.next()) {
            String name = resultSet.getString("Name");
            names.add(name);
        }
        return names;
    }

    public boolean checkValid() {
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS NAME IS INVALID !!");
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
}
