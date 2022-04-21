package com.example.golestan.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDB extends Database implements Users {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String majorSubject;
    private String college;
    private String courseCode;
    private String name;
    private String semester;
    private String status;
    private float score;
    private float totalAverage;
    private int studentId;
    private int enteringYear;
    private int unit;

    public StudentDB() {
        this.studentId = 0;
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.majorSubject = "";
        this.college = "";
        this.enteringYear = 0;
        this.courseCode = "";
        this.score = 0;
        this.totalAverage = 0;
        this.semester = "";
    }

    public StudentDB(String name, int unit, float score) {
        this.name = name;
        this.unit = unit;
        this.score = score;
    }

    public StudentDB(int studentId, String name, float score) {
        this.studentId = studentId;
        this.name = name;
        this.score = score;
    }

    @Override // This function add student to table of Students in database
    public void addToDatabase() throws SQLException {
        super.setQuery("INSERT INTO Students (StudentId, Username, Password, Firstname, Lastname, MajorSubject, College, EnteringYear, " +
                "CourseCode, Score, TotalAverage) VALUES " + "(" + studentId + ", '" + username + "', '" + password +
                "', '" + firstName + "', '" + lastName + "', '" + majorSubject + "', '" + college + "', " + enteringYear +
                ", '" + courseCode + "', " + score + ", " + totalAverage + ")");
        super.write();
        super.disconnect();
    }

    // When student receive a course this function add information of student with information of selected course.
    public void addStuWithCourse() throws SQLException {
        if (!checkExistCourse()) {
            super.setQuery("INSERT INTO Students (StudentId, Username, Password, Firstname, Lastname, MajorSubject, College, EnteringYear, " +
                    "CourseCode, Semester, Score, TotalAverage) VALUES " + "(" + studentId + ", '" + username + "', '" + password + "', '" +
                    firstName + "', '" + lastName + "', '" + majorSubject + "', '" + college + "', " + enteringYear + ", '" +
                    courseCode + "', '" + semester + "', " + score + ", " + totalAverage + ")");
            super.write();
            super.disconnect();
        }
    }

    @Override // This function check that username exists or not.
    public boolean checkExistUsername(String username) throws SQLException {
        super.setQuery("SELECT * FROM Students WHERE Username = '" + username + "'");
        if (super.read().isBeforeFirst()) {
            super.disconnect();
            return true;
        }

        super.disconnect();
        return false;
    }

    // This function check that student with this student id exists or not.
    public boolean checkStuWithStudentId() throws SQLException {
        super.setQuery("SELECT * FROM Students WHERE StudentId = " + studentId);
        if (super.read().isBeforeFirst()) {
            super.disconnect();
            return true;
        }

        super.disconnect();
        return false;
    }

    @Override // This function update information when student edit his information.
    public void updateDatabase(String oldUsername) throws SQLException {
        super.setQuery("UPDATE Students SET StudentId = " + studentId + ", Username = '" + username + "', Password = '" + password +
                "', Firstname = '" + firstName + "', Lastname = '" + lastName + "', MajorSubject = '" + majorSubject +
                "', College = '" + college + "', EnteringYear = " + enteringYear + " WHERE Username = '" + oldUsername + "'");
        super.write();
        super.disconnect();
    }

    // This function remove course when student click remove button in register pane.
    public void removeCourse() throws SQLException {
        if (checkExistUsername(username)) {
            super.setQuery("DELETE FROM Students WHERE Username = '" + username + "' AND CourseCode = '" + courseCode + "'");
            super.write();
            super.disconnect();
        }
    }

    // This function edit score when professor gives grade.
    public void editScore() throws SQLException {
        super.setQuery("UPDATE Students SET Score = " + score + " WHERE StudentId = " + studentId + " AND CourseCode = '" + courseCode + "'");
        super.write();
        super.disconnect();
    }

    // This function add total average of special semester to database
    public void addTotalAverage() throws SQLException {
        super.setQuery("UPDATE Students SET TotalAverage = " + totalAverage + "WHERE Username = '" + username + "' AND Semester = '" + semester + "'");
        super.write();
        super.disconnect();
    }

    // This function find a course with special code
    public ResultSet findCourseWithCode() throws SQLException {
        super.setQuery("SELECT * FROM Students WHERE CourseCode = '" + courseCode + "'");
        return super.read();
    }

    @Override
    public ResultSet findWithUsername() throws SQLException {
        super.setQuery("SELECT * FROM Students WHERE Username = '" + username + "'");
        return super.read();
    }

    // This function find a course with special semester
    public ResultSet findCourseWithSemester() throws SQLException {
        super.setQuery("SELECT CourseCode FROM Students WHERE Username = '" + username + "' AND Semester = '" + semester + "'");
        return super.read();
    }

    // This function find score of student with course code.
    public ResultSet findScoreWithCode() throws SQLException {
        super.setQuery("SELECT Score FROM Students WHERE CourseCode = '" + courseCode + "' AND Username = '" + username + "'");
        return super.read();
    }

    @Override
    public String findName() throws SQLException {
        ResultSet resultSet = findWithUsername();
        String firstName = "", lastName = "";
        while (resultSet.next()) {
            firstName = resultSet.getString("Firstname");
            lastName = resultSet.getString("Lastname");
        }
        return firstName + " " + lastName;
    }

    public int findEnteringYear() throws SQLException {
        ResultSet resultSet = findWithUsername();
        int year = 0;
        while (resultSet.next()) {
            year = resultSet.getInt("EnteringYear");
        }
        return year;
    }

    public float findTotalAve() throws SQLException {
        super.setQuery("SELECT TotalAverage FROM Students WHERE Username = '" + username +"' AND Semester = '" + semester + "'");
        ResultSet resultSet = super.read();
        float total = 0;
        while (resultSet.next()) {
            total = resultSet.getFloat("TotalAverage");
            if (total != 0) {
                break;
            }
        }
        return total;
    }

    // This function change status of register.
    public void changeStatus(String semester) throws SQLException {
        super.setQuery("UPDATE Students SET Status = '" + status + "' WHERE Username = '" + username + "' AND Semester = '" + semester + "'");
        super.write();
        super.disconnect();
    }

    // This function find status of register.
    public String findStatus() throws SQLException {
        super.setQuery("SELECT Status FROM Students WHERE CourseCode = '" + courseCode + "' AND Username = '" + username + "'");
        ResultSet resultSet = super.read();
        String status = "";
        while (resultSet.next()) {
            status = resultSet.getString("Status");
        }
        return status;
    }

    public String findStatusOfRegister() throws SQLException {
        super.setQuery("SELECT Status FROM Students WHERE Username = '" + username + "' AND Semester = '" + semester + "'");
        ResultSet resultSet = super.read();
        String status = "";
        while (resultSet.next()) {
            status = resultSet.getString("Status");
            if (!status.equals("")) {
                break;
            }
        }
        return status;
    }

    // This function check that course with this username and course code exists or not.
    public boolean checkExistCourse() throws SQLException {
        super.setQuery("SELECT * FROM Students WHERE CourseCode = '" + courseCode + "' AND Username = '" + username + "'");
        if (super.isExist()) {
            super.disconnect();
            return true;
        }

        super.disconnect();
        return false;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public void setMajorSubject(String majorSubject) {
        this.majorSubject = majorSubject;
    }

    public String getMajorSubject() {
        return majorSubject;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public int getEnteringYear() {
        return enteringYear;
    }

    public void setEnteringYear(int enteringYear) {
        this.enteringYear = enteringYear;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setTotalAverage(float totalAverage) {
        this.totalAverage = totalAverage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnit() {
        return unit;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
