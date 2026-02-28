package org.example.cinema.repository.db;

import org.example.cinema.domain.ClientType;
import org.example.cinema.repository.interfaces.IClientTypeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientTypeDBRepository implements IClientTypeRepository {
    private String connectionString = "jdbc:sqlserver://localhost:1435;" + "databaseName=CinemaDB;" + "user=cinema_app;" + "password=CinemaApp@2025!;" + "encrypt=true;" + "trustServerCertificate=true;";

    @Override
    public List<ClientType> getClientTypes() {
        List<ClientType> clientTypes = new ArrayList<>();
        String sql = "select * from ClientType";
        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                ClientType clientType = new ClientType(resultSet.getInt("TypeID"),
                        resultSet.getString("TypeName"));
                clientTypes.add(clientType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientTypes;
    }

    @Override
    public ClientType getClientType(String typeName) {
        String sql = "select * from ClientType where TypeName = ?";
        ClientType clientType = null;
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, typeName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet != null) {
                    clientType = new ClientType(resultSet.getInt("ClientTypeID"),
                            resultSet.getString("TypeName"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientType;
    }
}
