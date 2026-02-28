package org.example.cinema.repository.db;

import org.example.cinema.domain.Pricing;
import org.example.cinema.repository.interfaces.IPricingRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PricingDBRepository implements IPricingRepository {
    private String connectionString = "jdbc:sqlserver://localhost:1435;" + "databaseName=CinemaDB;" + "user=cinema_app;" + "password=CinemaApp@2025!;" + "encrypt=true;" + "trustServerCertificate=true;";

   @Override
    public List<Pricing> getPricings() {
        List<Pricing> pricings = new ArrayList<>();
        String sql = "select * from Pricing";
        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Pricing pricing = new Pricing(resultSet.getInt("PricingID"),
                                resultSet.getInt("ClientTypeID"),
                                resultSet.getInt("ScreeningID"),
                                resultSet.getDouble("Price"));
                pricings.add(pricing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pricings;
    }

    @Override
    public Pricing getPricing(int clientTypeID, int screeningTypeID) {
        String sql = "select * from Pricing where ClientTypeID = ? and ScreeningTypeID = ?";
        Pricing pricing = null;
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, clientTypeID);
            preparedStatement.setInt(2, screeningTypeID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    pricing = new Pricing(resultSet.getInt("PriceID"),
                            resultSet.getInt("ClientTypeID"),
                            resultSet.getInt("ScreeningTypeID"),
                            resultSet.getDouble("Price"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pricing;
    }

    @Override
    public Pricing getPricing(int pricingID) {
        String sql = "select * from Pricing where PriceID = ?";
        Pricing pricing = null;
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, pricingID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    pricing = new Pricing(resultSet.getInt("PriceID"),
                            resultSet.getInt("ClientTypeID"),
                            resultSet.getInt("ScreeningTypeID"),
                            resultSet.getDouble("Price"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pricing;
    }
}
