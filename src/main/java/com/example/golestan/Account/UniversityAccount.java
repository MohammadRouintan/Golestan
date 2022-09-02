package com.example.golestan.Account;

import com.example.golestan.Database.UniversityDB;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UniversityAccount extends UniversityDB implements UsersAccount {

    public UniversityAccount() {
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
        super.disconnect();
        return false;
    }

    @Override
    public boolean signup() throws SQLException {
        if (!checkValid()) {
            return false;
        } else if (super.checkUniWithName()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER ALREADY EXIST.\nYOU CANNOT USE THIS NAME !!");
            alert.show();
            return false;
        } else if (super.checkExistUsername(getUsername())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER ALREADY EXIST.\nYOU CANNOT USE THIS USERNAME !!");
            alert.show();
            return false;
        } else if (super.numberOfUni() >= 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("YOU CANNOT CREATE NEW ACCOUNT.\nBECAUSE AN UNIVERSITY ALREADY EXIST.");
            alert.show();
            return false;
        } else {
            super.addToDatabase();
        }

        return true;
    }

    @Override
    public boolean checkValid() {
        Pattern pattern = Pattern.compile("^[A-Za-z]{1,20}$");
        Matcher matcher = pattern.matcher(getName());

        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("THIS NAME IS INVALID !!");
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

        return true;
    }

    @Override
    public boolean editInfo(String oldUsername) throws SQLException {
        if (super.checkExistUsername(oldUsername)) {
            super.updateDatabase(oldUsername);
            return true;
        }

        return false;
    }
}
