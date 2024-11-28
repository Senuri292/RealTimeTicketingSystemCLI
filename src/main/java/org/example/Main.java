package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        addVendor();
    }
    public static void addVendor(){
        Scanner input = new Scanner(System.in);
        System.out.println("------------Vendor Registration------------");
        System.out.print("Enter Vendor's Name: ");
        String name = input.nextLine();
        System.out.print("Enter Vendor's ID: ");
        int id = input.nextInt();
        System.out.println("Enter Vendor's contact number: ");
        int contact = input.nextInt();
        Vendor vendor = new Vendor(name, id, contact);
        System.out.println(vendor.toString());
    }
}