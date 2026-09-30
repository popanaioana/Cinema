package org.example.cinema.controller;

import org.example.cinema.domain.ClientType;
import org.example.cinema.service.ClientTypeService;

import java.util.List;

public class ClientTypeController {
    private final ClientTypeService clientTypeService;

    public ClientTypeController(ClientTypeService clientTypeService) {
        this.clientTypeService = clientTypeService;
    }

    public List<ClientType> handleGetClientTypes() {
        return clientTypeService.getClientTypes();
    }

    public ClientType handleGetClientType(String typeName) {
        return clientTypeService.getClientType(typeName);
    }
}