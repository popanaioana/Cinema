package org.example.cinema.repository.interfaces;

import org.example.cinema.domain.Users;

import java.sql.SQLException;
import java.util.List;

public interface IUsersRepository {
    List<Users> getUsers();
    Users getUser(String username, String userPassword);
    Users getUser(int id);
    void addUser(Users user) throws SQLException;
    void updateUser(Users user);
    void deleteUser(Users user);
}
