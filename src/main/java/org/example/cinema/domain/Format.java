package org.example.cinema.domain;

public class Format {
    private int FormatID;
    private String TypeFormat;

    public Format(int formatID, String typeFormat) {
        this.FormatID = formatID;
        this.TypeFormat = typeFormat;
    }

    public String getTypeFormat() {
        return TypeFormat;
    }

    public int getFormatID() {
        return FormatID;
    }
}
