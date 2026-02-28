package org.example.cinema.domain;

import java.time.LocalDate;

public class Client extends Users{
    private int TypeID;

    public Client(String FirstName, String LastName, String email, String phoneNumber, LocalDate dateOfBirth, String userName, String password, int TypeID) {
        super(FirstName, LastName, email, phoneNumber, dateOfBirth, userName, password);
        this.TypeID = TypeID;
    }

    public Client(int UserID, String FirstName, String LastName, String Email, String PhoneNumber, LocalDate DateOfBirth, String UserName, String UserPassword, int TypeID) {
        super(UserID, FirstName, LastName, Email, PhoneNumber, DateOfBirth, UserName, UserPassword);
        this.TypeID = TypeID;
    }

    public int getTypeID() {
        return TypeID;
    }
}
