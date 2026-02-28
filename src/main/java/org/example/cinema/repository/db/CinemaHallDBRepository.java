package org.example.cinema.repository.db;

import org.example.cinema.domain.CinemaHall;
import org.example.cinema.repository.interfaces.ICinemaHallRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CinemaHallDBRepository implements ICinemaHallRepository {
    private String connectionString = "jdbc:sqlserver://localhost:1435;" + "databaseName=CinemaDB;" + "user=cinema_app;" + "password=CinemaApp@2025!;" + "encrypt=true;" + "trustServerCertificate=true;";

    @Override
    public List<CinemaHall> getCinemaHalls() {
        List<CinemaHall> cinemaHalls = new ArrayList<>();
        String sql = "select * from CinemaHall";
        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                CinemaHall cinemaHall = new CinemaHall(resultSet.getInt("CinemaHallID"),
                        resultSet.getInt("Number"), resultSet.getInt("RowsHall"), resultSet.getInt("ColumnsHall"));
                cinemaHalls.add(cinemaHall);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinemaHalls;
    }

    @Override
    public CinemaHall getCinemaHall(int cinemaHallID) {
        String sql = "select * from CinemaHall where CinemaHallID = ?";
        CinemaHall cinemaHall = null;
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, cinemaHallID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    cinemaHall = new CinemaHall(resultSet.getInt("CinemaHallID"),
                            resultSet.getInt("Number"),  resultSet.getInt("RowsHall"), resultSet.getInt("ColumnsHall"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinemaHall;
    }
}
