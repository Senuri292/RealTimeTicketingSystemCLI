package org.example;

public class Customer {
    private String firstName;
    private String lastName;
    private long customerID;

    public Customer(String firstName, String lastName, long customerID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerID = customerID;
    }
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public long getCustomerID() {return customerID;}
    public void setCustomerID(long customerID) {this.customerID = customerID;}

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
}

