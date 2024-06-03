package carsharing;

import carsharing.menus.MainMenu;
import carsharing.menus.Menu;
import carsharing.service.CarSharingService;

public class Main {

    public static void main(String[] args) {
        String dbName = null;
        // get database name from cli
        if (args.length == 2) {
            if (args[0].equals("-databaseFileName")) {
                dbName = args[1];
            }
        }
        Menu menu = new MainMenu(new CarSharingService(dbName));
        menu.launch();
    }
}
