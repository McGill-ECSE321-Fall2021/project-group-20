package ca.mcgill.ecse321.librarysystem.dto;



import java.util.Date;

public class BookingDto {
	public enum BookingType { Reservation, Borrow }

	  private String bookingID;
	  private Date startDate;
	  private Date endDate;
	  private BookingType type;
	  private ItemDto item;
	  private UserDto user;

	  
	  public BookingDto(){}

	  public BookingDto(Date aStartDate, Date aEndDate, BookingType aType, ItemDto aitem, UserDto aUser)
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
	  
	  
	  public BookingDto(String aBookingID, Date aStartDate, Date aEndDate, BookingType aType, ItemDto aitem, UserDto aUser)
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
	  public ItemDto getItem()
	  {
	    return item;
	  }
	  public UserDto getUser()
	  {
	    return user;
	  }
	  public boolean setItem(ItemDto aNewitem)
	  {
	    boolean wasSet = false;
	    if (aNewitem == null)
	    {
	      return wasSet;
	    }
	    
	    BookingDto existingBooking = aNewitem.getBooking();
	    if (existingBooking != null && !equals(existingBooking))
	    {
	      return wasSet;
	    }
	    
	    ItemDto anOlditem = item;
	    item = aNewitem;
	    item.setBooking(this);

	    if (anOlditem != null)
	    {
	      anOlditem.setBooking(null);
	    }
	    wasSet = true;
	    return wasSet;
	  }
	  public boolean setUser(UserDto aUser)
	  {
	    boolean wasSet = false;
	    if (aUser == null)
	    {
	      return wasSet;
	    }

	    if (aUser.numberOfUserbooking() >= UserDto.maximumNumberOfUserbooking())
	    {
	      return wasSet;
	    }
	    
	    UserDto existingUser = user;
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
	    ItemDto existingitem = item;
	    item = null;
	    if (existingitem != null)
	    {
	      existingitem.setBooking(null);
	    }
	    UserDto placeholderUser = user;
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
