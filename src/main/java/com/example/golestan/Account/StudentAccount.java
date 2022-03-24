package com.example.golestan.Account;

import com.example.golestan.Database.StudentDB;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentAccount extends StudentDB {

    public StudentAccount() {
        super();
    }

    public boolean login(String username, String password) throws SQLException {
        super.setUsername(username);
        super.setPassword(password);

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

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("PLEASE ENTER CORRECT PASSWORD !!");
        alert.show();
        return false;
    }

    public boolean signup(String studentId, String username, String password, String firstName, String lastName, String majorSubject, String college, String enteringYear) throws SQLException {
        if (checkValid(studentId, username, password, firstName, lastName, majorSubject, college, enteringYear)) {
            super.setStudentId(Integer.parseInt(studentId));
            super.setUsername(username);
            super.setPassword(password);
            super.setFirstName(firstName);
            super.setLastName(lastName);
            super.setMajorSubject(majorSubject);
            super.setCollege(college);
            super.setEnteringYear(Integer.parseInt(enteringYear));
        } else {
            return false;
        }

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

    public boolean checkValid(String studentId, String username, String password, String firstName, String lastName, String majorSubject, String college, String enteringYear) {
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("^[0-9]+$");
        matcher = pattern.matcher(studentId);
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS STUDENT ID IS INVALID !!");
            alert.show();
            return false;
        }

        if (username.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS USERNAME IS INVALID !!");
            alert.show();
            return false;
        }

        if (password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS PASSWORD IS INVALID !!");
            alert.show();
            return false;
        }

        pattern = Pattern.compile("^[A-za-z]+$");
        matcher = pattern.matcher(firstName);
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS FIRSTNAME IS INVALID !!");
            alert.show();
            return false;
        }

        matcher = pattern.matcher(lastName);
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS LASTNAME IS INVALID !!");
            alert.show();
            return false;
        }

        matcher = pattern.matcher(majorSubject);
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS MAJOR SUBJECT IS INVALID !!");
            alert.show();
            return false;
        }

        matcher = pattern.matcher(college);
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS COLLEGE IS INVALID !!");
            alert.show();
            return false;
        }


        pattern = Pattern.compile("^[1-9]{4}$");
        matcher = pattern.matcher(enteringYear);
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS ENTERING YEAR IS INVALID !!");
            alert.show();
            return false;
        }

        return true;
    }
}
