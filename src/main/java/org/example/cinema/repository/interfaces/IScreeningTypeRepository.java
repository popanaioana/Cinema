package org.example.cinema.repository.interfaces;

import org.example.cinema.domain.ScreeningType;

import java.util.List;

public interface IScreeningTypeRepository {
    List<ScreeningType> getScreeningTypes();
    ScreeningType getScreeningType(String screeningTypeName);
}
