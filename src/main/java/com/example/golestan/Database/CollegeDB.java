package com.example.golestan.Database;

import java.sql.SQLException;

public class CollegeDB extends Database {
    private String name;

    public CollegeDB() {
        this.name = "";
    }

    public CollegeDB(String name) {
        this.name = name;
    }

    public boolean addCollege() throws SQLException {
        if (checkCollegeWithName()) {
            return false;
        } else {
            super.setQuery("INSERT INTO Colleges (Name) VALUES ('" + name + "')");
            super.write();
        }
        super.disconnect();
        return true;
    }

    public boolean checkCollegeWithName() throws SQLException {
        super.setQuery("SELECT * FROM Colleges WHERE Name = '" + name + "'");
        if (super.isExist()) {
            super.disconnect();
            return true;
        }

        super.disconnect();
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
