package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Vendor {
    private String vendorName;
    private int vendorId;
    private int vendorContact;
    private int releaseInterval;
    private ArrayList<Vendor> vendorDetails = new ArrayList<>();

    public Vendor(String vendorName, int vendorId, int vendorContact, int releaseInterval) {
        this.vendorName = vendorName;
        this.vendorId = vendorId;
        this.vendorContact = vendorContact;
        this.releaseInterval = releaseInterval;
    }
    public String getVendorName() {return vendorName;}
    public int getVendorId() {return vendorId;}
    public int getVendorContact() {return vendorContact;}
    public ArrayList<Vendor> getVendorDetails() {return vendorDetails;}
    public int getReleaseInterval() {return releaseInterval;}

    public void setVendorName(String vendorName) {this.vendorName = vendorName;}
    public void setVendorId(int vendorId) {this.vendorId = vendorId;}
    public void setVendorContact(int vendorContact) {this.vendorContact = vendorContact;}
    public void setVendorDetails(ArrayList<Vendor> vendorDetails) {this.vendorDetails = vendorDetails;}
    public void setReleaseInterval(int releaseInterval) {this.releaseInterval = releaseInterval;}

    public static void insertVendor(int vendorId, String vendorName, int vendorContact, int releaseInterval) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC driver not found.");
            return;
        }
        String sql = "INSERT INTO \"Vendor\" VALUES (?, ?, ?, ?)";

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/RealTimeTicketingSystem", "postgres", "");
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set the values for the placeholders
            preparedStatement.setInt(1, vendorId);         // Set vendorId
            preparedStatement.setString(2, vendorName);    // Set vendorName
            preparedStatement.setInt(3, vendorContact);    // Set vendorContact
            preparedStatement.setInt(4, releaseInterval);  // Set releaseInterval

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();

            // Check if the insert was successful
            if (rowsInserted > 0) {
                System.out.println("Vendor registered into the database successfully!");
            }
        } catch (SQLException e) {
            // Print the error if an exception occurs
            System.err.println("Error inserting vendor: " + e.getMessage());
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
