package org.example.cinema.repository.db;

import org.example.cinema.config.DatabaseConfig;
import org.example.cinema.domain.ScreeningType;
import org.example.cinema.repository.interfaces.IScreeningTypeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ScreeningTypeDBRepository implements IScreeningTypeRepository {

    @Override
    public List<ScreeningType> getScreeningTypes() {
        List<ScreeningType> screeningTypes = new ArrayList<>();
        String sql = "SELECT TypeID, TypeName FROM ScreeningType";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                screeningTypes.add(mapScreeningType(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return screeningTypes;
    }

    @Override
    public ScreeningType getScreeningType(String screeningTypeName) {
        String sql = "SELECT TypeID, TypeName FROM ScreeningType WHERE TypeName = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, screeningTypeName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapScreeningType(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ScreeningType mapScreeningType(ResultSet resultSet) throws SQLException {
        return new ScreeningType(
                resultSet.getInt("TypeID"),
                resultSet.getString("TypeName")
        );
    }
}