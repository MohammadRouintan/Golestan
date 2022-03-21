package com.example.golestan.Account;

import com.example.golestan.Database.ProfessorDB;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class ProfessorAccount extends ProfessorDB {

    public ProfessorAccount() {
        super();
    }

    public boolean login(String username, String password, String job) throws SQLException {
        super.setUsername(username);
        super.setPassword(password);
        if (job == null || !job.equals("Professor")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("PLEASE SELECT CORRECT JOB !!");
            alert.show();
            return false;
        }

        if (!super.checkProfWithUsername()) {
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


    public boolean signup(String username, String password, String firstName, String lastName, int group, String college) throws SQLException{
        super.setUsername(username);
        super.setPassword(password);
        super.setFirstName(firstName);
        super.setLastName(lastName);
        super.setGoroh(group);
        super.setCollege(college);

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
}
