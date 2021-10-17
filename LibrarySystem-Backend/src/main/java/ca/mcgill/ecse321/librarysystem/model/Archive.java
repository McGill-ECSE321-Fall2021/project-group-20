/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.Entity;

// line 38 "../../../../../librarysystem.ump"
@Entity
public class Archive extends Item
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Archive(Status aStatus, String aItemBarcode, LibrarySystem aLibrarySystem, Title aTitle)
  {
    super(aStatus, aItemBarcode, aLibrarySystem, aTitle);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}