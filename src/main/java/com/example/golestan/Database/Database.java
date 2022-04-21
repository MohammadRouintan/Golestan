package com.example.golestan.Database;

import java.sql.*;

public abstract class Database {
    private Connection connection;
    private Statement statement;
    private String query;

    public void setQuery(String query) {
        this.query = query;
    }

    private void connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Golestan";
        connection = DriverManager.getConnection(url, "Mohammad", "Mohammad");
        statement = connection.createStatement();
    }

    public void disconnect() throws SQLException {
        statement.close();
        connection.close();
    }

    public void write() throws SQLException {
        connect();
        statement.execute(this.query);
    }

    public ResultSet read() throws SQLException {
        connect();
        return statement.executeQuery(this.query);
    }

    public abstract void addToDatabase() throws SQLException;
}
