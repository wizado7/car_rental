package dataaccess.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Violation {
    private int id;
    private int rentalId;
    private String description;
    private BigDecimal fineAmount;
    private LocalDate dateOfViolation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }

    public LocalDate getDateOfViolation() {
        return dateOfViolation;
    }

    public void setDateOfViolation(LocalDate dateOfViolation) {
        this.dateOfViolation = dateOfViolation;
    }
}