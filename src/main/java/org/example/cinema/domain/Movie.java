package org.example.cinema.domain;

public class Movie {
    private int movieID;
    private int formatID;
    private int parentalConsentID;
    private String title;
    private String description;
    private int duration;

    public Movie(int formatID, int parentalConsentID, String title, String description, int duration) {
        this.formatID = formatID;
        this.parentalConsentID = parentalConsentID;
        this.title = title;
        this.description = description;
        this.duration = duration;
    }

    public Movie(int movieID, int formatID, int parentalConsentID, String title, String description, int duration) {
        this.movieID = movieID;
        this.formatID = formatID;
        this.parentalConsentID = parentalConsentID;
        this.title = title;
        this.description = description;
        this.duration = duration;
    }

    public int getMovieID() {
        return movieID;
    }

    public int getFormatID() {
        return formatID;
    }

    public int getParentalConsentID() {
        return parentalConsentID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }
}