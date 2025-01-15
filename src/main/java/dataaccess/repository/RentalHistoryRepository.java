package dataaccess.repository;

import config.ConnectionManager;
import dataaccess.entity.RentalHistory;
import dataaccess.interfaces.RentalHistoryDAO;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalHistoryRepository implements RentalHistoryDAO {
    private static RentalHistoryRepository instance;

    private RentalHistoryRepository() {}

    public static synchronized RentalHistoryRepository getInstance() {
        if (instance == null) {
            instance = new RentalHistoryRepository();
        }
        return instance;
    }

    @Override
    public RentalHistory findById(int id) {
        String query = "SELECT * FROM rent.rentalhistory WHERE id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRentalHistory(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса findById", e);
        }
        return null;
    }

    @Override
    public List<RentalHistory> findAll() {
        String query = "SELECT * FROM rent.rentalhistory";
        List<RentalHistory> histories = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                histories.add(mapRentalHistory(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса findAll", e);
        }
        return histories;
    }

    @Override
    public void save(RentalHistory rentalHistory) {
        String query = "INSERT INTO rent.rentalhistory (client_id, car_id, rental_date, return_date, total_cost, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, rentalHistory.getClientId());
            statement.setInt(2, rentalHistory.getCarId());
            statement.setDate(3, Date.valueOf(rentalHistory.getRentalDate()));
            statement.setDate(4, rentalHistory.getReturnDate() != null ? Date.valueOf(rentalHistory.getReturnDate().toLocalDate()) : null);
            statement.setBigDecimal(5, rentalHistory.getTotalCost());
            statement.setString(6, rentalHistory.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса save", e);
        }
    }

    @Override
    public void update(RentalHistory rentalHistory) {
        String query = "UPDATE rent.rentalhistory SET client_id = ?, car_id = ?, rental_date = ?, return_date = ?, total_cost = ?, status = ? WHERE id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, rentalHistory.getClientId());
            statement.setInt(2, rentalHistory.getCarId());
            statement.setDate(3, Date.valueOf(rentalHistory.getRentalDate()));
            statement.setDate(4, rentalHistory.getReturnDate() != null ? Date.valueOf(rentalHistory.getReturnDate().toLocalDate()) : null);
            statement.setBigDecimal(5, rentalHistory.getTotalCost());
            statement.setString(6, rentalHistory.getStatus());
            statement.setInt(7, rentalHistory.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса update", e);
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM rent.rentalhistory WHERE id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса delete", e);
        }
    }

    @Override
    public List<RentalHistory> findAllWithPagination(int offset, int limit) {
        String query = "SELECT * FROM rent.rentalhistory LIMIT ? OFFSET ?";
        List<RentalHistory> histories = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    histories.add(mapRentalHistory(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса findAllWithPagination", e);
        }
        return histories;
    }
    @Override
    public int count() {
        String query = "SELECT COUNT(*) AS total FROM rent.rentalhistory";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса count", e);
        }
        return 0;
    }

    @Override
    public List<RentalHistory> findFiltered(String clientName, String carModel, String rentalYear, String rentalMonth,
                                            String rentalDay, String returnYear, String returnMonth, String returnDay,
                                            String totalCost, int offset, int limit) {
        StringBuilder query = new StringBuilder("SELECT rh.* FROM rent.rentalhistory rh ");
        query.append("JOIN rent.clients c ON rh.client_id = c.id ");
        query.append("JOIN rent.cars cr ON rh.car_id = cr.id WHERE 1=1 ");

        if (clientName != null && !clientName.isEmpty()) query.append("AND c.full_name LIKE ? ");
        if (carModel != null && !carModel.isEmpty()) query.append("AND cr.model LIKE ? ");
        if (rentalYear != null && !rentalYear.isEmpty()) query.append("AND YEAR(rh.rental_date) = ? ");
        if (rentalMonth != null && !rentalMonth.isEmpty()) query.append("AND MONTH(rh.rental_date) = ? ");
        if (rentalDay != null && !rentalDay.isEmpty()) query.append("AND DAY(rh.rental_date) = ? ");
        if (returnYear != null && !returnYear.isEmpty()) query.append("AND YEAR(rh.return_date) = ? ");
        if (returnMonth != null && !returnMonth.isEmpty()) query.append("AND MONTH(rh.return_date) = ? ");
        if (returnDay != null && !returnDay.isEmpty()) query.append("AND DAY(rh.return_date) = ? ");
        if (totalCost != null && !totalCost.isEmpty()) query.append("AND rh.total_cost = ? ");

        query.append("LIMIT ? OFFSET ?");

        List<RentalHistory> histories = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int index = 1;
            if (clientName != null && !clientName.isEmpty()) statement.setString(index++, "%" + clientName + "%");
            if (carModel != null && !carModel.isEmpty()) statement.setString(index++, "%" + carModel + "%");
            if (rentalYear != null && !rentalYear.isEmpty()) statement.setString(index++, rentalYear);
            if (rentalMonth != null && !rentalMonth.isEmpty()) statement.setString(index++, rentalMonth);
            if (rentalDay != null && !rentalDay.isEmpty()) statement.setString(index++, rentalDay);
            if (returnYear != null && !returnYear.isEmpty()) statement.setString(index++, returnYear);
            if (returnMonth != null && !returnMonth.isEmpty()) statement.setString(index++, returnMonth);
            if (returnDay != null && !returnDay.isEmpty()) statement.setString(index++, returnDay);
            if (totalCost != null && !totalCost.isEmpty()) statement.setBigDecimal(index++, new BigDecimal(totalCost));
            statement.setInt(index++, limit);
            statement.setInt(index, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    histories.add(mapRentalHistory(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса findFiltered", e);
        }
        return histories;
    }

    @Override
    public int countFiltered(String clientName, String carModel, String rentalYear, String rentalMonth,
                             String rentalDay, String returnYear, String returnMonth, String returnDay,
                             String totalCost) {
        StringBuilder query = new StringBuilder("SELECT COUNT(*) AS total FROM rent.rentalhistory rh ");
        query.append("JOIN rent.clients c ON rh.client_id = c.id ");
        query.append("JOIN rent.cars cr ON rh.car_id = cr.id WHERE 1=1 ");

        if (clientName != null && !clientName.isEmpty()) query.append("AND c.full_name LIKE ? ");
        if (carModel != null && !carModel.isEmpty()) query.append("AND cr.model LIKE ? ");
        if (rentalYear != null && !rentalYear.isEmpty()) query.append("AND YEAR(rh.rental_date) = ? ");
        if (rentalMonth != null && !rentalMonth.isEmpty()) query.append("AND MONTH(rh.rental_date) = ? ");
        if (rentalDay != null && !rentalDay.isEmpty()) query.append("AND DAY(rh.rental_date) = ? ");
        if (returnYear != null && !returnYear.isEmpty()) query.append("AND YEAR(rh.return_date) = ? ");
        if (returnMonth != null && !returnMonth.isEmpty()) query.append("AND MONTH(rh.return_date) = ? ");
        if (returnDay != null && !returnDay.isEmpty()) query.append("AND DAY(rh.return_date) = ? ");
        if (totalCost != null && !totalCost.isEmpty()) query.append("AND rh.total_cost = ? ");

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int index = 1;
            if (clientName != null && !clientName.isEmpty()) statement.setString(index++, "%" + clientName + "%");
            if (carModel != null && !carModel.isEmpty()) statement.setString(index++, "%" + carModel + "%");
            if (rentalYear != null && !rentalYear.isEmpty()) statement.setString(index++, rentalYear);
            if (rentalMonth != null && !rentalMonth.isEmpty()) statement.setString(index++, rentalMonth);
            if (rentalDay != null && !rentalDay.isEmpty()) statement.setString(index++, rentalDay);
            if (returnYear != null && !returnYear.isEmpty()) statement.setString(index++, returnYear);
            if (returnMonth != null && !returnMonth.isEmpty()) statement.setString(index++, returnMonth);
            if (returnDay != null && !returnDay.isEmpty()) statement.setString(index++, returnDay);
            if (totalCost != null && !totalCost.isEmpty()) statement.setBigDecimal(index, new BigDecimal(totalCost));

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("total");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса countFiltered", e);
        }
        return 0;
    }


    private RentalHistory mapRentalHistory(ResultSet resultSet) throws SQLException {
        RentalHistory rentalHistory = new RentalHistory();
        rentalHistory.setId(resultSet.getInt("id"));
        rentalHistory.setClientId(resultSet.getInt("client_id"));
        rentalHistory.setCarId(resultSet.getInt("car_id"));
        rentalHistory.setRentalDate(resultSet.getDate("rental_date").toLocalDate());
        rentalHistory.setReturnDate(resultSet.getDate("return_date") != null ? Date.valueOf(resultSet.getDate("return_date").toLocalDate()).toLocalDate() : null);
        rentalHistory.setTotalCost(resultSet.getBigDecimal("total_cost"));
        rentalHistory.setStatus(String.valueOf(resultSet.getString("status")));
        return rentalHistory;
    }
}