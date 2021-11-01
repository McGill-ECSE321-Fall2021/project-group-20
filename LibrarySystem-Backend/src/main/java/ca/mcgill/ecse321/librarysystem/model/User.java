/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.sql.Date;

// line 48 "../../../../../librarysystem.ump"
@Entity
@Table(name="users")
@Inheritance(strategy=InheritanceType.JOINED)
public class User
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, User> usersByLibraryCardID = new HashMap<Integer, User>();
  private static Map<String, User> usersByUsername = new HashMap<String, User>();
  private static Map<String, User> usersByEmail = new HashMap<String, User>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private boolean isOnlineAcc;
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  @Id
  @GeneratedValue
  private int libraryCardID;
  private boolean isVerified;
  private int demeritPts;

  //User Associations
  @ManyToOne(optional=false)
  private Address address;
  @OneToMany(mappedBy="user")
  @LazyCollection(LazyCollectionOption.FALSE)	// To eagerly load the List of Bookings and all references instead of just the object and attributes
  private List<Booking> userbooking;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /* Default constructor */
  public User() {}
  
  /* Constructor that autogenerates a libraryCardID */
  //public User(boolean aIsOnlineAcc, String aFirstName, String aLastName, boolean aIsVerified, int aDemeritPts, Address aAddress, LibrarySystem aLibrarySystem)
  public User (boolean aIsOnlineAcc, String aFirstName, String aLastName, boolean aIsVerified, int aDemeritPts, Address aAddress)
  {
    isOnlineAcc = aIsOnlineAcc;
    username = null;
    password = null;
    email = null;
    firstName = aFirstName;
    lastName = aLastName;
    isVerified = aIsVerified;
    demeritPts = aDemeritPts;
    if (!setAddress(aAddress))
    {
      throw new RuntimeException("Unable to create User due to aAddress. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    userbooking = new ArrayList<Booking>();
  }
  
  /* Constructor that takes a libraryCardID manually */
  public User(boolean aIsOnlineAcc, String aFirstName, String aLastName, int aLibraryCardID, boolean aIsVerified, int aDemeritPts, Address aAddress)
  {
    isOnlineAcc = aIsOnlineAcc;
    username = null;
    password = null;
    email = null;
    firstName = aFirstName;
    lastName = aLastName;
    isVerified = aIsVerified;
    demeritPts = aDemeritPts;
    if (!setLibraryCardID(aLibraryCardID))
    {
      throw new RuntimeException("Cannot create due to duplicate libraryCardID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    if (!setAddress(aAddress))
    {
      throw new RuntimeException("Unable to create User due to aAddress. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    userbooking = new ArrayList<Booking>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsOnlineAcc(boolean aIsOnlineAcc)
  {
    boolean wasSet = false;
    isOnlineAcc = aIsOnlineAcc;
    wasSet = true;
    return wasSet;
  }

  public boolean setUsername(String aUsername)
  {
	  boolean wasSet = false;
	    String anOldUsername = getUsername();
	    if (anOldUsername != null && anOldUsername.equals(aUsername)) {
	      return true;
	    }
	    if (hasWithUsername(aUsername)) {
	      return wasSet;
	    }
	    username = aUsername;
	    wasSet = true;
	    if (anOldUsername != null) {
	      usersByUsername.remove(anOldUsername);
	    }
	    usersByUsername.put(aUsername, this);
	    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
	  boolean wasSet = false;
	    String anOldEmail = getEmail();
	    if (anOldEmail != null && anOldEmail.equals(aEmail)) {
	      return true;
	    }
	    if (hasWithEmail(aEmail)) {
	      return wasSet;
	    }
	    email = aEmail;
	    wasSet = true;
	    if (anOldEmail != null) {
	      usersByEmail.remove(anOldEmail);
	    }
	    usersByEmail.put(aEmail, this);
	    return wasSet;
  }

  public boolean setFirstName(String aFirstName)
  {
    boolean wasSet = false;
    firstName = aFirstName;
    wasSet = true;
    return wasSet;
  }

  public boolean setLastName(String aLastName)
  {
    boolean wasSet = false;
    lastName = aLastName;
    wasSet = true;
    return wasSet;
  }

  public boolean setLibraryCardID(int aLibraryCardID)
  {
    boolean wasSet = false;
    Integer anOldLibraryCardID = getLibraryCardID();
    if (anOldLibraryCardID != null && anOldLibraryCardID.equals(aLibraryCardID)) {
      return true;
    }
    if (hasWithLibraryCardID(aLibraryCardID)) {
      return wasSet;
    }
    libraryCardID = aLibraryCardID;
    wasSet = true;
    if (anOldLibraryCardID != null) {
      usersByLibraryCardID.remove(anOldLibraryCardID);
    }
    usersByLibraryCardID.put(aLibraryCardID, this);
    return wasSet;
  }

  public boolean setIsVerified(boolean aIsVerified)
  {
    boolean wasSet = false;
    isVerified = aIsVerified;
    wasSet = true;
    return wasSet;
  }

  public boolean setDemeritPts(int aDemeritPts)
  {
    boolean wasSet = false;
    demeritPts = aDemeritPts;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsOnlineAcc()
  {
    return isOnlineAcc;
  }
  
  /* Code from template attribute_GetUnique */
  public static User getWithUsername(String aUsername)
  {
    return usersByUsername.get(aUsername);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithUsername(String aUsername)
  {
    return getWithUsername(aUsername) != null;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }
  
  /* Code from template attribute_GetUnique */
  public static User getWithEmail(String aEmail)
  {
    return usersByEmail.get(aEmail);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithEmail(String aEmail)
  {
    return getWithEmail(aEmail) != null;
  }

  public String getEmail()
  {
    return email;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  /**
   * unique
   */
  public int getLibraryCardID()
  {
    return libraryCardID;
  }
  /* Code from template attribute_GetUnique */
  public static User getWithLibraryCardID(int aLibraryCardID)
  {
    return usersByLibraryCardID.get(aLibraryCardID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithLibraryCardID(int aLibraryCardID)
  {
    return getWithLibraryCardID(aLibraryCardID) != null;
  }

  public boolean getIsVerified()
  {
    return isVerified;
  }

  public int getDemeritPts()
  {
    return demeritPts;
  }
  /* Code from template association_GetOne */
  public Address getAddress()
  {
    return address;
  }
  /* Code from template association_GetMany */
  public Booking getUserbooking(int index)
  {
    Booking aUserbooking = userbooking.get(index);
    return aUserbooking;
  }

  public List<Booking> getUserbooking()
  {
    List<Booking> newUserbooking = Collections.unmodifiableList(userbooking);
    return newUserbooking;
  }

  public int numberOfUserbooking()
  {
    int number = userbooking.size();
    return number;
  }

  public boolean hasUserbooking()
  {
    boolean has = userbooking.size() > 0;
    return has;
  }

  public int indexOfUserbooking(Booking aUserbooking)
  {
    int index = userbooking.indexOf(aUserbooking);
    return index;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setAddress(Address aNewAddress)
  {
    boolean wasSet = false;
    if (aNewAddress != null)
    {
      address = aNewAddress;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUserbooking()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfUserbooking()
  {
    return 5;
  }
  /* Code from template association_AddOptionalNToOne */
  public Booking addUserbooking(String aBookingID, Date aStartDate, Date aEndDate, Booking.BookingType aType, Item aItembooked)
  {
    if (numberOfUserbooking() >= maximumNumberOfUserbooking())
    {
      return null;
    }
    else
    {
      return new Booking(aBookingID, aStartDate, aEndDate, aType, aItembooked, this);
    }
  }

  public boolean addUserbooking(Booking aUserbooking)
  {
    boolean wasAdded = false;
    if (userbooking.contains(aUserbooking)) { return false; }
    if (numberOfUserbooking() >= maximumNumberOfUserbooking())
    {
      return wasAdded;
    }

    User existingUser = aUserbooking.getUser();
    boolean isNewUser = existingUser != null && !this.equals(existingUser);
    if (isNewUser)
    {
      aUserbooking.setUser(this);
    }
    else
    {
      userbooking.add(aUserbooking);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUserbooking(Booking aUserbooking)
  {
    boolean wasRemoved = false;
    //Unable to remove aUserbooking, as it must always have a user
    if (!this.equals(aUserbooking.getUser()))
    {
      userbooking.remove(aUserbooking);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserbookingAt(Booking aUserbooking, int index)
  {  
    boolean wasAdded = false;
    if(addUserbooking(aUserbooking))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUserbooking()) { index = numberOfUserbooking() - 1; }
      userbooking.remove(aUserbooking);
      userbooking.add(index, aUserbooking);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserbookingAt(Booking aUserbooking, int index)
  {
    boolean wasAdded = false;
    if(userbooking.contains(aUserbooking))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUserbooking()) { index = numberOfUserbooking() - 1; }
      userbooking.remove(aUserbooking);
      userbooking.add(index, aUserbooking);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserbookingAt(aUserbooking, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    usersByUsername.remove(getUsername());
    usersByEmail.remove(getEmail());
    usersByLibraryCardID.remove(getLibraryCardID());
    address = null;
    for(int i=userbooking.size(); i > 0; i--)
    {
      Booking aUserbooking = userbooking.get(i - 1);
      aUserbooking.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isOnlineAcc" + ":" + getIsOnlineAcc()+ "," +
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "," +
            "email" + ":" + getEmail()+ "," +
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "," +
            "libraryCardID" + ":" + getLibraryCardID()+ "," +
            "isVerified" + ":" + getIsVerified()+ "," +
            "demeritPts" + ":" + getDemeritPts()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "address = "+(getAddress()!=null?Integer.toHexString(System.identityHashCode(getAddress())):"null") + System.getProperties().getProperty("line.separator")
            ;
  }
}