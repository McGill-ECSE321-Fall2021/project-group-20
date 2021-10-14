package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.Entity;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


// line 37 "model.ump"
// line 192 "model.ump"
@Entity
public class Archive extends Item
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Archive(Status aStatus, int aItemBarcode, LibrarySystem aLibrarySystem, Title aTitle)
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


