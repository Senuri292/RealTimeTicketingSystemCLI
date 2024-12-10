package Core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TicketPool implements TicketOperation {
    private int totalTickets;
    private int maxTicketCapacity;
    private final List<String> tickets = Collections.synchronizedList(new LinkedList<>());

    public TicketPool(int totalTickets, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public List<String> getTickets() {
        return tickets;
    }

    @Override
    public synchronized void addTickets(int ticket) {
        while (totalTickets == maxTicketCapacity) {
            try {
                System.out.println("TicketPool is full. Vendor waiting...");
                wait(); // Wait if the pool is full
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Vendor interrupted: " + e.getMessage());
            }
        }
        totalTickets += ticket;
        System.out.println("Ticket added: " + ticket + " | Total Tickets: " + totalTickets);
    }

    @Override
    public synchronized int removeTickets(int numOfTickets) {
        while (totalTickets == 0) {
            try {
                System.out.println("TicketPool is empty. Customer waiting...");
                wait(); // Wait if no tickets are available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Customer interrupted: " + e.getMessage());
            }
        }
        totalTickets -= numOfTickets;
        if (totalTickets <= 0) {
            System.out.println("Cannot complete order! missing tickets.....");
            totalTickets += numOfTickets;
        } else {
            System.out.println("Ticket removed: " + numOfTickets + " | Tickets left: " + totalTickets);
        }
        return numOfTickets;
    }
}

