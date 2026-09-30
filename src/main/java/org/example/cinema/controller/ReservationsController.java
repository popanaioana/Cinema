package org.example.cinema.controller;

import org.example.cinema.domain.Reservations;
import org.example.cinema.service.ReservationsService;

import java.util.List;

public class ReservationsController {

    private final ReservationsService reservationsService;

    public ReservationsController(ReservationsService reservationsService) {
        this.reservationsService = reservationsService;
    }

    public List<Reservations> handleGetReservationsByScreening(int screeningID) {
        return reservationsService.getReservationByScreening(screeningID);
    }

    public Reservations handleGetReservation(int reservationID) {
        return reservationsService.getReservation(reservationID);
    }

    public int handleAddReservation(int clientID, int screeningID, int priceID, int row, int column) {
        Reservations reservation = new Reservations(clientID, screeningID, priceID, row, column);
        return reservationsService.addReservation(reservation);
    }

    public void handleUpdateReservation(int reservationID, int clientID, int screeningID, int priceID, int row, int column) {
        Reservations reservation = new Reservations(reservationID, clientID, screeningID, priceID, row, column);
        reservationsService.updateReservation(reservation);
    }

    public void handleDeleteReservation(int reservationID) {
        reservationsService.deleteReservation(reservationID);
    }
}