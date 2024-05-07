/*
 * Class that simulates a user of a simple Uber app
 */
public class User 
{
  private String accountId;  
  private String name;
  private String address;
  private double wallet; 
  private int rides;
  private int deliveries;
  
  public User(String id, String name, String address, double wallet)
  {
    this.accountId = id;
    this.name = name;
    this.address = address;
    this.wallet = wallet;
    this.rides = 0;
    this.deliveries = 0;
  } 

  // Getters and Setters
  public String getAccountId()
  {
    return this.accountId;
  }
  public void setAccountId(String accountId)
  {
    this.accountId = accountId;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public String getAddress()
  {
    return address;
  }
  public void setAddress(String address)
  {
    this.address = address;
  }
  public double getWallet()
  {
    return wallet;
  }
  public void setWallet(int wallet)
  {
    this.wallet = wallet;
  }
  public int getRides()
  {
    return rides;
  }
  public void addRide()
  {
    this.rides++;
  }
  public void addDelivery()
  {
    this.deliveries++;
  }
  public int getDeliveries()
  {
    return deliveries;
  }
  public void decrementRide()
  {
    this.rides--;
  }
  public void decrementDelivery()
  {
    this.deliveries--;
  }
  
  // Pay for the cost of the service
  public void payForService(double cost)
  {
    wallet -= cost;
  }
  // Print Information about a User  
  public void printInfo()
  {
    System.out.printf("Id: %-5s Name: %-15s Address: %-15s Wallet: %2.2f", accountId, name, address, wallet);
  }

  // Check if two users are equal (Two users are equal if they have the same name and address)
  public boolean equals(Object other)
  {
    // Cast other to User
    User otherUser = (User) other;

    // Check if same and address are equal, if they are return true, else return false 
    if (otherUser.getName().equals(name) && otherUser.getAddress().equals(address))
    {
      return true;
    }
    return false;
  }
}
