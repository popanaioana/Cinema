package org.example.cinema.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Screening {
    private int screeningID;
    private int movieID;
    private int cinemaHallID;
    private int typeID;
    private LocalDate dateScreening;
    private LocalTime timeScreening;

    public Screening(int movieID, int cinemaHallID, int typeID, LocalDate dateScreening, LocalTime timeScreening) {
        this.movieID = movieID;
        this.cinemaHallID = cinemaHallID;
        this.typeID = typeID;
        this.dateScreening = dateScreening;
        this.timeScreening = timeScreening;
    }

    public Screening(int screeningID, int movieID, int cinemaHallID, int typeID, LocalDate dateScreening, LocalTime timeScreening) {
        this.screeningID = screeningID;
        this.movieID = movieID;
        this.cinemaHallID = cinemaHallID;
        this.typeID = typeID;
        this.dateScreening = dateScreening;
        this.timeScreening = timeScreening;
    }

    public int getScreeningID() {
        return screeningID;
    }

    public int getMovieID() {
        return movieID;
    }

    public int getCinemaHallID() {
        return cinemaHallID;
    }

    public int getTypeID() {
        return typeID;
    }

    public LocalDate getDateScreening() {
        return dateScreening;
    }

    public LocalTime getTimeScreening() {
        return timeScreening;
    }
}