package dataaccess.interfaces;

import dataaccess.entity.Client;

import java.util.List;

public interface ClientDAO {
    Client findById(int clientId);
    List<Client> findAll();

    Client findByPassportNumber(String passportNumber);

    void save(Client client);
    void update(Client client);
    void delete(int clientId);
}
