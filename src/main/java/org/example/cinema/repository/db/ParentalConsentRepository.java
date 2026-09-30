package org.example.cinema.repository.db;

import org.example.cinema.config.DatabaseConfig;
import org.example.cinema.domain.ParentalConsent;
import org.example.cinema.repository.interfaces.IParentalConsentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ParentalConsentRepository implements IParentalConsentRepository {

    @Override
    public List<ParentalConsent> getParentalConsents() {
        List<ParentalConsent> parentalConsents = new ArrayList<>();
        String sql = "SELECT ParentalConsentID, Age FROM ParentalConsent";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                parentalConsents.add(mapParentalConsent(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parentalConsents;
    }

    @Override
    public ParentalConsent getParentalConsent(int parentalConsentID) {
        String sql = "SELECT ParentalConsentID, Age FROM ParentalConsent WHERE ParentalConsentID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, parentalConsentID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapParentalConsent(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ParentalConsent mapParentalConsent(ResultSet resultSet) throws SQLException {
        return new ParentalConsent(
                resultSet.getInt("ParentalConsentID"),
                resultSet.getInt("Age")
        );
    }
}