package carsharing.menu;

import carsharing.menu.misc.MenuUtils;
import carsharing.menu.RentalMenu.RentalContext;
import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.model.Model;
import carsharing.service.CompanyCarService;

import java.util.List;
import java.util.Optional;

/**
 * This class represents a menu for users (customers) to interact with companies and cars.
 * Provides functionality to: <br>
 *  - Launch a company menu to select a company
 *  - Launch a car menu of selected company to select a car
 */
public class CompanyCarMenu implements Menu {

    private final CompanyCarService ccs;
    private final RentalContext rentalContext;

    public CompanyCarMenu(CompanyCarService ccs, RentalContext rentalContext) {
        this.ccs = ccs;
        this.rentalContext = rentalContext;
    }

    @Override
    public void launch() {
        Optional<Company> company = companyMenu(); // launch company menu and pick company
        if (company.isEmpty()) return; // no companies found

        Optional<Car> car = carMenu(company.get().getId(), true); // launch car menu and pick car
        if (car.isEmpty()) return; // no cars

        if (rentalContext == null) System.err.println("Rental context is null");
        else rentalContext.setCar(car.get()); // set car so it can be accessed in RentalMenu
    }

    /**
     * Launches the company menu - retrieves and prints a list of companies and prompts user for a choice.
     * The selected company is returned as {@code Optional} (empty if no cars found)
     */
    protected Optional<Company> companyMenu() {
        System.out.println("\n=== COMPANY MENU ===");
        List<Company> companies = ccs.findAllCompanies();

        if (companies.isEmpty()) {
            System.out.println("The company list is empty");
            return Optional.empty(); // returns to previous menu
        }

        Optional<Integer> choice = promptForChoice(companies);
        return choice.map(companies::get);
    }

    /**
    * Launches the car menu - retrieves and prints a list of cars that belong to provided company. If {@code isCustomer},
    * then booked cars are skipped, and user is prompted for car choice. The selected car is returned as {@code Optional}
     * (empty for manager or if no cars found).
    * */
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
            Optional<Integer> choice = promptForChoice(cars);
            if (choice.isPresent()) return Optional.of(cars.get(choice.get()));
        }
        return Optional.empty();
    }

    // Prints the list of models numbered and prompts user for choice until valid
    private Optional<Integer> promptForChoice(List<? extends Model> list) {
        MenuUtils.printModelListNumberedWithReturn(list);
        while (true) {
            try {
                int choice = MenuUtils.scanInteger();
                if (choice == 0) return Optional.empty();
                return Optional.of(choice - 1);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid choice!");
            }
        }
    }
}
