package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.librarysystem.model.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestCalendarRepositoryPersistence {
	
	@Autowired
	private CalendarRepository calendarRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private HourRepository hourRepository;
	@Autowired
	private LibrarySystemRepository librarySystemRepository;
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private TitleRepository titleRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	
	/*
	 * After each test, clear every table in the database.
	 */
	@AfterEach
	public void clearDatabase() {
		itemRepository.deleteAll();
		titleRepository.deleteAll();
		authorRepository.deleteAll();
		hourRepository.deleteAll();
		userRepository.deleteAll();
		librarySystemRepository.deleteAll();
		calendarRepository.deleteAll();
		employeeRepository.deleteAll();
		addressRepository.deleteAll();
	}
	
	/*
	 * Test 1: Persists a calendar to the database, then checks if a querried address based on a calendar is the same as the persisted calendar.
	 */
	@Test
	public void testPersistAndLoadCalendar() {
		
		Calendar calendar = new Calendar();
		
		calendarRepository.save(calendar);
		String calendarID = calendar.getCalendarID();
		
		calendar = null;
		
		calendar = calendarRepository.findCalendarByCalendarID(calendarID);

		assertNotNull(calendar);
		assertEquals(calendarID, calendar.getCalendarID());
	}
	
	/*
	 * Test 3: Persists a calendar to the database, then checks if a querried address based on library system is the same as the persisted calendar.
	 */
	@Test
	public void testPersistAndLoadCalendarWithLibrarySystem() {
		LibrarySystem ls = new LibrarySystem();
		librarySystemRepository.save(ls);
		
		Calendar calendar = new Calendar();
		calendar.setLibrarySystem(ls);
		calendarRepository.save(calendar);
		
		String calendarID = calendar.getCalendarID();
		String systemID = ls.getSystemID();
		
		calendar = null;
		
		calendar = calendarRepository.findCalendarByLibrarySystem(ls);
		assertNotNull(calendar);
		assertEquals(calendarID, calendar.getCalendarID());
		assertEquals(systemID, calendar.getLibrarySystem().getSystemID());
	}
	
	/*
	 * Test 2: Persists a calendar to the database, then checks if a querried address based on a list of hours is the same as the persisted calendar.
	 */
	@Test
	public void testPersistAndLoadCalendarWithListOfHours() {
		LibrarySystem ls = new LibrarySystem();
		librarySystemRepository.save(ls);
		
		Calendar calendar = new Calendar();
		calendar.setLibrarySystem(ls);
		calendarRepository.save(calendar);
		
		// Create an employee to associate shifts/timeslots in the calendar
		Employee u1 = new Employee();
		Address address1 = new Address();
		addressRepository.save(address1);
		u1.setAddress(address1);
		ls.addUser(u1);
		librarySystemRepository.save(ls);
		u1.setLibrarySystem(ls);
		employeeRepository.save(u1);
		
		// First shift/timeslot of employee u1
		Hour timeSlot1 = new Hour();
		timeSlot1.setWeekday("Monday");
		Time startTime1 = new Time(9, 0, 0);
		Time endTime1 = new Time(18, 0, 0);
		timeSlot1.setStartTime(startTime1);
		timeSlot1.setEndTime(endTime1);
		timeSlot1.setCalendar(calendar);
		timeSlot1.setEmployee(u1);
		hourRepository.save(timeSlot1);
		
		// Second shift/timeslot of employee u1
		Hour timeSlot2 = new Hour();
		timeSlot2.setWeekday("Tueday");
		Time startTime2 = new Time(9, 0, 0);
		Time endTime2 = new Time(18, 0, 0);
		timeSlot2.setStartTime(startTime2);
		timeSlot2.setEndTime(endTime2);
		timeSlot2.setCalendar(calendar);
		hourRepository.save(timeSlot2);
		
		// Third shift/timeslot of employee u1
		Hour timeSlot3 = new Hour();
		timeSlot3.setWeekday("Friday");
		Time startTime3 = new Time(9, 0, 0);
		Time endTime3 = new Time(18, 0, 0);
		timeSlot3.setStartTime(startTime3);
		timeSlot3.setEndTime(endTime3);
		timeSlot3.setCalendar(calendar);
		hourRepository.save(timeSlot3);
		
		calendar.addHour(timeSlot1);
		calendar.addHour(timeSlot2);
		calendar.addHour(timeSlot3);
		calendarRepository.save(calendar);
		
		String calendarID = calendar.getCalendarID();
		List<Hour> hours = new ArrayList<Hour>();
		hours.add(timeSlot1);
		hours.add(timeSlot2);
		hours.add(timeSlot3);
		
		calendar = null;
		 
		calendar = calendarRepository.findCalendarByHourIn(hours);
		assertNotNull(calendar);
		assertEquals(calendarID, calendar.getCalendarID());
		assertEquals(timeSlot1.getWeekday(), calendar.getHour().get(0).getWeekday());
		assertEquals(timeSlot2.getWeekday(), calendar.getHour().get(1).getWeekday());
		assertEquals(timeSlot3.getWeekday(), calendar.getHour().get(2).getWeekday());
	}	
}
