/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;

// line 41 "../../../../../librarysystem.ump"
public class Archive extends Item
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Archive> archivesByArchiveID = new HashMap<String, Archive>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Archive Attributes
  private String archiveID;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Archive(Status aStatus, int aItemBarcode, LibrarySystem aLibrarySystem, Title aTitle, String aArchiveID)
  {
    super(aStatus, aItemBarcode, aLibrarySystem, aTitle);
    if (!setArchiveID(aArchiveID))
    {
      throw new RuntimeException("Cannot create due to duplicate archiveID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setArchiveID(String aArchiveID)
  {
    boolean wasSet = false;
    String anOldArchiveID = getArchiveID();
    if (anOldArchiveID != null && anOldArchiveID.equals(aArchiveID)) {
      return true;
    }
    if (hasWithArchiveID(aArchiveID)) {
      return wasSet;
    }
    archiveID = aArchiveID;
    wasSet = true;
    if (anOldArchiveID != null) {
      archivesByArchiveID.remove(anOldArchiveID);
    }
    archivesByArchiveID.put(aArchiveID, this);
    return wasSet;
  }

  public String getArchiveID()
  {
    return archiveID;
  }
  /* Code from template attribute_GetUnique */
  public static Archive getWithArchiveID(String aArchiveID)
  {
    return archivesByArchiveID.get(aArchiveID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithArchiveID(String aArchiveID)
  {
    return getWithArchiveID(aArchiveID) != null;
  }

  public void delete()
  {
    archivesByArchiveID.remove(getArchiveID());
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "archiveID" + ":" + getArchiveID()+ "]";
  }
}