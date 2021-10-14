/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.Entity;

// line 35 "model.ump"
// line 187 "model.ump"
@Entity
public class Newspaper extends Item
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Newspaper(Status aStatus, int aItemBarcode, LibrarySystem aLibrarySystem, Title aTitle)
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


