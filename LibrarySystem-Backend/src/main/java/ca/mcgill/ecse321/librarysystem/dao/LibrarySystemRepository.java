package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;

public interface LibrarySystemRepository extends CrudRepository<LibrarySystem, String>{
	
	/* Returns a library system object given a systemID from the table of library system 
	 * the database.
	 * There should only be one library system.
	 */
	LibrarySystem findBysystemID(String systemID);
}
