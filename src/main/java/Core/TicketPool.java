package Core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TicketPool implements TicketOperation{
    private int totalTickets = 0;
    private int maxTicketCapacity;
    private final static List<String> tickets = Collections.synchronizedList(new LinkedList<>());

    public TicketPool(int totalTickets, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getTotalTickets() {return totalTickets;}
    public void setTotalTickets(int totalTickets) {this.totalTickets = totalTickets;}
    public void setMaxTicketCapacity(int maxTicketCapacity) {this.maxTicketCapacity = maxTicketCapacity;}
    public int getMaxTicketCapacity() {return maxTicketCapacity;}
    public List<String> getTickets() {return tickets;}

    @Override
    public void addTickets(String ticket) {
        while (tickets.size() == maxTicketCapacity) {
            try {
                System.out.println("TicketPool is full. Vendor waiting...");
                wait(); // Wait if the pool is full
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Vendor interrupted: " + e.getMessage());
            }
        }
        tickets.add(ticket);
        System.out.println("Ticket added: " + ticket + " | Total Tickets: " + tickets.size());
        notifyAll(); // Notify consumers that a ticket is available
    }

    @Override
    public String removeTickets() {
        while (tickets.isEmpty()) {
            try {
                System.out.println("TicketPool is empty. Customer waiting...");
                wait(); // Wait if no tickets are available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Customer interrupted: " + e.getMessage());
            }
        }
        String ticket = tickets.remove(tickets.size() - 1);
        System.out.println("Ticket removed: " + ticket + " | Tickets left: " + tickets.size());
        notifyAll(); // Notify producers that there is space in the pool
        return ticket;
    }

//    public void addTicket() {}
//
//    public void removeTicket() {}
//
//    public void viewRemainingTickets() {}
}


