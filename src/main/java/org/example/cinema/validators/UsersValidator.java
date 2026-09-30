package org.example.cinema.validators;

import org.example.cinema.domain.Users;

public class UsersValidator {
    public void validate(Users user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        validateUsername(user.getUserName());
        validatePassword(user.getUserPassword());
    }

    private void validateUsername(String username) {
        if (username == null || username.length() < 3 || username.length() > 25) {
            throw new IllegalArgumentException("Username must contain between 3 and 25 characters.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 3 || password.length() > 100) {
            throw new IllegalArgumentException("Password must contain between 3 and 100 characters.");
        }
    }
}