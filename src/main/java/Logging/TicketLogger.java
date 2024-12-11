package Logging;

import Core.TicketPool;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
// logger class handles logging
public class TicketLogger {
    private static final Logger LOGGER = Logger.getLogger(TicketLogger.class.getName());

    private static Runnable customer;
    private static Runnable vendor;

    private int numTickets;
    private int releaseRate;

    public TicketLogger(int numTickets, int releaseRate, int retrievalRate, int maxTicketCapacity) {
        this.numTickets = numTickets;
        this.releaseRate = releaseRate;
        this.retrievalRate = retrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }
    public void setNumTickets(int numTickets) {this.numTickets = numTickets;}
    public void setReleaseRate(int releaseRate) {this.releaseRate = releaseRate;}
    public void setRetrievalRate(int retrievalRate) {this.retrievalRate = retrievalRate;}
    public void setMaxTicketCapacity(int maxTicketCapacity) {this.maxTicketCapacity = maxTicketCapacity;}
    public int getReleaseRate() {return releaseRate;}
    public int getNumTickets() {return numTickets;}
    public int getRetrievalRate() {return retrievalRate;}
    public int getMaxTicketCapacity() {return maxTicketCapacity;}

    private int retrievalRate;
    private int maxTicketCapacity;

    public static TicketPool ticketPool;
    // making total tickets an atomic integer for easy use
    private static AtomicInteger totalTickets;

    public TicketLogger() {
        // Initialize ticketPool in the constructor
        this.ticketPool = new TicketPool(numTickets, maxTicketCapacity);
    }
    // log method to handle logs
    public static void log(int numTickets, int releaseRate, int retrievalRate, int maxTicketCapacity) {
        Scanner input = new Scanner(System.in);

        ticketPool = new TicketPool(numTickets, maxTicketCapacity);

        List<Thread> vendorThreads = new ArrayList<>();
        List<Thread> customerThreads = new ArrayList<>();

        totalTickets = new AtomicInteger(numTickets);
        // connecting to database and getting vendors details
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/RealTimeTicketingSystem", "postgres", "")) {
            String queryVendor = "SELECT \"vendorId\", \"vendorName\", \"numOfTickets\" FROM \"Vendor\"";
            PreparedStatement statementVendor = connection.prepareStatement(queryVendor);
            ResultSet resultSet = statementVendor.executeQuery();
            // put data into variables
            while (resultSet.next()) {
                String vendorId = resultSet.getString("vendorId");
                String vendorName = resultSet.getString("vendorName");
                int ticketsToSell = resultSet.getInt("numOfTickets");
                // vendor thread start
                Thread vendorThread = new Thread(() -> {
                    while (totalTickets.get() < maxTicketCapacity) { // Use ticketsToSell for each vendor
                        ticketPool.addTickets(ticketsToSell);
                        totalTickets.getAndAdd(ticketsToSell);
                        try {
                            Thread.sleep(releaseRate); // Simulate release rate delay
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            LOGGER.info("Vendor interrupted: " + e.getMessage());
                        }
                    }
                });
                vendorThreads.add(vendorThread);
            }
            // connecting to database and getting customers details
            String queryCustomer = "SELECT \"customerId\", \"firstName\", \"numOfTickets\" FROM \"Customer\"";
            PreparedStatement statementCustomer = connection.prepareStatement(queryCustomer);
            ResultSet resultSetCustomer = statementCustomer.executeQuery();
            // put data into variables
            while (resultSetCustomer.next()) {
                String customerId = resultSetCustomer.getString("customerId");
                String customerName = resultSetCustomer.getString("firstName");
                int ticketsToBuy = resultSetCustomer.getInt("numOfTickets");
                // customer thread start
                Thread customerThread = new Thread(() -> {
                    while (totalTickets.get() > 0) { // Use ticketsToBuy for each customer
                        int ticket = ticketPool.removeTickets(ticketsToBuy);
                        totalTickets.addAndGet(-ticketsToBuy);
                        try {
                            Thread.sleep(retrievalRate); // Simulate retrieval rate delay
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            LOGGER.info("Customer interrupted: " + e.getMessage());
                        }
                    }
                });
                customerThreads.add(customerThread);
            }
        } catch (Exception e) {
            LOGGER.info("Error fetching customers from database: {0}" + e.getMessage());
            return;
        }

        //Start all vendor threads
        for (Thread vendorThread : vendorThreads) {
            vendorThread.start();
        }
        // Start all customer threads
        for (Thread customerThread : customerThreads) {
            customerThread.start();
        }
        // Wait for all vendor threads to finish
        for (Thread vendorThread : vendorThreads) {
            try {
                vendorThread.join();
            } catch (InterruptedException e) {
                LOGGER.info("Thread interrupted: {0}" + e.getMessage());
            }
        }

        // Wait for all customer threads to finish
        for (Thread customerThread : customerThreads) {
            try {
                customerThread.join();
            } catch (InterruptedException e) {
                LOGGER.info("Thread interrupted: {0}" + e.getMessage());
            }
        }

        LOGGER.info("Ticketing Simulation completed.");
    }
}
