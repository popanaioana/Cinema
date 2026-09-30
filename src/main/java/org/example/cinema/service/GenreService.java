package org.example.cinema.service;

import org.example.cinema.domain.Genre;
import org.example.cinema.repository.interfaces.IGenreRepository;

import java.util.List;

public class GenreService {
    private final IGenreRepository genreRepository;

    public GenreService(IGenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getGenres() {
        return genreRepository.getGenres();
    }

    public List<Genre> getGenres(int movieID) {
        return genreRepository.getGenres(movieID);
    }

    public String getGenreNameByID(int genreID) {
        return genreRepository.getGenreNameByID(genreID);
    }

    public int getGenreID(String genre) {
        return genreRepository.getGenreID(genre);
    }
}