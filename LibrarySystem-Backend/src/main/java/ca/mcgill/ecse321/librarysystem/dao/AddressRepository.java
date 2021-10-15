package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Address;

public interface AddressRepository extends CrudRepository<Address, String>{
	
	/* Returns an address object corresponding to the given addressID */
	Address findByaddressID(String addressID);

}
