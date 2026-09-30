package org.example.cinema.domain;

public class Genre {
    private int genreID;
    private String name;

    public Genre(int genreID, String name) {
        this.genreID = genreID;
        this.name = name;
    }

    public int getGenreID() {
        return genreID;
    }

    public String getGenreName() {
        return name;
    }

    public void setGenreName(String genreName) {
        this.name = genreName;
    }
}