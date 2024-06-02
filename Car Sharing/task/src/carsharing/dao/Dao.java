package carsharing.dao;

import carsharing.model.Company;

import java.util.List;

public interface Dao <T> {
    List<T> findAll();
    T findById(int id);
    T findByName(String name);
    void save(T company);
    void update(T company);
    void deleteById(int id);
}
