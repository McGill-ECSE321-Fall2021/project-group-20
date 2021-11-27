package ca.mcgill.ecse321.librarysystem.dto;

import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;

public class NewspaperDto extends ItemDto{
	 
	  public NewspaperDto(Status aStatus, long aItemBarcode, TitleDto aTitle, Booking aBooking)
	  {
	    super(aStatus, aItemBarcode, aTitle, aBooking);
	  }
	 
}
