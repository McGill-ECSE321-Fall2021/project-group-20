/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
  private String civicNumber;
  private String street;
  private String city;
  private String postalCode;
  private String province;
  private String country;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Address(){}
	
  public Address(String aCivicNumber, String aStreet, String aCity, String aPostalCode, String aProvince, String aCountry)
  {
    civicNumber = aCivicNumber;
    street = aStreet;
    city = aCity;
    postalCode = aPostalCode;
    province = aProvince;
    country = aCountry;
  }
  
  public Address(String aAddressID, String aCivicNumber, String aStreet, String aCity, String aPostalCode, String aProvince, String aCountry)
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

  public boolean setCivicNumber(String aCivicNumber)
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

  public String getCivicNumber()
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

  public String toString()
  {
    return super.toString() + "["+
            "addressID" + ":" + getAddressID()+ "," +
            "civicNumber" + ":" + getCivicNumber()+ "," +
            "street" + ":" + getStreet()+ "," +
            "city" + ":" + getCity()+ "," +
            "postalCode" + ":" + getPostalCode()+ "," +
            "province" + ":" + getProvince()+ "," +
            "country" + ":" + getCountry()+ "]" + System.getProperties().getProperty("line.separator")
     ;
  }
}