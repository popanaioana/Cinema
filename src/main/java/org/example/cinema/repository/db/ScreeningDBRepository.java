package org.example.cinema.repository.db;

import org.example.cinema.config.DatabaseConfig;
import org.example.cinema.domain.Screening;
import org.example.cinema.repository.interfaces.IScreeningRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ScreeningDBRepository implements IScreeningRepository {

    @Override
    public List<Screening> getScreenings() {
        List<Screening> screenings = new ArrayList<>();
        String sql = "SELECT ScreeningID, MovieID, CinemaHallID, TypeID, DateScreening, TimeScreening FROM Screening";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                screenings.add(mapScreening(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return screenings;
    }

    @Override
    public Screening getScreening(int id) {
        String sql = "SELECT ScreeningID, MovieID, CinemaHallID, TypeID, DateScreening, TimeScreening FROM Screening WHERE ScreeningID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapScreening(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Screening> getScreenings(int movieId) {
        List<Screening> screenings = new ArrayList<>();
        String sql = "SELECT ScreeningID, MovieID, CinemaHallID, TypeID, DateScreening, TimeScreening FROM Screening WHERE MovieID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, movieId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    screenings.add(mapScreening(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return screenings;
    }

    @Override
    public void addScreening(Screening screening) {
        String sql = "INSERT INTO Screening (MovieID, CinemaHallID, TypeID, DateScreening, TimeScreening) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, screening.getMovieID());
            preparedStatement.setInt(2, screening.getCinemaHallID());
            preparedStatement.setInt(3, screening.getTypeID());
            preparedStatement.setDate(4, Date.valueOf(screening.getDateScreening()));
            preparedStatement.setTime(5, Time.valueOf(screening.getTimeScreening()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteScreening(int id) {
        String sql = "DELETE FROM Screening WHERE ScreeningID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateScreening(Screening screening) {
        String sql = "UPDATE Screening SET MovieID = ?, CinemaHallID = ?, TypeID = ?, DateScreening = ?, TimeScreening = ? WHERE ScreeningID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, screening.getMovieID());
            preparedStatement.setInt(2, screening.getCinemaHallID());
            preparedStatement.setInt(3, screening.getTypeID());
            preparedStatement.setDate(4, Date.valueOf(screening.getDateScreening()));
            preparedStatement.setTime(5, Time.valueOf(screening.getTimeScreening()));
            preparedStatement.setInt(6, screening.getScreeningID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getScreeningIDByDateTime(String dateTime, int movieID) {
        String sql = "SELECT ScreeningID FROM Screening " +
                "WHERE CONVERT(varchar, DateScreening, 23) + ' ' + " +
                "LEFT(CONVERT(varchar, TimeScreening, 8), 5) = ? " +
                "AND MovieID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, dateTime);
            preparedStatement.setInt(2, movieID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("ScreeningID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private Screening mapScreening(ResultSet resultSet) throws SQLException {
        return new Screening(
                resultSet.getInt("ScreeningID"),
                resultSet.getInt("MovieID"),
                resultSet.getInt("CinemaHallID"),
                resultSet.getInt("TypeID"),
                resultSet.getDate("DateScreening").toLocalDate(),
                resultSet.getTime("TimeScreening").toLocalTime()
        );
    }
}