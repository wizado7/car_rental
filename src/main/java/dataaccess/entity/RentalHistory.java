package dataaccess.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

public class RentalHistory {
    private int id;
    private int clientId;
    private int carId;
    private Date rentalDate;
    private Date returnDate;
    private BigDecimal totalCost;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public LocalDate getRentalDate() {
        return rentalDate.toLocalDate();
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = Date.valueOf(rentalDate);
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = Date.valueOf(returnDate);
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}