package org.example.cinema.controller;

import org.example.cinema.domain.ScreeningType;
import org.example.cinema.service.ScreeningTypeService;

import java.util.List;

public class ScreeningTypeController {
    private final ScreeningTypeService screeningTypeService;

    public ScreeningTypeController(ScreeningTypeService screeningTypeService) {
        this.screeningTypeService = screeningTypeService;
    }

    public List<ScreeningType> handleGetScreeningTypes() {
        return screeningTypeService.getScreeningTypes();
    }

    public ScreeningType handleGetScreeningType(String screeningTypeName) {
        return screeningTypeService.getScreeningType(screeningTypeName);
    }
}