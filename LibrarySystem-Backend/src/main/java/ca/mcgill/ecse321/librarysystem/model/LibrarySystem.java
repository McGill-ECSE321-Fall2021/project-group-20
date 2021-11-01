/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
  @OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
  @JoinColumn
  private Address businessaddress;
  
  @OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
  @JoinColumn
  private Calendar calendar;
  
  @OneToMany(cascade={CascadeType.ALL}, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @OrderColumn(name="libraryCardID")
  private List<User> users;
  
  @OneToMany(cascade={CascadeType.ALL})
  @LazyCollection(LazyCollectionOption.FALSE)
  @OrderColumn(name="itemBarcode")
  private List<Item> items;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LibrarySystem(){
	  users = new ArrayList<User>();
	  items = new ArrayList<Item>();
  }
  
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
    this.businessaddress=aNewBusinessaddress;
    return true;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setCalendar(Calendar aNewCalendar)
  {
    boolean wasSet = false;
    
    Calendar anOldCalendar = calendar;
    calendar = aNewCalendar;
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
    return new User(aIsOnlineAcc, aFirstName, aLastName, aLibraryCardID, aIsVerified, aDemeritPts, aAddress);
  }

  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
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
      users.remove(aUser);
      wasRemoved = true;
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
  public Item addItem(Item.Status aStatus, long aItemBarcode, Title aTitle)
  {
    return new Item(aStatus, aItemBarcode, aTitle);
  }

  public boolean addItem(Item aItem)
  {
    boolean wasAdded = false;
    if (items.contains(aItem)) { return false; }
      items.add(aItem);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeItem(Item aItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aItem, as it must always have a librarySystem
      items.remove(aItem);
      wasRemoved = true;
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