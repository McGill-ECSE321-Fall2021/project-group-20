/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.Entity;

// line 30 "../../../../../librarysystem.ump"
@Entity
public class Movie extends Item
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Movie Attributes
  private int length;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Movie() {}
  
  public Movie(Status aStatus, Title aTitle, int aLength)
  {
    super(aStatus, aTitle);
    length = aLength;
  }
  
  public Movie(Status aStatus, long aItemBarcode, Title aTitle, int aLength)
  {
    super(aStatus, aItemBarcode, aTitle);
    length = aLength;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLength(int aLength)
  {
    boolean wasSet = false;
    length = aLength;
    wasSet = true;
    return wasSet;
  }

  public int getLength()
  {
    return length;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "length" + ":" + getLength()+ "]";
  }
}