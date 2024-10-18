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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
 
public class Customer {
    private String firstName;
    private double totalPurchased;
    private int customerClass;
    private int lastPurchase;
    // Getters and setters
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public double getTotalPurchased() {
        return totalPurchased;
    }
    public void setTotalPurchased(double totalPurchased) {
        this.totalPurchased = totalPurchased;
    }
    public int getCustomerClass() {
        return customerClass;
    }
    public void setCustomerClass(int customerClass) {
        this.customerClass = customerClass;
    }
    public int getLastPurchase() {
        return lastPurchase;
    }
    public void setLastPurchase(int lastPurchase) {
        this.lastPurchase = lastPurchase;
    }
}
 class Program {
    private String line;
    private int lineCount = 0;
    private final List<Customer> customers = new ArrayList<>();
    private BufferedReader reader = null;
    private final List<String> errorMessages = new ArrayList<>();
    
    public Program() throws FileNotFoundException {
 
        this.reader = new BufferedReader(new FileReader("/Users/colmj/Downloads/customers (1).txt"));

    }
   
    public void run() throws FileNotFoundException {
         this.reader = new BufferedReader(new FileReader("/Users/colmj/Downloads/customers (1).txt"));
        Customer customer = new Customer();
        String[] names;
        try {
            while ((line = reader.readLine()) != null) {
            
                names = line.split(" ");
System.out.println(line);
               
            
                switch (lineCount) {
                    
                    case 0 -> {
                        if (names[0].matches("^[a-zA-Z\\s]+$|^$")) {
                            customer.setFirstName(line);
                            lineCount++;
                        } else {
                            outputError(line, "First Name");
                        }
                    }


                    case 1 -> {
                        try {
                            customer.setTotalPurchased(Double.parseDouble(line));
                            lineCount++;
                        } catch (NumberFormatException e) {
                            outputError(line, "Total Purchased");
                        }
                    }
                    case 2 -> {
                        customer.setCustomerClass(Integer.parseInt(line));
                        lineCount++;
                    }
                    case 3 -> {
                        customer.setLastPurchase(Integer.parseInt(line));
                        lineCount = 0;
                        customers.add(customer);
                        customer = new Customer();
                    }
                }
            }
        } catch (IOException e) {
            errorMessages.add(e.getMessage());
        } finally { 
            try {
               for (Customer c : customers) {
  System.out.println(c.getFirstName());
}
                reader.close();
            } catch (IOException e) {
                errorMessages.add(e.getMessage());
            }
        }
        writeDiscountsToFile();
        printErrorMessages();
    }
    private void writeDiscountsToFile() {
        String file = "customerDiscounts.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Customer c : customers) {
                if (c.getCustomerClass() == 1 && c.getLastPurchase() == 2024) {
                    String value = getDiscountTotal(c.getFirstName(), c.getTotalPurchased(), 30);
                    writer.write(value);
                } else if (c.getCustomerClass() == 1 && c.getLastPurchase() < 2024) {
                    String value = getDiscountTotal(c.getFirstName(), c.getTotalPurchased(), 20);
                    writer.write(value);
                }
            }
        } catch (IOException e) {
            errorMessages.add(e.getMessage());
        }
    }
    private void outputError(String value, String type) {
        String errorMessage = "Invalid File: " + value + " Not Valid for " + type;
        System.out.println(errorMessage);
        errorMessages.add(errorMessage);
    }
    private String getDiscountTotal(String firstName, double totalPurchased, int discountPercentage) {
        double discountAmount = totalPurchased / 100 * discountPercentage;
        double discountedTotal = totalPurchased - discountAmount;
        return firstName + " Total Price: " + discountedTotal + "\n";
    }
    private void printErrorMessages() {
        if (!errorMessages.isEmpty()) {
            System.out.println("Errors encountered during processing:");
            for (String error : errorMessages) {
                System.out.println(error);
            }
        }
    }
    public static String findFile(String directory, String fileName) {
        File dir = new File(directory);
        File[] files = dir.listFiles((d, name) -> name.equals(fileName));
        if (files != null && files.length > 0) {
            return files[0].getAbsolutePath();
        }
        return null;
    }
    public static void main(String[] args) throws FileNotFoundException {
        // small adjustment to receive path  and filename through args
        // u call it by java program <path> <file>
        // arg0 C:/Users/valter/Documents/CCT/Java/001_CCT/src
        // arg1 fileQuestion22.txt
System.out.println("Hello");

        //if (args.length < 2) {
           // System.out.println("Usage: java Program <file-path>");
            //return;//
            
        //}
 
         try {
             String filePath = "";
           // String filePath = findFile( args[0], args[1]); // You can change the directory as needed
           System.out.println("Hello1");
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