package ca.mcgill.ecse321.librarysystem.dto;

import ca.mcgill.ecse321.librarysystem.model.Item.Status;

public class ArchiveDto extends ItemDto{
	 
	  public ArchiveDto(Status aStatus, long aItemBarcode, TitleDto aTitle)
	  {
	    super(aStatus, aItemBarcode, aTitle);
	  }
	 
}
