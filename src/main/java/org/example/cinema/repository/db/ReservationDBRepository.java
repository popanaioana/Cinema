package org.example.cinema.repository.db;

import org.example.cinema.config.DatabaseConfig;
import org.example.cinema.domain.Reservations;
import org.example.cinema.repository.interfaces.IReservationsRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReservationDBRepository implements IReservationsRepository {

    @Override
    public List<Reservations> getReservations() {
        List<Reservations> reservations = new ArrayList<>();
        String sql = "SELECT ReservationID, ClientID, ScreeningID, PriceID, RowReservation, ColumnReservation FROM Reservations";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                reservations.add(mapReservation(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public Reservations getReservation(int id) {
        String sql = "SELECT ReservationID, ClientID, ScreeningID, PriceID, RowReservation, ColumnReservation FROM Reservations WHERE ReservationID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapReservation(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int addReservation(Reservations reservation) {
        String sql = "INSERT INTO Reservations (ClientID, ScreeningID, PriceID, RowReservation, ColumnReservation) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, reservation.getClientID());
            preparedStatement.setInt(2, reservation.getScreeningID());
            preparedStatement.setInt(3, reservation.getPriceID());
            preparedStatement.setInt(4, reservation.getRowReservation());
            preparedStatement.setInt(5, reservation.getColumnReservation());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void updateReservation(Reservations reservation) {
        String sql = "UPDATE Reservations SET ClientID = ?, ScreeningID = ?, PriceID = ?, RowReservation = ?, ColumnReservation = ? WHERE ReservationID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, reservation.getClientID());
            preparedStatement.setInt(2, reservation.getScreeningID());
            preparedStatement.setInt(3, reservation.getPriceID());
            preparedStatement.setInt(4, reservation.getRowReservation());
            preparedStatement.setInt(5, reservation.getColumnReservation());
            preparedStatement.setInt(6, reservation.getReservationID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteReservation(int id) {
        String sql = "DELETE FROM Reservations WHERE ReservationID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Reservations> getReservationByScreening(int screeningID) {
        List<Reservations> reservations = new ArrayList<>();
        String sql = "SELECT ReservationID, ClientID, ScreeningID, PriceID, RowReservation, ColumnReservation FROM Reservations WHERE ScreeningID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, screeningID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reservations.add(mapReservation(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    private Reservations mapReservation(ResultSet resultSet) throws SQLException {
        return new Reservations(
                resultSet.getInt("ReservationID"),
                resultSet.getInt("ClientID"),
                resultSet.getInt("ScreeningID"),
                resultSet.getInt("PriceID"),
                resultSet.getInt("RowReservation"),
                resultSet.getInt("ColumnReservation")
        );
    }
}