package org.example.cinema.service;

import org.example.cinema.domain.CinemaHall;
import org.example.cinema.repository.interfaces.ICinemaHallRepository;

import java.util.List;

public class CinemaHallService {
    private final ICinemaHallRepository cinemaHallRepository;

    public CinemaHallService(ICinemaHallRepository cinemaHallRepository) {
        this.cinemaHallRepository = cinemaHallRepository;
    }

    public List<CinemaHall> getCinemaHalls() {
        return cinemaHallRepository.getCinemaHalls();
    }

    public CinemaHall getCinemaHall(int cinemaHallID) {
        return cinemaHallRepository.getCinemaHall(cinemaHallID);
    }
}