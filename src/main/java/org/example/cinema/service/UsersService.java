package org.example.cinema.service;

import org.example.cinema.domain.Client;
import org.example.cinema.domain.Users;
import org.example.cinema.repository.interfaces.IUsersRepository;
import org.example.cinema.validators.ClientValidator;
import org.example.cinema.validators.UsersValidator;

import java.sql.SQLException;
import java.util.List;

public class UsersService {
    private final IUsersRepository usersRepository;
    private final UsersValidator usersValidator;
    private final ClientValidator clientValidator;

    public UsersService(IUsersRepository usersRepository, UsersValidator usersValidator, ClientValidator clientValidator) {
        this.usersRepository = usersRepository;
        this.usersValidator = usersValidator;
        this.clientValidator = clientValidator;
    }

    public List<Users> getUsers() {
        return usersRepository.getUsers();
    }

    public Users getUser(String username, String password) {
        return usersRepository.getUser(username, password);
    }

    public Users getUser(int id) {
        return usersRepository.getUser(id);
    }

    public void addUser(Users user) throws SQLException {
        if (user instanceof Client client) {
            clientValidator.validate(client);
        } else {
            usersValidator.validate(user);
        }
        usersRepository.addUser(user);
    }

    public void updateUser(Users user) {
        if (user instanceof Client client) {
            clientValidator.validate(client);
        } else {
            usersValidator.validate(user);
        }
        usersRepository.updateUser(user);
    }

    public void deleteUser(Users user) {
        usersRepository.deleteUser(user);
    }
}