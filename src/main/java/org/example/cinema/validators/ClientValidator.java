package org.example.cinema.validators;

import org.example.cinema.domain.Client;

import java.time.LocalDate;

public class ClientValidator {
    public void validate(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null.");
        }
        validateName(client.getFirstName(), "First name");
        validateName(client.getLastName(), "Last name");
        validateEmail(client.getEmail());
        validatePhoneNumber(client.getPhoneNumber());
        validateDateOfBirth(client.getDateOfBirth());
        validateUsername(client.getUserName());
        validatePassword(client.getUserPassword());
    }

    private void validateName(String name, String fieldName) {
        if (name == null || name.length() < 3 || name.length() > 20) {
            throw new IllegalArgumentException(
                    fieldName + " must contain between 3 and 20 characters."
            );
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.length() < 3 || email.length() > 50 || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email address.");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 10 || !phoneNumber.matches("\\d+")) {
            throw new IllegalArgumentException("Phone number must contain exactly 10 digits.");
        }
    }

    private void validateDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isBefore(LocalDate.now().minusYears(120)) || dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid date of birth.");
        }
    }

    private void validateUsername(String username) {
        if (username == null || username.length() < 3 || username.length() > 25) {
            throw new IllegalArgumentException( "Username must contain between 3 and 25 characters.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 3 || password.length() > 100) {
            throw new IllegalArgumentException( "Password must contain between 3 and 100 characters.");
        }
    }
}