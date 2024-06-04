package carsharing.menus.customer;

import carsharing.db.DatabaseConnection;
import carsharing.menus.Menu;
import carsharing.menus.MenuUtils;
import carsharing.model.Customer;
import carsharing.service.CustomerService;

import java.util.List;

public class CustomerMenu implements Menu {

    private final CustomerService service;
    private final Menu parent;
    private final RentMenu rentMenu;

    public CustomerMenu(CustomerService service, Menu parentMenu) {
        this.service = service;
        this.parent = parentMenu;
        this.rentMenu = new RentMenu(service, this);
    }

    @Override
    public void displayOptions() {
        System.out.println("\n=== CUSTOMER MENU ===");
    }

    @Override
    public void launch() {
        displayOptions();

        List<Customer> customers = service.findAll();

        if (customers.isEmpty()) {
            System.out.println("No customers");
        } else {
            System.out.println("Choose a customer:");
            MenuUtils.printNumberedList(customers);
            System.out.println("0. Back");
        }

        int choice = MenuUtils.getNumericInput();

        if (choice == 0) {
            parent.launch();
            return;
        }

        try {
            Customer c = customers.get(choice - 1);
            System.out.println("Choice: " + c.getName() + ",  " + c.getId());
            rentMenu.setCustomer(c);
            rentMenu.launch();
            return;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid choice");
        }


//        while (true) {
//
//
//
//        }





//        while (true) {
//            addCustomer();
//        }

//        while (true) {
//
//
////            service.findAll()
//
//            break;
//
//        }


    }



    public void addCustomer() {
        String name = MenuUtils.getStringInput();
        System.out.println("adding customer " + name);
        service.save(new Customer(name));
    }

}
