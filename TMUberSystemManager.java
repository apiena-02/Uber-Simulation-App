import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/*
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager
{
  // Private map of users, key is user account id and value is the user
  private Map<String, User> users;
  private ArrayList<Driver> drivers;
  // Private ArrayList for userList used in sorting
  private ArrayList<User> userList;

  // private Array of queues to store service requests 
  private Queue<TMUberService>[] serviceRequests;

  public double totalRevenue; // Total revenues accumulated via rides and deliveries
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  // These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  public TMUberSystemManager()
  {
    // Initialize a new LinkedHashMap to store users
    users = new LinkedHashMap<>();
    drivers = new ArrayList<Driver>();
    // Initialize a 4 new queues for each zone and their service requests
    serviceRequests = new Queue[4];
    // Initialize the userList ArrayList
    userList = new ArrayList<User>();
    
    totalRevenue = 0;
    // Inititalizing each queue to a linkedlist 
    for (int i = 0; i < serviceRequests.length; i++) 
    {
      serviceRequests[i] = new LinkedList<>();
    }
  }
  // Take an arrayList of users and add them to map of users 
  public void setUsers(ArrayList<User> userList)
  {
    // Looping through the arraylist 
    for (int i = 0; i < userList.size(); i++)
    {
      // Creating a user
      User user = userList.get(i);
      // Updating id if users have already been registered before loading a file 
      String id = "900" + users.size();
      user.setAccountId(id);
      // Adding the account id as the key and the user as the value 
      users.put(userList.get(i).getAccountId(), user);
    }
    // Adding the users to the userList
    for (Map.Entry<String,User> entry : users.entrySet())
    {
      this.userList.add(entry.getValue());
    }
  }

  // Print Information (printInfo()) about all registered users in the system from map
  public void listAllUsers()
  {
    int count = 1;
    // Loop through map of users and print each user's information
    for (int i = 0; i < userList.size(); i++)
    {
      System.out.printf("%-2s. ", count);
      userList.get(i).printInfo();
      System.out.println(); 
      count++;
    }
  }
  // Take an arrayList of drivers and add them to our arrayList of drivers 
  public void setDrivers(ArrayList<Driver> driversList)
  {
    for (int i = 0; i < driversList.size(); i++)
    {
      // Update id if drivers have already been registered before loading a file
      driversList.get(i).setId(String.valueOf(7000 + drivers.size()));
      // Add to ArrayList
      drivers.add(driversList.get(i));
    }
  }
  // Given user account id, find user in list of users
  // Return null if not found
  public User getUser(String accountId)
  {
    // Loop through map of users
    for (Map.Entry<String, User> entry : users.entrySet())
    {
      // Get the user account id
      String id = entry.getKey();
      // Check if the account id is equal 
      if (id.equals(accountId))
      {
        // return user
        return entry.getValue();
      }
    }
    return null;
  } 
  // Check for duplicate user
  private boolean userExists(User user)
  {
    // Loop through map of users to find user
    for (Map.Entry<String, User> entry : users.entrySet())
    {
      User person = entry.getValue();
      if (person.equals(user))
      {
        return true;
      }
    }
    return false;
  }
  
 // Check for duplicate driver
 private boolean driverExists(Driver driver)
 {
   // Loop through drivers ArrayList to find driver 
   for (int i = 0; i < drivers.size(); i++)
   {
    // Get driver 
    Driver person = drivers.get(i);
    // If driver is found return true, else false
    if (person.equals(driver))
    {
      return true;
    }
   }
   return false;
 }
  
  // Given a user, check if user ride/delivery request already exists in service requests
  private boolean existingRequest(TMUberService req)
  {
    // Iterate through each queue in the array
    for (Queue<TMUberService> queue : serviceRequests) 
    {
      // Check if request is a ride
      if (req instanceof TMUberRide) 
      {
          // Cast req to TMUberRide
          TMUberRide rideRequest = (TMUberRide) req;
          // Loop through the current queue
          for (TMUberService service : queue) 
          {
              // If request is a ride and matches req, return true
              if (service instanceof TMUberRide && rideRequest.equals(service)) 
              {
                  return true;
              }
          }
      } 
      // Else, check if request is a delivery 
      else if (req instanceof TMUberDelivery) 
      {
          // Cast req to TMUberDelivery
          TMUberDelivery deliveryRequest = (TMUberDelivery) req;
          // Loop through the current queue
          for (TMUberService service : queue) 
          {
              // If request is a delivery and matches req, return true
              if (service instanceof TMUberDelivery && deliveryRequest.equals(service)) 
              {
                  return true;
              }
          }
      }
    }
    // If request is not found in any queue, return false
    return false;
  }

  // Calculate the cost of a ride or of a delivery based on distance 
  private double getDeliveryCost(int distance)
  {
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance)
  {
    return distance * RIDERATE;
  }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers()
  {
    System.out.println();

    // Loop through drivers ArrayList and print each driver in correct format
    for (int i = 0; i < drivers.size(); i++)
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      drivers.get(i).printInfo();
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests()
  {
    System.out.println();
    // Loop through array of queues 
    for (int i = 0; i < serviceRequests.length; i++) 
    {
      // Print Zone informtion
      System.out.println();
      System.out.println("ZONE " + i);
      System.out.println("======");
      int requestNumber = 1;
      // Loop through each queue and print each serivce request
      for (TMUberService request : serviceRequests[i])
      {
        System.out.println();
        System.out.printf("%-2s. ------------------------------------------------------------", requestNumber);
        request.printInfo();
        System.out.println();
        requestNumber++;
      }
    }
  }

  // Add a new user to the system
  public void registerNewUser(String name, String address, double wallet)
  {
    // Check if address, name, and wallet are valid
    if (CityMap.validAddress(address) && name.length() != 0 && wallet >= 0)
    {
      // Create new user id 
      String id = "900" + users.size();
      // Create new user 
      User person = new User(id, name, address, wallet);
      // If the user exists throw new AccountAlreadyExistsException
      if (userExists(person))
      {
        throw new AccountAlreadyExistsException("User Already Exists in System");
      }
      // Add user to map of users 
      users.put(id, person);
      userList.add(person);
    }
    // If name is not valid, throw new InvalidNameException
    else if (name.length() == 0 || name == null)
    {
      throw new InvalidNameException("Invalid User Name");
    }
    // If address is not valid, throw new InvalidAddressException  
    else if (!CityMap.validAddress(address))
    {
      throw new InvalidAddressException("Invalid User Address");
    }
    // If wallet input is not valid, throw new InvalidMoneyInWalletException
    else if (!(wallet >= 0))
    {
      throw new InvalidMoneyInWalletException("Invalid Money in Wallet");
    }
  }

  // Add a new driver to the system
  public void registerNewDriver(String name, String carModel, String carLicencePlate, String address)
  {
    // Check for proper name, car model, and car licence plate
    if (name.length() != 0 && carModel.length() != 0 && carLicencePlate.length() != 0 && CityMap.validAddress(address))
    {
      // Create new driver id
      String id = "700" + drivers.size();
      // Create new driver
      Driver driver = new Driver(id, name, carModel, carLicencePlate, address);
      // If the driver exists throw new AccountAlreadyExistsException
      if (driverExists(driver)){
        throw new AccountAlreadyExistsException("Driver Already Exists in System");
      }
      // Add driver to the drivers ArrayList
      drivers.add(driver);
    }
    // If name is not valid, throw new InvalidNameException
    else if (name.length() == 0 || name == null)
    {
      throw new InvalidNameException("Invalid Driver Name");
    }
    // If car model is not valid, throw new InvalidCarModelException
    else if (carModel.length() == 0 || carModel == null)
    {
      throw new InvalidCarModelException("Invalid Car Model");
    }
    // If car licence plate is not valid, throw new InvalidCarLicencePlateException
    else if (carLicencePlate.length() == 0 || carLicencePlate == null)
    {
      throw new InvalidCarLicencePlateException("Invalid Car Licence Plate");
    }
    // If address is not valid, throw new InvalidAddressException   
    else if (!CityMap.validAddress(address)) {
      throw new InvalidAddressException("Invalid Address");
    }
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public void requestRide(String accountId, String from, String to)
  {
    // Get user based on Id
    User user = getUser(accountId);

    // Check if user exists and both address are valid
    if (user != null && CityMap.validAddress(from) && CityMap.validAddress(to)) 
    {
      // Get distance
      int distance = CityMap.getDistance(from, to);  
      // Check if distance is greater than 1   
      if (distance > 1)
      {
        // Get ride cost
        double cost = getRideCost(distance);
        // Check if user has enough funds for ride 
        if (user.getWallet() < cost) 
        {
          // If user does not have enough funds throw new InsufficientFundsException
          throw new InsufficientFundsException("Insufficient Funds");
        }
        // Create ride request 
        TMUberRide ride = new TMUberRide(from, to, user, distance, cost);    
        // Check if request exists    
        if (user.getRides()  == 0)
        {
          // Get the zone of request 
          int zone = CityMap.getCityZone(from);
          // Add request to the queue 
          serviceRequests[zone].offer(ride);
          System.out.printf("RIDE for: %-15s  From: %-15s  To: %-15s",user.getName(),from,to);
          // Increment users rides
          user.addRide();
        }
        // If user has current ride request throw new UserAlreadyHasRideRequestException
        else 
        {
          throw new UserAlreadyHasRideRequestException("User Already Has Ride Request");
        }
      }
      // If distance is less than 1 throw new InsufficientTravelDistanceException
      else 
      {
        throw new InsufficientTravelDistanceException("Insufficient Travel Distance");
      }
    }
    // If user does not exist throw new UserNotFoundException
    else if (user == null) 
    {
      throw new UserNotFoundException("User Account Not Found");
    }
    // If "From" or "To"address is not valid throw new InvalidAddressException
    else if (!CityMap.validAddress(from) || !CityMap.validAddress(to)) 
    {
      throw new InvalidAddressException("Invalid Address");
    }
  }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId)
  {    
    // Get user based on Id
    User user = getUser(accountId);
    // Check if user exists, both address are valid, and restaurant and food order id are valid
    if (user != null && CityMap.validAddress(from) && CityMap.validAddress(to) && !restaurant.isEmpty() && !foodOrderId.isEmpty())
    {
      // Get distance
      int distance = CityMap.getDistance(from, to);
      // Check if distance is greater than 1
      if (distance > 1)
      {
          // Get ride cost
          double cost = getDeliveryCost(distance);
          // Check if user has enough funds for delviery
          if (user.getWallet() < cost) {
            // If user does not have enough funds throw new InsufficientFundsException
            throw new InsufficientFundsException("Insufficient Funds");
          }
          // Create delivery request 
          TMUberDelivery delivery = new TMUberDelivery(from, to, user, distance, cost, restaurant, foodOrderId);
          // Check if request exists
          if (!existingRequest(delivery))
          {
            // Geto the zone of request
            int zone = CityMap.getCityZone(from);
            // Add request to queue
            serviceRequests[zone].offer(delivery);
            System.out.printf("DELIVERY for: %-15s  From: %-15s  To: %-15s",user.getName(),from,to);
            // Increment users rides and return true 
            user.addDelivery();
          }
          // If user has current delviery request with same restaurant and food order Id throw new UserAlreadyHasDeliveryRequestException
          else 
          {
            throw new UserAlreadyHasDeliveryRequestException("User Already Has Delivery Request at Restaurant with this Food Order");
          }
      }
      // If distance is less than 1 throw new InsufficientTravelDistanceException
      else 
      {
        throw new InsufficientTravelDistanceException("Insufficient Travel Distance");
      }
    }
    // If user does not exist throw new UserNotFoundException
    else if (user == null) 
    {
      throw new UserNotFoundException("User Account Not Found");
    }
    // If "From" or "To"address is not valid throw new InvalidAddressException
    else if (!CityMap.validAddress(from) || !CityMap.validAddress(to)) 
    {
      throw new InvalidAddressException("Invalid Address");
    }
    // If restaurant is empty throw new InvalidRestaurantException
    else if (restaurant.isEmpty()) 
    {
      throw new InvalidRestaurantException("Invalid Restaurant");
    }
    // If food order Id is empty throw new InvalidFoodOrderException
    else if (foodOrderId.isEmpty())
    {
      throw new InvalidFoodOrderException("Invalid Food Order ID");
    }
  }
  // Cancel an existing service request. 
  // parameter int request is the index in the serviceRequests array list
  public void cancelServiceRequest(int request, int zone)
  {
    // Create temporary queue 
    Queue<TMUberService> tempList = new LinkedList<>();
    // Check if zone is valid
    if (zone < 0 || zone > 3)
    {
       // If zone number is not valid throw new InvalidZoneException
      throw new InvalidZoneException("Invalid Zone Number");

    }
    // Check if request number is valid
    if (request < 0 || request > serviceRequests[zone].size())
    {
      // If request number is not valid throw new InvalidRequestNumberException
      throw new InvalidRequestNumberException("Invalid Request #");
    }
    int count = 1;
    // Loop through service requests in that zone 
    for (TMUberService service : serviceRequests[zone])
    {
      // If we are not at the request number add to temporary queue
      if (count != request)
      {
        tempList.add(service);

      }
      // If we are at the request number, don't add to queue
      else
      {
        // Get user
        User person = (User) service.getUser();
        // Check if request is a ride 
        if (service instanceof TMUberRide) 
        {
          // Decrement rides for user 
          person.decrementRide();
        }
        // Check if request is a delivery
        else if (service instanceof TMUberDelivery)
        {
          // Decrement delivery's for user 
          person.decrementDelivery();
        }
      }
      // Increment count
      count++;
    }
    // Update service request queue to the temporary queue created
    serviceRequests[zone] = tempList;
  }
  // Pickup method 
  public void pickup(String driverId)
  {
    // Check if driver exists 
    Driver driver = driverIdExists(driverId);
    if (driver != null)
    {
      // Get the zone the driver is in
      int zone = driver.getZone();
      // Make sure the driver is available 
      if (driver.getStatus().equals(Driver.Status.AVAILABLE))
      {
        // Check to make sure there are service requests available in that zone 
        if (serviceRequests[zone].size() == 0)
        {
          // Throw new NoServiceRequestException if no service requests in that zone
          throw new NoServiceRequestException("No service request in Zone " + zone);
        }
        else 
        {
          // Create a service object
          TMUberService service = serviceRequests[zone].peek();
          // Set the type of service for the driver
          driver.setService(service);
          System.out.println("Driver " + driverId + " Picking Up in Zone " + zone);
          // Set the driver status to driving 
          driver.setStatus(Driver.Status.DRIVING);
          // Update the driver's address 
          driver.setAddress(service.getFrom());
          // Remove request from queue
          serviceRequests[zone].poll();
        }
      }
      else
      {
        // If driver is not available for pickup throw new InvalidDriverStatusException
        throw new InvalidDriverStatusException("Driver is not available");
      }
    }
    else
    {
      // If driver does not exist throw new DriverNotFoundException
      throw new DriverNotFoundException("Driver Account Not Found");
    }
  }

  // Checks if driver exists based on Id , return driver or null
  public Driver driverIdExists(String driverId)
  {
    // Loops through drivers to see if there is a match
    for (Driver driver : drivers)
    {
      if (driver.getId().equals(driverId))
      {
        return driver;

      }
    }
    return null;
  }
  // Drop off a ride or a delivery. This completes a service.
  // parameter request is the index in the serviceRequests array list
  public void dropOff(String driverId)
  {
    // Check if driver exists 
    Driver driver = driverIdExists(driverId);
    if (driver != null)
    {
      // Make sure driver is driving 
      if (driver.getStatus().equals(Driver.Status.DRIVING))
      {
        // Get the service type 
        TMUberService service = driver.getService();
        // Get the user
        User user = service.getUser();
        // Get the cost
        double cost = service.getCost();
        // Add cost to revenue 
        totalRevenue += cost;
        // Get payment
        double payment = cost * PAYRATE;
        // Pay driver the payment 
        driver.pay(payment);
        // Subtract payment from revenue
        totalRevenue -= payment;
        // Set driver status to available 
        driver.setStatus(Driver.Status.AVAILABLE);
        // Ask user to pay for service
        user.payForService(cost);
        // Set driver service to null
        driver.setService(null);
        // Set driver address to the To address of the service 
        driver.setAddress(service.getTo());
        // Set the driver in the zone of that address 
        driver.setZone(CityMap.getCityZone(service.getTo()));
      }
      else
      {
        // If driver is not driving throw new InvalidDriverStatusException
        throw new InvalidDriverStatusException("Driver not driving");
      }
    }
    else
    {
      // If driver id does not exist throw new DriverNotFoundException
      throw new DriverNotFoundException("Driver Account Not Found");
    }

  }
  // Allow driver to drive to new address and be in a different zone 
  public void driveTo(String driverId, String address)
  {
    // Check if driver exists
    Driver driver = driverIdExists(driverId);
    if(driver != null)
    {
      // Make sure driver is available
      if (driver.getStatus().equals(Driver.Status.AVAILABLE))
      {
        // Check if address is valid 
        if(CityMap.validAddress(address))
        {
          // Set the driver address to the new address
          driver.setAddress(address);
          // Update the zone 
          int zone = CityMap.getCityZone(address);
          driver.setZone(zone);
          // Print message confirming they are at a new address 
          System.out.println("Driver " + driverId + " Now in Zone " + zone);
        }
        else
        {
          // If address is invalid throw new InvalidAddressException
          throw new InvalidAddressException("Invalid Address");
        }
      }
      else
      {
        // If driver is driving throw new InvalidDriverStatusException
        throw new InvalidDriverStatusException("Driver is not available");
      }
    }
    else
    {
      // If driver is not driving throw new InvalidDriverStatusException
      throw new DriverNotFoundException("Driver Account Not Found");
    }
  }
  // Sort users by name
  // Then list all users
  public void sortByUserName()
  {

    // Sort the users ArrayList by names
    Collections.sort(userList, new NameComparator());
    listAllUsers();
    
  }

  // Helper class for method sortByUserName
  private class NameComparator implements Comparator<User>
  {
    // Compare the names of two users
    public int compare(User a, User b) 
    {
      return a.getName().compareTo(b.getName());
    }
    
  }

  // Sort users by number amount in wallet
  // Then ist all users
  public void sortByWallet()
  {
    // Sort the users ArrayList by wallets
    Collections.sort(userList, new UserWalletComparator());  
    listAllUsers();
  }
  // Helper class for use by sortByWallet
  private class UserWalletComparator implements Comparator<User>
  {
    // Compare the wallets of two users
    public int compare(User a, User b) 
    {
      if (a.getWallet() < (b.getWallet())) 
      {
        return -1;
      }
      if (a.getWallet() > (b.getWallet())) 
      {
        return 1;
      }
      return 1;

    }
  }
  // Sort trips (rides or deliveries) by distance
  // Then list all current service requests
  public void sortByDistance()
  {
    // Sort the service request ArrayList by distance
    //Collections.sort(serviceRequests);
    listAllServiceRequests();
  }

}

// Exception if driver is not found 
class DriverNotFoundException extends RuntimeException 
{
  public DriverNotFoundException(String message) 
  {
      super(message);
  }
}
// Exception if user is not found 
class UserNotFoundException extends RuntimeException 
{
  public UserNotFoundException(String message) 
  {
      super(message);
  }
}
// Exception if the name entered is not valid  
class InvalidNameException extends RuntimeException 
{
  public InvalidNameException(String message) 
  {
      super(message);
  }
}
// Exception if the address entered is not valid 
class InvalidAddressException extends RuntimeException 
{
  public InvalidAddressException(String message) 
  {
      super(message);
  }
}
// Exception if the money entered is not valid 
class InvalidMoneyInWalletException extends RuntimeException 
{
  public InvalidMoneyInWalletException(String message) 
  {
      super(message);
  }
}
// Exception if the user or driver already exists in the system 
class AccountAlreadyExistsException extends RuntimeException 
{
  public AccountAlreadyExistsException(String message) 
  {
      super(message);
  }
}
// Exception if the car model entered is not valid 
class InvalidCarModelException extends RuntimeException 
{
  public InvalidCarModelException(String message) 
  {
      super(message);
  }
}
// Exception if the licence plate entered is not valid 
class InvalidCarLicencePlateException extends RuntimeException 
{
  public InvalidCarLicencePlateException(String message) 
  {
      super(message);
  }
}
// Exception if user already has ride request and is trying to request another ride
class UserAlreadyHasRideRequestException extends RuntimeException 
{
  public UserAlreadyHasRideRequestException(String message) 
  {
      super(message);
  }
}
// Exception if travel distance is below 1
class InsufficientTravelDistanceException extends RuntimeException 
{
  public InsufficientTravelDistanceException(String message) 
  {
      super(message);
  }
}
// Exception if user is trying to request ride or delivery but doesn't have enough funds 
class InsufficientFundsException extends RuntimeException 
{
  public InsufficientFundsException(String message) 
  {
      super(message);
  }
}
// Exception if user already has delivery request and is trying to request another delivery with same restaurant and food order id
class UserAlreadyHasDeliveryRequestException extends RuntimeException 
{
  public UserAlreadyHasDeliveryRequestException(String message) 
  {
      super(message);
  }
}
// Exception if the request number entered is not valid
class InvalidRequestNumberException extends RuntimeException 
{
  public InvalidRequestNumberException(String message) 
  {
      super(message);
  }
}
// Exception if the restaurant entered is not valid
class InvalidRestaurantException extends RuntimeException 
{
  public InvalidRestaurantException(String message) 
  {
      super(message);
  }
}
// Exception if the food order id entered is not valid
class InvalidFoodOrderException extends RuntimeException 
{
  public InvalidFoodOrderException(String message) 
  {
      super(message);
  }
}
// Exception if the zone entered is not valid
class InvalidZoneException extends RuntimeException
{
  public InvalidZoneException(String message)
  {
    super(message);
  }
}
// Exception if the driver is trying to pickup, dropoff, or driveTo but doesn't have the valid driving status
class InvalidDriverStatusException extends RuntimeException
{
  public InvalidDriverStatusException(String message)
  {
    super(message);
  }
}
// Exception if there are no service requests available in that zone
class NoServiceRequestException extends RuntimeException
{
  public NoServiceRequestException(String message)
  {
    super(message);
  }
}


