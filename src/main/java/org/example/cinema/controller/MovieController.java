package org.example.cinema.controller;

import org.example.cinema.domain.Movie;
import org.example.cinema.service.MovieService;

import java.util.List;

public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    public List<Movie> handleGetMovies() {
        return movieService.getMovies();
    }

    public Movie handleGetMovie(String movieName) {
        return movieService.getMovie(movieName);
    }

    public Movie handleGetMovie(int movieID) {
        return movieService.getMovie(movieID);
    }

    public void handleAddMovie(int formatID, int parentalConsentID, String title, String description, int duration) {
        Movie movie = new Movie(formatID, parentalConsentID, title, description, duration);
        movieService.addMovie(movie);
    }

    public void handleUpdateMovie(int movieID, int formatID, int parentalConsentID, String title, String description, int duration) {
        Movie movie = new Movie(movieID, formatID, parentalConsentID, title, description, duration);
        movieService.updateMovie(movie);
    }

    public void handleDeleteMovie(int movieID) {
        movieService.deleteMovie(movieID);
    }

    public List<Movie> handleGetMoviesByGenre(int genreID) {
        return movieService.getMoviesByGenre(genreID);
    }

    public int handleGetNoOfMovies() {
        return movieService.getNoOfMovies();
    }
}