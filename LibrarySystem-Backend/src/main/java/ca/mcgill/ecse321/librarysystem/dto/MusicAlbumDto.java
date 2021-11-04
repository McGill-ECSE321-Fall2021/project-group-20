package ca.mcgill.ecse321.librarysystem.dto;

import ca.mcgill.ecse321.librarysystem.dto.ItemDto.Status;

public class MusicAlbumDto {
	  private int duration;

	
	  public MusicAlbumDto() {}
	  
	  public MusicAlbumDto(Status aStatus, TitleDto aTitle, int aDuration)
	  {
	    super(aStatus, aTitle);
	    duration = aDuration;
	  }
	  
	  public MusicAlbumDto(Status aStatus, long aItemBarcode, TitleDto aTitle, int aDuration)
	  {
	    super(aStatus, aItemBarcode, aTitle);
	    duration = aDuration;
	  }
	
	  public boolean setDuration(int aDuration)
	  {
	    boolean wasSet = false;
	    duration = aDuration;
	    wasSet = true;
	    return wasSet;
	  }
	  
	  public int getDuration()
	  {
	    return duration;
	  }
}
