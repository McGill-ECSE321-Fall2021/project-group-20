package ca.mcgill.ecse321.librarysystem.dto;

import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;


public class ItemDto {
	private Status status;
	private long itemBarcode;
	private TitleDto title;
	private BookingDto booking;

	public ItemDto(Item.Status astatus, long aItemBarcode, TitleDto aTitle) {
		status = astatus;
		itemBarcode = aItemBarcode;
		boolean didAddTitle = setTitle(aTitle);

		if (!didAddTitle) {
			throw new RuntimeException(
					"Unable to create item due to title. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
	}
	
	

	public boolean setStatus(Status aStatus)
	  {
	    boolean wasSet = false;
	    status = aStatus;
	    wasSet = true;
	    return wasSet;
	  }
	 
	 public boolean setItemBarcode(long aItemBarcode)
	  {
	    boolean wasSet = false;
	    itemBarcode = aItemBarcode;
	    wasSet = true;
	    return wasSet;
	  }
	 
	 public Status getStatus()
	  {
	    return status;
	  }
	 
	 public long getItemBarcode()
	  {
	    return itemBarcode;
	  }
	  /* Code from template association_GetOne */
	  /* Code from template association_GetOne */
	  public TitleDto getTitle()
	  {
	    return title;
	  }
	  /* Code from template association_GetOne */
	  
	  public BookingDto getBooking()
	  {
	    return booking;
	  }

	  public boolean hasBooking()
	  {
	    boolean has = booking != null;
	    return has;
	  }
	  /* Code from template association_SetOneToMandatoryMany */
	  public boolean setTitle(TitleDto aTitle)
	  {
	    title = aTitle;
		title.setItem(this);
		return true;
	  }
	  /* Code from template association_SetOptionalOneToOne */
	  public boolean setBooking(BookingDto aNewBooking)
	  {
	    boolean wasSet = false;
	    if (booking != null && !booking.equals(aNewBooking) && equals(booking.getItem()))
	    {
	      //Unable to setBooking, as existing booking would become an orphan
	      return wasSet;
	    }

	    booking = aNewBooking;
	    ItemDto anOldItembooked = aNewBooking != null ? aNewBooking.getItem() : null;

	    if (!this.equals(anOldItembooked))
	    {
	      if (anOldItembooked != null)
	      {
	        anOldItembooked.booking = null;
	      }
	      if (booking != null)
	      {
	        booking.setItem(this);
	      }
	    }
	    wasSet = true;
	    return wasSet;
	  }
	 
	  public void delete()
	  {
	    TitleDto placeholderTitle = title;
	    this.title = null;
	    BookingDto existingBooking = booking;
	    booking = null;
	    if (existingBooking != null)
	    {
	      existingBooking.delete();
	    }
	  }
	 
	 

}
