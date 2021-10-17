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
	
	@AfterEach
	public void clearDatabase() {
		// Clear the library system, calendar and address in the persistence. 
		itemRepository.deleteAll();
		titleRepository.deleteAll();
		authorRepository.deleteAll();
		userRepository.deleteAll();
		calendarRepository.deleteAll();
		hourRepository.deleteAll();
		addressRepository.deleteAll();
		librarySystemRepository.deleteAll();
	}
	
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
	
//	@Test
//	public void testPersistAndLoadCalendarWithListOfHours() {
//		LibrarySystem ls = new LibrarySystem();
//		librarySystemRepository.save(ls);
//		
//		Calendar calendar = new Calendar();
//		calendar.setLibrarySystem(ls);
//		calendarRepository.save(calendar);
//		
//		// First Time slot	
//		Employee u1 = new Employee();
//		Address address1 = new Address();
//		addressRepository.save(address1);
//		u1.setAddress(address1);
//		ls.addUser(u1);
//		librarySystemRepository.save(ls);
//		u1.setLibrarySystem(ls);
//		userRepository.save(u1);
//		
//		Hour timeSlot1 = new Hour();
//		timeSlot1.setWeekday("Monday");
//		Time startTime1 = new Time(9, 0, 0);
//		Time endTime1 = new Time(18, 0, 0);
//		timeSlot1.setStartTime(startTime1);
//		timeSlot1.setEndTime(endTime1);
//		timeSlot1.setCalendar(calendar);
//		timeSlot1.setEmployee(u1);
//		hourRepository.save(timeSlot1);
//		
//		// Second Time slot
////		Hour timeSlot2 = new Hour();
////		timeSlot2.setWeekday("Tueday");
////		Time startTime2 = new Time(9, 0, 0);
////		Time endTime2 = new Time(18, 0, 0);
////		timeSlot2.setStartTime(startTime2);
////		timeSlot2.setEndTime(endTime2);
////		timeSlot2.setCalendar(calendar);
////		hourRepository.save(timeSlot2);
////		
////		// Third Time slot
////		Hour timeSlot3 = new Hour();
////		timeSlot3.setWeekday("Friday");
////		Time startTime3 = new Time(9, 0, 0);
////		Time endTime3 = new Time(18, 0, 0);
////		timeSlot3.setStartTime(startTime3);
////		timeSlot3.setEndTime(endTime3);
////		timeSlot3.setCalendar(calendar);
////		hourRepository.save(timeSlot3);
////		
////		calendar.addHour(timeSlot1);
////		calendar.addHour(timeSlot2);
////		calendar.addHour(timeSlot3);
////		calendarRepository.save(calendar);
//		
//		String calendarID = calendar.getCalendarID();
//		List<Hour> hours = new ArrayList<Hour>();
//		hours.add(timeSlot1);
////		hours.add(timeSlot2);
////		hours.add(timeSlot3);
//		
//		calendar = null;
//		 
//		calendar = calendarRepository.findCalendarByHourIn(hours);
//		assertNotNull(calendar);
//		assertEquals(calendarID, calendar.getCalendarID());
//		assertEquals(timeSlot1.getWeekday(), calendar.getHour().get(0).getWeekday());
////		assertEquals(timeSlot2.getWeekday(), calendar.getHour().get(1).getWeekday());
////		assertEquals(timeSlot2.getWeekday(), calendar.getHour().get(2).getWeekday());
//	}	
}
