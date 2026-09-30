package org.example.cinema.domain;

public class ScreeningType {

    private int typeID;
    private String typeName;

    public ScreeningType(int typeID, String typeName) {
        this.typeID = typeID;
        this.typeName = typeName;
    }

    public int getScreeningTypeID() {
        return typeID;
    }

    public String getTypeName() {
        return typeName;
    }
}