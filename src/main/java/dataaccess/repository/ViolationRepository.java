package dataaccess.repository;
import config.ConnectionManager;
import dataaccess.entity.Violation;
import dataaccess.interfaces.ViolationDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViolationRepository implements ViolationDAO {
    private static ViolationRepository instance;

    private ViolationRepository() {}

    public static synchronized ViolationRepository getInstance() {
        if (instance == null) {
            instance = new ViolationRepository();
        }
        return instance;
    }

    @Override
    public Violation findById(int id) {
        String query = "SELECT * FROM rent.violations WHERE id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapViolation(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса findById", e);
        }
        return null;
    }

    @Override
    public List<Violation> findAll() {
        String query = "SELECT * FROM rent.violations";
        List<Violation> violations = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                violations.add(mapViolation(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса findAll", e);
        }
        return violations;
    }

    public Violation findByRentalId(int rentalId) {
        String query = "SELECT id, rental_id, description, fine_amount, date_of_violation FROM rent.violations WHERE rental_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, rentalId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapViolation(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса findByRentalId", e);
        }

        return null;
    }

    @Override
    public void save(Violation violation) {
        String query = "INSERT INTO rent.violations (rental_id, description, fine_amount, date_of_violation) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, violation.getRentalId());
            statement.setString(2, violation.getDescription());
            statement.setBigDecimal(3, violation.getFineAmount());
            statement.setDate(4, Date.valueOf(violation.getDateOfViolation()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса save", e);
        }
    }

    @Override
    public void update(Violation violation) {
        String query = "UPDATE rent.violations SET rental_id = ?, description = ?, fine_amount = ?, date_of_violation = ? WHERE id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, violation.getRentalId());
            statement.setString(2, violation.getDescription());
            statement.setBigDecimal(3, violation.getFineAmount());
            statement.setDate(4, Date.valueOf(violation.getDateOfViolation()));
            statement.setInt(5, violation.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса update", e);
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM rent.violations WHERE id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса delete", e);
        }
    }

    private Violation mapViolation(ResultSet resultSet) throws SQLException {
        Violation violation = new Violation();
        violation.setId(resultSet.getInt("id"));
        violation.setRentalId(resultSet.getInt("rental_id"));
        violation.setDescription(resultSet.getString("description"));
        violation.setFineAmount(resultSet.getBigDecimal("fine_amount"));
        violation.setDateOfViolation(resultSet.getDate("date_of_violation").toLocalDate());
        return violation;
    }
}
