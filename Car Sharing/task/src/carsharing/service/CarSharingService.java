package carsharing.service;

import carsharing.dao.CarDao;
import carsharing.dao.CompanyDao;
import carsharing.db.DatabaseConnection;
import carsharing.model.Car;
import carsharing.model.Company;

import java.util.List;

public class CarSharingService {

    private final DatabaseConnection databaseConnection;
    private final CompanyDao companyDao;
    private final CarDao carDao;

    public CarSharingService(String databaseName) {
        this.databaseConnection = new DatabaseConnection(databaseName);
        databaseConnection.connect();
        this.companyDao = new CompanyDao(databaseConnection);
        this.carDao = new CarDao(databaseConnection);
    }

    public List<Company> findAllCompanies() {
        return companyDao.findAll();
    }

    public void saveCompany(Company company) {
        companyDao.save(company);
    }

    public List<Car> findAllCarsForCompany(long companyId) {
        return carDao.findAllById(companyId);
    }

    public void saveCar(Car car) {
        carDao.save(car);
    }

    public void closeAllConnections() {
        if (databaseConnection.getConnection() != null) {
            databaseConnection.close();
        }
    }
}
