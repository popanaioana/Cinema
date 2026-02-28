package org.example.cinema.controller;

import org.example.cinema.domain.Screening;
import org.example.cinema.service.ScreeningService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ScreeningController {
    private ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    public List<Screening> handleGetAllScreenings() {
        return screeningService.getScreenings();
    }

    public Screening handleGetScreening(int id) {
        return screeningService.getScreening(id);
    }

    public List<Screening> handleGetScreenings(int movieId) {
        return screeningService.getScreenings(movieId);
    }

    public void handleAddScreening(int movieId, int cinemaHallId, LocalDate dateScreening, LocalTime timeScreening, int typeId) {
        Screening screening = new Screening(movieId, cinemaHallId, typeId, dateScreening, timeScreening);
        screeningService.addScreening(screening);
    }

    public void handleUpdateScreening(int screeningId, int movieId, int cinemaHallId, LocalDate dateScreening, LocalTime timeScreening, int typeId) {
        Screening screening = new Screening(screeningId, movieId, cinemaHallId, typeId, dateScreening, timeScreening);
        screeningService.updateScreening(screening);
    }

    public void handleDeleteScreening(int id) {
        screeningService.deleteScreening(id);
    }

    public int handleGetScreeningIDByDateTime(String dateTime, int movieID) {
        return screeningService.gerScreeningIDByDateTime(dateTime, movieID);
    }
}
