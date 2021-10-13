/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;

// line 38 "../../../../../librarysystem.ump"
public class Newspaper extends Item
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Newspaper> newspapersByNewsID = new HashMap<String, Newspaper>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Newspaper Attributes
  private String newsID;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Newspaper(Status aStatus, int aItemBarcode, LibrarySystem aLibrarySystem, Title aTitle, String aNewsID)
  {
    super(aStatus, aItemBarcode, aLibrarySystem, aTitle);
    if (!setNewsID(aNewsID))
    {
      throw new RuntimeException("Cannot create due to duplicate newsID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNewsID(String aNewsID)
  {
    boolean wasSet = false;
    String anOldNewsID = getNewsID();
    if (anOldNewsID != null && anOldNewsID.equals(aNewsID)) {
      return true;
    }
    if (hasWithNewsID(aNewsID)) {
      return wasSet;
    }
    newsID = aNewsID;
    wasSet = true;
    if (anOldNewsID != null) {
      newspapersByNewsID.remove(anOldNewsID);
    }
    newspapersByNewsID.put(aNewsID, this);
    return wasSet;
  }

  public String getNewsID()
  {
    return newsID;
  }
  /* Code from template attribute_GetUnique */
  public static Newspaper getWithNewsID(String aNewsID)
  {
    return newspapersByNewsID.get(aNewsID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithNewsID(String aNewsID)
  {
    return getWithNewsID(aNewsID) != null;
  }

  public void delete()
  {
    newspapersByNewsID.remove(getNewsID());
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "newsID" + ":" + getNewsID()+ "]";
  }
}