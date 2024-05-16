import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;

// Simulation of a Simple Command-line based Uber App 

public class TMUberUI
{
  public static void main(String[] args)
  {
    // Create the System Manager - the main system code is in here 

    TMUberSystemManager tmuber = new TMUberSystemManager();
    
    Scanner scanner = new Scanner(System.in);
    System.out.print(">");

    // Process keyboard actions
    while (scanner.hasNextLine())
    {
      String action = scanner.nextLine();
      // Try block 
      try
      {
        if (action == null || action.equals("")) 
        {
          System.out.print("\n>");
          continue;
        }
        // Quit the App
        else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
          return;
        // Print all the registered drivers
        else if (action.equalsIgnoreCase("DRIVERS"))  
        {
          tmuber.listAllDrivers(); 
        }
        // Print all the registered users
        else if (action.equalsIgnoreCase("USERS"))  
        {
          tmuber.listAllUsers(); 
        }
        // Print all current ride requests or delivery requests
        else if (action.equalsIgnoreCase("REQUESTS"))  
        {
          tmuber.listAllServiceRequests(); 
        }
        // Register a new driver
        else if (action.equalsIgnoreCase("REGDRIVER")) 
        {
          String name = "";
          System.out.print("Name: ");
          if (scanner.hasNextLine())
          {
            name = scanner.nextLine();
          }
          String carModel = "";
          System.out.print("Car Model: ");
          if (scanner.hasNextLine())
          {
            carModel = scanner.nextLine();
          }
          String license = "";
          System.out.print("Car License: ");
          if (scanner.hasNextLine())
          {
            license = scanner.nextLine();
          }
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }
          tmuber.registerNewDriver(name, carModel, license, address);
          System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s Address: %-15s", name, carModel, license, address);
        }
        // Register a new user
        else if (action.equalsIgnoreCase("REGUSER")) 
        {
          String name = "";
          System.out.print("Name: ");
          if (scanner.hasNextLine())
          {
            name = scanner.nextLine();
          }
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }
          double wallet = 0.0;
          System.out.print("Wallet: ");
          if (scanner.hasNextDouble())
          {
            wallet = scanner.nextDouble();
            scanner.nextLine();
          }
          tmuber.registerNewUser(name, address, wallet);
          System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);
        }
        // Request a ride
        else if (action.equalsIgnoreCase("REQRIDE")) 
        {
          String accountId = "";
          System.out.print("User Account Id: ");
          if (scanner.hasNextLine())
          {
            accountId = scanner.nextLine();
          }
          String fromAddress = "";
          System.out.print("From Address: ");
          if (scanner.hasNextLine())
          {
            fromAddress = scanner.nextLine();
          }
          String toAddress = "";
          System.out.print("To Address: ");
          if (scanner.hasNextLine())
          {
            toAddress = scanner.nextLine();
          }
          tmuber.requestRide(accountId, fromAddress, toAddress);
        }
        // Request a food delivery
        else if (action.equalsIgnoreCase("REQDLVY")) 
        {
          String accountId = "";
          System.out.print("User Account Id: ");
          if (scanner.hasNextLine())
          {
            accountId = scanner.nextLine();
          }
          String fromAddress = "";
          System.out.print("From Address: ");
          if (scanner.hasNextLine())
          {
            fromAddress = scanner.nextLine();
          }
          String toAddress = "";
          System.out.print("To Address: ");
          if (scanner.hasNextLine())
          {
            toAddress = scanner.nextLine();
          }
          String restaurant = "";
          System.out.print("Restaurant: ");
          if (scanner.hasNextLine())
          {
            restaurant = scanner.nextLine();
          }
          String foodOrderId = "";
          System.out.print("Food Order #: ");
          if (scanner.hasNextLine())
          {
            foodOrderId = scanner.nextLine();
          }
          tmuber.requestDelivery(accountId, fromAddress, toAddress, restaurant, foodOrderId);
        }
        // Sort users by name
        else if (action.equalsIgnoreCase("SORTBYNAME")) 
        {
          tmuber.sortByUserName();
        }
        // Sort users by number of ride they have had
        else if (action.equalsIgnoreCase("SORTBYWALLET")) 
        {
          tmuber.sortByWallet();
        }
        // Cancel a current service (ride or delivery) request
        else if (action.equalsIgnoreCase("CANCELREQ")) 
        {
          int request = -1;
          System.out.print("Request #: ");
          if (scanner.hasNextInt())
          {
            request = scanner.nextInt();
            scanner.nextLine(); 
          }
          int zone = -1;
          System.out.print("Zone: ");
          if (scanner.hasNextInt())
          {
            zone = scanner.nextInt();
            scanner.nextLine(); 
          }

          tmuber.cancelServiceRequest(request, zone);
          System.out.println("Service request # " + request + " cancelled in Zone " + zone);
        }
        // Drop-off the user or the food delivery to the destination address
        else if (action.equalsIgnoreCase("DROPOFF")) 
        {
          String driverId = "";
          System.out.print("Driver ID: ");
          if (scanner.hasNextLine())
          {
            driverId = scanner.nextLine();
          }
          tmuber.dropOff(driverId);
          System.out.println("Driver " + driverId + " Dropping Off");
        }
        // Get the Current Total Revenues
        else if (action.equalsIgnoreCase("REVENUES")) 
        {
          System.out.println("Total Revenue: " + tmuber.totalRevenue);
        }
        // Unit Test of Valid City Address 
        else if (action.equalsIgnoreCase("ADDR")) 
        {
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }
          System.out.print(address);
          if (CityMap.validAddress(address))
            System.out.println("\nValid Address"); 
          else
            System.out.println("\nBad Address"); 
        }
        // Unit Test of CityMap Distance Method
        else if (action.equalsIgnoreCase("DIST")) 
        {
          String from = "";
          System.out.print("From: ");
          if (scanner.hasNextLine())
          {
            from = scanner.nextLine();
          }
          String to = "";
          System.out.print("To: ");
          if (scanner.hasNextLine())
          {
            to = scanner.nextLine();
          }
          System.out.print("\nFrom: " + from + " To: " + to);
          System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");

        }

        // Allow driver to pickup service request in their zone 
        else if (action.equalsIgnoreCase("PICKUP"))
        {
          String driverId = "";
          System.out.print("Driver Id: ");
          if (scanner.hasNextLine())
          {
            driverId = scanner.nextLine();
          }
          tmuber.pickup(driverId);
        }

        // Load users from a file 
        else if (action.equalsIgnoreCase("LOADUSERS"))
        {
          String filename = "";
          System.out.print("User File: ");
          if (scanner.hasNextLine())
          {
            filename = scanner.nextLine();
          }
          // Try block 
          try 
          {
            ArrayList<User> usersList = TMUberRegistered.loadPreregisteredUsers(filename);
            tmuber.setUsers(usersList);
            System.out.println("Users Loaded");
          } 
          // Catch FileNotFoundException if file is not found 
          catch (FileNotFoundException e) 
          {
            System.out.println("Users File: " + filename + " Not Found");
          }   
        }

        // Load drivers from a file
        else if (action.equalsIgnoreCase("LOADDRIVERS"))
        {
          String filename = "";
          System.out.print("Driver File: ");
          if (scanner.hasNextLine())
          {
            filename = scanner.nextLine();
          }
           // Try block 
          try
          {
            ArrayList<Driver> driversList =TMUberRegistered.loadPreregisteredDrivers(filename);
            tmuber.setDrivers(driversList);
            System.out.println("Drivers Loaded");
          }
           // Catch FileNotFoundException if file is not found 
          catch (FileNotFoundException e)
          {
            System.out.println("Drivers File: " + filename + " Not Found");
          }
        }

        // Allow driver to travel to new address 
        else if (action.equalsIgnoreCase("DRIVETO"))
        {
          String driverId = "";
          System.out.print("Driver Id: ");
          if (scanner.hasNextLine())
          {
            driverId = scanner.nextLine();
          }

          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }
          tmuber.driveTo(driverId, address);
        }
    }
      // Catching exceptions and printing the message contained in the exception object
      catch (RuntimeException e)
      {
        System.out.println(e.getMessage());
      }
      System.out.print("\n>");
    }
  }
}

