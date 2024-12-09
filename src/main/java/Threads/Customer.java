package Threads;
import Core.TicketPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class Customer implements Runnable {
    private int vendorId;
    private int customerId;
    private String firstName;

    private TicketPool ticketPool;

    public Customer(int customerId, String firstName) {
        this.customerId = customerId;
        this.firstName = firstName;
    }

    public Customer(TicketPool ticketPool) {
        this.ticketPool = ticketPool;  // Initialize it through the constructor
    }

    public int getCustomerId() {return customerId;}
    public void setCustomerID(int customerId) {this.customerId = customerId;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public static void insertCustomer(int customerId, String firstName) throws SQLException {
        String sql = "INSERT INTO \"Customer\" VALUES (?, ?)";

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/RealTimeTicketingSystem", "postgres", "");
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, customerId);
            preparedStatement.setString(2, firstName);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Customer registered into the database successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting Customer: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            String ticket = ticketPool.removeTickets(); // Purchase a ticket
            System.out.println(firstName + " purchased: " + ticket);
            try {
                Thread.sleep(1000); // Simulate time between purchases
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Customer interrupted: " + e.getMessage());
                break;
            }
        }
    }
}

//    public void addCustomer(){
//
//    }
//
//    public void viewCustomers(){
//
//    }
//
//    public void updateCustomer(long customerID){
//
//    }
//
//    public void removeCustomer(long customerID){
//
//    }



