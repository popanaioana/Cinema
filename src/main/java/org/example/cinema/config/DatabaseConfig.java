package org.example.cinema.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConfig {

    private static final String URL = System.getenv("CINEMA_DB_URL");

    private static final String USER = System.getenv("CINEMA_DB_USER");

    private static final String PASSWORD = System.getenv("CINEMA_DB_PASSWORD");

    private DatabaseConfig() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}