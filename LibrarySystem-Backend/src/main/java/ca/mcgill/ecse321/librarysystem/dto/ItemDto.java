//package ca.mcgill.ecse321.librarysystem.dto;
//
//import ca.mcgill.ecse321.librarysystem.model.Title;
//import ca.mcgill.ecse321.librarysystem.model.Item.Status;
//
//public class ItemDto {
//	public enum Status {
//		Available, Reserved, Borrowed, Damaged
//	}
//
//	private Status status;
//	private long itemBarcode;
//	private TitleDto titleDto;
//	private BookingDto bookingDto;
//
//	public ItemDto(Status aStatus, long aItemBarcode, Title aTitle) {
//		status = aStatus;
//		itemBarcode = aItemBarcode;
//		boolean didAddTitle = setTitleDto(aTitle);
//
//		if (!didAddTitle) {
//			throw new RuntimeException(
//					"Unable to create item due to title. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
//		}
//
//	}
//	
//	 public boolean setStatusDto(Status aStatus)
//	  {
//	    boolean wasSet = false;
//	    status = aStatus;
//	    wasSet = true;
//	    return wasSet;
//	  }
//	 
//	 public boolean setItemBarcode(long aItemBarcode)
//	  {
//	    boolean wasSet = false;
//	    itemBarcode = aItemBarcode;
//	    wasSet = true;
//	    return wasSet;
//	  }
//	 
//	 public Status getStatus()
//	  {
//	    return status;
//	  }
//
//}
