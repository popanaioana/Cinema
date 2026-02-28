package org.example.cinema.service;

import org.example.cinema.domain.ScreeningType;
import org.example.cinema.repository.db.ScreeningTypeDBRepository;

import java.util.List;

public class ScreeningTypeService {
    private ScreeningTypeDBRepository screeningTypeDBRepository;

    public ScreeningTypeService(ScreeningTypeDBRepository screeningTypeRepository) {
        this.screeningTypeDBRepository = screeningTypeRepository;
    }

    public List<ScreeningType> getScreeningTypes() {
        return screeningTypeDBRepository.getScreeningTypes();
    }

    public ScreeningType getScreeningType(String screeningTypeName) {
        return screeningTypeDBRepository.getScreeningType(screeningTypeName);
    }
}
