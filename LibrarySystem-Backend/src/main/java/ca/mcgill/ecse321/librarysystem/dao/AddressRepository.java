package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "address_data", path = "address_data")
public interface AddressRepository extends CrudRepository<Address, String>{

	/* Returns the address object associated with a given addressID */
	Address findByAddressID(String addressID);
	
	/* The following set of methods returns a list of addresses based on the civic number, street, city, postal code, province or country */
	List<Address> findAddressByCivicNumber(String civicNumber);
	List<Address> findAddressByStreet(String street);
	List<Address> findAddressByCity(String city);
	List<Address> findAddressByPostalCode(String postalCode);
	List<Address> findAddressByProvince(String province);
	List<Address> findAddressByCountry(String country);
	
	/* Returns a list of addresses corresponding with the given civic number, street, city, postal code, province and country */
	List<Address> findAddressByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(String CivicNumber, String street, String city, String postalCode, String province, String country);
	
	/* Checks if an address exists in the database based on an addressID */
	boolean existsByAddressID(String addressID);
	
	/* Checks if an address exists in the database based on civic number, street, city, postal code, province and country */
	boolean existsByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(String CivicNumber, String street, String city, String postalCode, String province, String country);
}
