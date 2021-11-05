package ca.mcgill.ecse321.librarysystem.dao;
import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.librarysystem.model.Event; 
import ca.mcgill.ecse321.librarysystem.model.Hour;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "event_data", path = "event_data")
public interface  EventRepository extends CrudRepository<Event, String> {
	//Find Event by event date
	Event findByEventDate(Date eventDate);
	
	//Find Event by Event ID
    Event findByEventID(String eventID);
    
    //Find Event by specific Event Hour
    Event findByEventhour(Hour eventhour);
    
    //Find Event by Event name
    List<Event> findByName(String name);
    
    //Checks if event exists with the specific event ID
    boolean existsByEventID(String eventID);
}
