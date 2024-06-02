package carsharing.dao;

import carsharing.db.DatabaseClient;
import carsharing.model.Company;
import org.h2.command.dml.TransactionCommand;
import org.h2.mvstore.tx.Transaction;

import java.util.List;

public class CompanyDaoImpl implements CompanyDao {

    private final DatabaseClient db;

    public CompanyDaoImpl(String databaseName) {
        this.db = new DatabaseClient(databaseName);
        this.db.init();
    }

    @Override
    public List<Company> findAll() {
        return db.findAll();
    }

    // other methods

    @Override
    public Company findById(int id) {
        return null;
    }

    @Override
    public Company findByName(String name) {
        return null;
    }

    @Override
    public void save(Company company) {
        db.add(company);
    }

    @Override
    public void update(Company company) {

    }

    @Override
    public void deleteById(int id) {

    }
}
