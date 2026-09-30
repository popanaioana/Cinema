package org.example.cinema.controller;

import org.example.cinema.domain.CinemaHall;
import org.example.cinema.service.CinemaHallService;

import java.util.List;

public class CinemaHallController {
    private final CinemaHallService cinemaHallService;

    public CinemaHallController(CinemaHallService cinemaHallService) {
        this.cinemaHallService = cinemaHallService;
    }

    public List<CinemaHall> handleGetCinemaHalls(){
        return cinemaHallService.getCinemaHalls();
    }

    public CinemaHall handleGetCinemaHall(int cinemaHallID){
        return cinemaHallService.getCinemaHall(cinemaHallID);
    }
}