package org.example.cinema.repository.db;

import org.example.cinema.config.DatabaseConfig;
import org.example.cinema.domain.Pricing;
import org.example.cinema.repository.interfaces.IPricingRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PricingDBRepository implements IPricingRepository {

    @Override
    public List<Pricing> getPricings() {
        List<Pricing> pricings = new ArrayList<>();
        String sql = "SELECT PriceID, ClientTypeID, ScreeningTypeID, Price FROM Pricing";

        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                pricings.add(mapPricing(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pricings;
    }

    @Override
    public Pricing getPricing(int clientTypeID, int screeningTypeID) {
        String sql = "SELECT PriceID, ClientTypeID, ScreeningTypeID, Price FROM Pricing WHERE ClientTypeID = ? AND ScreeningTypeID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, clientTypeID);
            preparedStatement.setInt(2, screeningTypeID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapPricing(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Pricing getPricing(int priceID) {
        String sql = "SELECT PriceID, ClientTypeID, ScreeningTypeID, Price FROM Pricing WHERE PriceID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, priceID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapPricing(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Pricing mapPricing(ResultSet resultSet) throws SQLException {
        return new Pricing(
                resultSet.getInt("PriceID"),
                resultSet.getInt("ClientTypeID"),
                resultSet.getInt("ScreeningTypeID"),
                resultSet.getDouble("Price")
        );
    }
}