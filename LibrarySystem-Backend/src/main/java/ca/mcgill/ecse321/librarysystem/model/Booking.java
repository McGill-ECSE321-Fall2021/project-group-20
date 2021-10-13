/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;
import java.sql.Date;

// line 97 "../../../../../librarysystem.ump"
public class Booking
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum BookingType { Reservation, Borrow }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Booking> bookingsByBookingID = new HashMap<String, Booking>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Booking Attributes
  private String bookingID;
  private Date start;
  private Date end;
  private BookingType type;

  //Booking Associations
  private Item itembooked;
  private User user;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Booking(String aBookingID, Date aStart, Date aEnd, BookingType aType, Item aItembooked, User aUser)
  {
    start = aStart;
    end = aEnd;
    type = aType;
    if (!setBookingID(aBookingID))
    {
      throw new RuntimeException("Cannot create due to duplicate bookingID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddItembooked = setItembooked(aItembooked);
    if (!didAddItembooked)
    {
      throw new RuntimeException("Unable to create booking due to itembooked. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddUser = setUser(aUser);
    if (!didAddUser)
    {
      throw new RuntimeException("Unable to create userbooking due to user. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBookingID(String aBookingID)
  {
    boolean wasSet = false;
    String anOldBookingID = getBookingID();
    if (anOldBookingID != null && anOldBookingID.equals(aBookingID)) {
      return true;
    }
    if (hasWithBookingID(aBookingID)) {
      return wasSet;
    }
    bookingID = aBookingID;
    wasSet = true;
    if (anOldBookingID != null) {
      bookingsByBookingID.remove(anOldBookingID);
    }
    bookingsByBookingID.put(aBookingID, this);
    return wasSet;
  }

  public boolean setStart(Date aStart)
  {
    boolean wasSet = false;
    start = aStart;
    wasSet = true;
    return wasSet;
  }

  public boolean setEnd(Date aEnd)
  {
    boolean wasSet = false;
    end = aEnd;
    wasSet = true;
    return wasSet;
  }

  public boolean setType(BookingType aType)
  {
    boolean wasSet = false;
    type = aType;
    wasSet = true;
    return wasSet;
  }

  public String getBookingID()
  {
    return bookingID;
  }
  /* Code from template attribute_GetUnique */
  public static Booking getWithBookingID(String aBookingID)
  {
    return bookingsByBookingID.get(aBookingID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithBookingID(String aBookingID)
  {
    return getWithBookingID(aBookingID) != null;
  }

  public Date getStart()
  {
    return start;
  }

  public Date getEnd()
  {
    return end;
  }

  public BookingType getType()
  {
    return type;
  }
  /* Code from template association_GetOne */
  public Item getItembooked()
  {
    return itembooked;
  }
  /* Code from template association_GetOne */
  public User getUser()
  {
    return user;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setItembooked(Item aNewItembooked)
  {
    boolean wasSet = false;
    if (aNewItembooked == null)
    {
      //Unable to setItembooked to null, as booking must always be associated to a itembooked
      return wasSet;
    }
    
    Booking existingBooking = aNewItembooked.getBooking();
    if (existingBooking != null && !equals(existingBooking))
    {
      //Unable to setItembooked, the current itembooked already has a booking, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Item anOldItembooked = itembooked;
    itembooked = aNewItembooked;
    itembooked.setBooking(this);

    if (anOldItembooked != null)
    {
      anOldItembooked.setBooking(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setUser(User aUser)
  {
    boolean wasSet = false;
    //Must provide user to userbooking
    if (aUser == null)
    {
      return wasSet;
    }

    //user already at maximum (5)
    if (aUser.numberOfUserbooking() >= User.maximumNumberOfUserbooking())
    {
      return wasSet;
    }
    
    User existingUser = user;
    user = aUser;
    if (existingUser != null && !existingUser.equals(aUser))
    {
      boolean didRemove = existingUser.removeUserbooking(this);
      if (!didRemove)
      {
        user = existingUser;
        return wasSet;
      }
    }
    user.addUserbooking(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    bookingsByBookingID.remove(getBookingID());
    Item existingItembooked = itembooked;
    itembooked = null;
    if (existingItembooked != null)
    {
      existingItembooked.setBooking(null);
    }
    User placeholderUser = user;
    this.user = null;
    if(placeholderUser != null)
    {
      placeholderUser.removeUserbooking(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "bookingID" + ":" + getBookingID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "start" + "=" + (getStart() != null ? !getStart().equals(this)  ? getStart().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "end" + "=" + (getEnd() != null ? !getEnd().equals(this)  ? getEnd().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "type" + "=" + (getType() != null ? !getType().equals(this)  ? getType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "itembooked = "+(getItembooked()!=null?Integer.toHexString(System.identityHashCode(getItembooked())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null");
  }
}