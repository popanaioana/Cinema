package org.example.cinema.service;

import org.example.cinema.domain.Format;
import org.example.cinema.repository.interfaces.IFormatRepository;

import java.util.List;

public class FormatService {
    private final IFormatRepository formatRepository;

    public FormatService(IFormatRepository formatRepository) {
        this.formatRepository = formatRepository;
    }

    public List<Format> getFormats() {
        return formatRepository.getFormats();
    }

    public Format getFormat(String formatType) {
        return formatRepository.getFormat(formatType);
    }

    public Format getFormat(int formatID) {
        return formatRepository.getFormat(formatID);
    }
}