package dataaccess.interfaces;

import dataaccess.entity.Car;

import java.util.List;

public interface CarDAO {
    Car findById(int carId);
    List<Car> findAll();
    void save(Car car);
    void update(Car car);
    void delete(int carId);
}