package carsharing.menus;

import carsharing.service.CarSharingService;

public class MainMenu implements Menu {

    private final Menu managerMenu;

    private final CarSharingService service;

    public MainMenu(CarSharingService service) {
        this.managerMenu = new ManagerMenu(service, this);
        this.service = service;
    }

    @Override
    public void launch() {
        while (true) {
            displayOptions();
            int choice = MenuUtils.getNumericInput();
            switch (choice) {
                case 0 -> { // exit app
                    service.closeAllConnections();
                    MenuUtils.closeScanner();
                    System.exit(0);
                }
                case 1 -> {
                    managerMenu.launch();
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    @Override
    public void displayOptions() {
        System.out.println("""
                \n=== MAIN MENU ===
                  1. Log in as a manager
                  0. Exit
                """);
    }
}
