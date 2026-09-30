package org.example.cinema.service;

import org.example.cinema.domain.Reservations;
import org.example.cinema.repository.interfaces.IReservationsRepository;

import java.util.List;

public class ReservationsService {
    private final IReservationsRepository reservationRepository;

    public ReservationsService(IReservationsRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservations> getReservations() {
        return reservationRepository.getReservations();
    }

    public Reservations getReservation(int id) {
        return reservationRepository.getReservation(id);
    }

    public int addReservation(Reservations reservation) {
        return reservationRepository.addReservation(reservation);
    }

    public void updateReservation(Reservations reservation) {
        reservationRepository.updateReservation(reservation);
    }

    public void deleteReservation(int id) {
        reservationRepository.deleteReservation(id);
    }

    public List<Reservations> getReservationByScreening(int screeningID) {
        return reservationRepository.getReservationByScreening(screeningID);
    }
}