package org.example.cinema.controller;

import org.example.cinema.domain.AdminCinema;
import org.example.cinema.domain.Client;
import org.example.cinema.domain.Users;
import org.example.cinema.service.UsersService;

import java.sql.SQLException;
import java.time.LocalDate;

public class UsersController {
    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    public void handleAddUser(String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth, String userName, String password, int typeID) throws SQLException {
        Client client = new Client(firstName, lastName, email, phoneNumber, dateOfBirth, userName, password, typeID);
        usersService.addUser(client);
    }

    public Users handleGetUser(String userName, String password) {
        if (usersService.getUser(userName, password) instanceof Client) {
            Client client = (Client) usersService.getUser(userName, password);
            return client;
        } else if (usersService.getUser(userName, password) instanceof AdminCinema) {
            AdminCinema adminCinema = (AdminCinema) usersService.getUser(userName, password);
            return adminCinema;
        }
        return null;
    }

    public Users handleGetUser(int id) {
        return usersService.getUser(id);
    }

    public void handleUpdateUserClient(String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth, String userName, String userPassword, int typeID) {
        Client client = new Client(firstName, lastName, email, phoneNumber, dateOfBirth, userName, userPassword, typeID);
        usersService.updateUser(client);
    }

    public void handleUpdateUserAdminCinema(String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth, String userName, String userPassword, double salary, LocalDate dateOfEmployement) {
        AdminCinema adminCinema = new AdminCinema(firstName, lastName, email, phoneNumber, dateOfBirth, userName, userPassword, salary, dateOfEmployement);
    }

    public void handleDeleteUser(String userName, String password) {
        Users user = usersService.getUser(userName, password);
        if (user != null) {
            usersService.deleteUser(user);
        }
    }
}
