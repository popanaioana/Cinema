package org.example.cinema.domain;

import java.time.LocalDate;

public abstract class Users {
    private int UserID;
    private String FirstName;
    private String LastName;
    private String Email;
    private String PhoneNumber;
    private LocalDate DateOfBirth;
    private String UserName;
    private String UserPassword;

    public Users(String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth, String userName, String password) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Email = email;
        this.PhoneNumber = phoneNumber;
        this.DateOfBirth = dateOfBirth;
        this.UserName = userName;
        this.UserPassword = password;
    }

    public Users(int UserID, String FirstName, String LastName, String Email, String PhoneNumber, LocalDate DateOfBirth, String UserName, String UserPassword) {
        this.UserID = UserID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
        this.DateOfBirth = DateOfBirth;
        this.UserName = UserName;
        this.UserPassword = UserPassword;
    }

    public int getUserID() {
        return UserID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return DateOfBirth;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserID(int generatedId) {
        this.UserID = generatedId;
    }
}
