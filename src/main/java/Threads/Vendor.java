package Threads;

import Core.TicketPool;
import java.sql.*;

public class Vendor implements Runnable {
    private String vendorName;
    private static int vendorId;
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
    public static int getVendorId() {return vendorId;}
    public void setVendorName(String vendorName) {this.vendorName = vendorName;}
    public void setVendorId(int vendorId) {this.vendorId = vendorId;}
    public int getNumOfTickets() {return numOfTickets;}
    public void setNumOfTickets(int numOfTickets) {this.numOfTickets = numOfTickets;}
    // inserting vendor details into database
    public static void insertVendor(int vendorId, String vendorName, int numOfTickets) throws SQLException {
        String sql = "INSERT INTO \"Vendor\" VALUES (?, ?, ?)";
        // link connection
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
        // letting user know which details were saved
        System.out.println("Vendors credentials: "+ vendorId + "-" + vendorName);
    }
    // overriding ru method from runnable
    @Override
    public void run() {
        int ticketCount = 1;
        while (true) {
            ticketPool.addTickets(numOfTickets); // Add tickets to the pool
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




