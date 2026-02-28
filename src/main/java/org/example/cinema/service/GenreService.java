package org.example.cinema.service;

import org.example.cinema.domain.Genre;
import org.example.cinema.repository.db.GenreDBRepository;

import java.util.List;

public class GenreService {
    private GenreDBRepository genreRepository;

    public GenreService(GenreDBRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getGenres() {
        return genreRepository.getGenres();
    }

    public List<Genre> getGenre(int movieID) {
        return genreRepository.getGenres(movieID);
    }

    public String getGenreNameByID(int genreID) {
        return genreRepository.getGenreNameByID(genreID);
    }

    public int getGenreID(String genre) {
        return genreRepository.getGenreID(genre);
    }
}
