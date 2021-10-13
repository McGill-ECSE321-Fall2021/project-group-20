/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;

// line 34 "../../../../../librarysystem.ump"
public class MusicAlbum extends Item
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, MusicAlbum> musicalbumsByMusicID = new HashMap<String, MusicAlbum>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MusicAlbum Attributes
  private String musicID;
  private int duration;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MusicAlbum(Status aStatus, int aItemBarcode, LibrarySystem aLibrarySystem, Title aTitle, String aMusicID, int aDuration)
  {
    super(aStatus, aItemBarcode, aLibrarySystem, aTitle);
    duration = aDuration;
    if (!setMusicID(aMusicID))
    {
      throw new RuntimeException("Cannot create due to duplicate musicID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMusicID(String aMusicID)
  {
    boolean wasSet = false;
    String anOldMusicID = getMusicID();
    if (anOldMusicID != null && anOldMusicID.equals(aMusicID)) {
      return true;
    }
    if (hasWithMusicID(aMusicID)) {
      return wasSet;
    }
    musicID = aMusicID;
    wasSet = true;
    if (anOldMusicID != null) {
      musicalbumsByMusicID.remove(anOldMusicID);
    }
    musicalbumsByMusicID.put(aMusicID, this);
    return wasSet;
  }

  public boolean setDuration(int aDuration)
  {
    boolean wasSet = false;
    duration = aDuration;
    wasSet = true;
    return wasSet;
  }

  public String getMusicID()
  {
    return musicID;
  }
  /* Code from template attribute_GetUnique */
  public static MusicAlbum getWithMusicID(String aMusicID)
  {
    return musicalbumsByMusicID.get(aMusicID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithMusicID(String aMusicID)
  {
    return getWithMusicID(aMusicID) != null;
  }

  public int getDuration()
  {
    return duration;
  }

  public void delete()
  {
    musicalbumsByMusicID.remove(getMusicID());
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "musicID" + ":" + getMusicID()+ "," +
            "duration" + ":" + getDuration()+ "]";
  }
}