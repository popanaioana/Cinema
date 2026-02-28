package org.example.cinema.repository.db;

import org.example.cinema.domain.Genre;
import org.example.cinema.repository.interfaces.IGenreRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDBRepository implements IGenreRepository {
    private String connectionString = "jdbc:sqlserver://localhost:1435;" + "databaseName=CinemaDB;" + "user=cinema_app;" + "password=CinemaApp@2025!;" + "encrypt=true;" + "trustServerCertificate=true;";

    @Override
    public List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        String sql = "select * from Genre";
        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Genre genre = new Genre(resultSet.getInt("GenreID"),
                        resultSet.getString("Name"));
                genres.add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    @Override
    public List<Genre> getGenres(int movieID) {
        String sql = "SELECT * FROM MovieGenre WHERE MovieID = ?";
        List<Genre> genres = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, movieID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Genre genre = new Genre(
                            resultSet.getInt("GenreID"),
                            ""
                    );
                    genres.add(genre);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    @Override
    public String getGenreNameByID(int genreID) {
        String sql = "select Name from Genre where GenreID = ?";
        String name = null;
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, genreID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    name = resultSet.getString("Name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    @Override
    public int getGenreID(String genre){
        String sql = "select GenreID from Genre where Name = ?";
        int genreID = -1;
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, genre);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    genreID = resultSet.getInt("GenreID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genreID;
    }
}
