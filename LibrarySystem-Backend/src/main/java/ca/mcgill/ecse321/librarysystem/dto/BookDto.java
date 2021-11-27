/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.dto;


import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;


public class BookDto extends ItemDto
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Book Attributes
  private String isbn;
  private String numPages;

  //------------------------
  // CONSTRUCTOR
  //------------------------
 

  
  public BookDto(Status aStatus, long aItemBarcode, TitleDto aTitle, String aIsbn, String aNumPages, Booking aBooking)
  {
    super(aStatus, aItemBarcode, aTitle, aBooking);
    isbn = aIsbn;
    numPages = aNumPages;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsbn(String aIsbn)
  {
    boolean wasSet = false;
    isbn = aIsbn;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumPages(String aNumPages)
  {
    boolean wasSet = false;
    numPages = aNumPages;
    wasSet = true;
    return wasSet;
  }

  public String getIsbn()
  {
    return isbn;
  }

  public String getNumPages()
  {
    return numPages;
  }

  public void delete()
  {
    super.delete();
  }


}