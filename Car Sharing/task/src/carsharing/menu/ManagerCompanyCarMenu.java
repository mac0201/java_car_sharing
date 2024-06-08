package carsharing.menu;

import carsharing.menu.misc.MenuOptions;
import carsharing.menu.misc.MenuUtils;
import carsharing.model.Company;
import carsharing.service.CompanyCarService;

import java.util.Optional;

/**
 * This class extends the {@link CompanyCarMenu} class and provides extra functionality for adding companies and cars.
 */
public class ManagerCompanyCarMenu extends CompanyCarMenu {

    private final CompanyCarService ccs;

    public ManagerCompanyCarMenu(CompanyCarService ccs) {
        super(ccs, null);
        this.ccs = ccs;
    }

    @Override
    public void launch() {
        while (true) {
            System.out.println("\n=== MANAGER COMPANY MENU ===");
            MenuOptions.MANAGER_COMPANY.printOptionsNumbered();
            int option = MenuUtils.scanInteger();
            switch (option) {
                case 0 -> { return; } // return to previous menu
                case 1 -> { // list companies
                    Optional<Company> company = companyMenu();
                    if (company.isEmpty()) continue;
                    inner: while (true) {
                        System.out.printf("\n=== MANAGER CAR MENU FOR COMPANY %s ===\n", company.get().getName().toUpperCase());
                        MenuOptions.MANAGER_CAR.printOptionsNumbered();
                        option = MenuUtils.scanInteger();
                        switch (option) {
                            case 0 -> { break inner; } // return to company menu
                            case 1 -> carMenu(company.get().getId(), false); // list cars, pass false to skip car selection
                            case 2 -> addCar(company.get().getId()); // add car
                            default -> System.out.println("\nInvalid option!");
                        }
                    }
                }
                case 2 -> addCompany(); // add company
                default -> System.out.println("\nInvalid choice!");
            }
        }
    }

    private void addCompany() {
        System.out.println("Enter the company name:");
        String name = MenuUtils.scanString();
        if (MenuUtils.validModelName(name)) {
            ccs.saveCompany(name);
            System.out.println("Company added: " + name);
        }
    }

    private void addCar(long companyId) {
        System.out.println("Enter the car name:");
        String name = MenuUtils.scanString();
        if (MenuUtils.validModelName(name)) {
            ccs.saveCar(name, companyId);
            System.out.println("Added car " + name + " for company " + companyId);
        }
    }
}
