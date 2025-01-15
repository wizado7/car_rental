package dataaccess.repository;

import config.ConnectionManager;
import dataaccess.entity.Client;
import dataaccess.interfaces.ClientDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClientRepository implements ClientDAO {
    private static ClientRepository instance;

    private ClientRepository() {}

    public static synchronized ClientRepository getInstance() {
        if (instance == null) {
            instance = new ClientRepository();
        }
        return instance;
    }

    @Override
    public Client findById(int clientId) {
        String query = "SELECT * FROM rent.clients WHERE id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, clientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapClient(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса findById", e);
        }
        return null;
    }

    @Override
    public List<Client> findAll() {
        String query = "SELECT * FROM rent.clients";
        List<Client> clients = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                clients.add(mapClient(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса findAll", e);
        }
        return clients;
    }

    @Override
    public Client findByPassportNumber(String passportNumber) {
        String query = "SELECT * FROM rent.clients WHERE passport_number = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, passportNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapClient(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса findByPassportNumber", e);
        }
        return null;
    }

    @Override
    public void save(Client client) {
        String query = "INSERT INTO rent.clients (full_name, phone_number, email, passport_number, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getFullName());
            statement.setString(2, client.getPhoneNumber());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getPassportNumber());
            statement.setString(5, client.getAddress());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса save", e);
        }
    }

    @Override
    public void update(Client client) {
        String query = "UPDATE rent.clients SET full_name = ?, phone_number = ?, Email = ?, passport_number = ?, address = ? WHERE id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getFullName());
            statement.setString(2, client.getPhoneNumber());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getPassportNumber());
            statement.setString(5, client.getAddress());
            statement.setInt(6, client.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса update", e);
        }
    }

    @Override
    public void delete(int clientId) {
        String query = "DELETE FROM rent.clients WHERE id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, clientId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса delete", e);
        }
    }

    private Client mapClient(ResultSet resultSet) throws SQLException {
        Client client = new Client();
        client.setId(resultSet.getInt("id"));
        client.setFullName(resultSet.getString("full_name"));
        client.setPhoneNumber(resultSet.getString("phone_number"));
        client.setEmail(resultSet.getString("email"));
        client.setPassportNumber(resultSet.getString("passport_number"));
        client.setAddress(resultSet.getString("address"));
        return client;
    }
}
