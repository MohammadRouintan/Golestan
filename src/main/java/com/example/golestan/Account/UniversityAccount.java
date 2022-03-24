package com.example.golestan.Account;

import com.example.golestan.Database.UniversityDB;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UniversityAccount extends UniversityDB {

    public UniversityAccount() {
        super();
    }

    public boolean login(String username, String password) throws SQLException {
        super.setUsername(username);
        super.setPassword(password);

        if (!super.checkUniWithUsername()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER NOT FOUND !!\nPLEASE ENTER CORRECT USERNAME OR PASSWORD.");
            alert.show();
            return false;
        } else {
            ResultSet resultSet = findUni();
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


    public boolean signup(String name, String username, String password) throws SQLException {
        if (checkValid(name, username, password)) {
            super.setName(name);
            super.setUsername(username);
            super.setPassword(password);
        } else {
            return false;
        }

        if (super.checkUniWithName()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER ALREADY EXIST.\nYOU CANNOT USE THIS NAME !!");
            alert.show();
            return false;
        } else if (super.checkUniWithUsername()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER ALREADY EXIST.\nYOU CANNOT USE THIS USERNAME !!");
            alert.show();
            return false;
        } else {
            boolean check = super.addUni();
            return check;
        }
    }

    public boolean checkValid(String name, String username, String password) {
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS NAME IS INVALID !!");
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
            alert.setContentText("THIS USERNAME IS INVALID !!");
            alert.show();
            return false;
        }

        return true;
    }

}
