package com.example.RealTimeTicketingSystem;

import Config.Configuration;
import Logging.TicketLogger;
import Threads.Customer;
import Threads.Vendor;
import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        Scanner input = new Scanner(System.in);

        System.out.println("---------------Welcome to the Ticketing System CLI--------------");
        int choice = 0;
        do {
            // Display the menu
            System.out.println("What do you want to do?: ");
            System.out.println("1. Register as a vendor");
            System.out.println("2. Register as a customer");
            System.out.println("3. Start");
            System.out.println("4. Exit");

            // Get user's choice
            try {
                System.out.print("Enter your choice: ");
                choice = input.nextInt();
            }catch (InputMismatchException e){
                input.nextLine();
            }
            // Perform action based on the choice
            switch (choice) {
                case 1:
                    System.out.println("------------Vendor Registration------------");
                    addVendor();
                    break;

                case 2:
                    System.out.println("------------Customer Registration------------");
                    addCustomer();
                    break;

                case 3:
                    System.out.println("------------Add Tickets------------");
                    start();
                    break;

                case 4:
                    break;

                default:
                    System.out.println("Invalid choice! Please choose from the menu.");
            }
        } while (choice != 4);
        System.out.println("Exiting the Program! Thank you and have a nice day!!!............................");

    }
    public static void addVendor() throws SQLException {
        Scanner input = new Scanner(System.in);
        int vendorId = 0;
        String name = "";
        int numOfTicketsVendor = 0;

        try {
            System.out.print("Enter Vendor ID: ");
            vendorId = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Vendor ID! Please enter a integer Vendor ID.");
            input.nextLine();
        }

        try {
            System.out.print("Enter Vendor's Name: ");
            name = input.next();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Vendor's Name! Please enter a integer Vendor ID.");
            input.nextLine();
        }
        try {
            System.out.print("Enter the number of tickets vendor have: ");
            numOfTicketsVendor = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a integer value.");
            input.nextLine();
        }

        Vendor vendor = new Vendor(name, vendorId, numOfTicketsVendor);
        try {
            Vendor.insertVendor(vendorId, name, numOfTicketsVendor);

        } catch (SQLException e) {
            // Print the error if an exception occurs
            System.err.println("Error inserting vendor: " + e.getMessage());
        }
    }

    public static void addCustomer() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the Customer ID: ");
        int customerId = input.nextInt();
        System.out.print("Enter Customer's First Name: ");
        String fName = input.next();
        System.out.print("Enter number of tickets customer wants to buy: ");
        int numOfTicketsCustomer = input.nextInt();

        Customer customer = new Customer(customerId, fName, numOfTicketsCustomer);
        try {
            Customer.insertCustomer(customerId, fName, numOfTicketsCustomer);

        } catch (SQLException e) {
            // Print the error if an exception occurs
            System.err.println("Error inserting Customer: " + e.getMessage());
        }
    }

    public static void start() throws IOException {
        Configuration.loadFromFile();
        Scanner input = new Scanner(System.in);
        int numTickets = 0;
        int releaseRate = 0;
        int retrievalRate = 0;
        int maxTicketCapacity = 0;

        System.out.println("Welcome to RealTimeTicketingSystem CLI!");
        try {
            System.out.print("Enter Total Number of Tickets: ");
            numTickets = input.nextInt();
        }catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a integer value.");
            input.nextLine();
        }
        try {
            System.out.print("Enter Ticket Release Rate: ");
            releaseRate = input.nextInt();
        }catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a integer value.");
            input.nextLine();
        }
        try {
            System.out.print("Enter Customer Retrieval Rate: ");
            retrievalRate = input.nextInt();
        }catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a integer value.");
            input.nextLine();
        }
        try {
            System.out.print("Enter Maximum Ticket Capacity: ");
            maxTicketCapacity = input.nextInt();
        }catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a integer value.");
        }

        Configuration.saveToFile(numTickets, releaseRate, retrievalRate, maxTicketCapacity);
        TicketLogger.log(numTickets, releaseRate, retrievalRate, maxTicketCapacity);

    }
}