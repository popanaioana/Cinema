package org.example.cinema.domain;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

public class Screening {
    private int ScreeningID;
    private int MovieID;
    private int CinemaHallID;
    private int TypeID;
    private LocalDate DateScreening;
    private LocalTime TimeScreening;

    public Screening(int MovieID, int CinemaHallID, int TypeID, LocalDate DateScreening, LocalTime TimeScreening) {
        this.MovieID = MovieID;
        this.CinemaHallID = CinemaHallID;
        this.TypeID = TypeID;
        this.DateScreening = DateScreening;
        this.TimeScreening = TimeScreening;
    }

    public Screening(int ScreeningID, int MovieID, int CinemaHallID, int TypeID, LocalDate DateScreening, LocalTime TimeScreening) {
        this.ScreeningID = ScreeningID;
        this.MovieID = MovieID;
        this.CinemaHallID = CinemaHallID;
        this.TypeID = TypeID;
        this.DateScreening = DateScreening;
        this.TimeScreening = TimeScreening;
    }

    public int getScreeningID() {
        return ScreeningID;
    }

    public int getMovieID() {
        return MovieID;
    }

    public int getCinemaHallID() {
        return CinemaHallID;
    }

    public int getTypeID() {
        return TypeID;
    }

    public LocalDate getDateScreening() {
        return DateScreening;
    }

    public LocalTime getTimeScreening() {
        return TimeScreening;
    }
}
