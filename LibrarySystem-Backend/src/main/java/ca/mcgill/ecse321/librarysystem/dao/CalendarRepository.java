package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.Hour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "calendar", path = "calendar")
public interface CalendarRepository extends CrudRepository<Calendar, String>{
	/* Find the calendar from the table of calendars in the database based of a given a calendarID */
	Calendar findCalendarByCalendarID(String calendarID);
	
	/* Find the calendar from the table of calendars in the database based on a list of hours*/
	Calendar findCalendarByHourIn(List<Hour> hour);
	
	/* Checks if the calendar exists based on a calendarID */
	boolean existsByCalendarID(String calendarID);
}
