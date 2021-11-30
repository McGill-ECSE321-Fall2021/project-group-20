package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.AddressDto;
import ca.mcgill.ecse321.librarysystem.dto.CalendarDto;
import ca.mcgill.ecse321.librarysystem.dto.EmployeeDto;
import ca.mcgill.ecse321.librarysystem.dto.EventDto;
import ca.mcgill.ecse321.librarysystem.dto.HourDto;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.service.CalendarService;
import ca.mcgill.ecse321.librarysystem.service.EmployeeService;
import ca.mcgill.ecse321.librarysystem.service.EventService;
import ca.mcgill.ecse321.librarysystem.service.HourService;

import org.apache.coyote.Response;
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

	@GetMapping(value = {"/hours/system", "/hours/system/"})
	public ResponseEntity getSystemHours() {
		List<HourDto> hourList =  new ArrayList<>();
		List<Hour> hours;
		try {
			hours = hourService.getAllHours();

			for (Hour h: hours) {
				if (h.getType().toString().equals("System")) hourList.add(convertToDto(h));
			}

			if (hourList.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any business hours in system");
			return new ResponseEntity<>(hourList, HttpStatus.OK);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}

	@GetMapping(value = {"/hours/shifts", "/hours/shifts/"})
	public ResponseEntity getShifts() {
		List<HourDto> hourList =  new ArrayList<>();
		List<Hour> hours;
		try {
			hours = hourService.getAllHours();

			for (Hour h: hours) {
				if (h.getType().toString().equals("Shift")) hourList.add(convertToDto(h));
			}

			if (hourList.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any shifts in system");
			return new ResponseEntity<>(hourList, HttpStatus.OK);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}

	@GetMapping(value = {"/hours/shifts/day", "/hours/shifts/day/"})
	public ResponseEntity getDayShifts(@RequestParam String day) {
		List<HourDto> hourList =  new ArrayList<>();
		List<Hour> hours;
		try {
			hours = hourService.getAllHours();

			for (Hour h: hours) {
				if (h.getType().toString().equals("Shift") && h.getWeekday().equals(day)) hourList.add(convertToDto(h));
			}

			if (hourList.size() == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find any shifts in system");
			return new ResponseEntity<>(hourList, HttpStatus.OK);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}

	@GetMapping(value = {"/hours/shifts/notWorking", "/hours/shifts/notWorking"})
	public ResponseEntity getNotWorking(@RequestParam String day) {
		List<Hour> hours;
		List<Employee> employees;
		List<EmployeeDto> emp = new ArrayList<>();
		try {
			hours = hourService.getAllHours();
			employees = employeeService.getAllEmployees();

			for (Employee e: employees) {
				if (e.getRole().equals(Employee.Role.HeadLibrarian)) employees.remove(e);
			}

			for (Hour h: hours) {
				if (h.getType().toString().equals("Shift") && h.getWeekday().equals(day)) employees.remove(h.getEmployee());
			}

			for (Employee e: employees) {
				emp.add(convertToDto(e));
			}
			return new ResponseEntity<>(emp, HttpStatus.OK);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}

	@GetMapping(value = {"/hours/shifts/{id}", "/hours/shifts/{id}/"})
	public ResponseEntity getEmployeeShifts(@PathVariable String id) {
		List<HourDto> hourList =  new ArrayList<>();
		List<Hour> hours;
		try {
			hours = hourService.getAllHours();

			for (Hour h: hours) {
				if (h.getType().toString().equals("Shift") && h.getEmployee().getLibraryCardID() == Integer.parseInt(id)) hourList.add(convertToDto(h));
			}

			if (hourList.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any shifts in system");
			return new ResponseEntity<>(hourList, HttpStatus.OK);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}

	@GetMapping(value = {"/hours/events", "/hours/events/"})
	public ResponseEntity getEvents() {
		List<HourDto> hourList =  new ArrayList<>();
		List<Hour> hours;
		try {
			hours = hourService.getAllHours();

			for (Hour h: hours) {
				if (h.getType().toString().equals("Event")) hourList.add(convertToDto(h));
			}

			if (hourList.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any events in system");
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

	@DeleteMapping(value = {"/hour/weekday/id", "/hour/weekday/id/"})
	public ResponseEntity deleteHourByID(@RequestParam String id) {
		try {
			hourService.deleteHour(Integer.valueOf(id));
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

	@PutMapping (value = {"/hour/update", "/hour/update/"})
	public ResponseEntity updateSystemHour(@RequestParam String id, @RequestParam String startTime, @RequestParam String endTime) {
		try {
			Hour h = hourService.updateHour(Integer.parseInt(id), Time.valueOf(startTime), Time.valueOf(endTime));
			if (h == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find/update this Hour");
			return new ResponseEntity<>(convertToDto(h), HttpStatus.OK);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}
	
	@PostMapping (value = {"/hour/create", "/hour/create/"})
	public ResponseEntity createHour(@RequestParam String weekday, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String EmployeeId,
									 @RequestParam String calendarId, @RequestParam String type) {
		try {
			if (!startTime.contains(":")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please format time as HH:mm:ss");
			Employee e = employeeService.getEmployee(Integer.valueOf(EmployeeId));
			Calendar c = calendarService.getCalendar(calendarId);
			Hour h = hourService.createHour(weekday, Time.valueOf(startTime), Time.valueOf(endTime), e, c, type);
			HourDto hour = convertToDto(h);
			if (hour == null) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not create Hour");
			return new ResponseEntity<>(hour, HttpStatus.OK);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
	}

	@PostMapping (value = {"/hour/initialize", "hour/initialize/"})
	public ResponseEntity initialize(@RequestParam String EmployeeId, @RequestParam String calendarId) {
		try {
			List<Hour> hList = new ArrayList<>();
			List<HourDto> dtoList = new ArrayList<>();
			Employee e = employeeService.getEmployee(Integer.valueOf(EmployeeId));
			Calendar c = calendarService.getCalendar(calendarId);
			Hour monday = hourService.createHour("Monday", Time.valueOf("00:00:00"), Time.valueOf("00:00:00"), e, c, "System");
			Hour tuesday = hourService.createHour("Tuesday", Time.valueOf("00:00:00"), Time.valueOf("00:00:00"), e, c, "System");
			Hour wednesday = hourService.createHour("Wednesday", Time.valueOf("00:00:00"), Time.valueOf("00:00:00"), e, c, "System");
			Hour thursday = hourService.createHour("Thursday", Time.valueOf("00:00:00"), Time.valueOf("00:00:00"), e, c, "System");
			Hour friday = hourService.createHour("Friday", Time.valueOf("00:00:00"), Time.valueOf("00:00:00"), e, c, "System");
			Hour saturday = hourService.createHour("Saturday", Time.valueOf("00:00:00"), Time.valueOf("00:00:00"), e, c, "System");
			Hour sunday = hourService.createHour("Sunday", Time.valueOf("00:00:00"), Time.valueOf("00:00:00"), e, c, "System");
			hList.add(monday);
			hList.add(tuesday);
			hList.add(wednesday);
			hList.add(thursday);
			hList.add(friday);
			hList.add(saturday);
			hList.add(sunday);

			for (Hour h : hList) {
				dtoList.add(convertToDto(h));
			}
			return new ResponseEntity<>(dtoList, HttpStatus.OK);
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
		return new HourDto(h.getId(), h.getEvent(), h.getWeekday(),h.getStartTime(),h.getEndTime(),convertToDto(h.getEmployee()),
				convertToDto(h.getCalendar()), HourDto.Type.valueOf(h.getType().toString()));
		
	}
	
	 private EmployeeDto convertToDto(Employee e) {
	        if (e == null) throw new IllegalArgumentException("Cannot find this Employee");
	        return new EmployeeDto(e.getLibraryCardID(), e.getIsOnlineAcc(), e.getIsLoggedIn(), e.getFirstName(), e.getLastName(),
				e.getIsVerified(), e.getDemeritPts(), convertToDto(e.getAddress()), e.getUsername(), e.getEmail(), e.getOutstandingBalance(), EmployeeDto.Role.valueOf(e.getRole().toString()), e.getUserbooking());
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
