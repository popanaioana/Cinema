package org.example.cinema.service;

import org.example.cinema.domain.Reservations;
import org.example.cinema.repository.db.ReservationDBRepository;

import java.util.List;

public class ReservationsService {
    private ReservationDBRepository reservationDBRepository;

    public ReservationsService(ReservationDBRepository reservationRepository) {
        this.reservationDBRepository = reservationRepository;
    }

    public List<Reservations> getReservations() {
        return reservationDBRepository.getReservations();
    }

    public Reservations getReservation(int id) {
        return reservationDBRepository.getReservation(id);
    }

    public int addReservation(Reservations reservation) {
        return reservationDBRepository.addReservation(reservation);
    }

    public void updateReservation(Reservations reservation) {
        reservationDBRepository.updateReservation(reservation);
    }

    public void deleteReservation(int id) {
        reservationDBRepository.deleteReservation(id);
    }

    public List<Reservations> getReservationByScreening(int screeningID) {
        return reservationDBRepository.getReservationByScreening(screeningID);
    }
}
