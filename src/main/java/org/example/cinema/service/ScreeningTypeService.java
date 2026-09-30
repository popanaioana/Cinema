package org.example.cinema.service;

import org.example.cinema.domain.ScreeningType;
import org.example.cinema.repository.interfaces.IScreeningTypeRepository;

import java.util.List;

public class ScreeningTypeService {
    private final IScreeningTypeRepository screeningTypeRepository;

    public ScreeningTypeService(IScreeningTypeRepository screeningTypeRepository) {
        this.screeningTypeRepository = screeningTypeRepository;
    }

    public List<ScreeningType> getScreeningTypes() {
        return screeningTypeRepository.getScreeningTypes();
    }

    public ScreeningType getScreeningType(String screeningTypeName) {
        return screeningTypeRepository.getScreeningType(screeningTypeName);
    }
}