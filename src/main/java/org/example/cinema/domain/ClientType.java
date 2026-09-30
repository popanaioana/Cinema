package org.example.cinema.domain;

public class ClientType {
    private int typeID;
    private String typeName;

    public ClientType(int typeID, String typeName) {
        this.typeID = typeID;
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getTypeID() {
        return typeID;
    }
}