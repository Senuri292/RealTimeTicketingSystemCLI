package org.example;
import java.util.*;

public class Vendor {
    private String vendorName;
    private String eventName;
    private int vendorId;
    private int vendorContact;
    private ArrayList<Vendor> vendorDetails = new ArrayList<>();

    public Vendor(String vendorName, int vendorId, int vendorContact) {
        this.vendorName = vendorName;
        this.vendorId = vendorId;
        this.vendorContact = vendorContact;
    }
    public String getEventName() {return eventName;}
    public String getVendorName() {return vendorName;}
    public int getVendorId() {return vendorId;}
    public int getVendorContact() {return vendorContact;}
    public ArrayList<Vendor> getVendorDetails() {return vendorDetails;}
    public void setVendorName(String vendorName) {this.vendorName = vendorName;}
    public void setEventName(String eventName) {this.eventName = eventName;}
    public void setVendorId(int vendorId) {this.vendorId = vendorId;}
    public void setVendorContact(int vendorContact) {this.vendorContact = vendorContact;}
    public void setVendorDetails(ArrayList<Vendor> vendorDetails) {this.vendorDetails = vendorDetails;}

//    public void addVendorDetails(Vendor vendorDetails) {
//
//    }

//    public void viewVendors () {
//        //give a return type maybe a list
//    }

//    public void updateVendor(long vendorID) {
//
//        int updateID;
//        System.out.println("------------Update Vendor Details------------");
//
//        System.out.println("Enter Vendor ID: ");
//        updateID = input.nextInt();
//
//
//    }

    //public void deleteVendor(long vendorID) {}


}

