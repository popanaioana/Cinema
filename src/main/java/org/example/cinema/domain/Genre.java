package org.example.cinema.domain;

public class Genre {
    private int GenreID;
    private String Name;

    public Genre(int GenreID, String Name) {
        this.GenreID = GenreID;
        this.Name = Name;
    }

    public String getGenreName() {
        return Name;
    }

    public int getGenreID() {
        return GenreID;
    }

    public void setGenreName(String s) {
        Name = s;
    }
}
