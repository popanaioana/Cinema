package org.example.cinema.service;

import org.example.cinema.domain.Format;
import org.example.cinema.repository.db.FormatDBRepository;

import java.util.List;

public class FormatService {
    private FormatDBRepository formatRepository;

    public FormatService(FormatDBRepository formatDBRepository) {
        this.formatRepository = formatDBRepository;
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
