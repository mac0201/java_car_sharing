package carsharing;

import carsharing.dao.CompanyDao;
import carsharing.db.DatabaseConnection;
import carsharing.model.Company;

import java.util.List;
import java.util.Scanner;

public class CarSharing {

    private final Scanner scanner;
    private final CompanyDao companyDao;
    private final DatabaseConnection dbConnection;

    public CarSharing(String databaseName) {
        this.scanner = new Scanner(System.in);
        this.dbConnection = new DatabaseConnection(databaseName);
        this.companyDao = new CompanyDao(dbConnection);
    }

    public void runApp() {
        mainMenu();
    }

    private void mainMenu() {

        String menu = """
                
                1. Log in as a manager
                0. Exit
                """;

        while (true) {
            System.out.println(menu);
            int option = scanner.nextInt();
            switch (option) {
                case 0 -> {
                    if (dbConnection.getConnection() != null) dbConnection.close();  // close connection
                    System.exit(0);
                }
                case 1 -> {
                    // initialise database connection when manager menu is accessed.
                    // The connection is re-used by DAO to prevent making unnecessary connections on every query
                    dbConnection.connect();
                    managerMenu();
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void managerMenu() {
        String menu = """
                
                1. Company list
                2. Create a company
                0. Back
                """;
        loop: while (true) {
            System.out.println(menu);
            int option = scanner.nextInt();
            switch (option) {
                case 0 -> { break loop; }
                case 1 -> listCompanies();
                case 2 -> addCompany();
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private void listCompanies() {
        System.out.println("Company list:");
        List<Company> companies = companyDao.findAll();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty");
        } else {
            companies.stream()
                    .map(c -> String.format("%d. %s", c.getId(), c.getName()))
                    .forEach(System.out::println);
        }
    }

    private void addCompany() {
        System.out.println("Enter the company name:");
        scanner.nextLine();
        String name = scanner.nextLine();
        Company company = new Company(name);
        companyDao.save(company);
        System.out.println("The company was created!");
    }
}
