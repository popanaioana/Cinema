package org.example.cinema.controller;

import org.example.cinema.domain.Format;
import org.example.cinema.service.FormatService;

import java.util.List;

public class FormatController {
    private FormatService formatService;

    public FormatController(FormatService formatService) {
        this.formatService = formatService;
    }

    public List<Format> handleGetFormats(){
        return formatService.getFormats();
    }

    public Format handleGetFormat(String formatType){
        return formatService.getFormat(formatType);
    }

    public Format handleGetFormat(int formatID){
        return formatService.getFormat(formatID);
    }
}
