package org.example.cinema.repository.db;

import org.example.cinema.domain.ScreeningType;
import org.example.cinema.repository.interfaces.IScreeningTypeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScreeningTypeDBRepository implements IScreeningTypeRepository {
    private String connectionString = "jdbc:sqlserver://localhost:1435;" + "databaseName=CinemaDB;" + "user=cinema_app;" + "password=CinemaApp@2025!;" + "encrypt=true;" + "trustServerCertificate=true;";

    @Override
    public List<ScreeningType> getScreeningTypes() {
        List<ScreeningType> screeningTypes = new ArrayList<>();
        String sql = "select * from ScreeningType";
        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                ScreeningType screeningType = new ScreeningType(resultSet.getInt("TypeID"), resultSet.getString("TypeName"));
                screeningTypes.add(screeningType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return screeningTypes;
    }

    @Override
    public ScreeningType getScreeningType(String screeningTypeName) {
        String sql = "select * from ScreeningType where TypeName = ?";
        ScreeningType screeningType = null;
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, screeningTypeName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    screeningType = new ScreeningType(resultSet.getInt("TypeID"), resultSet.getString("TypeName"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return screeningType;
    }
}
