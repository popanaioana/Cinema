package org.example.cinema.validators;

import org.example.cinema.domain.Client;

import java.time.LocalDate;

public class ClientValidator {
    public void validate(Client client) throws RuntimeException {
        if (client.getFirstName().length() < 3 || client.getFirstName().length() > 20 || client.getLastName().length() < 3 || client.getLastName().length() > 20 ||
                client.getEmail().length() < 3 || client.getEmail().length() > 50 || client.getEmail().contains("@") == false || client.getEmail().contains(".") == false ||
                client.getPhoneNumber().length() != 10 || client.getPhoneNumber().matches("\\d+") == false ||
                client.getDateOfBirth().isBefore(LocalDate.now().minusYears(120)) || client.getDateOfBirth().isAfter(LocalDate.now()) ||
                client.getUserName().length() < 3 || client.getUserName().length() > 25 || client.getUserPassword().length() < 3 || client.getUserPassword().length() > 100) {
            throw new RuntimeException("Invalid client.");
        }
    }
}
