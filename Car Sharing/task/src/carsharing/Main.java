package carsharing;

public class Main {

    public static void main(String[] args) {
        String dbName = null;
        // get database name from cli
        if (args.length == 2) {
            if (args[0].equals("-databaseFileName")) {
                dbName = args[1];
            }
        }

        CarSharing app = new CarSharing(dbName);
        app.launch();



//        var databaseConn = new DatabaseConnection(dbName);
//        var carService = new CarSharingService(dbName);
//        var customerService = new CustomerService();
//
//        Menu menu = new MainMenu(new CompanyCarService(dbName));
//        menu.launch();
    }
}
