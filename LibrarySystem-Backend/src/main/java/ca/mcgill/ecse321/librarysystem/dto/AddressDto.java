package ca.mcgill.ecse321.librarysystem.dto;

import ca.mcgill.ecse321.librarysystem.model.Address;

public class AddressDto {

    private String addressID;
    private String civicNumber;
    private String street;
    private String city;
    private String postalCode;
    private String province;
    private String country;

    public AddressDto(){}

    public AddressDto(String aCivicNumber, String aStreet, String aCity, String aPostalCode, String aProvince, String aCountry) {
        civicNumber = aCivicNumber;
        street = aStreet;
        city = aCity;
        postalCode = aPostalCode;
        province = aProvince;
        country = aCountry;
    }

    public AddressDto(String aAddressID, String aCivicNumber, String aStreet, String aCity, String aPostalCode, String aProvince, String aCountry) {
        addressID = aAddressID;
        civicNumber = aCivicNumber;
        street = aStreet;
        city = aCity;
        postalCode = aPostalCode;
        province = aProvince;
        country = aCountry;
    }

    public boolean setAddressID(String aAddressID) {
        boolean wasSet = false;
        addressID = aAddressID;
        wasSet = true;
        return wasSet;
    }

    public boolean setCivicNumber(String aCivicNumber) {
        boolean wasSet = false;
        civicNumber = aCivicNumber;
        wasSet = true;
        return wasSet;
    }

    public boolean setStreet(String aStreet) {
        boolean wasSet = false;
        street = aStreet;
        wasSet = true;
        return wasSet;
    }

    public boolean setCity(String aCity) {
        boolean wasSet = false;
        city = aCity;
        wasSet = true;
        return wasSet;
    }

    public boolean setPostalCode(String aPostalCode) {
        boolean wasSet = false;
        postalCode = aPostalCode;
        wasSet = true;
        return wasSet;
    }

    public boolean setProvince(String aProvince) {
        boolean wasSet = false;
        province = aProvince;
        wasSet = true;
        return wasSet;
    }

    public boolean setCountry(String aCountry) {
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
}