package org.example.cinema.domain;

public class ScreeningType {
    private int TypeID;
    private String TypeName;

    public ScreeningType(int TypeID, String TypeName) {
        this.TypeID = TypeID;
        this.TypeName = TypeName;
    }

    public String getTypeName() {
        return TypeName;
    }

    public int getScreeningTypeID() {
        return TypeID;
    }
}
