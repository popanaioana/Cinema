package org.example.cinema.controller;

import org.example.cinema.domain.Screening;
import org.example.cinema.service.ScreeningService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ScreeningController {

    private final ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    public List<Screening> handleGetAllScreenings() {
        return screeningService.getScreenings();
    }

    public Screening handleGetScreening(int screeningID) {
        return screeningService.getScreening(screeningID);
    }

    public List<Screening> handleGetScreenings(int movieID) {
        return screeningService.getScreenings(movieID);
    }

    public void handleAddScreening(int movieID, int cinemaHallID, LocalDate dateScreening, LocalTime timeScreening, int typeID) {
        Screening screening = new Screening(movieID, cinemaHallID, typeID, dateScreening, timeScreening);
        screeningService.addScreening(screening);
    }

    public void handleUpdateScreening(int screeningID, int movieID, int cinemaHallID, LocalDate dateScreening, LocalTime timeScreening, int typeID) {
        Screening screening = new Screening(screeningID, movieID, cinemaHallID, typeID, dateScreening, timeScreening);
        screeningService.updateScreening(screening);
    }

    public void handleDeleteScreening(int screeningID) {
        screeningService.deleteScreening(screeningID);
    }

    public int handleGetScreeningIDByDateTime(String dateTime, int movieID) {
        return screeningService.getScreeningIDByDateTime(dateTime, movieID);
    }
}