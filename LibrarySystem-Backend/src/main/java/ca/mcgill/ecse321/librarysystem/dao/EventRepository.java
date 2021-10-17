package ca.mcgill.ecse321.librarysystem.dao;
import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.librarysystem.model.Event; 
import ca.mcgill.ecse321.librarysystem.model.Hour;



public interface  EventRepository extends CrudRepository<Event, String> {
	Event findByEventDate(Date eventDate);
    Event findByEventID(String eventID);
    Event findByEventhour(Hour eventhour);
    List<Event> findByName(String name);
    boolean existsByEventID(String eventID);
}
