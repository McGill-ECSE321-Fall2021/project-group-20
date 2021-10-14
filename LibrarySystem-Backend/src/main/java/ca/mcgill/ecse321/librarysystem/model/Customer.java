/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;

import javax.persistence.Entity;

// line 66 "model.ump"
// line 211 "model.ump"
@Entity
public class Customer extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(boolean aIsOnlineAcc, String aFirstName, String aLastName, int aLibraryCardID, boolean aIsVerified, int aDemeritPts, Address aAddress, LibrarySystem aLibrarySystem)
  {
    super(aIsOnlineAcc, aFirstName, aLastName, aLibraryCardID, aIsVerified, aDemeritPts, aAddress, aLibrarySystem);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}


