package ca.mcgill.ecse321.librarysystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.Hour;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;

public interface CalendarRepository extends CrudRepository<Calendar, String>{
	
	/* Find the calendar from the table of calendarIDs in the database */
	Calendar findByCalendarID(String calendarID);
	
	/* Find the calendar based on a systemID */
	Calendar findByLibrarySystem(LibrarySystem librarySystem);;
	
	/* Find the calendar associated with list of hours */
	Calendar findByHourIn(List<Hour> hour);
	
	boolean existsByCalendarID(String calendarID);
}
