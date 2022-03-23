package com.example.golestan.Database;

import java.sql.SQLException;

public class CourseDB extends Database {
    private String name;
    private String code;
    private int vahed;
    private String profFirstName;
    private String profLastName;
    private String college;
    private String semester;
    private int startClass;
    private int endClass;
    private String day;
    private String examDate;

    public CourseDB() {
        this.name = "";
        this.code = "";
        this.vahed = 0;
        this.profFirstName = "";
        this.profLastName = "";
        this.college = "";
        this.semester = "";
        this.startClass = 0;
        this.endClass = 0;
        this.day = "";
        this.examDate = "";
    }

    public CourseDB(String name, String code, int vahed, String profFirstName, String profLastName, String college, String semester, int startClass, int endClass, String day, String examDate) {
        this.name = name;
        this.code = code;
        this.vahed = vahed;
        this.profFirstName = profFirstName;
        this.profLastName = profLastName;
        this.college = college;
        this.semester = semester;
        this.startClass = startClass;
        this.endClass = endClass;
        this.day = day;
        this.examDate = examDate;
    }

    public boolean addCourse() throws SQLException {
        if (checkCourse()) {
            return false;
        } else {
            super.setQuery("INSERT INTO Courses (Name, Code, Vahed, ProfessorFirstName, ProfessorLastName, College, " +
                    "Semester, StartClass, EndClass, Day, ExamDate) VALUES ('" + name + "', '" + code + "', " + vahed +
                    ", '" + profFirstName + "', '" + profLastName + "', '" + college + "', '" + semester + "', " + startClass +
                    ", " + endClass + ", '" + day + "', '" + examDate + "')");
            super.write();
        }

        super.disconnect();
        return true;
    }

    public boolean checkCourse() throws SQLException {
        super.setQuery("SELECT * FROM Courses WHERE Code = '" + code + "'");
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getVahed() {
        return vahed;
    }

    public void setVahed(int vahed) {
        this.vahed = vahed;
    }

    public String getProfFirstName() {
        return profFirstName;
    }

    public void setProfFirstName(String profFirstName) {
        this.profFirstName = profFirstName;
    }

    public String getProfLastName() {
        return profLastName;
    }

    public void setProfLastName(String profLastName) {
        this.profLastName = profLastName;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }
}
