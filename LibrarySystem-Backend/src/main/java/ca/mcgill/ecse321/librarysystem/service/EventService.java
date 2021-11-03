package ca.mcgill.ecse321.librarysystem.service;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.EventRepository;
import ca.mcgill.ecse321.librarysystem.model.Event;
import ca.mcgill.ecse321.librarysystem.model.Hour;
import ca.mcgill.ecse321.librarysystem.model.Title;


@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	
	  @Transactional
	    public Event createEvent(String aEventID, String aName, Date aEventDate, Hour aEventhour) {
		  if ( aEventID == null ) throw new IllegalArgumentException("Please enter a valid id");
		  if(aName == null)throw new IllegalArgumentException("Please enter a valid name");
		  if (aEventDate == null) throw new IllegalArgumentException("Please enter a valid date");
	      if( aEventhour == null )throw new IllegalArgumentException("Please enter a valid hour");
		  Event newEvent = new Event (aEventID, aName,aEventDate, aEventhour);
		  eventRepository.save(newEvent);
	      return newEvent;
				  
		  
	  }
	  @Transactional
	    public Event createEvent(String aName, Date aEventDate, Hour aEventhour) {
			  if   (aName == null)throw new IllegalArgumentException("Please enter a valid name");
				  if (aEventDate == null) throw new IllegalArgumentException("Please enter a valid date");
				  if( aEventhour == null )throw new IllegalArgumentException("Please enter a valid hour");
				  Event newEvent = new Event (aName,aEventDate, aEventhour);
				  eventRepository.save(newEvent);
				  return newEvent;
				  
		  
	  }
	  
	  @Transactional
	    public Event createEvent() {
		
				  Event newEvent = new Event ();
				  eventRepository.save(newEvent);
				  return newEvent;
				  
		  
	  }
	  
	  
	  @Transactional
	  public Event getEventByDate(Date inputDate) {
		  return (Event) eventRepository.findByEventDate(inputDate); 
		  
		  
	  }
	  
	  @Transactional 
	  public Event getEventByID (String ID) {
		  return (Event) eventRepository.findByEventID(ID);
	  }
	  
	  @Transactional
	  public Event getEventByHour (Hour EHour) {
		  return (Event) eventRepository.findByEventhour(EHour);
	  }
	
	  @Transactional 
	  public List<Event> getEventlistByName (String name){
		  if (name == null || name.length() == 0) throw new IllegalArgumentException("Please enter a valid title name");
		  List<Event> events = eventRepository.findByName(name);
	        if (events.size() == 0) throw new NullPointerException("Events not found");
	        return events;
		  
	  }
	  
	  @Transactional
	  public void  deleteEventbyDate (Date inputDate) {
		  Event myEvent = eventRepository.findByEventDate(inputDate);
		  eventRepository.delete(myEvent);
		  myEvent.delete();
		  return;
		  
		  
	  }
	  
	  @Transactional
	  public void deleteEventByID (String ID) {
		  Event myEvent = eventRepository.findByEventID(ID);
		  eventRepository.delete(myEvent);
		  myEvent.delete();
		  return;
		  
	  }
	  
	  @Transactional
	  public void deleteEventbyHour (Hour DHour) {
		  Event myEvent = eventRepository.findByEventhour(DHour);
		  eventRepository.delete(myEvent);
		  myEvent.delete();
		  return;
		  
		  
		  
		  
		  
	  }
	  
	  @Transactional
	  public boolean deleteEventByName (String name) {
		  if (name == null || name.length() == 0) throw new IllegalArgumentException("Please enter a valid event name");
	        List<Event> events = eventRepository.findByName(name);
	        if (events.size() == 0) throw new NullPointerException("Events not found");
	        for (Event event : events) {
	            
	            eventRepository.delete(event);
	            event.delete();
	        }
	        List<Event> event = eventRepository.findByName(name);
	        return (event.size() == 0);
	  }
	  
	  @Transactional 
	  public boolean updateEventdateE (Date DAY) {
		  if (DAY==null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d ");
		  Event myEvent =  eventRepository.findByEventDate(DAY);
		  return myEvent.setEventDate(DAY);
	  }
	  
	  
	  @Transactional
	  public boolean updateEventHour (Hour hour) {
		  if (hour==null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d ");
		  Event myEvent =  eventRepository.findByEventhour(hour);
		  return myEvent.setEventhour(hour);
	  }
	  
	  @Transactional 
	  public boolean updateEventNameByDayandHour (Date DAY, String updateName) {
		  if (DAY==null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d ");
		  Event myEvent = eventRepository.findByEventDate(DAY);
		  return myEvent.setName(updateName);

		  
	  }

}
