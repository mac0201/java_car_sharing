package carsharing;

import carsharing.db.DatabaseJDBC;

public class Main {

    public static void main(String[] args) {

        String dbName = null;
        // get database name from cli
        if (args.length == 2) {
            if (args[0].equals("-databaseFileName")) {
                dbName = args[1];
            }
        }

        DatabaseJDBC db = new DatabaseJDBC(dbName);
        db.init();

    }
}
