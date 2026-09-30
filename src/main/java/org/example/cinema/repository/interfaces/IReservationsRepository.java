package org.example.cinema.repository.interfaces;

import org.example.cinema.domain.Reservations;

import java.util.List;

public interface IReservationsRepository {
    List<Reservations> getReservations();
    Reservations getReservation(int id);
    int addReservation(Reservations reservation);
    void updateReservation(Reservations reservation);
    void deleteReservation(int id);
    List<Reservations> getReservationByScreening(int screeningID);
}