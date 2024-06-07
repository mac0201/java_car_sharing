package carsharing.menu;

import carsharing.menu.misc.MenuOptions;
import carsharing.menu.misc.MenuUtils;
import carsharing.model.Company;
import carsharing.service.CompanyCarService;

import java.util.Optional;

public class ManagerCompanyCarMenu extends CompanyCarMenu implements Menu {

    private final CompanyCarService ccs;

    public ManagerCompanyCarMenu(CompanyCarService ccs) {
        super(ccs, null);
        this.ccs = ccs;
    }

    @Override
    public void launch() {





        while (true) {
            System.out.println("=== MANAGER COMPANY MENU !!===");
            MenuOptions.MANAGER_COMPANY.printOptionsNumbered();
            int option = MenuUtils.scanInteger();
            switch (option) {
                case 0 -> { return; }
                case 1 -> { // list companies
                    Optional<Company> company = companyMenu();
                    if (company.isEmpty()) continue;
                    inner: while (true) {
                        System.out.printf("=== MANAGER CAR MENU  FOR COMPANY %s ===\n", company.get().getName());
                        MenuOptions.MANAGER_CAR.printOptionsNumbered();
                        option = MenuUtils.scanInteger();
                        switch (option) {
                            case 0 -> { break inner; }
                            case 1 -> {
                                carMenu(company.get().getId(), false);
//                                break inner;
                            }
                            case 2 -> addCar(company.get().getId());
                            default -> System.out.println("Invalid option!");
                        }

                    }
                }
                case 2 -> {
                    addCompany();
                    continue;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void addCompany() {
        System.out.println("Enter the company name:");
        String name = MenuUtils.scanString();
        ccs.saveCompany(name);
        System.out.println("Company added: " + name);
    }

    private void addCar(long companyId) {
        System.out.println("Enter the car name:");
        String name = MenuUtils.scanString();
        ccs.saveCar(name, companyId);
        System.out.println("Added car " + name + " for company " + companyId);
    }

    // add company

    // add car



}
