package com.example.golestan.Account;

import com.example.golestan.Database.UniversityDB;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class UniversityAccount extends UniversityDB {

    public UniversityAccount() {
        super();
    }

    public boolean login(String username, String password, String job) throws SQLException {
        super.setUsername(username);
        super.setPassword(password);
        if (job == null || !job.equals("University")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("PLEASE SELECT CORRECT JOB !!");
            alert.show();
            return false;
        }

        if (!super.checkUniWithUsername()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("USER NOT FOUND !!\nPLEASE ENTER CORRECT USERNAME OR PASSWORD.");
            alert.show();
        } else {
            while (super.read().next()) {
                String recievePassword = super.read().getString("Password");
                if (recievePassword.equals(password)) {
                    super.disconnect();
                    return true;
                }
            }
        }

        super.disconnect();
        return false;
    }


    public boolean signup(String name, String username, String password) throws SQLException {
        super.setName(name);
        super.setUsername(username);
        super.setPassword(password);

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
            super.addUni();
        }

        return true;
    }
}
