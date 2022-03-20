package com.example.golestan.Database;

import java.sql.*;

public abstract class Database {
    private Connection connection;
    private Statement statement;
    private String url;
    private String query;
    private ResultSet resultSet;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private void connect() throws SQLException {
        url = "jdbc:mysql://localhost:3306/Golestan";
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
        resultSet = statement.executeQuery(this.query);
        return resultSet;
    }

    public boolean isExist() throws SQLException {
        return read().isBeforeFirst();
    }
}
