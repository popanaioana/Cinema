package org.example.cinema.repository.interfaces;

import org.example.cinema.domain.Movie;

import java.sql.SQLException;
import java.util.List;

public interface IMovieRepository {
    List<Movie> getMovies();
    Movie getMovie(String movieName);
    Movie getMovie(int movieID);
    void addMovie(Movie movie);
    void updateMovie(Movie movie);
    void deleteMovie(int id);
    List<Movie> getMoviesByGenre(int genreID);
    int getNoOfMovies();
}
