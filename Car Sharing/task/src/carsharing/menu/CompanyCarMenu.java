package carsharing.menu;


import carsharing.menu.misc.MenuUtils;
import carsharing.menu.RentalMenu.RentalContext;
import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.service.CompanyCarService;

import java.util.List;
import java.util.Optional;

public class CompanyCarMenu implements Menu {

    private final CompanyCarService ccs;
    private final RentalContext rentalContext;

    public CompanyCarMenu(CompanyCarService ccs) {
        this.ccs = ccs;
        this.rentalContext = null;
    }

    public CompanyCarMenu(CompanyCarService ccs, RentalContext rentalContext) {
        this.ccs = ccs;
        this.rentalContext = rentalContext;
    }

    @Override
    public void launch() {

        // pick company
        Optional<Company> company = companyMenu();

        if (company.isEmpty()) return;

        Optional<Car> car = carMenu(company.get().getId(), true);

        if (car.isEmpty()) return;

        if (rentalContext == null) {
            System.out.println("Rental context is null");
            return;
        }

        rentalContext.setCar(car.get());



        // run get car with true



    }

    protected Optional<Company> companyMenu() {

        // list companies
        System.out.println("\n=== COMPANY MENU ===");

        List<Company> companies = ccs.findAllCompanies();

        if (companies.isEmpty()) {
            System.out.println("The company list is empty");
            return Optional.empty(); // returns to previous menu
        }

        MenuUtils.printModelListNumberedWithReturn(companies);

        while (true) {
            try {
                int choice = MenuUtils.scanInteger();
                if (choice == 0) return Optional.empty();
                return Optional.of(companies.get(choice - 1));
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid choice!");
            }
        }
    }

    protected Optional<Car> carMenu(long companyId, boolean isCustomer) {


        List<Car> cars = ccs.findAllCarsForCompany(companyId, isCustomer);

        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
            return Optional.empty();
        }


        if (!isCustomer) {
            MenuUtils.printModelListNumbered(cars);
        }
        else {
            System.out.println("\nSelect car:");
            MenuUtils.printModelListNumberedWithReturn(cars);
            // get car for booking
            while (true) {
                try {
                    int choice = MenuUtils.scanInteger();
                    if (choice == 0) return Optional.empty();
//                    if (rentalContext == null) {
//                        System.err.println("Rental context is null");
//                        return Optional.empty();
//                    }
//                    var car = cars.get(choice - 1);

                    return Optional.of(cars.get(choice - 1));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Invalid choice!");
                }
            }
        }
        return Optional.empty();
    }

    public RentalContext getRentalContext() {
        return rentalContext;
    }
}
