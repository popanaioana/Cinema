package org.example.cinema.domain;

public class Movie {
    private int MovieID;
    private int FormatID;
    private int ParentalConsentID;
    private String Title;
    private String Description;
    private int Duration;

    public Movie(int FormatID, int ParentalConsentID, String Title, String Description, int Duration) {
        this.FormatID = FormatID;
        this.ParentalConsentID = ParentalConsentID;
        this.Title = Title;
        this.Description = Description;
        this.Duration = Duration;
    }

    public Movie(int MovieID, int FormatID, int ParentalConsentID, String Title, String Description, int Duration) {
        this.MovieID = MovieID;
        this.FormatID = FormatID;
        this.ParentalConsentID = ParentalConsentID;
        this.Title = Title;
        this.Description = Description;
        this.Duration = Duration;
    }

    public int getFormatID() {
        return FormatID;
    }

    public int getParentalConsentID() {
        return ParentalConsentID;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public int getDuration() {
        return Duration;
    }

    public int getMovieID() {
        return MovieID;
    }
}
