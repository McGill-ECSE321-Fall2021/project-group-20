package ca.mcgill.ecse321.librarysystem.dao;
import org.springframework.data.repository.CrudRepository;

import java.sql.Time;
import java.util.List;

import ca.mcgill.ecse321.librarysystem.model.Hour;
import ca.mcgill.ecse321.librarysystem.model.Employee; 
import ca.mcgill.ecse321.librarysystem.model.Event; 
import ca.mcgill.ecse321.librarysystem.model.Calendar; 

public interface HourRepository extends CrudRepository <Hour,String> {
	//Find a list of hours linked to a  calendar
	List<Hour> findBycalendar(Calendar calendar);
	
	//Find a list of hours linked to a employee
	List<Hour> findByemployee(Employee employee);
	
	//Find a list of hours by their end time
	List<Hour> findByendTime(Time endTime);
	
	//Find hour associated to an event
	Hour getByevent(Event event);
	
	//Find a list of hours by their start time
	List<Hour> findBystartTime(Time startTime);
	
	//Find hour for a weekday
	Hour findByweekday(String weekday);
	
	//Checks if hour exists for an event
	boolean existsByevent(Event event);
	
	//Checks if a hour exists for a weekday
	boolean existsByweekday(String weekday);
}

