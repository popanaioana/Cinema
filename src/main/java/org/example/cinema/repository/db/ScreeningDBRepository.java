package org.example.cinema.repository.db;

import org.example.cinema.domain.Movie;
import org.example.cinema.domain.Screening;
import org.example.cinema.repository.interfaces.IScreeningRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScreeningDBRepository implements IScreeningRepository {
    private String connectionString = "jdbc:sqlserver://localhost:1435;" + "databaseName=CinemaDB;" + "user=cinema_app;" + "password=CinemaApp@2025!;" + "encrypt=true;" + "trustServerCertificate=true;";

    @Override
    public List<Screening> getScreenings() {
        List<Screening> screenings = new ArrayList<>();
        String sql = "select * from Screening";
        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Screening screening = new Screening( resultSet.getInt("ScreeningID"),
                        resultSet.getInt("MovieID"),
                        resultSet.getInt("CinemaHallID"),
                        resultSet.getInt("TypeID"),
                        resultSet.getDate("DateScreening").toLocalDate(),
                        resultSet.getTime("TimeScreening").toLocalTime()
                );
                screenings.add(screening);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return screenings;
    }

    @Override
    public Screening getScreening(int id) {
        String sql = "select * from Screening where ScreeningID = ?";
        Screening screening = null;
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    screening = new Screening(resultSet.getInt("ScreeningID"),
                            resultSet.getInt("MovieID"),
                            resultSet.getInt("CinemaHallID"),
                            resultSet.getInt("TypeID"),
                            resultSet.getDate("DateScreening").toLocalDate(),
                            resultSet.getTime("TimeScreening").toLocalTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return screening;
    }

    @Override
    public List<Screening> getScreenings(int movieId) {
        List<Screening> screenings = new ArrayList<>();
        String sql = "SELECT * FROM Screening WHERE MovieID = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, movieId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Screening screening = new Screening(
                        resultSet.getInt("ScreeningID"),
                        resultSet.getInt("MovieID"),
                        resultSet.getInt("CinemaHallID"),
                        resultSet.getInt("TypeID"),
                        resultSet.getDate("DateScreening").toLocalDate(),
                        resultSet.getTime("TimeScreening").toLocalTime()
                );
                screenings.add(screening);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return screenings;
    }

    @Override
    public void addScreening(Screening screening) {
        String sql = "insert into Screening (MovieID, CinemaHallID, TypeID, DateScreening, TimeScreening) values (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
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
        String sql = "delete from Screening where ScreeningID = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateScreening(Screening screening) {
        String sql = "update Screening set MovieID = ?, CinemaHallID = ?, TypeID = ?, DateScreening = ?, TimeScreening = ? where ScreeningID = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
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
        String sql = "select ScreeningID from Screening where CONVERT(varchar, DateScreening, 23) + ' ' + LEFT(CONVERT(varchar, TimeScreening, 8), 5) = ? and MovieID = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, dateTime); // dateTime format: "yyyy-MM-dd HH:mm"
            ps.setInt(2, movieID);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("ScreeningID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
