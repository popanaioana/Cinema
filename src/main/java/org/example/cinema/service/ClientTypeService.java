package org.example.cinema.service;

import org.example.cinema.domain.ClientType;
import org.example.cinema.repository.interfaces.IClientTypeRepository;

import java.util.List;

public class ClientTypeService {
    private final IClientTypeRepository clientTypeRepository;

    public ClientTypeService(IClientTypeRepository clientTypeRepository) {
        this.clientTypeRepository = clientTypeRepository;
    }

    public List<ClientType> getClientTypes() {
        return clientTypeRepository.getClientTypes();
    }

    public ClientType getClientType(String typeName) {
        return clientTypeRepository.getClientType(typeName);
    }
}