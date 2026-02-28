package org.example.cinema.service;

import org.example.cinema.domain.CinemaHall;
import org.example.cinema.repository.db.CinemaHallDBRepository;

import java.util.List;

public class CinemaHallService {
    private CinemaHallDBRepository cinemaHallDBRepository;

    public CinemaHallService(CinemaHallDBRepository cinemaHallRepository) {
        this.cinemaHallDBRepository = cinemaHallRepository;
    }

    public List<CinemaHall> getCinemaHalls() {
        return cinemaHallDBRepository.getCinemaHalls();
    }

    public CinemaHall getCinemaHall(int cinemaHallID) {
        return cinemaHallDBRepository.getCinemaHall(cinemaHallID);
    }
}
