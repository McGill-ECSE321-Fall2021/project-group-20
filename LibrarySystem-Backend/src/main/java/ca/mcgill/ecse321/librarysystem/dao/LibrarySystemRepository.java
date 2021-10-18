package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;
import ca.mcgill.ecse321.librarysystem.model.User;

public interface LibrarySystemRepository extends CrudRepository<LibrarySystem, String>{
	
	/* Returns a library system object given a systemID from the table of library system 
	 * the database.
	 * There should only be one library system.
	 */
	LibrarySystem findLibrarySystemBySystemID(String systemID);
	LibrarySystem findLibrarySystemByUsers(User users);
	LibrarySystem findLibrarySystemByBusinessaddress(Address businessaddress);
//	LibrarySystem findLibrarySystemByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(String
//			civicNumber, String street, String city, String postalCode, String province, String country);
	LibrarySystem findLibrarySystemByItems(Item items);
	LibrarySystem findLibrarySystemByCalendar(Calendar calendar);
}
