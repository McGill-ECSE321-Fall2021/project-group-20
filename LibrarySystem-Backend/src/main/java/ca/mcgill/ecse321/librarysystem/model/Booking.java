/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

// line 91 "../../../../../librarysystem.ump"
@Entity
public class Booking
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum BookingType { Reservation, Borrow }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Booking Attributes
  @Id
  @GeneratedValue(generator="system-uuid")
  @GenericGenerator(name="system-uuid", strategy = "uuid")
  private String bookingID;
  private Date startDate;
  private Date endDate;
  private BookingType type;

  //Booking Associations
  @OneToOne(optional=true)
  private Item item;
  @ManyToOne(optional=false)
  private User user;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Booking(){}

  public Booking(Date aStartDate, Date aEndDate, BookingType aType, Item aitem, User aUser)
  {
    startDate = aStartDate;
    endDate = aEndDate;
    type = aType;
    boolean didAdditem = setItem(aitem);
    if (!didAdditem)
    {
      throw new RuntimeException("Unable to create booking due to item. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddUser = setUser(aUser);
    if (!didAddUser)
    {
      throw new RuntimeException("Unable to create userbooking due to user. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  
  
  public Booking(String aBookingID, Date aStartDate, Date aEndDate, BookingType aType, Item aitem, User aUser)
  {
    bookingID = aBookingID;
    startDate = aStartDate;
    endDate = aEndDate;
    type = aType;
    boolean didAdditem = setItem(aitem);
    if (!didAdditem)
    {
      throw new RuntimeException("Unable to create booking due to item. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
    bookingID = aBookingID;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndDate(Date aEndDate)
  {
    boolean wasSet = false;
    endDate = aEndDate;
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

  public Date getStartDate()
  {
    return startDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }

  public BookingType getType()
  {
    return type;
  }
  /* Code from template association_GetOne */
  public Item getItem()
  {
    return item;
  }
  /* Code from template association_GetOne */
  public User getUser()
  {
    return user;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setItem(Item aNewitem)
  {
    boolean wasSet = false;
    if (aNewitem == null)
    {
      //Unable to setitem to null, as booking must always be associated to a item
      return wasSet;
    }
    
    Booking existingBooking = aNewitem.getBooking();
    if (existingBooking != null && !equals(existingBooking))
    {
      //Unable to setitem, the current item already has a booking, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Item anOlditem = item;
    item = aNewitem;
    item.setBooking(this);

    if (anOlditem != null)
    {
      anOlditem.setBooking(null);
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
    Item existingitem = item;
    item = null;
    if (existingitem != null)
    {
      existingitem.setBooking(null);
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
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "type" + "=" + (getType() != null ? !getType().equals(this)  ? getType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "item = "+(getItem()!=null?Integer.toHexString(System.identityHashCode(getItem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null");
  }
}