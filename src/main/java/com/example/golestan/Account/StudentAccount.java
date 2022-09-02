package com.example.golestan.Account;

import com.example.golestan.Database.StudentDB;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentAccount extends StudentDB implements UsersAccount{

    public StudentAccount() {
        super();
    }

    @Override
    public boolean login(String username, String password) throws SQLException {
        super.setUsername(username);
        super.setPassword(password);

        if (!super.checkExistUsername(username)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER NOT FOUND !!\nPLEASE ENTER CORRECT USERNAME OR PASSWORD.");
            alert.show();
            return false;
        } else {
            ResultSet resultSet = findWithUsername();
            while (resultSet.next()) {
                String pass = resultSet.getString("Password");
                if (pass.equals(password)) {
                    return true;
                }
            }
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("PLEASE ENTER CORRECT PASSWORD !!");
        alert.show();
        return false;
    }

    @Override
    public boolean signup() throws SQLException {
        if (!checkValid()) {
            return false;
        } else if (super.checkExistUsername(getUsername())) {
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
            super.addToDatabase();
        }

        return true;
    }

    @Override
    public boolean checkValid() {
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("^[A-Za-z]+$");
        matcher = pattern.matcher(getFirstName());
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS FIRSTNAME IS INVALID !!");
            alert.show();
            return false;
        }

        matcher = pattern.matcher(getLastName());
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS LASTNAME IS INVALID !!");
            alert.show();
            return false;
        }

        matcher = pattern.matcher(getMajorSubject());
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS MAJOR IS INVALID !!");
            alert.show();
            return false;
        }

        pattern = Pattern.compile("^[0-9]{7,9}$");
        matcher = pattern.matcher(String.valueOf(getStudentId()));
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS STUDENT ID IS INVALID !!");
            alert.show();
            return false;
        }

        if (getUsername().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS USERNAME IS INVALID !!");
            alert.show();
            return false;
        }

        if (getPassword().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS PASSWORD IS INVALID !!");
            alert.show();
            return false;
        }

        if (getCollege() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS COLLEGE IS INVALID !!");
            alert.show();
            return false;
        }

        if (getEnteringYear() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS ENTERING YEAR IS INVALID !!");
            alert.show();
            return false;
        }

        return true;
    }

    @Override
    public boolean editInfo(String oldUsername) throws SQLException {
        boolean flag = false;
        if (oldUsername.equals(getUsername()) || !super.checkExistUsername(getUsername())) {
            flag = true;
        }

        if (super.checkExistUsername(oldUsername) && !super.checkStuWithStudentId() && flag) {
            super.updateDatabase(oldUsername);
            return true;
        }

        return false;
    }

}
