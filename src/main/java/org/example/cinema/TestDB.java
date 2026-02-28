package org.example.cinema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDB {
    public static void main(String[] args) {
        String connectionString = "jdbc:sqlserver://localhost:1435;" + "databaseName=CinemaDB;" + "user=cinema_app;" + "password=CinemaApp@2025!;" + "encrypt=true;" + "trustServerCertificate=true;";
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            System.out.println("Successfully connected to database!");
        } catch (SQLException e) {
            System.out.println("Failed.");
            e.printStackTrace();
        }
    }
}