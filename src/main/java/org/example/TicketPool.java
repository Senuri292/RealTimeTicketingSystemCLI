package org.example;

public class TicketPool {
    private int totalTickets;
    private String ticketReleaseDate;
    private String customerRetrievalDate;
    private int maxTicketCapacity;

    public TicketPool(int totalTickets, String ticketReleaseDate, String customerRetrievalDate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseDate = ticketReleaseDate;
        this.customerRetrievalDate = customerRetrievalDate;
        this.maxTicketCapacity = maxTicketCapacity;
    }
    public int getTotalTickets() {return totalTickets;}
    public void setTotalTickets(int totalTickets) {this.totalTickets = totalTickets;}
    public String getTicketReleaseDate() {return ticketReleaseDate;}
    public void setTicketReleaseDate(String ticketReleaseDate) {this.ticketReleaseDate = ticketReleaseDate;}
    public String getCustomerRetrievalDate() {return customerRetrievalDate;}
    public void setCustomerRetrievalDate(String customerRetrievalDate) {this.customerRetrievalDate = customerRetrievalDate;}
    public int getMaxTicketCapacity() {return maxTicketCapacity;}
    public void setMaxTicketCapacity(int maxTicketCapacity) {this.maxTicketCapacity = maxTicketCapacity;}

//    public void addTicket() {}
//
//    public void removeTicket() {}
//
//    public void viewRemainingTickets() {}
}


