package org.example.cinema.controller;

import org.example.cinema.domain.Client;
import org.example.cinema.domain.Pricing;
import org.example.cinema.domain.Reservations;
import org.example.cinema.domain.Screening;
import org.example.cinema.service.PricingService;
import org.example.cinema.service.ReservationsService;

import java.util.List;

public class ReservationsController {
    private ReservationsService reservationsService;
    private PricingService pricingService;

    public ReservationsController(ReservationsService reservationsService,  PricingService pricingService) {
        this.reservationsService = reservationsService;
        this.pricingService = pricingService;
    }

    public List<Reservations> handleGetReservationsByScreening(int screeningID) {
        return reservationsService.getReservationByScreening(screeningID);
    }

    public int handleAddReservation(int clientID, int screeningID, int pricingID, int row, int column) {
        Reservations reservations = new Reservations(clientID, screeningID, pricingID, row, column);
        return reservationsService.addReservation(reservations);
    }

    //not ok yet
    public void handleUpdateReservation(int reservationID, int clientID, int screeningID, int priceID, int row, int column) {
        Reservations reservation = new Reservations(reservationID, clientID, screeningID, priceID, row, column);
        reservationsService.updateReservation(reservation);
    }

    public void handleDeleteReservation(int id) {
        reservationsService.deleteReservation(id);
    }

    public Reservations handleGetReservation(int reservationID) {
        return  reservationsService.getReservation(reservationID);
    }
}
