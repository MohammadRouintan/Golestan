package com.example.golestan.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Users {
    public void updateDatabase(String oldUsername) throws SQLException;
    public boolean checkExistUsername(String username) throws SQLException;
    public ResultSet findWithUsername() throws SQLException;
    public String findName() throws SQLException;
}
