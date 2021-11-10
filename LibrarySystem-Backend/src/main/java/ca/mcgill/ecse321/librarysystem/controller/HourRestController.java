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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class HourRestController {
	@Autowired
	private HourService hourService;
	
	@Autowired
	private EventService eventService;

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private CalendarService calendarService;
	
	
	@GetMapping(value = {"/hours", "/hours/"})
	public List<HourDto> getAllHours(){
		List<HourDto> hourList =  new ArrayList<>();
		for (Hour h : hourService.getAllHours()) {
			hourList.add(convertToDto(h));
		}
		
		return hourList;
	}
	
	
	@GetMapping(value = {"/hour/calendar","/hour/calendar/"})
	public List<HourDto> getHourListBycalendar(@RequestParam String calendarID){
		Calendar c = calendarService.getCalendar(calendarID);
		List<HourDto> hourList =  new ArrayList<>();
		for (Hour h : hourService.getHourlistBycalendar(c)) {
			hourList.add(convertToDto(h));
		}
		
		return hourList;
		
	}
	
	
	@GetMapping(value = {"/hour/employee","/hour/employee/"})
	public List<HourDto> getHourListByemployee(@RequestParam String employeeUserName){
		Employee e = employeeService.getEmployee(employeeUserName);
		List<HourDto> hourList =  new ArrayList<>();
		for (Hour h : hourService.getHourlistByEmployee(e)) {
			hourList.add(convertToDto(h));
		}
		
		return hourList;
		
	}
	
	
	@GetMapping(value = {"/hour/endTime","/hour/endTime/"})
	public List<HourDto> getHourListByendTime(@RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") String endTime){
		List<HourDto> hourList =  new ArrayList<>();
		for (Hour h : hourService.getHourListbyendTime(Time.valueOf(endTime))) {
			hourList.add(convertToDto(h));
		}
		
		return hourList;
		
	}
	@GetMapping(value = {"/hour/startTime","/hour/startTime/"})
	public List<HourDto> getHourListBystartTime(@RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") String startTime){
		List<HourDto> hourList =  new ArrayList<>();
		for (Hour h : hourService.getHourListbystartTime(Time.valueOf(startTime))) {
			hourList.add(convertToDto(h));
		}
		
		return hourList;
		
	}
	
	@GetMapping(value = {"/hour/eventDate","/hour/eventDate/"})
	public HourDto getHourbyEvent(@RequestParam("eventDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  String eventDate){
	Event e = eventService.getEventByDate(Date.valueOf(eventDate));
	return convertToDto(hourService.getHourbyEvent(e));
	}
	
	@GetMapping(value = {"/hour/weekday", "/hour/weekday/"})
	public HourDto getHourbyWeekday(@RequestParam String weekday) {
		return convertToDto(hourService.getHourbyWeekday(weekday));
	}
	
	
	@DeleteMapping (value = {"/hour/{eventID}","/hour/{eventID}/"})
	public void deleteHourbyEvent (@PathVariable String eventID) {
		Event e = eventService.getEventByID(eventID);
		hourService.deleteHourbyEvent(e);
		return;
		
	}
	
	
	
	@DeleteMapping (value = {"/hour/{employeeID}","/hour/{employeeID}/"})
	public void deleteHourbyEmployee (@PathVariable String employeeID) {
		Employee e = employeeService.getEmployee(Integer.valueOf(employeeID));
		hourService.deleteHourbyEmployee(e);
		return;
		
	}
	
	@DeleteMapping(value = {"/hour/weekday", "/hour/weekday/"})
	public void deleteHourbyWeekday(@RequestParam String weekday) {
		hourService.deleteHourbyWeekday(weekday);
		return;
	}
	
	@PutMapping (value = {"/hour/update/startTime","/hour/update/startTime/" })
	public HourDto updateHourStartTimebyWeekday(@RequestParam String weekday,@RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") String startTime) {
		if( hourService.updateHourStartTimebyWeekday(weekday, Time.valueOf(startTime)))
			return convertToDto(hourService.getHourbyWeekday(weekday));
		return null;
	}
	@PutMapping (value = {"/hour/update/endTime","/hour/update/endTime/" })
	public HourDto updateHourEndTimebyWeekday(@RequestParam String weekday,@RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") String endTime) {
		if (hourService.updateHourEndTimebyWeekday(weekday, Time.valueOf(endTime)))
			return convertToDto(hourService.getHourbyWeekday(weekday));
		return null;
	}
	
	@PutMapping (value = {"/hour/update/employee","/hour/update/employee/" })
	public HourDto updateHourEmployeebyWeekday(@RequestParam String weekday,@RequestParam String newemployeeID) {
		Employee e = employeeService.getEmployee(Integer.valueOf(newemployeeID));
		if (hourService.updateHourEmployeebyWeekday(weekday, e))
			return convertToDto(hourService.getHourbyWeekday(weekday));
		return null;
	}
	
	//Dont update calendar since once calendar for the whole system wouldnt make sense to change the calendar to a new one
	
	@PutMapping (value = {"/hour/update/event","/hour/update/event/" })
	public HourDto updateEventatThisHourbyWeekday(@RequestParam String weekday,@RequestParam String newEventid) {
		Event e = eventService.getEventByID(newEventid);
		if (hourService.updateEventatThisHourbyWeekday(weekday, e))
			return convertToDto(hourService.getHourbyEvent(e));
		return null;
	}
	
	@PostMapping (value = {"/hour/create", "/hour/create/"})
	public HourDto createHour(@RequestParam String weekday,@RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") String startTime,@RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") String endTime, @RequestParam String EmployeeId , @RequestParam String calendarId) {
		Employee e = employeeService.getEmployee(Integer.valueOf(EmployeeId));
		Calendar c = calendarService.getCalendar(calendarId);
		return convertToDto(hourService.createHour(weekday,Time.valueOf(startTime),Time.valueOf(endTime),e,c));
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
