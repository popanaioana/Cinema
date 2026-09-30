package org.example.cinema.controller;

import org.example.cinema.domain.Genre;
import org.example.cinema.service.GenreService;

import java.util.List;

public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    public List<Genre> handleGetGenres() {
        return genreService.getGenres();
    }

    public List<Genre> handleGetGenres(int movieID) {
        return genreService.getGenres(movieID);
    }

    public String handleGetGenreNameByID(int genreID) {
        return genreService.getGenreNameByID(genreID);
    }

    public int handleGetGenreID(String genre) {
        return genreService.getGenreID(genre);
    }
}