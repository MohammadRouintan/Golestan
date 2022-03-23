package com.example.golestan.Database;

import java.sql.SQLException;

public class SemesterDB extends Database {
    private String name;
    private int year;

    public SemesterDB() {
        this.name = "";
        this.year = 0;
    }

    public SemesterDB(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public boolean addSemester() throws SQLException {
        if (checkSemester()) {
            return false;
        } else {
            super.setQuery("INSERT INTO Semesters (Name, Year) VALUES ('" + name + "', '" + year + "')");
            super.write();
        }
        super.disconnect();
        return true;
    }

    public boolean checkSemester() throws SQLException {
        super.setQuery("SELECT * FROM Semesters WHERE Name = '" + name + "' AND Year = '" + year + "'");
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
