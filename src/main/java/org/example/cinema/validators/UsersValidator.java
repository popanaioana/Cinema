package org.example.cinema.validators;

import org.example.cinema.domain.Users;

public class UsersValidator {
    public void validate(Users user) throws RuntimeException {
        if (user.getUserName().length() < 3 || user.getUserName().length() > 25 || user.getUserPassword().length() < 3 || user.getUserPassword().length() > 100) {
            throw new RuntimeException("Invalid user.");
        }
    }
}
