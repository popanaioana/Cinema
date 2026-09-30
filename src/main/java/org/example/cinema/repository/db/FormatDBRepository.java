package org.example.cinema.repository.db;

import org.example.cinema.config.DatabaseConfig;
import org.example.cinema.domain.Format;
import org.example.cinema.repository.interfaces.IFormatRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FormatDBRepository implements IFormatRepository {

    @Override
    public List<Format> getFormats() {
        List<Format> formats = new ArrayList<>();
        String sql = "SELECT FormatID, TypeFormat FROM Format";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                formats.add(mapFormat(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return formats;
    }

    @Override
    public Format getFormat(String formatType) {
        String sql = "SELECT FormatID, TypeFormat FROM Format WHERE TypeFormat = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, formatType);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapFormat(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Format getFormat(int formatID) {
        String sql = "SELECT FormatID, TypeFormat FROM Format WHERE FormatID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, formatID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapFormat(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Format mapFormat(ResultSet resultSet) throws SQLException {
        return new Format(
                resultSet.getInt("FormatID"),
                resultSet.getString("TypeFormat")
        );
    }
}