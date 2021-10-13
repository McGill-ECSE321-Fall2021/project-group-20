/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;

// line 72 "../../../../../librarysystem.ump"
public class Customer extends User
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Customer> customersByCustomerID = new HashMap<Integer, Customer>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private int customerID;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(boolean aIsOnlineAcc, String aFirstName, String aLastName, int aLibraryCardID, boolean aIsVerified, int aDemeritPts, Address aAddress, LibrarySystem aLibrarySystem, int aCustomerID)
  {
    super(aIsOnlineAcc, aFirstName, aLastName, aLibraryCardID, aIsVerified, aDemeritPts, aAddress, aLibrarySystem);
    if (!setCustomerID(aCustomerID))
    {
      throw new RuntimeException("Cannot create due to duplicate customerID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCustomerID(int aCustomerID)
  {
    boolean wasSet = false;
    Integer anOldCustomerID = getCustomerID();
    if (anOldCustomerID != null && anOldCustomerID.equals(aCustomerID)) {
      return true;
    }
    if (hasWithCustomerID(aCustomerID)) {
      return wasSet;
    }
    customerID = aCustomerID;
    wasSet = true;
    if (anOldCustomerID != null) {
      customersByCustomerID.remove(anOldCustomerID);
    }
    customersByCustomerID.put(aCustomerID, this);
    return wasSet;
  }

  public int getCustomerID()
  {
    return customerID;
  }
  /* Code from template attribute_GetUnique */
  public static Customer getWithCustomerID(int aCustomerID)
  {
    return customersByCustomerID.get(aCustomerID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithCustomerID(int aCustomerID)
  {
    return getWithCustomerID(aCustomerID) != null;
  }

  public void delete()
  {
    customersByCustomerID.remove(getCustomerID());
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "customerID" + ":" + getCustomerID()+ "]";
  }
}