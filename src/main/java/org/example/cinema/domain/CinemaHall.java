package org.example.cinema.domain;

public class CinemaHall {
    private int cinemaHallID;
    private int number;
    private int rowsHall;
    private int columnsHall;

    public CinemaHall(int cinemaHallID, int number, int rowsHall, int columnsHall) {
        this.cinemaHallID = cinemaHallID;
        this.number = number;
        this.rowsHall = rowsHall;
        this.columnsHall = columnsHall;
    }

    public int getColumns() {
        return columnsHall;
    }

    public int getRows() {
        return rowsHall;
    }

    public int getName() {
        return number;
    }

    public int getCinemaHallID() {
        return cinemaHallID;
    }
}