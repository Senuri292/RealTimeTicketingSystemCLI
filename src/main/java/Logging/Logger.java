package Logging;

import Threads.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Logger {
    private static Runnable customer;
    private static Runnable vendor;

    private int numTickets;
    private int releaseRate;
    private int retrievalRate;
    private int maxTicketCapacity;

    public static void log(int numTickets, int releaseRate, int retrievalRate, int maxTicketCapacity) {
        Scanner input = new Scanner(System.in);

        List<Thread> vendorThreads = new ArrayList<>();
        List<Thread> customerThreads = new ArrayList<>();

        int totalTickets = 0;
        while (totalTickets <= maxTicketCapacity) {
            Thread vendorThread = new Thread(vendor);
            vendorThreads.add(vendorThread);
            vendorThread.start();
            System.out.println("Vendor added " + numTickets+ " Tickets to the ticket pool");
            totalTickets += numTickets;
            try {
                Thread.sleep(releaseRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Vendor interrupted: " + e.getMessage());
                break;
            }
        }
        while (totalTickets > 0) {
            Thread customerThread = new Thread(customer);
            customerThreads.add(customerThread);
            customerThread.start();
            System.out.println("Customer bought " + numTickets + " Tickets from the ticket pool");
            totalTickets -= numTickets;
            try {
                Thread.sleep(retrievalRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Customer interrupted: " + e.getMessage());
            }
        }

        for (Thread thread : customerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
            }
        }

        for (Thread thread : vendorThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
            }
        }

        System.out.println("All operations completed!");
    }
}
