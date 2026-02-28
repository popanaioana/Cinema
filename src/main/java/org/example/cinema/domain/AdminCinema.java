package org.example.cinema.domain;

import java.time.LocalDate;
import java.util.Date;

public class AdminCinema extends Users{
    private double Salary;
    private LocalDate DateOfEmployement;

    public AdminCinema(String FirstName, String LastName, String Email, String PhoneNumber, LocalDate DateOfBirth, String UserName, String UserPassword, double Salary, LocalDate DateOfEmployement) {
        super(FirstName, LastName, Email, PhoneNumber, DateOfBirth, UserName, UserPassword);
        this.Salary = Salary;
        this.DateOfEmployement = DateOfEmployement;
    }

    public AdminCinema(int UserID, String FirstName, String LastName, String Email, String PhoneNumber, LocalDate DateOfBirth, String UserName, String UserPassword, double Salary, LocalDate DateOfEmployement) {
        super(UserID, FirstName, LastName, Email, PhoneNumber, DateOfBirth, UserName, UserPassword);
        this.Salary = Salary;
        this.DateOfEmployement = DateOfEmployement;
    }

    public double getSalary() {
        return Salary;
    }

    public LocalDate getDateOfEmployment() {
        return DateOfEmployement;
    }
}
