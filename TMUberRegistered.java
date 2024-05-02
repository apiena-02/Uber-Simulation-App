// Name: Apiena Selvarajah 
// Student Number: 501168369
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class TMUberRegistered
{
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountID = 900;
    private static int firstDriverId = 700;

    // Generate a new user account id
    public static String generateUserAccountId(ArrayList<User> current)
    {
        return "" + firstUserAccountID + current.size();
    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current)
    {
        return "" + firstDriverId + current.size();
    }

    // Database of Preregistered users
    public static ArrayList<User> loadPreregisteredUsers(String filename) throws FileNotFoundException
    {
        // Create users ArrayList
        ArrayList<User> users = new ArrayList<>();
        // Create file 
        File userFile = new File(filename);
        // Scan through the file using a while loop 
        Scanner scanner = new Scanner(userFile);
        while (scanner.hasNextLine())
        {
            // Get name
            String name = scanner.nextLine();
            // Get address
            String address = scanner.nextLine();
            // Get money for wallet
            double wallet = Double.parseDouble(scanner.nextLine());
            // Generate user account id 
            String id = generateUserAccountId(users);
            // Create new User
            User user = new User(id, name, address, wallet);
            // Add new user to the users ArrayList
            users.add(user);
        }
        // Close scanner when done
        scanner.close();
        // Return users
        return users;
    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    public static ArrayList<Driver> loadPreregisteredDrivers(String filename) throws FileNotFoundException
    {
        // Create drivers ArrayList
        ArrayList<Driver> drivers = new ArrayList<>();
        // Create file
        File userFile = new File(filename);
        // Scan through the file using a while loop
        Scanner scanner = new Scanner(userFile);
        while (scanner.hasNextLine())
        {
            // Get name
            String name = scanner.nextLine();
            // Get car model
            String carModel = scanner.nextLine();
            // Get license plate
            String licensePlate = scanner.nextLine();
            // Get address
            String address = scanner.nextLine();
            // Generate driver id
            String id = generateDriverId(drivers);
            // Create new Driver
            Driver driver = new Driver(id, name, carModel, licensePlate, address);
            // Add driver to the drivers ArrayList 
            drivers.add(driver);
        }
        // Close scanner when done
        scanner.close();
        // Return drivers
        return drivers;
    }
}

