package org.example.cinema.validators;

import org.example.cinema.domain.Screening;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScreeningValidator {
    public void validate(Screening screening) {
        LocalDate date = screening.getDateScreening();
        LocalTime time = screening.getTimeScreening();
        if (date.isBefore(LocalDate.now()) || date.isAfter(LocalDate.now().plusMonths(1)) ||
                time.isBefore(LocalTime.of(7, 0)) || time.isAfter(LocalTime.of(23, 59))) {
            throw new RuntimeException();
        }
    }

}
