package org.example.cinema.repository.db;

import org.example.cinema.domain.Movie;
import org.example.cinema.repository.interfaces.IMovieRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDBRepository implements IMovieRepository {
    private String connectionString = "jdbc:sqlserver://localhost:1435;" + "databaseName=CinemaDB;" + "user=cinema_app;" + "password=CinemaApp@2025!;" + "encrypt=true;" + "trustServerCertificate=true;";

    @Override
    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "select * from Movie";
        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Movie movie = new Movie( resultSet.getInt("MovieID"),
                        resultSet.getInt("FormatID"),
                        resultSet.getInt("ParentalConsentID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getInt("Duration"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    public Movie getMovie(String movieName) {
        String sql = "select * from Movie where Title = ?";
        Movie movie = null;
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, movieName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    movie = new Movie(resultSet.getInt("MovieID"),
                            resultSet.getInt("FormatID"),
                            resultSet.getInt("ParentalConsentID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Description"),
                            resultSet.getInt("Duration"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }

    @Override
    public Movie getMovie(int movieID) {
        String sql = "select * from Movie where MovieID = ?";
        Movie movie = null;
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, movieID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    movie = new Movie(resultSet.getInt("MovieID"),
                            resultSet.getInt("FormatID"),
                            resultSet.getInt("ParentalConsentID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Description"),
                            resultSet.getInt("Duration"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }

    @Override
    public void addMovie(Movie movie) {
        String sql = "insert into Movie (FormatID, ParentalConsentID, Title, Description, Duration) values (?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(connectionString);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, movie.getFormatID());
            preparedStatement.setInt(2,movie.getParentalConsentID());
            preparedStatement.setString(3,movie.getTitle());
            preparedStatement.setString(4,movie.getDescription());
            preparedStatement.setInt(5,movie.getDuration());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }

    @Override
    public void updateMovie(Movie movie) {
        String sql = "update Movie set FormatID = ?, ParentalConsentID = ?, Title = ?, Description = ?, Duration = ? where MovieID = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
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
        String sql = "delete from Movie where MovieID = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreID) {
        List<Movie> movies = new ArrayList<>();
        String sql = "select m.* from Movie m join MovieGenre mg on m.MovieID = mg.MovieID where mg.GenreID = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, genreID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Movie movie = new Movie(
                            resultSet.getInt("MovieID"),
                            resultSet.getInt("FormatID"),
                            resultSet.getInt("ParentalConsentID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Description"),
                            resultSet.getInt("Duration")
                    );
                    movies.add(movie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    public int getNoOfMovies() {
        String sql = "select count(*) as total from Movie";
        int noOfMovies = -1;
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        noOfMovies = resultSet.getInt("total");
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noOfMovies;
    }
}
