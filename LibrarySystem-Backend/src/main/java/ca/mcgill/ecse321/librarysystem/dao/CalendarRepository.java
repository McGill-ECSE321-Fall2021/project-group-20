package ca.mcgill.ecse321.librarysystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.Hour;

public interface CalendarRepository extends CrudRepository<Calendar, String>{
	
	/* Find the calendar from the table of calendarIDs in the database */
	Calendar findBycalendarID(String calendarID);
	
	/* Find the calendar based on a systemID */
	Calendar findBysystemID(String systemID);
	
	/* Find the calendar associated with list of hours */
	Calendar findBylistOfHours(List<Hour> listOfHours);
}
