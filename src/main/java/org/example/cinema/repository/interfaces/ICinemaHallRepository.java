package org.example.cinema.repository.interfaces;

import org.example.cinema.domain.CinemaHall;

import java.util.List;

public interface ICinemaHallRepository {
    List<CinemaHall> getCinemaHalls();
    CinemaHall getCinemaHall(int number);
}
