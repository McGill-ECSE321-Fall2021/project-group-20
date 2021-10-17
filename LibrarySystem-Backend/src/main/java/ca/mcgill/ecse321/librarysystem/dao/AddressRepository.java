package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Address;

public interface AddressRepository extends CrudRepository<Address, String>{
	
	/* Returns an address object corresponding to the given attribute */
	Address findByaddressID(String addressID);
	Address findBycivicNumber(int CivicNumber);
	Address findBystreet(String street);
	Address findBycity(String city);
	Address findBypostalCode(String postalCode);
	Address findByprovince(String province);
	Address findBycountry(String country);
	Address findBysystemID(String systemID);
}
