package carsharing.menu;


import carsharing.menu.misc.MenuUtils;
import carsharing.model.Customer;
import carsharing.service.CompanyCarService;
import carsharing.service.CustomerService;

import java.util.List;


public class CustomerMenu implements Menu {

    private final CustomerService cs;
    private final CompanyCarService ccs;

    public CustomerMenu(CustomerService cs, CompanyCarService ccs) {
        this.cs = cs;
        this.ccs = ccs;
    }

    @Override
    public void launch() {

        System.out.println("\n=== CUSTOMER MENU ===");

        List<Customer> customers = cs.findAll();

        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
            return;
        }

        MenuUtils.printModelListNumberedWithReturn(customers);

        while (true) {
            try {
                int choice = MenuUtils.scanInteger();

                if (choice == 0) return; //! back to previous menu

                long customerId = customers.get(choice - 1).getId();

                var rentMenu = new RentalMenu(cs, ccs, customerId);

                rentMenu.launch();
                return;

            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid choice!");
            }
        }
    }

    public void addCustomer() {
        System.out.println("Enter the customer name:");
        String name = MenuUtils.scanString();
        cs.save(name);
    }
}
