/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;

// line 26 "../../../../../librarysystem.ump"
public class Book extends Item
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Book> booksByIsbn = new HashMap<String, Book>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Book Attributes
  private String isbn;
  private String numPages;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Book(Status aStatus, int aItemBarcode, LibrarySystem aLibrarySystem, Title aTitle, String aIsbn, String aNumPages)
  {
    super(aStatus, aItemBarcode, aLibrarySystem, aTitle);
    numPages = aNumPages;
    if (!setIsbn(aIsbn))
    {
      throw new RuntimeException("Cannot create due to duplicate isbn. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsbn(String aIsbn)
  {
    boolean wasSet = false;
    String anOldIsbn = getIsbn();
    if (anOldIsbn != null && anOldIsbn.equals(aIsbn)) {
      return true;
    }
    if (hasWithIsbn(aIsbn)) {
      return wasSet;
    }
    isbn = aIsbn;
    wasSet = true;
    if (anOldIsbn != null) {
      booksByIsbn.remove(anOldIsbn);
    }
    booksByIsbn.put(aIsbn, this);
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
  /* Code from template attribute_GetUnique */
  public static Book getWithIsbn(String aIsbn)
  {
    return booksByIsbn.get(aIsbn);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithIsbn(String aIsbn)
  {
    return getWithIsbn(aIsbn) != null;
  }

  public String getNumPages()
  {
    return numPages;
  }

  public void delete()
  {
    booksByIsbn.remove(getIsbn());
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "isbn" + ":" + getIsbn()+ "," +
            "numPages" + ":" + getNumPages()+ "]";
  }
}