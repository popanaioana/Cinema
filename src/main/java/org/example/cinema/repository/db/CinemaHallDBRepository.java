package org.example.cinema.repository.db;

import org.example.cinema.config.DatabaseConfig;
import org.example.cinema.domain.CinemaHall;
import org.example.cinema.repository.interfaces.ICinemaHallRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CinemaHallDBRepository implements ICinemaHallRepository {

    @Override
    public List<CinemaHall> getCinemaHalls() {
        List<CinemaHall> cinemaHalls = new ArrayList<>();
        String sql = "SELECT CinemaHallID, Number, RowsHall, ColumnsHall FROM CinemaHall";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                cinemaHalls.add(mapCinemaHall(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinemaHalls;
    }

    @Override
    public CinemaHall getCinemaHall(int cinemaHallID) {
        String sql = "SELECT CinemaHallID, Number, RowsHall, ColumnsHall FROM CinemaHall WHERE CinemaHallID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cinemaHallID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapCinemaHall(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private CinemaHall mapCinemaHall(ResultSet resultSet) throws SQLException {
        return new CinemaHall(
                resultSet.getInt("CinemaHallID"),
                resultSet.getInt("Number"),
                resultSet.getInt("RowsHall"),
                resultSet.getInt("ColumnsHall")
        );
    }
}