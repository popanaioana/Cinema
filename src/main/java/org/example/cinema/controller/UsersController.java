package org.example.cinema.controller;

import org.example.cinema.domain.AdminCinema;
import org.example.cinema.domain.Client;
import org.example.cinema.domain.Users;
import org.example.cinema.service.UsersService;

import java.sql.SQLException;
import java.time.LocalDate;

public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    public void handleAddUser(String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth, String userName, String password, int typeID) throws SQLException {
        Client client = new Client(firstName, lastName, email, phoneNumber, dateOfBirth, userName, password, typeID);
        usersService.addUser(client);
    }

    public Users handleGetUser(String userName, String password) {
        return usersService.getUser(userName, password);
    }

    public Users handleGetUser(int userID) {
        return usersService.getUser(userID);
    }

    public void handleUpdateUserClient(int userID, String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth, String userName, String userPassword, int typeID) {
        Client client = new Client(userID, firstName, lastName, email, phoneNumber, dateOfBirth, userName, userPassword, typeID);
        usersService.updateUser(client);
    }

    public void handleUpdateUserAdminCinema(int userID, String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth, String userName, String userPassword, double salary, LocalDate dateOfEmployment) {
        AdminCinema adminCinema = new AdminCinema(userID, firstName, lastName, email, phoneNumber, dateOfBirth, userName, userPassword, salary, dateOfEmployment);
        usersService.updateUser(adminCinema);
    }

    public void handleDeleteUser(String userName, String password) {
        Users user = usersService.getUser(userName, password);
        if (user != null) {
            usersService.deleteUser(user);
        }
    }
}