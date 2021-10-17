/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.Entity;

// line 26 "../../../../../librarysystem.ump"
@Entity
public class Book extends Item
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
  public Book() {}
  
  public Book(Status aStatus, String aItemBarcode, LibrarySystem aLibrarySystem, Title aTitle, String aIsbn, String aNumPages)
  {
    super(aStatus, aItemBarcode, aLibrarySystem, aTitle);
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


  public String toString()
  {
    return super.toString() + "["+
            "isbn" + ":" + getIsbn()+ "," +
            "numPages" + ":" + getNumPages()+ "]";
  }
}