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
        List<Customer> customers = cs.findAll(); // find all customers
        if (customers.isEmpty()) {
            System.out.println("\nThe customer list is empty!");
            return;
        }

        // print the list of customers numbered with return option
        MenuUtils.printModelListNumberedWithReturn(customers);

        while (true) {
            try {
                int choice = MenuUtils.scanInteger(); // get input
                if (choice == 0) return; // back to previous menu
                long customerId = customers.get(choice - 1).getId(); // get id of chosen customer
                var rentMenu = new RentalMenu(cs, ccs, customerId); // initialise rental menu and pass customer id
                rentMenu.launch(); // launch rental menu
                return;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid choice!");
            }
        }
    }

    public void addCustomer() {
        System.out.println("Enter the customer name:");
        String name = MenuUtils.scanString();
        if (MenuUtils.validModelName(name)) {
            cs.save(name);
            System.out.println("Customer added: " + name);
        }
    }
}
