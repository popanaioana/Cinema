package org.example.cinema.service;

import org.example.cinema.domain.Screening;
import org.example.cinema.repository.interfaces.IScreeningRepository;
import org.example.cinema.validators.ScreeningValidator;

import java.util.List;

public class ScreeningService {
    private final IScreeningRepository screeningRepository;
    private final ScreeningValidator screeningValidator;

    public ScreeningService(IScreeningRepository screeningRepository, ScreeningValidator screeningValidator) {
        this.screeningRepository = screeningRepository;
        this.screeningValidator = screeningValidator;
    }

    public List<Screening> getScreenings() {
        return screeningRepository.getScreenings();
    }

    public Screening getScreening(int id) {
        return screeningRepository.getScreening(id);
    }

    public List<Screening> getScreenings(int movieID) {
        return screeningRepository.getScreenings(movieID);
    }

    public void addScreening(Screening screening) {
        screeningValidator.validate(screening);
        screeningRepository.addScreening(screening);
    }

    public void updateScreening(Screening screening) {
        screeningValidator.validate(screening);
        screeningRepository.updateScreening(screening);
    }

    public void deleteScreening(int id) {
        screeningRepository.deleteScreening(id);
    }

    public int getScreeningIDByDateTime(String dateTime, int movieID) {
        return screeningRepository.getScreeningIDByDateTime(dateTime, movieID);
    }
}