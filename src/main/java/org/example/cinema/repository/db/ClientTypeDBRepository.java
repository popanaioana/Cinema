package org.example.cinema.repository.db;

import org.example.cinema.config.DatabaseConfig;
import org.example.cinema.domain.ClientType;
import org.example.cinema.repository.interfaces.IClientTypeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientTypeDBRepository implements IClientTypeRepository {

    @Override
    public List<ClientType> getClientTypes() {
        List<ClientType> clientTypes = new ArrayList<>();
        String sql = "SELECT TypeID, TypeName FROM ClientType";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                clientTypes.add(mapClientType(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientTypes;
    }

    @Override
    public ClientType getClientType(String typeName) {
        String sql = "SELECT TypeID, TypeName FROM ClientType WHERE TypeName = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, typeName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapClientType(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ClientType mapClientType(ResultSet resultSet) throws SQLException {
        return new ClientType(
                resultSet.getInt("TypeID"),
                resultSet.getString("TypeName")
        );
    }
}