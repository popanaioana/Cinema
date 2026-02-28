package org.example.cinema.domain;

public class Reservations {
    private int ReservationID;
    private int ClientID;
    private int ScreeningID;
    private int PriceID;
    private int RowReservation;
    private int ColumnReservation;

    public Reservations(int ClientID, int ScreeningID, int PriceID, int RowReservation, int ColumnReservation) {
        this.ClientID = ClientID;
        this.ScreeningID = ScreeningID;
        this.PriceID = PriceID;
        this.RowReservation = RowReservation;
        this.ColumnReservation = ColumnReservation;
    }

    public Reservations(int ReservationID, int ClientID, int ScreeningID, int PriceID, int RowReservation, int ColumnReservation) {
        this.ReservationID = ReservationID;
        this.ClientID = ClientID;
        this.ScreeningID = ScreeningID;
        this.PriceID = PriceID;
        this.RowReservation = RowReservation;
        this.ColumnReservation = ColumnReservation;
    }

    public int getReservationID() {
        return ReservationID;
    }

    public int getClientID() {
        return ClientID;
    }

    public int getScreeningID() {
        return ScreeningID;
    }

    public int getPriceID() {
        return PriceID;
    }

    public int getRowReservation() {
        return RowReservation;
    }

    public int getColumnReservation() {
        return ColumnReservation;
    }

    public int getRow() {
        return RowReservation;
    }

    public int getCol() {
        return ColumnReservation;
    }
}
