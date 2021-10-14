/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

// line 81 "../../../../../librarysystem.ump"
@Entity
public class Address
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Address> addresssByAddressID = new HashMap<String, Address>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Address Attributes
  @Id
  @GeneratedValue
  private String addressID;
  private int civicNumber;
  private String street;
  private String city;
  private String postalCode;
  private String province;
  private String country;

  //Address Associations
  @OneToOne(mappedBy="buisnessaddress")
  private LibrarySystem librarySystem;
  @OneToMany(mappedBy="address")
  private List<User> users;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Address(String aAddressID, int aCivicNumber, String aStreet, String aCity, String aPostalCode, String aProvince, String aCountry, LibrarySystem aLibrarySystem)
  {
    civicNumber = aCivicNumber;
    street = aStreet;
    city = aCity;
    postalCode = aPostalCode;
    province = aProvince;
    country = aCountry;
    if (!setAddressID(aAddressID))
    {
      throw new RuntimeException("Cannot create due to duplicate addressID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    if (aLibrarySystem == null || aLibrarySystem.getBuisnessaddress() != null)
    {
      throw new RuntimeException("Unable to create Address due to aLibrarySystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    librarySystem = aLibrarySystem;
    users = new ArrayList<User>();
  }

  public Address(String aAddressID, int aCivicNumber, String aStreet, String aCity, String aPostalCode, String aProvince, String aCountry, String aSystemIDForLibrarySystem, Calendar aCalendarForLibrarySystem)
  {
    if (!setAddressID(aAddressID))
    {
      throw new RuntimeException("Cannot create due to duplicate addressID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    civicNumber = aCivicNumber;
    street = aStreet;
    city = aCity;
    postalCode = aPostalCode;
    province = aProvince;
    country = aCountry;
    librarySystem = new LibrarySystem(aSystemIDForLibrarySystem, this, aCalendarForLibrarySystem);
    users = new ArrayList<User>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAddressID(String aAddressID)
  {
    boolean wasSet = false;
    String anOldAddressID = getAddressID();
    if (anOldAddressID != null && anOldAddressID.equals(aAddressID)) {
      return true;
    }
    if (hasWithAddressID(aAddressID)) {
      return wasSet;
    }
    addressID = aAddressID;
    wasSet = true;
    if (anOldAddressID != null) {
      addresssByAddressID.remove(anOldAddressID);
    }
    addresssByAddressID.put(aAddressID, this);
    return wasSet;
  }

  public boolean setCivicNumber(int aCivicNumber)
  {
    boolean wasSet = false;
    civicNumber = aCivicNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setStreet(String aStreet)
  {
    boolean wasSet = false;
    street = aStreet;
    wasSet = true;
    return wasSet;
  }

  public boolean setCity(String aCity)
  {
    boolean wasSet = false;
    city = aCity;
    wasSet = true;
    return wasSet;
  }

  public boolean setPostalCode(String aPostalCode)
  {
    boolean wasSet = false;
    postalCode = aPostalCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setProvince(String aProvince)
  {
    boolean wasSet = false;
    province = aProvince;
    wasSet = true;
    return wasSet;
  }

  public boolean setCountry(String aCountry)
  {
    boolean wasSet = false;
    country = aCountry;
    wasSet = true;
    return wasSet;
  }

  public String getAddressID()
  {
    return addressID;
  }
  /* Code from template attribute_GetUnique */
  public static Address getWithAddressID(String aAddressID)
  {
    return addresssByAddressID.get(aAddressID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithAddressID(String aAddressID)
  {
    return getWithAddressID(aAddressID) != null;
  }

  public int getCivicNumber()
  {
    return civicNumber;
  }

  public String getStreet()
  {
    return street;
  }

  public String getCity()
  {
    return city;
  }

  public String getPostalCode()
  {
    return postalCode;
  }

  public String getProvince()
  {
    return province;
  }

  public String getCountry()
  {
    return country;
  }
  /* Code from template association_GetOne */
  public LibrarySystem getLibrarySystem()
  {
    return librarySystem;
  }
  /* Code from template association_GetMany */
  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public User addUser(boolean aIsOnlineAcc, String aFirstName, String aLastName, int aLibraryCardID, boolean aIsVerified, int aDemeritPts, LibrarySystem aLibrarySystem)
  {
    return new User(aIsOnlineAcc, aFirstName, aLastName, aLibraryCardID, aIsVerified, aDemeritPts, this, aLibrarySystem);
  }

  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    Address existingAddress = aUser.getAddress();
    boolean isNewAddress = existingAddress != null && !this.equals(existingAddress);
    if (isNewAddress)
    {
      aUser.setAddress(this);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    //Unable to remove aUser, as it must always have a address
    if (!this.equals(aUser.getAddress()))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    addresssByAddressID.remove(getAddressID());
    LibrarySystem existingLibrarySystem = librarySystem;
    librarySystem = null;
    if (existingLibrarySystem != null)
    {
      existingLibrarySystem.delete();
    }
    for(int i=users.size(); i > 0; i--)
    {
      User aUser = users.get(i - 1);
      aUser.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "addressID" + ":" + getAddressID()+ "," +
            "civicNumber" + ":" + getCivicNumber()+ "," +
            "street" + ":" + getStreet()+ "," +
            "city" + ":" + getCity()+ "," +
            "postalCode" + ":" + getPostalCode()+ "," +
            "province" + ":" + getProvince()+ "," +
            "country" + ":" + getCountry()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "librarySystem = "+(getLibrarySystem()!=null?Integer.toHexString(System.identityHashCode(getLibrarySystem())):"null");
  }
}