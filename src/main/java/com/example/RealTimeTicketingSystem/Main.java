package com.example.RealTimeTicketingSystem;

import Config.Configuration;
import Core.TicketPool;
import Threads.Customer;
import Threads.Vendor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Logging.Logger;


public class Main {
    public static void main(String[] args) throws SQLException, IOException {

    }


    public void start() throws IOException {
        Configuration.loadFromFile();
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to RealTimeTicketingSystem CLI!");

        System.out.print("Enter Total Number of Tickets: ");
        int numTickets = input.nextInt();
        System.out.print("Enter Ticket Release Rate: ");
        int releaseRate = input.nextInt();
        System.out.print("Enter Customer Retrieval Rate: ");
        int retrievalRate = input.nextInt();
        System.out.print("Enter Maximum Ticket Capacity: ");
        int maxTicketCapacity = input.nextInt();


        Configuration.saveToFile(numTickets, releaseRate, retrievalRate, maxTicketCapacity);

        Logger.log(numTickets, releaseRate, retrievalRate, maxTicketCapacity);

    }
}