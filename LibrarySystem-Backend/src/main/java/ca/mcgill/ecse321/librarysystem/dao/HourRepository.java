package ca.mcgill.ecse321.librarysystem.dao;
import org.springframework.data.repository.CrudRepository;

import java.sql.Time;
import java.util.List;

import ca.mcgill.ecse321.librarysystem.model.Hour;
import ca.mcgill.ecse321.librarysystem.model.Employee; 
import ca.mcgill.ecse321.librarysystem.model.Event; 
import ca.mcgill.ecse321.librarysystem.model.Calendar; 

public interface HourRepository extends CrudRepository <Hour,String> {
	List<Hour> findBycalendar(Calendar calendar);
	List<Hour> findByemployee(Employee employee);
	List<Hour> findByendTime(Time endTime);
	Hour findByevent(Event event);
	List<Hour> findBystartTime(Time startTime);
	Hour findByweekday(String weekday);
	

}
