package org.example.cinema.repository.db;

import org.example.cinema.domain.Format;
import org.example.cinema.repository.interfaces.IFormatRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormatDBRepository implements IFormatRepository {
    private String connectionString = "jdbc:sqlserver://localhost:1435;" + "databaseName=CinemaDB;" + "user=cinema_app;" + "password=CinemaApp@2025!;" + "encrypt=true;" + "trustServerCertificate=true;";

    @Override
    public List<Format> getFormats() {
        List<Format> formats = new ArrayList<>();
        String sql = "select * from Format";
        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Format format = new Format(resultSet.getInt("FormatID"),
                        resultSet.getString("TypeFormat"));
                formats.add(format);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return formats;
    }

    @Override
    public Format getFormat(String formatType) {
        String sql = "select * from Format where TypeFormat = ?";
        Format format = null;
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, formatType);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    format = new Format(resultSet.getInt("FormatID"),
                            resultSet.getString("TypeFormat"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return format;
    }

    @Override
    public Format getFormat(int formatID) {
        String sql = "select * from Format where FormatID = ?";
        Format format = null;
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, formatID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    format = new Format(resultSet.getInt("FormatID"),
                            resultSet.getString("TypeFormat"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return format;
    }
}
