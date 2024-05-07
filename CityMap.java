import java.util.Arrays;
import java.util.Scanner;

public class CityMap
{
  // Checks for string consisting of all digits
  private static boolean allDigits(String s)
  {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return  true;
  }

  // Get all parts of address string
  private static String[] getParts(String address)
  {
    String parts[] = new String[3];
    
    if (address == null || address.length() == 0)
    {
      parts = new String[0];
      return parts;
    }
    int numParts = 0;
    Scanner sc = new Scanner(address);
    while (sc.hasNext())
    {
      if (numParts >= 3)
        parts = Arrays.copyOf(parts, parts.length+1);

      parts[numParts] = sc.next();
      numParts++;
    }
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static boolean validAddress(String address)
  {
    // Setting flags for each part of an address
    boolean part1 = false;
    boolean part2 = false;
    boolean part3 = false;

    // Getting an array of the 3 address parts 
    String [] addressParts = getParts(address);

    // If the array length is not equal 3 return false
    if (addressParts.length != 3)
    {
      return false;
    }

    // Check if the first part of the address is correct and set the part 1 flag to true
    if (addressParts[0].length() == 2 && allDigits(addressParts[0]))
    {
      part1 = true;
    }

    // Check if the second part of the address is correct and set the part 2 flag to true
    String num = addressParts[1].substring(0,1);
    // Checking if the ending is 1st, 2nd, 3rd, or th with a number between 4 and 9
    if (addressParts[1].substring(0, 3).equals("1st") || 
    addressParts[1].substring(0, 3).equals("2nd") ||
    addressParts[1].substring(0, 3).equals("3rd") ||
    (addressParts[1].substring(1, 3).equals("th") 
    && Integer.parseInt(num) >= 4 && Integer.parseInt(num) <=9 )) 
    {
      part2 = true;
    }
    
    // Check if the third part of the address is equal to "Street" or "Avenue" and set the part 3 flag to true
    if (addressParts[2].equalsIgnoreCase("Avenue") || addressParts[2].equalsIgnoreCase("Street")) {
      part3 = true;
    }

    // If all 3 parts of the address are true then return true else return false
    if (part1 && part2 && part3) {
      return true;
    }
    return false;
  }

  // Computes the city block coordinates from an address string
  public static int[] getCityBlock(String address)
  {
    // Initializing the block 
    int[] block = {-1, -1};

    // Checking if the address is valid 
    if (validAddress(address)) {
      // If valid get the parts of the adress 
      String [] addressParts = getParts(address);
      // If the third part is equal to "Street" then add the first digit of the residence number then the street number
      if (addressParts[2].equalsIgnoreCase("Street")) 
      {
        block[0] = Integer.parseInt(addressParts[0].substring(0,1));
        block[1] = Integer.parseInt(addressParts[1].substring(0,1));
      }

      // If the third part is equal to "Avenue" then add the avenue number then the first digit of the residence number
      else if (addressParts[2].equalsIgnoreCase("Avenue")) 
      {
        block[0] = Integer.parseInt(addressParts[1].substring(0,1));
        block[1] = Integer.parseInt(addressParts[0].substring(0,1));
      }
    }
    // Return the block
    return block;
  }
  
  // Calculates the distance in city blocks between the 'from' address and 'to' address
  public static int getDistance(String from, String to)
  {
    // Initializing the distance to 0
    int distance = 0;

    // Getting the array for each address
    int [] blockFrom = getCityBlock(from);
    int [] blockTo = getCityBlock(to);

    // Calculating the distance using the formula provided 
    distance = Math.abs(blockTo[0] - blockFrom[0]) + (Math.abs(blockTo[1] - blockFrom[1]));

    // Return distance
    return distance;
  }

  // Find zone from given address
  public static int getCityZone(String address)
  {
    // Checking if address is in Zone 0
    int [] block = getCityBlock(address);
    if (6 <= block[1] && block[1] <= 9 && 1 <= block[0] && block[0] <= 5)
    {
      return 0;
    }
    // Checking if address is in Zone 1
    else if (6 <= block[1] && block[1] <= 9 && 6 <= block[0] && block[0] <= 9)
    {
      return 1;
    }
    // Checking if address is in Zone 2
    else if (1 <= block[1] && block[1] <= 5 && 6 <= block[0] && block[0] <= 9)
    {
      return 2;
    }
    // Checking if address is in Zone 3
    else if (1 <= block[1] && block[1] <= 5 && 1 <= block[0] && block[0] <= 5)
    {
      return 3;
    }
    // If address is neither of them return -1
    return -1;
  }
}
