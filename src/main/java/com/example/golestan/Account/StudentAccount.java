package com.example.golestan.Account;

import com.example.golestan.Database.StudentDB;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentAccount extends StudentDB {

    public StudentAccount() {
        super();
    }

    public boolean login(String username, String password, String job) throws SQLException {
        super.setUsername(username);
        super.setPassword(password);
        if (!job.equals("Student")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("PLEASE SELECT CORRECT JOB !!");
            alert.show();
            return false;
        }

        if (!super.checkStuWithUsername()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER NOT FOUND !!\nPLEASE ENTER CORRECT USERNAME OR PASSWORD.");
            alert.show();
            return false;
        } else {
            ResultSet resultSet = findStu();
            while (resultSet.next()) {
                String str = resultSet.getString("Password");
                if (str.equals(password)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean signup(int studentId, String username, String password, String firstName, String lastName, String majorSubject, String college, int enteringYear) throws SQLException {
        super.setStudentId(studentId);
        super.setUsername(username);
        super.setPassword(password);
        super.setFirstName(firstName);
        super.setLastName(lastName);
        super.setMajorSubject(majorSubject);
        super.setCollege(college);
        super.setEnteringYear(enteringYear);

        if (super.checkStuWithUsername()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER ALREADY EXIST.\nYOU CANNOT USE THIS USERNAME !!");
            alert.show();
            return false;
        } else if (super.checkStuWithStudentId()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER ALREADY EXIST.\nYOU CANNOT USE THIS STUDENT ID !!");
            alert.show();
            return false;
        } else {
            super.addStu();
        }

        return true;
    }
}
