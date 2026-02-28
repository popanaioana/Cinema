package org.example.cinema.service;

import org.example.cinema.controller.ClientTypeController;
import org.example.cinema.domain.ClientType;
import org.example.cinema.repository.db.ClientTypeDBRepository;

import java.util.List;

public class ClientTypeService {
    private ClientTypeDBRepository clientTypeDBRepository;

    public ClientTypeService(ClientTypeDBRepository clientTypeDBRepository) {
        this.clientTypeDBRepository = clientTypeDBRepository;
    }

    public List<ClientType> getClientTypes() {
        return clientTypeDBRepository.getClientTypes();
    }

    public ClientType getClientType(String typeName) {
        return clientTypeDBRepository.getClientType(typeName);
    }
}
