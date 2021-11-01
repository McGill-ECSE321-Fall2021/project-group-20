/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.Entity;

// line 36 "../../../../../librarysystem.ump"
@Entity
public class Newspaper extends Item
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Newspaper() {}
  
  public Newspaper(Status aStatus, Title aTitle)
  {
    super(aStatus, aTitle);
  }
	
  public Newspaper(Status aStatus, long aItemBarcode, Title aTitle)
  {
    super(aStatus, aItemBarcode, aTitle);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}