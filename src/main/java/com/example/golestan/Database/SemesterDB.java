package com.example.golestan.Database;

import java.sql.ResultSet;
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

    public String currentSemester() throws SQLException {
        super.setQuery("SELECT * FROM Semesters");
        ResultSet resultSet = super.read();
        while (resultSet.next()) {
            String name = resultSet.getString("Name");
            int year = resultSet.getInt("Year");
            String Current = resultSet.getString("Current");
            if (Current.equals("yes")) {
                return name + year;
            }
        }
        return "";
    }

    public void setCurrent() throws SQLException {
        deleteCurrent();
        super.setQuery("UPDATE Semesters SET Current = 'yes' WHERE Name = '" + name + "' AND  Year = " + year);
        super.write();
        super.disconnect();
    }

    private void deleteCurrent() throws SQLException {
        super.setQuery("SELECT * FROM Semesters");
        ResultSet resultSet = super.read();
        while (resultSet.next()) {
            String name = resultSet.getString("Name");
            int year = resultSet.getInt("Year");
            String Current = resultSet.getString("Current");
            if (Current.equals("yes")) {
                super.setQuery("UPDATE Semesters SET Current = '' WHERE Name = '" + name + "' AND Year = " + year);
                super.write();
                super.disconnect();
                break;
            }
        }
    }

    public ResultSet semesterList() throws SQLException {
        super.setQuery("SELECT * FROM Semesters");
        ResultSet resultSet = super.read();

        return resultSet;
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
