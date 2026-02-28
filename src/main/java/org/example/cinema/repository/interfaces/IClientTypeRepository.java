package org.example.cinema.repository.interfaces;

import org.example.cinema.domain.ClientType;

import java.util.List;

public interface IClientTypeRepository {
    List<ClientType> getClientTypes();
    ClientType getClientType(String typeName);
}
