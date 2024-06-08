package carsharing.menu;

import carsharing.menu.misc.MenuOptions;
import carsharing.menu.misc.MenuUtils;
import carsharing.model.Car;
import carsharing.service.CompanyCarService;
import carsharing.service.CustomerService;

public class RentalMenu implements Menu {

    private final CustomerService cs;
    private final CompanyCarService ccs;
    private final long customerId;

    public RentalMenu(CustomerService cs, CompanyCarService ccs, long customerId) {
        this.cs = cs;
        this.ccs = ccs;
        this.customerId = customerId;
    }

    @Override
    public void launch() {

        while (true) {
            System.out.printf("\n=== RENTAL MENU FOR CUSTOMER %s ===\n", customerId);
            MenuOptions.RENT.printOptionsNumbered();
            int choice = MenuUtils.scanInteger();

            if (choice == 0) return;

            boolean customerRenting = isRenting(); // check if customer is renting once, when menu is launched

            if (choice == 1) { // rent a car
                if (customerRenting) {
                    System.out.println("You've already rented a car!");
                    continue;
                }
                var rentalContext = new RentalContext(); // create a rental context instance which will contain chosen car
                var companyMenu = new CompanyCarMenu(ccs, rentalContext); // create company/car menu and pass context
                companyMenu.launch();
                if (rentalContext.getCar() != null) { // if context contains chosen car
                    cs.rentCar(customerId, rentalContext.getCar().getId()); // rent
                    System.out.printf("You rented '%s'\n", rentalContext.getCar().getName());
                }
            }
            else if (choice == 2) { // return car
                if (!customerRenting) System.out.println("You didn't rent a car!");
                else returnCar();
            }
            else if (choice == 3) { // get car info
                if (!customerRenting)  System.out.println("You didn't rent a car!");
                else rentedCarData();
            }
            else System.out.println("Invalid choice!");
        }
    }

    // check if customer is renting car
    private boolean isRenting() {
        return cs.isRentingCar(customerId);
    }

    // return rented car
    private void returnCar() {
        cs.returnCar(customerId);
        System.out.println("You've returned a rented car!");
    }

    private void rentedCarData() {
        var data = cs.findRentedCarData(customerId);
        System.out.printf("""
                        Your rented car:
                        %s
                        Company:
                        %s
                        %n""",
                data.getOrDefault("car", "-- UNKNOWN --"),
                data.getOrDefault("company", "-- UNKNOWN --"));
    }

    public static class RentalContext {
        private Car car;

        public void setCar(Car car) {
            this.car = car;
        }

        public Car getCar() {
            return car;
        }
    }
}