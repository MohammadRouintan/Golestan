package com.example.golestan.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    @Override // This function add semester to table of Semesters in database.
    public void addToDatabase() throws SQLException {
        super.setQuery("INSERT INTO Semesters (Name, Year, Current) VALUES ('" + name + "', '" + year + "', '')");
        super.write();
        super.disconnect();
    }

    // This function check that semester with this name and year exists or not.
    public boolean checkSemester() throws SQLException {
        super.setQuery("SELECT * FROM Semesters WHERE Name = '" + name + "' AND Year = '" + year + "'");
        if (super.read().isBeforeFirst()) {
            super.disconnect();
            return true;
        }

        super.disconnect();
        return false;
    }

    // This function return current semester.
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

    // This function set current semester.
    public void setCurrent() throws SQLException {
        deleteCurrent();
        super.setQuery("UPDATE Semesters SET Current = 'yes' WHERE Name = '" + name + "' AND  Year = " + year);
        super.write();
        super.disconnect();
    }

    // This function delete old current semester.
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

    // This function return list of semester.
    public ArrayList<String> semesterList() throws SQLException {
        super.setQuery("SELECT * FROM Semesters");
        ResultSet resultSet = super.read();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()) {
            String name = resultSet.getString("Name");
            int year = resultSet.getInt("Year");
            list.add(name + " " + year);
        }
        return list;
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
