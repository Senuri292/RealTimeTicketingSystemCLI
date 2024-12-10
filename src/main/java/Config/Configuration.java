package Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Logger;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) throws IOException {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }
    public int getTotalTickets() {return totalTickets;}
    public void setTotalTickets(int totalTickets) {this.totalTickets = totalTickets;}
    public int getTicketReleaseRate() {return ticketReleaseRate;}
    public void setTicketReleaseRate(int ticketReleaseRate) {this.ticketReleaseRate = ticketReleaseRate;}
    public int getCustomerRetrievalRate() {return customerRetrievalRate;}
    public void setCustomerRetrievalRate(int customerRetrievalRate) {this.customerRetrievalRate = customerRetrievalRate;}
    public int getMaxTicketCapacity() {return maxTicketCapacity;}
    public void setMaxTicketCapacity(int maxTicketCapacity) {this.maxTicketCapacity = maxTicketCapacity;}

    public static void saveToFile(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) throws IOException {
        Configuration config = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(config);
        try (FileWriter fileWriter = new FileWriter("Configuration.json")) {
            fileWriter.write(json);
            System.out.println("File saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configuration loadFromFile() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileReader fileReader = new FileReader("Configuration.json")) {
            return gson.fromJson(fileReader, Configuration.class);
        }
    }


    // Step 3: Write the JSON to a file


}
