package org.example.cinema.domain;

import java.time.LocalDate;

public class AdminCinema extends Users {
    private double salary;
    private LocalDate dateOfEmployment;

    public AdminCinema(String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth, String userName, String userPassword, double salary, LocalDate dateOfEmployment) {
        super(firstName, lastName, email, phoneNumber, dateOfBirth, userName, userPassword);
        this.salary = salary;
        this.dateOfEmployment = dateOfEmployment;
    }

    public AdminCinema(int userID, String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth, String userName, String userPassword, double salary, LocalDate dateOfEmployment) {
        super(userID, firstName, lastName, email, phoneNumber, dateOfBirth, userName, userPassword);
        this.salary = salary;
        this.dateOfEmployment = dateOfEmployment;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
    }
}