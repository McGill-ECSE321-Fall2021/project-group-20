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
	
	/* Returns the library system object associated with a given user from the table of library system */
	LibrarySystem findLibrarySystemByUsers(User users);
	
	/* Returns the library system object associated with a given address from the table of library system */
	LibrarySystem findLibrarySystemByBusinessaddress(Address businessaddress);
	
	/* Returns the library system object given a item */
	LibrarySystem findLibrarySystemByItems(Item items);
	
	/* Returns the library system object associated with the given calendar */
	LibrarySystem findLibrarySystemByCalendar(Calendar calendar);
}
