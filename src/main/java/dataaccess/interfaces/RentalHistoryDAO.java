package dataaccess.interfaces;

import dataaccess.entity.RentalHistory;

import java.util.List;

public interface RentalHistoryDAO {
    RentalHistory findById(int rentalId);
    List<RentalHistory> findAll();
    void save(RentalHistory rentalHistory);
    void update(RentalHistory rentalHistory);
    void delete(int rentalId);

    List<RentalHistory> findAllWithPagination(int offset, int limit);

    int count();

    List<RentalHistory> findFiltered(String clientName, String carModel, String rentalYear, String rentalMonth,
                                     String rentalDay, String returnYear, String returnMonth, String returnDay,
                                     String totalCost, int offset, int limit);

    int countFiltered(String clientName, String carModel, String rentalYear, String rentalMonth,
                      String rentalDay, String returnYear, String returnMonth, String returnDay,
                      String totalCost);
}
