/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;

// line 30 "../../../../../librarysystem.ump"
public class Movie extends Item
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Movie> moviesByMovieID = new HashMap<String, Movie>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Movie Attributes
  private String movieID;
  private int length;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Movie(Status aStatus, int aItemBarcode, LibrarySystem aLibrarySystem, Title aTitle, String aMovieID, int aLength)
  {
    super(aStatus, aItemBarcode, aLibrarySystem, aTitle);
    length = aLength;
    if (!setMovieID(aMovieID))
    {
      throw new RuntimeException("Cannot create due to duplicate movieID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMovieID(String aMovieID)
  {
    boolean wasSet = false;
    String anOldMovieID = getMovieID();
    if (anOldMovieID != null && anOldMovieID.equals(aMovieID)) {
      return true;
    }
    if (hasWithMovieID(aMovieID)) {
      return wasSet;
    }
    movieID = aMovieID;
    wasSet = true;
    if (anOldMovieID != null) {
      moviesByMovieID.remove(anOldMovieID);
    }
    moviesByMovieID.put(aMovieID, this);
    return wasSet;
  }

  public boolean setLength(int aLength)
  {
    boolean wasSet = false;
    length = aLength;
    wasSet = true;
    return wasSet;
  }

  public String getMovieID()
  {
    return movieID;
  }
  /* Code from template attribute_GetUnique */
  public static Movie getWithMovieID(String aMovieID)
  {
    return moviesByMovieID.get(aMovieID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithMovieID(String aMovieID)
  {
    return getWithMovieID(aMovieID) != null;
  }

  public int getLength()
  {
    return length;
  }

  public void delete()
  {
    moviesByMovieID.remove(getMovieID());
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "movieID" + ":" + getMovieID()+ "," +
            "length" + ":" + getLength()+ "]";
  }
}