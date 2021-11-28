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
	public ResponseEntity getAllHours(){
		List<HourDto> hourList =  new ArrayList<>();
		List<Hour> hours;
		try {
			hours = hourService.getAllHours();

			for (Hour h: hours) {
				hourList.add(convertToDto(h));
			}

			if (hourList.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Hours in system");
			return new ResponseEntity<>(hourList, HttpStatus.OK);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}
	
	
	@GetMapping(value = {"/hours/calendar","/hours/calendar/"})
	public ResponseEntity getHourListBycalendar(@RequestParam String calendarID){
		try {
			Calendar c = calendarService.getCalendar(calendarID);
			List<HourDto> hourList =  new ArrayList<>();
			for (Hour h : hourService.getHourlistBycalendar(c)) {
				hourList.add(convertToDto(h));
			}
			if (hourList == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Hours in this Calendar");
			return new ResponseEntity<>(hourList, HttpStatus.OK);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}
	
	
	@GetMapping(value = {"/hours/employee","/hours/employee/"})
	public ResponseEntity getHourListByemployee(@RequestParam String employeeUserName){
		try {
			Employee e = employeeService.getEmployee(employeeUserName);
			List<HourDto> hourList =  new ArrayList<>();
			for (Hour h : hourService.getHourlistByEmployee(e)) {
				hourList.add(convertToDto(h));
			}
			if (hourList == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Hours for this employee");
			return new ResponseEntity<>(hourList, HttpStatus.OK);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}
	
	
	@GetMapping(value = {"/hours/endTime","/hours/endTime/"})
	public ResponseEntity getHourListByendTime(@RequestParam String endTime){
		try {
			if (!endTime.contains(":")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please format time as HH:mm:ss");
			List<HourDto> hourList =  new ArrayList<>();
			for (Hour h : hourService.getHourListbyendTime(Time.valueOf(endTime))) {
				hourList.add(convertToDto(h));
			}
			if (hourList == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Hours by this end time");
			return new ResponseEntity<>(hourList, HttpStatus.OK);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}
	@GetMapping(value = {"/hours/startTime","/hours/startTime/"})
	public ResponseEntity getHourListBystartTime(@RequestParam String startTime){
		try {
			if (!startTime.contains(":")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please format time as HH:mm:ss");
			List<HourDto> hourList =  new ArrayList<>();
			for (Hour h : hourService.getHourListbystartTime(Time.valueOf(startTime))) {
				hourList.add(convertToDto(h));
			}
			if (hourList == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Hours by this end time");
			return new ResponseEntity<>(hourList, HttpStatus.OK);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}
	
	@GetMapping(value = {"/hour/eventDate","/hour/eventDate/"})
	public ResponseEntity getHourbyEvent(@RequestParam String eventDate){
		try {
			if (!eventDate.contains("/")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please format date as MM/dd/yyyy");
			Event e = eventService.getEventByDate(Date.valueOf(eventDate));
			return new ResponseEntity<>(convertToDto(hourService.getHourbyEvent(e)), HttpStatus.OK);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}
	
	@GetMapping(value = {"/hour/weekday", "/hour/weekday/"})
	public ResponseEntity getHourbyWeekday(@RequestParam String weekday) {
		try {
			HourDto hour = convertToDto(hourService.getHourbyWeekday(weekday));
			if (hour == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Could not find Hour by weekday");
			return new ResponseEntity<>(hour, HttpStatus.OK);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}
	
	
	@DeleteMapping (value = {"/hour/event/{eventID}","/hour/event/{eventID}/"})
	public ResponseEntity deleteHourbyEvent (@PathVariable("eventID") String eventID) {
		try {
			Event e = eventService.getEventByID(eventID);
			hourService.deleteHourbyEvent(e);
			return ResponseEntity.status(HttpStatus.OK).body("Hour has been deleted");

		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}
	
	
	
	@DeleteMapping (value = {"/hour/{employeeID}","/hour/{employeeID}/"})
	public ResponseEntity deleteHourbyEmployee (@PathVariable("employeeID") String employeeID) {
		try {
			Employee e = employeeService.getEmployee(Integer.valueOf(employeeID));
			hourService.deleteHourbyEmployee(e);
			return ResponseEntity.status(HttpStatus.OK).body("Hour has been deleted");

		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}
	
	@DeleteMapping(value = {"/hour/weekday", "/hour/weekday/"})
	public ResponseEntity deleteHourbyWeekday(@RequestParam String weekday) {
		try {
			hourService.deleteHourbyWeekday(weekday);
			return ResponseEntity.status(HttpStatus.OK).body("Hour has been deleted");

		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}
	
	@PutMapping (value = {"/hour/update/startTime","/hour/update/startTime/" })
	public ResponseEntity updateHourStartTimebyWeekday(@RequestParam String weekday, @RequestParam String startTime) {
		try {
			if (!startTime.contains(":")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please format time as HH:mm:ss");
			if (hourService.updateHourStartTimebyWeekday(weekday, Time.valueOf(startTime))) return new ResponseEntity<>(convertToDto(hourService.getHourbyWeekday(weekday)), HttpStatus.OK);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not update Hour");
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}

	@PutMapping (value = {"/hour/update/endTime","/hour/update/endTime/" })
	public ResponseEntity updateHourEndTimebyWeekday(@RequestParam String weekday, @RequestParam String endTime) {
		try {
			if (!endTime.contains(":")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please format time as HH:mm:ss");
			if (hourService.updateHourEndTimebyWeekday(weekday, Time.valueOf(endTime))) return new ResponseEntity<>(convertToDto(hourService.getHourbyWeekday(weekday)), HttpStatus.OK);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not update Hour");
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}
	
	@PutMapping (value = {"/hour/update/employee","/hour/update/employee/" })
	public ResponseEntity updateHourEmployeebyWeekday(@RequestParam String weekday,@RequestParam String newEmployeeID) {
		try {
			Employee e = employeeService.getEmployee(Integer.valueOf(newEmployeeID));
			if (hourService.updateHourEmployeebyWeekday(weekday, e)) return new ResponseEntity<>(convertToDto(hourService.getHourbyWeekday(weekday)), HttpStatus.OK);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not update Hour");
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}
	
	@PostMapping (value = {"/hour/create", "/hour/create/"})
	public ResponseEntity createHour(@RequestParam String weekday, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String EmployeeId , @RequestParam String calendarId) {
		try {
			if (!startTime.contains(":")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please format time as HH:mm:ss");
			Employee e = employeeService.getEmployee(Integer.valueOf(EmployeeId));
			Calendar c = calendarService.getCalendar(calendarId);
			Hour h = hourService.createHour(weekday, Time.valueOf(startTime), Time.valueOf(endTime), e, c);
			HourDto hour = convertToDto(h);
			if (hour == null) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not create Hour");
			return new ResponseEntity<>(hour, HttpStatus.OK);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}
	
	private EventDto convertToDto (Event e ) {
		if (e == null) throw new IllegalArgumentException("Cannot find this event");
		return new EventDto(e.getEventID(),e.getName(),e.getEventDate(),convertToDto(e.getEventhour()));
	}
	
	private HourDto convertToDto(Hour h){
		if (h== null) throw new IllegalArgumentException("Cannot find this hour");
		return new HourDto(h.getEvent(), h.getWeekday(),h.getStartTime(),h.getEndTime(),convertToDto(h.getEmployee()),convertToDto(h.getCalendar()));
		
	}
	
	 private EmployeeDto convertToDto(Employee e) {
	        if (e == null) throw new IllegalArgumentException("Cannot find this Employee");
	        return new EmployeeDto(e.getLibraryCardID(), e.getIsLoggedIn(), e.getIsOnlineAcc(), e.getFirstName(),
	                e.getLastName(), e.getIsVerified(), e.getDemeritPts(), convertToDto(e.getAddress()),
	                EmployeeDto.Role.valueOf(e.getRole().name()), e.getOutstandingBalance(), e.getUserbooking());
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
