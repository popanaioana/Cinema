package org.example.cinema.controller;

import org.example.cinema.domain.Genre;
import org.example.cinema.service.GenreService;

import java.util.List;

public class GenreController {
    private GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    public List<Genre> handleGetGenres(){
        return genreService.getGenres();
    }

    public List<Genre> handleGetGenre(int movieID){
        return genreService.getGenre(movieID);
    }

    public String handleGetGenreNameByID(int genreID) {
        return genreService.getGenreNameByID(genreID);
    }

    public int handleGetGenreID(String genre) {
        return genreService.getGenreID(genre);
    }
}
