package ca.mcgill.ecse321.librarysystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;

public interface AddressRepository extends CrudRepository<Address, String>{
	
	/* Returns an address object corresponding to the given attribute */
	Address findByAddressID(String addressID);
	List<Address> findAddressByCivicNumber(String civicNumber);
	List<Address> findAddressByStreet(String street);
	List<Address> findAddressByCity(String city);
	List<Address> findAddressByPostalCode(String postalCode);
	List<Address> findAddressByProvince(String province);
	List<Address> findAddressByCountry(String country);
	List<Address> findAddressByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(String CivicNumber, String street, String city, String postalCode, String province, String country);
	Address findAddressByLibrarySystem(LibrarySystem librarySystem);
	boolean existsByAddressID(String addressID);
	boolean existsByLibrarySystem(LibrarySystem librarySystem);
	boolean existsByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(String CivicNumber, String street, String city, String postalCode, String province, String country);
}
