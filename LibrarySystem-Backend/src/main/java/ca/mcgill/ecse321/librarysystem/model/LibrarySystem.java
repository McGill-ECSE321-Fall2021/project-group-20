/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

// line 3 "../../../../../librarysystem.ump"
@Entity
public class LibrarySystem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //LibrarySystem Attributes
	@Id
	@GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
  private String systemID;

  //LibrarySystem Associations
	@OneToOne(optional=true)
  private Address businessaddress;
  @OneToOne(mappedBy="librarySystem")
  private Calendar calendar;
  @OneToMany(mappedBy="librarySystem")
  private List<User> users;
  @OneToMany(mappedBy="librarySystem")
  private List<Item> items;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LibrarySystem(){}
  
  public LibrarySystem(Address aBusinessaddress, Calendar aCalendar)
  {
    boolean didAddBusinessaddress = setBusinessaddress(aBusinessaddress);
    if (!didAddBusinessaddress)
    {
      throw new RuntimeException("Unable to create librarySystem due to businessaddress. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCalendar = setCalendar(aCalendar);
    if (!didAddCalendar)
    {
      throw new RuntimeException("Unable to create librarySystem due to calendar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    users = new ArrayList<User>();
    items = new ArrayList<Item>();
  }
  
  public LibrarySystem(String aSystemID, Address aBusinessaddress, Calendar aCalendar)
  {
    systemID = aSystemID;
    boolean didAddBusinessaddress = setBusinessaddress(aBusinessaddress);
    if (!didAddBusinessaddress)
    {
      throw new RuntimeException("Unable to create librarySystem due to businessaddress. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCalendar = setCalendar(aCalendar);
    if (!didAddCalendar)
    {
      throw new RuntimeException("Unable to create librarySystem due to calendar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    users = new ArrayList<User>();
    items = new ArrayList<Item>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSystemID(String aSystemID)
  {
    boolean wasSet = false;
    systemID = aSystemID;
    wasSet = true;
    return wasSet;
  }

  public String getSystemID()
  {
    return systemID;
  }
  /* Code from template association_GetOne */
  public Address getBusinessaddress()
  {
    return businessaddress;
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
  /* Code from template association_SetOneToOptionalOne */
  public boolean setBusinessaddress(Address aNewBusinessaddress)
  {
    boolean wasSet = false;
    if (aNewBusinessaddress == null)
    {
      //Unable to setBusinessaddress to null, as librarySystem must always be associated to a businessaddress
      return wasSet;
    }
    
    LibrarySystem existingLibrarySystem = aNewBusinessaddress.getLibrarySystem();
    if (existingLibrarySystem != null && !equals(existingLibrarySystem))
    {
      //Unable to setBusinessaddress, the current businessaddress already has a librarySystem, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Address anOldBusinessaddress = businessaddress;
    businessaddress = aNewBusinessaddress;
    businessaddress.setLibrarySystem(this);

    if (anOldBusinessaddress != null)
    {
      anOldBusinessaddress.setLibrarySystem(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setCalendar(Calendar aNewCalendar)
  {
    boolean wasSet = false;
    if (aNewCalendar == null)
    {
      //Unable to setCalendar to null, as librarySystem must always be associated to a calendar
      return wasSet;
    }
    
    LibrarySystem existingLibrarySystem = aNewCalendar.getLibrarySystem();
    if (existingLibrarySystem != null && !equals(existingLibrarySystem))
    {
      //Unable to setCalendar, the current calendar already has a librarySystem, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Calendar anOldCalendar = calendar;
    calendar = aNewCalendar;
    calendar.setLibrarySystem(this);

    if (anOldCalendar != null)
    {
      anOldCalendar.setLibrarySystem(null);
    }
    wasSet = true;
    return wasSet;
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
    Address existingBusinessaddress = businessaddress;
    businessaddress = null;
    if (existingBusinessaddress != null)
    {
      existingBusinessaddress.delete();
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
            "  " + "businessaddress = "+(getBusinessaddress()!=null?Integer.toHexString(System.identityHashCode(getBusinessaddress())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "calendar = "+(getCalendar()!=null?Integer.toHexString(System.identityHashCode(getCalendar())):"null");
  }
}