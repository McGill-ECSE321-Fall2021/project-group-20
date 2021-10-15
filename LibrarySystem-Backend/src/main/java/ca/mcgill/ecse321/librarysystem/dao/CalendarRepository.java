package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Calendar;

public interface CalendarRepository extends CrudRepository<Calendar, String>{
	
	/* Find the calendar from the table of calendarIDs in the database */
	Calendar findBycalendarID(String calendarID);

}
