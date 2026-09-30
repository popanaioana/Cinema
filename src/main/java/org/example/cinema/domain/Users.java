package org.example.cinema.domain;

import java.time.LocalDate;

public abstract class Users {
    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String userName;
    private String userPassword;

    public Users(String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth, String userName, String userPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public Users(int userID, String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth, String userName, String userPassword) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public int getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserID(int generatedID) {
        this.userID = generatedID;
    }
}