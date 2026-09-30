package org.example.cinema.validators;

import org.example.cinema.domain.Movie;

public class MovieValidator {
    public void validate(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null.");
        }
        validateTitle(movie.getTitle());
        validateDescription(movie.getDescription());
        validateDuration(movie.getDuration());
    }

    private void validateTitle(String title) {
        if (title == null || title.length() < 3 || title.length() > 50) {
            throw new IllegalArgumentException("Title must contain between 3 and 50 characters.");
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.length() < 3 || description.length() > 255) {
            throw new IllegalArgumentException("Description must contain between 3 and 255 characters.");
        }
    }

    private void validateDuration(int duration) {
        if (duration < 5) {
            throw new IllegalArgumentException("Movie duration must be at least 5 minutes.");
        }
    }
}