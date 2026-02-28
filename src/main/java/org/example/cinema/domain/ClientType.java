package org.example.cinema.domain;

public class ClientType {
    private int TypeID;
    private String TypeName;

    public ClientType(int TypeID, String TypeName) {
        this.TypeID = TypeID;
        this.TypeName = TypeName;
    }

    public String getTypeName() {
        return TypeName;
    }
}
