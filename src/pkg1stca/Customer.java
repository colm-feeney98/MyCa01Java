/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pkg1stca;

/**
 *
 * @author colmj
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.System.Logger;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

//https://github.com/colm-feeney98/MyCa01Java/tree/master 

public class Customer {

    //Fields to store customer information. 
    private String firstName;
    private double totalPurchased;
    private int customerClass;
    private int lastPurchase;

    // Getters for firstname
    public String getFirstName() {
        return firstName;
    }

    //Setter for firstname
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    // Getter for totalPurchased

    public double getTotalPurchased() {
        return totalPurchased;
    }
    // Setter for totalPurchased

    public void setTotalPurchased(double totalPurchased) {
        this.totalPurchased = totalPurchased;
    }

    // Getter for customerClass
    public int getCustomerClass() {
        return customerClass;
    }

    // Setter for customerClass
    public void setCustomerClass(int customerClass) {
        this.customerClass = customerClass;
    }
    // Getter for lastPurchase

    public int getLastPurchase() {
        return lastPurchase;
    }

    // Setter for lastPurchase
    public void setLastPurchase(int lastPurchase) {
        this.lastPurchase = lastPurchase;
    }
}
// Variables for reading the input file and tracking customer data

class Program {

    private String line;
    private int lineCount = 0;
    private final List<Customer> customers = new ArrayList<>();
    private BufferedReader reader = null;
    private final List<String> errorMessages = new ArrayList<>();

    // Constructor that initializes the BufferedReader to read from a file
    public Program() throws FileNotFoundException {

        this.reader = new BufferedReader(new FileReader("/Users/colmj/Downloads/customers (1).txt"));

    }

    // Method to process the file and create Customer objects
    public void run() throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader("/Users/colmj/Downloads/customers (1).txt"));
        Customer customer = new Customer();
        String[] names;
        try {
            while ((line = reader.readLine()) != null) {

                names = line.split(" ");
               // System.out.println(line);

                // Switch statement to process each line based on its order
                switch (lineCount) {

                    case 0 -> {
                        if (names[0].matches("^[a-zA-Z\\s]+$|^$")) {
                            customer.setFirstName(line);
                            lineCount++;
                        } else {
                            outputError(line, "First Name");
                        }
                    }

                    // Parse and set total purchased amount
                    case 1 -> {
                        try {
                            customer.setTotalPurchased(Double.parseDouble(line));
                            lineCount++;
                        } catch (NumberFormatException e) {
                            outputError(line, "Total Purchased");
                        }
                    }
                    // Parse and set customer class
                    case 2 -> {
                        customer.setCustomerClass(Integer.parseInt(line));
                        lineCount++;
                    }
                    // Parse and set last purchase year, then add customer to the list
                    case 3 -> {
                        customer.setLastPurchase(Integer.parseInt(line));
                        lineCount = 0;
                        customers.add(customer);
                        customer = new Customer();
                    }
                }
            }
            // Catch and store any IO exceptions that occur while reading the file 
        } catch (IOException e) {
            errorMessages.add(e.getMessage());
        } finally {
            try {
             
                reader.close();
            } catch (IOException e) {
                errorMessages.add(e.getMessage());
            }
        }
        // Write discount information to an output file
        Outputdiscount();
        printErrorMessages();
    }

    // Method to write customer discounts to a file based on specific criteria
    private void Outputdiscount() {
        
            for (Customer c : customers) {
                if (c.getCustomerClass() == 1 && c.getLastPurchase() == 2024) {
                    String value = getDiscountTotal(c.getFirstName(), c.getTotalPurchased(), 30);
                      System.out.println(value);
                   
                } else if (c.getCustomerClass() == 1 && c.getLastPurchase() < 2024) {
                    String value = getDiscountTotal(c.getFirstName(), c.getTotalPurchased(), 20);
                    System.out.println(value);
                   // writer.write(value);
                }
                
                 else if (c.getCustomerClass() == 1 && ( c.getLastPurchase() < (Year.now().getValue () -5 ) )) {
                    String value = getDiscountTotal(c.getFirstName(), c.getTotalPurchased(), 10);
                    System.out.println(value);
                   
               }
                
                else if (c.getCustomerClass() == 2 && c.getLastPurchase() == 2024) {
                    String value = getDiscountTotal(c.getFirstName(), c.getTotalPurchased(), 15);
                      System.out.println(value);
                   
                } else if (c.getCustomerClass() == 2 && c.getLastPurchase() < 2024) {
                    String value = getDiscountTotal(c.getFirstName(), c.getTotalPurchased(), 13);
                    System.out.println(value);
                  
                }
                
                 else if (c.getCustomerClass() == 2 && ( c.getLastPurchase() < (Year.now().getValue () -5 ) )) {
                    String value = getDiscountTotal(c.getFirstName(), c.getTotalPurchased(), 5);
                    System.out.println(value);
}
                    
                 else if (c.getCustomerClass() == 3 && c.getLastPurchase() == 2024) {
                    String value = getDiscountTotal(c.getFirstName(), c.getTotalPurchased(), 3);
                      System.out.println(value);
                   
                } 
                    else if (c.getCustomerClass() == 3 && c.getLastPurchase() < 2024) {
                    String value = getDiscountTotal(c.getFirstName(), c.getTotalPurchased(), 0);
                    System.out.println(value);
                   // writer.write(value);
                }
                
            }
            
            } 
       
    

    // Method to log and display error messages
    private void outputError(String value, String type) {
        String errorMessage = "Invalid File: " + value + " Not Valid for " + type;
        System.out.println(errorMessage);
       // errorMessages.add(errorMessage);
    }
    // Method to calculate and return the discounted total price for a customer

    private String getDiscountTotal(String firstName, double totalPurchased, int discountPercentage) {
        double discountAmount = totalPurchased / 100 * discountPercentage;
        double discountedTotal = totalPurchased - discountAmount;
        return firstName + " Total Price: " + discountedTotal + "\n";
    }

    // Method to print all error messages stored during processing
    private void printErrorMessages() {
        if (!errorMessages.isEmpty()) {
            System.out.println("Errors encountered during processing:");
            for (String error : errorMessages) {
                System.out.println(error);
            }
        }
    }
    // Method to find a specific file in a given directory

    public static String findFile(String directory, String fileName) {
        File dir = new File(directory);
        File[] files = dir.listFiles((d, name) -> name.equals(fileName));
        if (files != null && files.length > 0) {
            return files[0].getAbsolutePath();
        }
        return null;// Return null if file not found
    }
    // Main method to execute the program

    public static void main(String[] args) throws FileNotFoundException {

        try {
            String filePath = "/Users/colmj/Downloads/customers (1).txt";
            // String filePath = findFile( args[0], args[1]); // You can change the directory as needed

            if (filePath != null) {
                Program program = new Program();
                program.run();
            } else {
                System.out.println("File not found.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
