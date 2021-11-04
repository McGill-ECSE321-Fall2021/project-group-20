/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.Entity;

// line 67 "../../../../../librarysystem.ump"
@Entity
public class Customer extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------


	public Customer() {}
	
	public Customer(boolean aIsOnlineAcc, boolean aIsLoggedIn, String aFirstName, String aLastName, boolean aIsVerified, int aDemeritPts, Address aAddress) {
		super(aIsOnlineAcc, aIsLoggedIn, aFirstName, aLastName, aIsVerified, aDemeritPts, aAddress);
	}
	
    public Customer(boolean aIsOnlineAcc, boolean aIsLoggedIn, String aFirstName, String aLastName, int aLibraryCardID, boolean aIsVerified, int aDemeritPts, Address aAddress)
    {
        super(aIsOnlineAcc, aIsLoggedIn, aFirstName, aLastName, aLibraryCardID, aIsVerified, aDemeritPts, aAddress);
    }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}