/*
 * 
 * This class simulates a car driver in a simple uber app 
 * 
 * Everything has been done for you except the equals() method
 */
public class Driver
{
  private String id;
  private String name;
  private String carModel;
  private String licensePlate;
  private double wallet;
  private String type;
  private TMUberService service; 
  private String address;
  private int zone;
  
  public static enum Status {AVAILABLE, DRIVING};
  private Status status;
    
  
  public Driver(String id, String name, String carModel, String licensePlate, String address)
  {
    this.id = id;
    this.name = name;
    this.carModel = carModel;
    this.licensePlate = licensePlate;
    this.status = Status.AVAILABLE;
    this.wallet = 0;
    this.type = "";
    this.address = address;
    this.zone = CityMap.getCityZone(address);
  }

  // Set service 
  public void setService(TMUberService service) 
  {
    this.service = service;
  }
  // Get the drivers zone
  public int getZone()
  {
    return this.zone;
  }
  // Set the drivers zone
  public void setZone(int num)
  {
    this.zone = num;
  }
  // Set the drivers address
  public void setAddress(String newAddress)
  {
    this.address = newAddress;
  }
  // Get the service of the driver
  public TMUberService getService()
  {
    return this.service;
  }
  // Print Information about a driver
  public void printInfo()
  {
    // Check if driver is driving and print info along with the service type, from address, and to address
    if (status.equals(Status.DRIVING))
    {
      System.out.printf("Id: %-3s Name: %-15s Car Model: %-15s License Plate: %-10s Wallet: %2.2f Status: %-10s Address: %-15s Zone: %d From: %-15s To: %-15s\n" , 
                      id, name, carModel, licensePlate, wallet, status, address, zone, service.getFrom(), service.getTo());
    }
    // If driver is available print info
    else
    {
      System.out.printf("Id: %-3s Name: %-15s Car Model: %-15s License Plate: %-10s Wallet: %2.2f Status: %-10s Address: %-15s Zone: %d\n", 
                      id, name, carModel, licensePlate, wallet, status, address, zone);
    }
  }
  // Getters and Setters
  public String getType()
  {
    return type;
  }
  public void setType(String type)
  {
    this.type = type;
  }
  public String getId()
  {
    return id;
  }
  public void setId(String id)
  {
    this.id = id;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public String getCarModel()
  {
    return carModel;
  }
  public void setCarModel(String carModel)
  {
    this.carModel = carModel;
  }
  public String getLicensePlate()
  {
    return licensePlate;
  }
  public void setLicensePlate(String licensePlate)
  {
    this.licensePlate = licensePlate;
  }
  public Status getStatus()
  {
    return status;
  }
  public void setStatus(Status status)
  {
    this.status = status;
  }
  public double getWallet()
  {
    return wallet;
  }
  public void setWallet(double wallet)
  {
    this.wallet = wallet;
  }
  /*
   * Two drivers are equal if they have the same name and license plates.
   * This method is overriding the inherited method in superclass Object
   * 
   * Fill in the code 
   */
  public boolean equals(Object other)
  {
    // Cast other to Driver 
    Driver otherDriver = (Driver) other;

    // Check if names and license plates are equal and return true if they are, else return false
    if (otherDriver.getName().equals(name) && otherDriver.getLicensePlate().equals(licensePlate))
    {
      return true;
    }
    return false;
  }
  
  // A driver earns a fee for every ride or delivery
  public void pay(double fee)
  {
    wallet += fee;
  }
}
