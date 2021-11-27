package ca.mcgill.ecse321.librarysystem.dto;

import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;

public class MovieDto extends ItemDto {
	private int length;
	
	  
	  public MovieDto(Status aStatus, long aItemBarcode, TitleDto aTitle, int aLength, Booking aBooking)
	  {
	    super(aStatus, aItemBarcode, aTitle, aBooking);
	    length = aLength;
	  }
	  
	  public boolean setLength(int aLength)
	  {
	    boolean wasSet = false;
	    length = aLength;
	    wasSet = true;
	    return wasSet;
	  }
	  
	  public int getLength()
	  {
	    return length;
	  }

	  
}
