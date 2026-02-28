package org.example.cinema.domain;

public class CinemaHall {
    private int CinemaHallID;
    private int Number;
    private int RowsHall;
    private int ColumnsHall;

    public CinemaHall(int CinemaHallID, int Number, int RowsHall, int ColumnsHall) {
        this.CinemaHallID = CinemaHallID;
        this.Number = Number;
        this.RowsHall = RowsHall;
        this.ColumnsHall = ColumnsHall;
    }

    public int getColumns() {
        return ColumnsHall;
    }

    public int getRows() {
        return RowsHall;
    }

    public int getName() {
        return Number;
    }

    public int getCinemaHallID() {
        return CinemaHallID;
    }
}
