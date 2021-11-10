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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
	
	 @GetMapping(value = {"/events", "/events/"})
	    public List<EventDto> getAllEvents() {
	        List<EventDto> eventList = new ArrayList<>();
	        for (Event e : eventService.getAllEvents()) {
	            eventList.add(convertToDto(e));
	        }
	        return eventList;
	    }
	 
	 @GetMapping(value = {"/event/date", "/event/date/"})
	 public EventDto getEventbyDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  String date) {
		 return convertToDto(eventService.getEventByDate(Date.valueOf(date)));
		 
	 }
	 
	 @GetMapping(value = {"/event/{id}", "/event/{id}/"})
	 public EventDto getEventbyId(@PathVariable("id") String id) {
		 return convertToDto(eventService.getEventByID(id));
	 }
	 
	 @GetMapping(value = {"/event/hour", "/event/hour/"})
	 public EventDto getEventbyHour(@RequestParam String weekday, @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") String startTime,
			 						@RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") String endTime,@RequestParam String employeeUsername, @RequestParam String calendarID) {
		 Employee e = employeeService.getEmployee(employeeUsername);
		 Calendar c = calendarService.getCalendar(calendarID);
		 Hour h = new Hour (weekday,Time.valueOf(startTime),Time.valueOf(endTime),e,c);
		 return convertToDto(eventService.getEventByHour((h)));
		 
		 
	 }
	

	    @GetMapping(value = { "/events/name", "/events/name/"})
	    public List<EventDto> getEventlistByName(@RequestParam String name) {
	        List<EventDto> eventDtos = new ArrayList<>();
	        for (Event e : eventService.getEventlistByName(name)) {
	            eventDtos.add(convertToDto(e));
	        }
	        return eventDtos;
	    }
	    
	    @PostMapping(value = {"/event/create","/event/create/"})
	    public EventDto createEvent(@RequestParam String name, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  String date,String weekday, @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") String startTime,
					@RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") String endTime,@RequestParam String employeeUserName, @RequestParam String calendarID) {
	    	 Employee e = employeeService.getEmployee(employeeUserName);
			 Calendar c = calendarService.getCalendar(calendarID);
			 Hour h = new Hour (weekday,Time.valueOf(startTime),Time.valueOf(endTime),e,c);
	    	return convertToDto(eventService.createEvent(name,Date.valueOf(date) , h));
	    }
	    
	    
	    
	    @DeleteMapping(value = { "/event/date", "/event/date/" })
	    public void deleteEventbyDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  String date) throws IllegalArgumentException, NullPointerException {
	          eventService.deleteEventbyDate(Date.valueOf(date)); 
	          return;
	
	    }
	    
	    @DeleteMapping(value = {"/event/{id}", "/event/{id}/"})
	    public void deleteEventByID (@PathVariable("id") String id) {
	    	eventService.deleteEventByID(id);
	    	return;
	    	
	    }
	    
	    @DeleteMapping(value = {"/event/hour", "/event/hour/"})
	    public void deleteEventbyHour (@RequestParam String weekday, @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") String startTime,
			@RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") String endTime,@RequestParam String employeeUserName, @RequestParam String calendarID) {
	   	 Employee e = employeeService.getEmployee(employeeUserName);
		 Calendar c = calendarService.getCalendar(calendarID);
		 Hour h = new Hour (weekday,Time.valueOf(startTime),Time.valueOf(endTime),e,c);
	    	eventService.deleteEventbyHour(h);
	    	return;
	    }
	    
	    
	    
	    
	    //Dont allow delete event by name cause that would delete all events with the same name which we don not want
	    
	    @PutMapping(value = {"/event/update/date", "/event/update/date/"})
	    public EventDto updateEventdate(@RequestParam String id, @RequestParam ("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  String date) {
	    	if( eventService.updateEventdate(id, Date.valueOf(date)))
	    		return convertToDto(eventService.getEventByDate(Date.valueOf(date)));
	    	return null;
	    }
	    
	    
	    @PutMapping(value = {"/event/update/hour", "/event/update/hour/"})
	    public EventDto updateEventHour(@RequestParam String Upweekday ,@RequestParam String id, @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") String UpstartTime,
			@RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") String UpendTime,@RequestParam String employeeUserName, @RequestParam String calendarID) {
	    	Employee e = employeeService.getEmployee(employeeUserName);
			 Calendar c = calendarService.getCalendar(calendarID);
			 Hour h = new Hour (Upweekday,Time.valueOf(UpstartTime),Time.valueOf(UpendTime),e,c);
	    	if( eventService.updateEventHour(id, h))
	    		return convertToDto(eventService.getEventByHour(h));
	    	return null;
	    }
	    
	    
	    
	    @PutMapping(value = {"/event/update/name", "/event/update/name/"})
	    public EventDto updateEventdate(@RequestParam String id, @RequestParam ("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  String date, @RequestParam String updatedName) {
	    	
	    	if( eventService.updateEventNameByDayandID(id, Date.valueOf(date), updatedName))
	    		convertToDto(eventService.getEventByID(id));
	    	return null;
	    }
	    
	    
	    
	private EventDto convertToDto (Event e ) {
		if (e == null) throw new IllegalArgumentException("Cannot find this event");
		return new EventDto(e.getEventID(),e.getName(),e.getEventDate(),convertToDto(e.getEventhour()));
	}
	
	private HourDto convertToDto(Hour h){
		if (h== null) throw new IllegalArgumentException("Cannot find this hour");
		return new HourDto(h.getWeekday(),h.getStartTime(),h.getEndTime(),convertToDto(h.getEmployee()),convertToDto(h.getCalendar()));
		
	}
	
	 private EmployeeDto convertToDto(Employee e) {
	        if (e == null) throw new IllegalArgumentException("Cannot find this Employee");
	        return new EmployeeDto(e.getLibraryCardID(), e.getIsLoggedIn(), e.getIsOnlineAcc(), e.getFirstName(),
	                e.getLastName(), e.getIsVerified(), e.getDemeritPts(), convertToDto(e.getAddress()),
	                EmployeeDto.Role.valueOf(e.getRole().name()), e.getOutstandingBalance());
	    }

	 private AddressDto convertToDto(Address a) {
	        if (a == null) throw new NullPointerException("Cannot find Address");
	        return new AddressDto(a.getCivicNumber(), a.getStreet(), a.getCity(), a.getPostalCode(), a.getProvince(), a.getCountry());
	    }
	
	private CalendarDto convertToDto (Calendar c) {
		if (c== null) throw new IllegalArgumentException("Cannot find this Calendar");
		return new CalendarDto(c.getCalendarID());
	}
	
	
	
	
	
	
}
