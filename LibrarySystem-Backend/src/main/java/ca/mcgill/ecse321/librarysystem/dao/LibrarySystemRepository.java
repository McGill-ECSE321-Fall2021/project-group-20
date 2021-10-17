package ca.mcgill.ecse321.librarysystem.dao;

import java.util.List;

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
	//LibrarySystem findLibrarySystemByUser(List<User> users);
	//LibrarySystem findLibrarySystemByBusinessaddress(Address businessaddress);
	//LibrarySystem findLibrarySystemByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(String
	//		cvicNumber, String street, String city, String postalCode, String province, String country);
	//LibrarySystem findLibrarySystemByItem(List<Item> items);
	//LibrarySystem findLibrarySystemByCalendar(Calendar calendar);
}
