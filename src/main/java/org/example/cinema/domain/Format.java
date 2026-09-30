package org.example.cinema.domain;

public class Format {
    private int formatID;
    private String typeFormat;

    public Format(int formatID, String typeFormat) {
        this.formatID = formatID;
        this.typeFormat = typeFormat;
    }

    public int getFormatID() {
        return formatID;
    }

    public String getTypeFormat() {
        return typeFormat;
    }
}