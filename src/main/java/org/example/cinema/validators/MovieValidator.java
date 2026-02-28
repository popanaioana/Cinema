package org.example.cinema.validators;

import org.example.cinema.domain.Movie;

public class MovieValidator {
    public void validate(Movie movie) throws RuntimeException {
        if (movie.getDuration() < 5 || movie.getTitle().length() < 3 || movie.getTitle().length() > 50 || movie.getDescription().length() < 3 || movie.getDescription().length() > 255) {
            throw new RuntimeException("Invalid movie.");
        }
    }
}