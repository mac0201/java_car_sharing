package carsharing.service;

import carsharing.dao.CarDao;
import carsharing.dao.CompanyDao;
import carsharing.db.DatabaseConnection;
import carsharing.model.Car;
import carsharing.model.Company;

import java.util.List;

public class CompanyCarService {

    private final CompanyDao companyDao;
    private final CarDao carDao;

    public CompanyCarService(DatabaseConnection dbConnection) {
        this.companyDao = new CompanyDao(dbConnection);
        this.carDao = new CarDao(dbConnection);
    }

    public List<Company> findAllCompanies() {
        return companyDao.findAll();
    }

    public void saveCompany(String name) {
        companyDao.save(new Company(name));
    }

    public List<Car> findAllCarsForCompany(long companyId, boolean skipBooked) {
        return skipBooked
                ? carDao.findAllByIdSkipBooked(companyId)
                : carDao.findAllById(companyId);
    }

    public void saveCar(Car car) {
        carDao.save(car);
    }

    public void saveCar(String name, long companyId) {
        saveCar(new Car(name, companyId));
    }

}
