package carsharing.menu;

import carsharing.menu.misc.MenuOptions;
import carsharing.menu.misc.MenuUtils;
//import carsharing.menu.misc.RentalContext;
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

        // TODO
        /*
		- if customer wants to rent a car while already booked, print:
			"You've already rented a car!"

		- when displaying cars during booking, filter the ones that are already booked (company car menu for customer)
	        - SELECT * FROM cars WHERE NOT EXISTS (SELECT * FROM users WHERE users.rented_car_id = cars.id);
            - SELECT cars.* FROM cars LEFT JOIN users ON users.rented_car_id = cars.id WHERE users.rented_car_id IS NULL;

		- make db connection class singleton
        * */


        var rentalContext = new RentalContext();
        while (true) {
            System.out.printf("=== RENTAL MENU FOR CUSTOMER %s ===\n", customerId);
            MenuOptions.RENT.printOptionsNumbered();
            int choice = MenuUtils.scanInteger();
            switch (choice) {
                case 0 -> { return; }
                case 1 -> {
                    var companyMenu = new CompanyCarMenu(ccs, rentalContext);
                    companyMenu.launch();
                    if (rentalContext.getCarId() != null) { // ! improve
                        cs.rentCar(customerId, rentalContext.getCarId());
                        System.out.printf("You rented '%s'\n", rentalContext.getCar().getName());
                    }
                }
                case 2 -> returnCar();
                case 3 -> rentedCarInfo();
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private boolean isRenting() {
        return cs.isRentingCar(customerId);
    }

    private void returnCar() {
        if (!isRenting()) {
            System.out.println("You didn't rent a car!");
            return;
        }
        cs.returnCar(customerId);
    }

    private void rentedCarInfo() {
        if (!isRenting()) {
            System.out.println("You didn't rent a car!");
            return;
        }
        String info = cs.getRentedCarInfo(customerId);
        System.out.println(info);
    }

    public static class RentalContext {
        private Long customerId;
        private Long carId;
        private Car car;

        public void setCar(Car car) {
            this.car = car;
            this.carId = car.getId();
        }

        public Car getCar() {
            return car;
        }

        public void setCustomerId(Long customerId) {
            this.customerId = customerId;
        }

        public void setCarId(Long carId) {
            this.carId = carId;
        }

        public Long getCarId() {
            return carId;
        }

        public Long getCustomerId() {
            return customerId;
        }
    }

}