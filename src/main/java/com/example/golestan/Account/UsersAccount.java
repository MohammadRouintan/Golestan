package com.example.golestan.Account;

import java.sql.SQLException;

public interface UsersAccount {
    public boolean login(String username, String password) throws SQLException;
    public boolean signup() throws SQLException;
    public boolean checkValid() throws SQLException;
    public boolean editInfo(String oldUsername) throws SQLException;
}
