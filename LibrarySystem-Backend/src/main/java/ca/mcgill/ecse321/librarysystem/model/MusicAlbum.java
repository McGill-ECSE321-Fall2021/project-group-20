/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.Entity;

// line 32 "model.ump"
// line 182 "model.ump"
@Entity
public class MusicAlbum extends Item
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MusicAlbum Attributes
  private int duration;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MusicAlbum(Status aStatus, int aItemBarcode, LibrarySystem aLibrarySystem, Title aTitle, int aDuration)
  {
    super(aStatus, aItemBarcode, aLibrarySystem, aTitle);
    duration = aDuration;
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "duration" + ":" + getDuration()+ "]";
  }
}


