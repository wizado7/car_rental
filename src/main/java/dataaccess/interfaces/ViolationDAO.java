package dataaccess.interfaces;

import dataaccess.entity.Violation;

import java.util.List;

public interface ViolationDAO {
    Violation findById(int violationId);
    List<Violation> findAll();
    void save(Violation violation);
    void update(Violation violation);
    void delete(int violationId);
}