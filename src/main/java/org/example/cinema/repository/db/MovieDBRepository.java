package org.example.cinema.repository.db;

import org.example.cinema.config.DatabaseConfig;
import org.example.cinema.domain.Movie;
import org.example.cinema.repository.interfaces.IMovieRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieDBRepository implements IMovieRepository {

    @Override
    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT MovieID, FormatID, ParentalConsentID, Title, Description, Duration FROM Movie";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                movies.add(mapMovie(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    public Movie getMovie(String movieName) {
        String sql = "SELECT MovieID, FormatID, ParentalConsentID, Title, Description, Duration FROM Movie WHERE Title = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, movieName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapMovie(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Movie getMovie(int movieID) {
        String sql = "SELECT MovieID, FormatID, ParentalConsentID, Title, Description, Duration FROM Movie WHERE MovieID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, movieID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapMovie(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addMovie(Movie movie) {
        String sql = "INSERT INTO Movie (FormatID, ParentalConsentID, Title, Description, Duration) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, movie.getFormatID());
            preparedStatement.setInt(2, movie.getParentalConsentID());
            preparedStatement.setString(3, movie.getTitle());
            preparedStatement.setString(4, movie.getDescription());
            preparedStatement.setInt(5, movie.getDuration());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMovie(Movie movie) {
        String sql = "UPDATE Movie " +
                "SET FormatID = ?, ParentalConsentID = ?, Title = ?, Description = ?, Duration = ? " +
                "WHERE MovieID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, movie.getFormatID());
            preparedStatement.setInt(2, movie.getParentalConsentID());
            preparedStatement.setString(3, movie.getTitle());
            preparedStatement.setString(4, movie.getDescription());
            preparedStatement.setInt(5, movie.getDuration());
            preparedStatement.setInt(6, movie.getMovieID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMovie(int id) {
        String sql = "DELETE FROM Movie WHERE MovieID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreID) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT m.MovieID, m.FormatID, m.ParentalConsentID, m.Title, m.Description, m.Duration " +
                "FROM Movie m JOIN MovieGenre mg ON m.MovieID = mg.MovieID WHERE mg.GenreID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, genreID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    movies.add(mapMovie(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    public int getNoOfMovies() {
        String sql = "SELECT COUNT(*) AS total FROM Movie";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Movie mapMovie(ResultSet resultSet) throws SQLException {
        return new Movie(
                resultSet.getInt("MovieID"),
                resultSet.getInt("FormatID"),
                resultSet.getInt("ParentalConsentID"),
                resultSet.getString("Title"),
                resultSet.getString("Description"),
                resultSet.getInt("Duration")
        );
    }
}