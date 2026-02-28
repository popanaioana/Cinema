package org.example.cinema.repository.interfaces;

import org.example.cinema.domain.Genre;

import java.util.List;

public interface IGenreRepository {
    List<Genre> getGenres();
    public List<Genre> getGenres(int movieID);
    public String getGenreNameByID(int genreID);
    public int getGenreID(String genre);
}
