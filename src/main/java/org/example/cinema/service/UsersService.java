package org.example.cinema.service;

import org.example.cinema.domain.Client;
import org.example.cinema.domain.Users;
import org.example.cinema.repository.db.UsersDBRepository;
import org.example.cinema.validators.ClientValidator;
import org.example.cinema.validators.UsersValidator;

import java.sql.SQLException;
import java.util.List;

public class UsersService {
    private UsersDBRepository usersRepository;
    private UsersValidator usersValidator;
    private ClientValidator clientValidator;

    public UsersService(UsersDBRepository usersRepository, UsersValidator usersValidator, ClientValidator clientValidator) {
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
        clientValidator.validate(((Client)user));
        usersRepository.addUser(user);
    }

    public void updateUser(Users user) {
        usersValidator.validate(user);
        usersRepository.updateUser(user);
    }

    public void deleteUser(Users user) {
        usersRepository.deleteUser(user);
    }
}
