/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;

// line 3 "../../../../../librarysystem.ump"
public class LibrarySystem
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, LibrarySystem> librarysystemsBySystemID = new HashMap<String, LibrarySystem>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //LibrarySystem Attributes
  private String systemID;

  //LibrarySystem Associations
  private Address buisnessaddress;
  private Calendar calendar;
  private List<User> users;
  private List<Item> items;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LibrarySystem(String aSystemID, Address aBuisnessaddress, Calendar aCalendar)
  {
    if (!setSystemID(aSystemID))
    {
      throw new RuntimeException("Cannot create due to duplicate systemID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    if (aBuisnessaddress == null || aBuisnessaddress.getLibrarySystem() != null)
    {
      throw new RuntimeException("Unable to create LibrarySystem due to aBuisnessaddress. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    buisnessaddress = aBuisnessaddress;
    if (aCalendar == null || aCalendar.getLibrarySystem() != null)
    {
      throw new RuntimeException("Unable to create LibrarySystem due to aCalendar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    calendar = aCalendar;
    users = new ArrayList<User>();
    items = new ArrayList<Item>();
  }

  public LibrarySystem(String aSystemID, String aAddressIDForBuisnessaddress, int aCivicNumberForBuisnessaddress, String aStreetForBuisnessaddress, String aCityForBuisnessaddress, String aPostalCodeForBuisnessaddress, String aProvinceForBuisnessaddress, String aCountryForBuisnessaddress, String aCalendarIDForCalendar)
  {
    if (!setSystemID(aSystemID))
    {
      throw new RuntimeException("Cannot create due to duplicate systemID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    buisnessaddress = new Address(aAddressIDForBuisnessaddress, aCivicNumberForBuisnessaddress, aStreetForBuisnessaddress, aCityForBuisnessaddress, aPostalCodeForBuisnessaddress, aProvinceForBuisnessaddress, aCountryForBuisnessaddress, this);
    calendar = new Calendar(aCalendarIDForCalendar, this);
    users = new ArrayList<User>();
    items = new ArrayList<Item>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSystemID(String aSystemID)
  {
    boolean wasSet = false;
    String anOldSystemID = getSystemID();
    if (anOldSystemID != null && anOldSystemID.equals(aSystemID)) {
      return true;
    }
    if (hasWithSystemID(aSystemID)) {
      return wasSet;
    }
    systemID = aSystemID;
    wasSet = true;
    if (anOldSystemID != null) {
      librarysystemsBySystemID.remove(anOldSystemID);
    }
    librarysystemsBySystemID.put(aSystemID, this);
    return wasSet;
  }

  public String getSystemID()
  {
    return systemID;
  }
  /* Code from template attribute_GetUnique */
  public static LibrarySystem getWithSystemID(String aSystemID)
  {
    return librarysystemsBySystemID.get(aSystemID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithSystemID(String aSystemID)
  {
    return getWithSystemID(aSystemID) != null;
  }
  /* Code from template association_GetOne */
  public Address getBuisnessaddress()
  {
    return buisnessaddress;
  }
  /* Code from template association_GetOne */
  public Calendar getCalendar()
  {
    return calendar;
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
  /* Code from template association_GetMany */
  public Item getItem(int index)
  {
    Item aItem = items.get(index);
    return aItem;
  }

  public List<Item> getItems()
  {
    List<Item> newItems = Collections.unmodifiableList(items);
    return newItems;
  }

  public int numberOfItems()
  {
    int number = items.size();
    return number;
  }

  public boolean hasItems()
  {
    boolean has = items.size() > 0;
    return has;
  }

  public int indexOfItem(Item aItem)
  {
    int index = items.indexOf(aItem);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public User addUser(boolean aIsOnlineAcc, String aFirstName, String aLastName, int aLibraryCardID, boolean aIsVerified, int aDemeritPts, Address aAddress)
  {
    return new User(aIsOnlineAcc, aFirstName, aLastName, aLibraryCardID, aIsVerified, aDemeritPts, aAddress, this);
  }

  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    LibrarySystem existingLibrarySystem = aUser.getLibrarySystem();
    boolean isNewLibrarySystem = existingLibrarySystem != null && !this.equals(existingLibrarySystem);
    if (isNewLibrarySystem)
    {
      aUser.setLibrarySystem(this);
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
    //Unable to remove aUser, as it must always have a librarySystem
    if (!this.equals(aUser.getLibrarySystem()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Item addItem(Item.Status aStatus, int aItemBarcode, Title aTitle)
  {
    return new Item(aStatus, aItemBarcode, this, aTitle);
  }

  public boolean addItem(Item aItem)
  {
    boolean wasAdded = false;
    if (items.contains(aItem)) { return false; }
    LibrarySystem existingLibrarySystem = aItem.getLibrarySystem();
    boolean isNewLibrarySystem = existingLibrarySystem != null && !this.equals(existingLibrarySystem);
    if (isNewLibrarySystem)
    {
      aItem.setLibrarySystem(this);
    }
    else
    {
      items.add(aItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeItem(Item aItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aItem, as it must always have a librarySystem
    if (!this.equals(aItem.getLibrarySystem()))
    {
      items.remove(aItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addItemAt(Item aItem, int index)
  {  
    boolean wasAdded = false;
    if(addItem(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveItemAt(Item aItem, int index)
  {
    boolean wasAdded = false;
    if(items.contains(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addItemAt(aItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    librarysystemsBySystemID.remove(getSystemID());
    Address existingBuisnessaddress = buisnessaddress;
    buisnessaddress = null;
    if (existingBuisnessaddress != null)
    {
      existingBuisnessaddress.delete();
    }
    Calendar existingCalendar = calendar;
    calendar = null;
    if (existingCalendar != null)
    {
      existingCalendar.delete();
    }
    while (users.size() > 0)
    {
      User aUser = users.get(users.size() - 1);
      aUser.delete();
      users.remove(aUser);
    }
    
    while (items.size() > 0)
    {
      Item aItem = items.get(items.size() - 1);
      aItem.delete();
      items.remove(aItem);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "systemID" + ":" + getSystemID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "buisnessaddress = "+(getBuisnessaddress()!=null?Integer.toHexString(System.identityHashCode(getBuisnessaddress())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "calendar = "+(getCalendar()!=null?Integer.toHexString(System.identityHashCode(getCalendar())):"null");
  }
}