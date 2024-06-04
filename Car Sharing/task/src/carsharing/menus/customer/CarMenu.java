package carsharing.menus.customer;

import carsharing.menus.Menu;
import carsharing.menus.MenuUtils;
import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.service.CompanyCarService;

public class CarMenu implements Menu {

    private final CompanyCarService service;
    private final Menu parent; // manager menu
    private Company company;

    public CarMenu(Menu parent, CompanyCarService service) {
        this.parent = parent;
        this.service = service;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public void launch() {
        while (true) {
            displayOptions();
            int choice = MenuUtils.getNumericInput();
            switch (choice) {
                case 0 -> { // BACK TO MANAGER MENU
                    parent.launch();
                    return;
                }
                case 1 -> findAllCompanyCars();
                case 2 -> saveCar();
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void findAllCompanyCars() {
        var cars = service.findAllCarsForCompany(company.getId());
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            System.out.println("Car list:");
            MenuUtils.printNumberedList(cars);
        }
    }

    private void saveCar() {
        System.out.println("Enter the car name:");
        String name = MenuUtils.getStringInput();
        service.saveCar(new Car(name, company.getId()));
        System.out.println("The car was added!");
    }

    @Override
    public void displayOptions() {
        System.out.printf("""
                \n=== '%s' COMPANY ===
                  1. Car list
                  2. Create a car
                  0. Back
                %n""", company.getName());
    }
}
