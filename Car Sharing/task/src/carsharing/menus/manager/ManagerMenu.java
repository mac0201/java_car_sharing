package carsharing.menus.manager;

import carsharing.menus.CarMenu;
import carsharing.menus.Menu;
import carsharing.menus.MenuUtils;
import carsharing.model.Company;
import carsharing.service.CarSharingService;

import java.util.List;

/**
 * Manager menu can:
 *  - save company
 *  - list all companies
 *  - access car menu
 */
public class ManagerMenu implements Menu {

    private final Menu parent; // main menu
    private final Menu companyMenu;
    private final CarSharingService service;

    public ManagerMenu(CarSharingService service, Menu parent) {
        this.parent = parent;
        this.service = service;
        this.companyMenu = new CarMenu(this, service);
    }

    @Override
    public void launch() {
        while (true) {
            displayOptions();
            int choice = MenuUtils.getNumericInput();
            switch (choice) {
                case 0 -> { // BACK TO MAIN MENU
                    parent.launch();
                    return;
                }
                case 1 -> { // company list
                    var companies = getAndListCompanies();
                    if (!companies.isEmpty()) {
                        choice = MenuUtils.getNumericInput();
                        try {
                            Company x = companies.get(choice - 1);
                            ((CarMenu) companyMenu).setCompany(x);
                            companyMenu.launch();
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Invalid choice!");
                        }
                    }
                }
                case 2 -> { // create company
                    saveCompany();
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private List<Company> getAndListCompanies() {
        var companies = service.findAllCompanies();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty");
        } else {
            int counter = 1;
            System.out.println("Choose the company:");
            for (var c : companies) {
                System.out.printf("%d. %s\n", counter++, c.getName());
            }
            System.out.println("0. Back");
        }
        return companies;
    }

    private void saveCompany() {
        System.out.println("Enter the company name");
        String name = MenuUtils.getStringInput();
        service.saveCompany(new Company(name));
        System.out.println("The company was created!");
    }

    public void displayOptions() {
        System.out.println("""
                \n=== MANAGER MENU ===
                  1. Company list
                  2. Create a company
                  0. Back
                """);
    }
}
