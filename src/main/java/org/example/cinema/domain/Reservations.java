package org.example.cinema.domain;

public class Reservations {
    private int reservationID;
    private int clientID;
    private int screeningID;
    private int priceID;
    private int rowReservation;
    private int columnReservation;

    public Reservations(int clientID, int screeningID, int priceID, int rowReservation, int columnReservation) {
        this.clientID = clientID;
        this.screeningID = screeningID;
        this.priceID = priceID;
        this.rowReservation = rowReservation;
        this.columnReservation = columnReservation;
    }

    public Reservations(int reservationID, int clientID, int screeningID, int priceID, int rowReservation, int columnReservation) {
        this.reservationID = reservationID;
        this.clientID = clientID;
        this.screeningID = screeningID;
        this.priceID = priceID;
        this.rowReservation = rowReservation;
        this.columnReservation = columnReservation;
    }

    public int getReservationID() {
        return reservationID;
    }

    public int getClientID() {
        return clientID;
    }

    public int getScreeningID() {
        return screeningID;
    }

    public int getPriceID() {
        return priceID;
    }

    public int getRowReservation() {
        return rowReservation;
    }

    public int getColumnReservation() {
        return columnReservation;
    }

    public int getRow() {
        return rowReservation;
    }

    public int getCol() {
        return columnReservation;
    }
}