package org.example.cinema.repository.interfaces;

import org.example.cinema.domain.Genre;

import java.util.List;

public interface IGenreRepository {
    List<Genre> getGenres();
    List<Genre> getGenres(int movieID);
    String getGenreNameByID(int genreID);
    int getGenreID(String genre);
}
