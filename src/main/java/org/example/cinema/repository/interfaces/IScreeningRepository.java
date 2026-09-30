package org.example.cinema.repository.interfaces;

import org.example.cinema.domain.Screening;

import java.util.List;

public interface IScreeningRepository {
    List<Screening> getScreenings();
    Screening getScreening(int id);
    List<Screening> getScreenings(int movieID);
    void addScreening(Screening screening);
    void deleteScreening(int id);
    void updateScreening(Screening screening);
    int getScreeningIDByDateTime(String dateTime, int movieID);
}
