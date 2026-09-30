package org.example.cinema.domain;

import java.time.LocalDate;

public class Client extends Users {
    private int typeID;

    public Client(String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth, String userName, String password, int typeID) {
        super(firstName, lastName, email, phoneNumber, dateOfBirth, userName, password);
        this.typeID = typeID;
    }

    public Client(int userID, String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth, String userName, String userPassword, int typeID) {
        super(userID, firstName, lastName, email, phoneNumber, dateOfBirth, userName, userPassword);
        this.typeID = typeID;
    }

    public int getTypeID() {
        return typeID;
    }
}