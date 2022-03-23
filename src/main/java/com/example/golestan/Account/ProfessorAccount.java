package com.example.golestan.Account;

import com.example.golestan.Database.ProfessorDB;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfessorAccount extends ProfessorDB {

    public ProfessorAccount() {
        super();
    }

    public boolean login(String username, String password) throws SQLException {
        super.setUsername(username);
        super.setPassword(password);


        if (!super.checkProfWithUsername()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER NOT FOUND !!\nPLEASE ENTER CORRECT USERNAME OR PASSWORD.");
            alert.show();
            return false;
        } else {
            ResultSet resultSet = findProf();
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
        super.disconnect();
        return false;
    }


    public boolean signup(String username, String password, String firstName, String lastName, String group, String college) throws SQLException{
        if (checkValid(username, password, firstName, lastName, group, college)) {
            super.setUsername(username);
            super.setPassword(password);
            super.setFirstName(firstName);
            super.setLastName(lastName);
            super.setGoroh(Integer.parseInt(group));
            super.setCollege(college);
        } else {
            return false;
        }

        if (super.checkProfWithUsername()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER ALREADY EXIST.\nYOU CANNOT USE THIS USERNAME !!");
            alert.show();
            return false;
        } else {
            super.addProf();
        }

        return true;
    }

    public boolean checkValid(String username, String password, String firstName, String lastName, String group, String college) {
        Pattern pattern;
        Matcher matcher;

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

        pattern = Pattern.compile("^[A-Za-z]+$");
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

        matcher = pattern.matcher(college);
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS COLLEGE IS INVALID !!");
            alert.show();
            return false;
        }

        pattern = Pattern.compile("^[1-9]+$");
        matcher = pattern.matcher(group);
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS GROUP IS INVALID !!");
            alert.show();
            return false;
        }
        return true;
    }
}
