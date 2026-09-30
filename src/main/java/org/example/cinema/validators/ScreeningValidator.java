package org.example.cinema.validators;

import org.example.cinema.domain.Screening;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScreeningValidator {
    private static final LocalTime OPENING_TIME = LocalTime.of(7, 0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(23, 59);

    public void validate(Screening screening) {
        if (screening == null) {
            throw new IllegalArgumentException("Screening cannot be null.");
        }
        validateDate(screening.getDateScreening());
        validateTime(screening.getDateScreening(), screening.getTimeScreening());
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Screening date cannot be null.");
        }
        LocalDate today = LocalDate.now();
        if (date.isBefore(today) || date.isAfter(today.plusMonths(1))) {
            throw new IllegalArgumentException("Screening date must be between today and one month from today.");
        }
    }

    private void validateTime(LocalDate date, LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("Screening time cannot be null.");
        }
        if (time.isBefore(OPENING_TIME) || time.isAfter(CLOSING_TIME)) {
            throw new IllegalArgumentException("Screening time must be between 07:00 and 23:59.");
        }
        if (date.equals(LocalDate.now()) && time.isBefore(LocalTime.now())) {
            throw new IllegalArgumentException("Screening time cannot be in the past.");
        }
    }
}