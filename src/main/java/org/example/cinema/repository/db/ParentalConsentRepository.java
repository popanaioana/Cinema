package org.example.cinema.repository.db;

import org.example.cinema.domain.ParentalConsent;
import org.example.cinema.repository.interfaces.IParentalConsentRepository;
import org.example.cinema.service.ParentalConsentService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParentalConsentRepository implements IParentalConsentRepository {
    private String connectionString = "jdbc:sqlserver://localhost:1435;" + "databaseName=CinemaDB;" + "user=cinema_app;" + "password=CinemaApp@2025!;" + "encrypt=true;" + "trustServerCertificate=true;";

    @Override
    public List<ParentalConsent> getParentalConsents() {
        List<ParentalConsent> parentalConsents = new ArrayList<>();
        String sql = "select * from ParentalConsent";
        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                ParentalConsent parentalConsent = new ParentalConsent(resultSet.getInt("ParentalConsentID"),
                        resultSet.getInt("Age"));
                parentalConsents.add(parentalConsent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parentalConsents;
    }

    @Override
    public ParentalConsent getParentalConsent(int parentalConsentID) {
        String sql = "select * from ParentalConsent where ParentalConsentID = ?";
        ParentalConsent parentalConsent = null;
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, parentalConsentID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    parentalConsent = new ParentalConsent(resultSet.getInt("ParentalConsentID"),
                            resultSet.getInt("Age"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parentalConsent;
    }
}
