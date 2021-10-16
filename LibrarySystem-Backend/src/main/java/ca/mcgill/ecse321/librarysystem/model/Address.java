/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

// line 75 "../../../../../librarysystem.ump"
@Entity
public class Address
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Address Attributes
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
  private String addressID;
  private int civicNumber;
  private String street;
  private String city;
  private String postalCode;
  private String province;
  private String country;

  //Address Associations
  @OneToOne(mappedBy="businessaddress",fetch=FetchType.EAGER)
  private LibrarySystem librarySystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  
  
  public Address(){}
	
  public Address(int aCivicNumber, String aStreet, String aCity, String aPostalCode, String aProvince, String aCountry)
  {
    civicNumber = aCivicNumber;
    street = aStreet;
    city = aCity;
    postalCode = aPostalCode;
    province = aProvince;
    country = aCountry;
  }
  
  public Address(String aAddressID, int aCivicNumber, String aStreet, String aCity, String aPostalCode, String aProvince, String aCountry)
  {
    addressID = aAddressID;
    civicNumber = aCivicNumber;
    street = aStreet;
    city = aCity;
    postalCode = aPostalCode;
    province = aProvince;
    country = aCountry;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAddressID(String aAddressID)
  {
    boolean wasSet = false;
    addressID = aAddressID;
    wasSet = true;
    return wasSet;
  }

  public boolean setCivicNumber(int aCivicNumber)
  {
    boolean wasSet = false;
    civicNumber = aCivicNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setStreet(String aStreet)
  {
    boolean wasSet = false;
    street = aStreet;
    wasSet = true;
    return wasSet;
  }

  public boolean setCity(String aCity)
  {
    boolean wasSet = false;
    city = aCity;
    wasSet = true;
    return wasSet;
  }

  public boolean setPostalCode(String aPostalCode)
  {
    boolean wasSet = false;
    postalCode = aPostalCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setProvince(String aProvince)
  {
    boolean wasSet = false;
    province = aProvince;
    wasSet = true;
    return wasSet;
  }

  public boolean setCountry(String aCountry)
  {
    boolean wasSet = false;
    country = aCountry;
    wasSet = true;
    return wasSet;
  }

  public String getAddressID()
  {
    return addressID;
  }

  public int getCivicNumber()
  {
    return civicNumber;
  }

  public String getStreet()
  {
    return street;
  }

  public String getCity()
  {
    return city;
  }

  public String getPostalCode()
  {
    return postalCode;
  }

  public String getProvince()
  {
    return province;
  }

  public String getCountry()
  {
    return country;
  }
  /* Code from template association_GetOne */
  public LibrarySystem getLibrarySystem()
  {
    return librarySystem;
  }

  public boolean hasLibrarySystem()
  {
    boolean has = librarySystem != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setLibrarySystem(LibrarySystem aNewLibrarySystem)
  {
    boolean wasSet = false;
    if (librarySystem != null && !librarySystem.equals(aNewLibrarySystem) && equals(librarySystem.getBusinessaddress()))
    {
      //Unable to setLibrarySystem, as existing librarySystem would become an orphan
      return wasSet;
    }

    librarySystem = aNewLibrarySystem;
    Address anOldBusinessaddress = aNewLibrarySystem != null ? aNewLibrarySystem.getBusinessaddress() : null;

    if (!this.equals(anOldBusinessaddress))
    {
      if (anOldBusinessaddress != null)
      {
        anOldBusinessaddress.librarySystem = null;
      }
      if (librarySystem != null)
      {
        librarySystem.setBusinessaddress(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    LibrarySystem existingLibrarySystem = librarySystem;
    librarySystem = null;
    if (existingLibrarySystem != null)
    {
      existingLibrarySystem.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "addressID" + ":" + getAddressID()+ "," +
            "civicNumber" + ":" + getCivicNumber()+ "," +
            "street" + ":" + getStreet()+ "," +
            "city" + ":" + getCity()+ "," +
            "postalCode" + ":" + getPostalCode()+ "," +
            "province" + ":" + getProvince()+ "," +
            "country" + ":" + getCountry()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "librarySystem = "+(getLibrarySystem()!=null?Integer.toHexString(System.identityHashCode(getLibrarySystem())):"null");
  }
}