package dataaccess.repository;

import config.ConnectionManager;
import dataaccess.entity.Car;
import dataaccess.interfaces.CarDAO;

import java.sql.Connection;
import java.sql.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CarRepository implements CarDAO {
    private static CarRepository instance;

    private CarRepository() {}

    public static synchronized CarRepository getInstance() {
        if (instance == null) {
            instance = new CarRepository();
        }
        return instance;
    }

    @Override
    public Car findById(int carId) {
        String query = "SELECT * FROM rent.cars WHERE id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, carId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapCar(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса findById", e);
        }
        return null;
    }

    @Override
    public List<Car> findAll() {
        String query = "SELECT * FROM rent.cars";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                cars.add(mapCar(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса findAll", e);
        }
        return cars;
    }

    @Override
    public void save(Car car) {
        String query = "INSERT INTO rent.cars (brand, model, year, registration_number, daily_rate, Status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, car.getBrand());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setString(4, car.getRegistrationNumber());
            statement.setBigDecimal(5, car.getDailyRate());
            statement.setString(6, car.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса save", e);
        }
    }

    @Override
    public void update(Car car) {
        String query = "UPDATE rent.cars SET brand = ?, model = ?, year = ?, registration_number = ?, daily_rate = ?, Status = ? WHERE id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, car.getBrand());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setString(4, car.getRegistrationNumber());
            statement.setBigDecimal(5, car.getDailyRate());
            statement.setString(6, car.getStatus());
            statement.setInt(7, car.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса update", e);
        }
    }

    @Override
    public void delete(int carId) {
        String query = "DELETE FROM rent.cars WHERE id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, carId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса delete", e);
        }
    }

    private Car mapCar(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        car.setId(resultSet.getInt("id"));
        car.setBrand(resultSet.getString("brand"));
        car.setModel(resultSet.getString("model"));
        car.setYear(resultSet.getInt("year"));
        car.setRegistrationNumber(resultSet.getString("registration_number"));
        car.setDailyRate(resultSet.getBigDecimal("daily_rate"));
        car.setStatus(String.valueOf(resultSet.getString("status")));
        return car;
    }

}