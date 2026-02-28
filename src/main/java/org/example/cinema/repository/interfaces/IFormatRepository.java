package org.example.cinema.repository.interfaces;

import org.example.cinema.domain.Format;

import java.util.List;

public interface IFormatRepository {
    List<Format> getFormats();
    Format getFormat(String formatType);
    Format getFormat(int formatID);
}
