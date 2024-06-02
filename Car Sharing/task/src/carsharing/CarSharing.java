package carsharing;

import carsharing.dao.CompanyDao;
import carsharing.model.Company;

import java.awt.*;
import java.util.Scanner;

public class CarSharing {

    private final Scanner scanner;
    private final CompanyDao companyDao;

    public CarSharing(String databaseName) {
        this.scanner = new Scanner(System.in);
        this.companyDao = new CompanyDao(databaseName);
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
                case 0 -> System.exit(0); // close connection
                case 1 -> managerMenu(); // establish db connection if not active
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
                case 0 -> { break loop; } // close db connection
                case 1 -> { listCompanies(); }
                case 2 -> { addCompany(); }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private void listCompanies() {
        System.out.println("Company list:");
        System.out.println(companyDao.findAll());
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
