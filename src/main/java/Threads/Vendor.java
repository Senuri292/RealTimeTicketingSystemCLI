package Threads;
import Core.TicketPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;


public class Vendor implements Runnable {
    private String vendorName;
    private int vendorId;
    private int numOfTickets;

    private TicketPool ticketPool;

    public Vendor(String vendorName, int vendorId, int numOfTickets) {
        this.vendorName = vendorName;
        this.vendorId = vendorId;
        this.numOfTickets = numOfTickets;
    }

    public Vendor(TicketPool ticketPool) {
        this.ticketPool = ticketPool;  // Initialize it through the constructor
    }

    public Vendor() {}

    public String getVendorName() {return vendorName;}
    public int getVendorId() {return vendorId;}
    public void setVendorName(String vendorName) {this.vendorName = vendorName;}
    public void setVendorId(int vendorId) {this.vendorId = vendorId;}

    public static void insertVendor(int vendorId, String vendorName, int numOfTickets) throws SQLException {
        String sql = "INSERT INTO \"Vendor\" VALUES (?, ?, ?)";

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/RealTimeTicketingSystem", "postgres", "");
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, vendorId);
            preparedStatement.setString(2, vendorName);
            preparedStatement.setInt(3, numOfTickets);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Vendor registered into the database successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting vendor: " + e.getMessage());
        }

    }

    @Override
    public void run() {
        int ticketCount = 1;
        while (true) {
            String ticket = vendorName + "-Ticket-" + ticketCount++;
            ticketPool.addTickets(ticket); // Add tickets to the pool
            try {
                Thread.sleep(500); // Simulate time taken to release a ticket
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Vendor interrupted: " + e.getMessage());
                break;
            }
        }
    }

}
//    public void addVendorDetails(Vendor vendorDetails) {}
//    public void viewVendors () {
//        //give a return type maybe a list
//    }
//    public void updateVendor(long vendorID) {
//        int updateID;
//        System.out.println("------------Update Vendor Details------------");
//        System.out.println("Enter Vendor ID: ");
//        updateID = input.nextInt();}
//public void deleteVendor(long vendorID) {}



