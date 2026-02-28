package org.example.cinema.service;

import org.example.cinema.domain.Movie;
import org.example.cinema.repository.db.MovieDBRepository;
import org.example.cinema.validators.MovieValidator;

import java.util.List;

public class MovieService {
    private MovieDBRepository movieRepository;
    private MovieValidator movieValidator;

    public MovieService(MovieDBRepository movieRepository, MovieValidator movieValidator) {
        this.movieRepository = movieRepository;
        this.movieValidator = movieValidator;
    }

    public MovieService(MovieDBRepository movieDBRepository) {
        this.movieRepository = movieDBRepository;
    }

    public List<Movie> getMovies() {
        return movieRepository.getMovies();
    }

    public Movie getMovie(String movieName) {
        return movieRepository.getMovie(movieName);
    }

    public Movie getMovie(int movieID) {
        return movieRepository.getMovie(movieID);
    }

    public void addMovie(Movie movie) {
        movieValidator.validate(movie);
        movieRepository.addMovie(movie);
    }

    public void updateMovie(Movie movie) {
        movieValidator.validate(movie);
        movieRepository.updateMovie(movie);
    }

    public void deleteMovie(int id) {
        movieRepository.deleteMovie(id);
    }

    public List<Movie> getMoviesByGenre(int genreID) {
        return movieRepository.getMoviesByGenre(genreID);
    }

    public int getNoOfMovies() {
        return movieRepository.getNoOfMovies();
    }
}
