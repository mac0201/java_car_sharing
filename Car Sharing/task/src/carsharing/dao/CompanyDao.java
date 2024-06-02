package carsharing.dao;

import carsharing.model.Company;

import java.util.List;

public interface CompanyDao {
    List<Company> findAll();
    Company findById(int id);
    Company findByName(String name);
    void save(Company company);
    void update(Company company);
    void deleteById(int id);
}
