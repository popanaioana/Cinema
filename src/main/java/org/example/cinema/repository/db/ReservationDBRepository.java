package org.example.cinema.repository.db;

import org.example.cinema.domain.Reservations;
import org.example.cinema.repository.interfaces.IReservationsRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDBRepository implements IReservationsRepository {
    private String connectionString = "jdbc:sqlserver://localhost:1435;" + "databaseName=CinemaDB;" + "user=cinema_app;" + "password=CinemaApp@2025!;" + "encrypt=true;" + "trustServerCertificate=true;";

    @Override
    public List<Reservations> getReservations() {
        List<Reservations> reservations = new ArrayList<>();
        String sql = "select * from Reservations";
        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Reservations reservation = new Reservations(
                        resultSet.getInt("ReservationID"),
                        resultSet.getInt("ClientID"),
                        resultSet.getInt("ScreeningID"),
                        resultSet.getInt("PriceID"),
                        resultSet.getInt("RowReservation"),
                        resultSet.getInt("ColumnReservation")
                );
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public Reservations getReservation(int id) {
        String sql = "select * from Reservations where ReservationID = ?";
        Reservations reservation = null;
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    reservation = new Reservations(resultSet.getInt("ReservationID"),
                            resultSet.getInt("ClientID"),
                            resultSet.getInt("ScreeningID"),
                            resultSet.getInt("PriceID"),
                            resultSet.getInt("RowReservation"),
                            resultSet.getInt("ColumnReservation"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    @Override
    public int addReservation(Reservations reservation) {
        String sql = "insert into Reservations ( ClientID, ScreeningID, PriceID, RowReservation, ColumnReservation) values ( ?, ?, ?, ?, ?)";
        int generatedID = -1;
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1, reservation.getClientID());
            preparedStatement.setInt(2, reservation.getScreeningID());
            preparedStatement.setInt(3, reservation.getPriceID());
            preparedStatement.setInt(4, reservation.getRowReservation());
            preparedStatement.setInt(5, reservation.getColumnReservation());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedID = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedID;
    }

    @Override
    public void updateReservation(Reservations reservation) {
        String sql = "update Reservations set ClientID = ?, ScreeningID = ?, PriceID = ?, RowReservation = ?, ColumnReservation = ? where ReservationID = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
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
        String sql = "delete from Reservations where ReservationID = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reservations> getReservationByScreening(int screeningID) {
        List<Reservations> reservations = new ArrayList<>();
        String sql = "select * from Reservations where ScreeningID = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, screeningID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Reservations reservation = new Reservations(
                            resultSet.getInt("ReservationID"),
                            resultSet.getInt("ClientID"),
                            resultSet.getInt("ScreeningID"),
                            resultSet.getInt("PriceID"),
                            resultSet.getInt("RowReservation"),
                            resultSet.getInt("ColumnReservation")
                    );
                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
}
