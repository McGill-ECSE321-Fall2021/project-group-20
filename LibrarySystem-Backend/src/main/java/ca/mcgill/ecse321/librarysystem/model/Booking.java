/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
  @GeneratedValue
  private String bookingID;
  private Date startDate;
  private Date endDate;
  private BookingType type;

  //Booking Associations
  @OneToOne(mappedBy="booking")
  private Item itembooked;
  @ManyToOne(optional=false)
  private User user;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Booking(String aBookingID, Date aStartDate, Date aEndDate, BookingType aType, Item aItembooked, User aUser)
  {
    bookingID = aBookingID;
    startDate = aStartDate;
    endDate = aEndDate;
    type = aType;
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
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "type" + "=" + (getType() != null ? !getType().equals(this)  ? getType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "itembooked = "+(getItembooked()!=null?Integer.toHexString(System.identityHashCode(getItembooked())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null");
  }
}