package ca.mcgill.ecse321.librarysystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.Hour;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;

public interface CalendarRepository extends CrudRepository<Calendar, String>{
	/* Find the calendar from the table of calendars in the database based of a given a calendarID */
	Calendar findCalendarByCalendarID(String calendarID);
	
	/* Find the calendar from the table of calendars in the database based on a library system */
	Calendar findCalendarByLibrarySystem(LibrarySystem librarySystem);;
	
	/* Find the calendar from the table of calendars in the database based on a list of hours*/
	Calendar findCalendarByHourIn(List<Hour> hour);
	
	/* Checks if the calendar exists based on a calendarID */
	boolean existsByCalendarID(String calendarID);
}
