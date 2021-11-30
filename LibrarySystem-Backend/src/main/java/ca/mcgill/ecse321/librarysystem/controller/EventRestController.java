package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.AddressDto;
import ca.mcgill.ecse321.librarysystem.dto.CalendarDto;
import ca.mcgill.ecse321.librarysystem.dto.EmployeeDto;
import ca.mcgill.ecse321.librarysystem.dto.EventDto;
import ca.mcgill.ecse321.librarysystem.dto.HourDto;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.Employee;
import ca.mcgill.ecse321.librarysystem.model.Event;
import ca.mcgill.ecse321.librarysystem.model.Hour;
import ca.mcgill.ecse321.librarysystem.service.CalendarService;
import ca.mcgill.ecse321.librarysystem.service.EmployeeService;
import ca.mcgill.ecse321.librarysystem.service.EventService;
import ca.mcgill.ecse321.librarysystem.service.HourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class EventRestController {

	@Autowired
	private EventService eventService;

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private CalendarService calendarService;

	@Autowired
	private HourService hourService;
	
	 @GetMapping(value = {"/events", "/events/"})
	    public ResponseEntity getAllEvents() {
	        List<EventDto> eventList = new ArrayList<>();
			List<Event> events;
			try {
				events = eventService.getAllEvents();
				for (Event e : events) {
					eventList.add(convertToDto(e));
				}
				if (eventList.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any events in system");
				return new ResponseEntity<>(eventList, HttpStatus.OK);
			} catch (IllegalArgumentException | NullPointerException msg) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
			}
	    }
	 
	 @GetMapping(value = {"/event/date", "/event/date/"})
	 public ResponseEntity getEventbyDate(@RequestParam String date) {
		 try {
			 String[] dateS = date.split("/");
			 Date d = Date.valueOf(dateS[2] + "-" + dateS[0] + "-" + dateS[1]);
			 Event event = eventService.getEventByDate(d);
			 if (event == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find event by this date");
			 return new ResponseEntity<>(convertToDto(event), HttpStatus.OK);
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	 }
	 
	 @GetMapping(value = {"/event/{id}", "/event/{id}/"})
	 public ResponseEntity getEventbyId(@PathVariable("id") String id) {
		 try {
			 Event event = eventService.getEventByID(id);
			 if (event == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find event by this ID");
			 return new ResponseEntity<>(convertToDto(event), HttpStatus.OK);
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	 }
	 
	 @GetMapping(value = {"/event/hour", "/event/hour/"})
	 public ResponseEntity getEventbyHour(@RequestParam String weekday) {
		 try {
			 Hour hour = hourService.getHourbyWeekday(weekday);
			 if (hour == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find Hour");
			 Event event = eventService.getEventByHour(hour);
			 if (event == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find event by this hour");
			 return new ResponseEntity<>(convertToDto(event), HttpStatus.OK);
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	 }
	

	    @GetMapping(value = { "/events/{name}", "/events/{name}/"})
	    public ResponseEntity getEventlistByName(@PathVariable("name") String name) {
	        List<EventDto> eventDtos = new ArrayList<>();
			List<Event> events;
			try {
				events = eventService.getEventlistByName(name);
				for (Event e : eventService.getEventlistByName(name)) {
					eventDtos.add(convertToDto(e));
				}
				if (eventDtos.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any events by this name");
				return new ResponseEntity<>(eventDtos, HttpStatus.OK);
			} catch (Exception msg) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
			}
	    }
	    
	    @PostMapping(value = {"/event/create","/event/create/"})
	    public ResponseEntity createEvent(@RequestParam String name, @RequestParam String date, @RequestParam String weekday, @RequestParam String startTime,
					@RequestParam String endTime,@RequestParam String employeeUserName, @RequestParam String calendarID) {

		 try {
			 Employee e = employeeService.getEmployee(employeeUserName);
			 Calendar c = calendarService.getCalendar(calendarID);
			 Hour h = new Hour (weekday,Time.valueOf(startTime),Time.valueOf(endTime),e,c, Hour.Type.Event);
			 String[] dateS = date.split("/");
			 Date d = Date.valueOf(dateS[2] + "-" + dateS[0] + "-" + dateS[1]);
			 Event event = eventService.createEvent(name, d, h);
			 return new ResponseEntity<>(convertToDto(event), HttpStatus.OK);
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	    }
	    
	    
	    
	    @DeleteMapping(value = { "/event/date", "/event/date/" })
	    public ResponseEntity deleteEventbyDate(@RequestParam String date) {
		 try {
			 String[] dateS = date.split("/");
			 Date d = Date.valueOf(dateS[2] + "-" + dateS[0] + "-" + dateS[1]);
			 eventService.deleteEventbyDate(d);
			 return ResponseEntity.status(HttpStatus.OK).body("Event has been deleted");
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	    }
	    
	    @DeleteMapping(value = {"/event/{id}", "/event/{id}/"})
	    public ResponseEntity deleteEventByID (@PathVariable("id") String id) {
	    	try {
				eventService.deleteEventByID(id);
				return ResponseEntity.status(HttpStatus.OK).body("Event has been deleted");
			} catch (Exception msg) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
			}
	    }
	    
	    @DeleteMapping(value = {"/event/hour", "/event/hour/"})
	    public ResponseEntity deleteEventbyHour (@RequestParam String weekday) {
	   	 try {
				Hour hour = hourService.getHourbyWeekday(weekday);
				eventService.deleteEventbyHour(hour);
				return ResponseEntity.status(HttpStatus.OK).body("Event has been deleted");
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	    }

	    //Dont allow delete event by name cause that would delete all events with the same name which we don not want
	    
	    @PutMapping(value = {"/event/update/date", "/event/update/date/"})
	    public ResponseEntity updateEventdate(@RequestParam String id, @RequestParam String date) {
		 try {
			 String[] dateS = date.split("/");
			 Date d = Date.valueOf(dateS[2] + "-" + dateS[0] + "-" + dateS[1]);
			 if (eventService.updateEventdate(id, d)) {
				 EventDto eventDto = convertToDto(eventService.getEventByID(id));
				 return new ResponseEntity<>(eventDto, HttpStatus.OK);
			 }
			 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not update date");
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	    }
	    
	    
	    @PutMapping(value = {"/event/update/hour", "/event/update/hour/"})
	    public ResponseEntity updateEventHour(@RequestParam String upweekday ,@RequestParam String id, @RequestParam String upstartTime,
			@RequestParam String upendTime,@RequestParam String employeeUserName, @RequestParam String calendarID) {
	    	try {
				Employee e = employeeService.getEmployee(employeeUserName);
				Calendar c = calendarService.getCalendar(calendarID);
				Hour h = new Hour(upweekday, Time.valueOf(upstartTime), Time.valueOf(upendTime), e, c, Hour.Type.Event);
				if (eventService.updateEventHour(id, h)) return new ResponseEntity<>(convertToDto(eventService.getEventByHour(h)), HttpStatus.OK);
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not update hour");
			} catch (Exception msg) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
			}
	    }
	    
	    
	    
	    @PutMapping(value = {"/event/update/name", "/event/update/name/"})
	    public ResponseEntity updateEventNameByID(@RequestParam String id, @RequestParam String updatedName) {
	    	try {
				if (eventService.updateEventNameByID(id, updatedName)) return new ResponseEntity<>(convertToDto(eventService.getEventByID(id)), HttpStatus.OK);
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not update name");
			} catch (Exception msg) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
			}
	    }
	    
	    
	    
	private EventDto convertToDto (Event e ) {
		if (e == null) throw new IllegalArgumentException("Cannot find this event");
		return new EventDto(e.getEventID(),e.getName(),e.getEventDate(),convertToDto(e.getEventhour()));
	}
	
	private HourDto convertToDto(Hour h){
		if (h== null) throw new IllegalArgumentException("Cannot find this hour");
		return new HourDto(h.getId(), h.getEvent(), h.getWeekday(),h.getStartTime(),h.getEndTime(),
				convertToDto(h.getEmployee()),convertToDto(h.getCalendar()), HourDto.Type.valueOf(h.getType().toString()));
		
	}
	
	 private EmployeeDto convertToDto(Employee e) {
	        if (e == null) throw new IllegalArgumentException("Cannot find this Employee");
	        return new EmployeeDto(e.getLibraryCardID(), e.getIsLoggedIn(), e.getIsOnlineAcc(), e.getFirstName(),
	                e.getLastName(), e.getIsVerified(), e.getDemeritPts(), convertToDto(e.getAddress()), e.getUsername(), e.getEmail(),
					e.getOutstandingBalance(), EmployeeDto.Role.valueOf(e.getRole().name()), e.getUserbooking());
	    }

	 private AddressDto convertToDto(Address a) {
	        if (a == null) throw new NullPointerException("Cannot find Address");
	        return new AddressDto(a.getAddressID(), a.getCivicNumber(), a.getStreet(), a.getCity(), a.getPostalCode(), a.getProvince(), a.getCountry());
	    }
	
	private CalendarDto convertToDto (Calendar c) {
		if (c== null) throw new IllegalArgumentException("Cannot find this Calendar");
		return new CalendarDto(c.getCalendarID(), c.getHour());
	}
	
	
	
	
	
	
}
