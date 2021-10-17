package ca.mcgill.ecse321.librarysystem.dao;
import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.librarysystem.model.Event; 
import ca.mcgill.ecse321.librarysystem.model.Hour;



public interface  EventRepository extends CrudRepository<Event, String> {
	Event findByeventDate(Date eventDate);
    Event findByeventID(String eventID);
    Event findByeventhour(Hour eventhour);
    List<Event> findByName(String name);
	
	

}
