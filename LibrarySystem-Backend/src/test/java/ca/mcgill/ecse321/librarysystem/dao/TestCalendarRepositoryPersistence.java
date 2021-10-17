package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

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
	
	@AfterEach
	public void clearDatabase() {
		// Clear the library system, calendar and address in the persistence. 
		itemRepository.deleteAll();
		titleRepository.deleteAll();
		authorRepository.deleteAll();
		librarySystemRepository.deleteAll();
		hourRepository.deleteAll();
		addressRepository.deleteAll();
		calendarRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadCalendar() {
		
		Calendar calendar = new Calendar();
		
		calendarRepository.save(calendar);
		String calendarID = calendar.getCalendarID();
		
		calendar = null;
		
		calendar = calendarRepository.findBycalendarID(calendarID);
		assertNotNull(calendar);
		assertEquals(calendarID, calendar.getCalendarID());
	}
	
	@Test
	public void testPersistAndLoadCalendarWithSystemID() {
		LibrarySystem ls = new LibrarySystem();
		librarySystemRepository.save(ls);
		
		String systemID = ls.getSystemID();
		
		Calendar calendar = new Calendar();
		calendar.setLibrarySystem(ls);
		calendarRepository.save(calendar);
		
		String calendarID = calendar.getCalendarID();
		
		calendar = null;
		
		calendar = calendarRepository.findBysystemID(systemID);
		assertNotNull(calendar);
		assertEquals(calendarID, calendar.getCalendarID());
		assertEquals(systemID, calendar.getLibrarySystem().getSystemID());
	}
	
	@Test
	public void testPersistAndLoadCalendarWithListOfHours() {
		// First Time slot
		Hour timeSlot1 = new Hour();
		timeSlot1.setWeekday("Monday");
		Time startTime1 = new Time(9, 0, 0);
		Time endTime1 = new Time(18, 0, 0);
		timeSlot1.setStartTime(startTime1);
		timeSlot1.setEndTime(endTime1);
		hourRepository.save(timeSlot1);
		
		// Second Time slot
		Hour timeSlot2 = new Hour();
		timeSlot1.setWeekday("Tueday");
		Time startTime2 = new Time(9, 0, 0);
		Time endTime2 = new Time(18, 0, 0);
		timeSlot1.setStartTime(startTime2);
		timeSlot1.setEndTime(endTime2);
		hourRepository.save(timeSlot2);
		
		// Third Time slot
		Hour timeSlot3 = new Hour();
		timeSlot1.setWeekday("Friday");
		Time startTime3 = new Time(9, 0, 0);
		Time endTime3 = new Time(18, 0, 0);
		timeSlot1.setStartTime(startTime3);
		timeSlot1.setEndTime(endTime3);
		hourRepository.save(timeSlot3);
		
		Calendar calendar = new Calendar();
		calendarRepository.save(calendar);
		
		String calendarID = calendar.getCalendarID();
		
		
		calendar = null;
		 
		calendar = calendarRepository.findBysystemID(systemID);
		assertNotNull(calendar);
		assertEquals(calendarID, calendar.getCalendarID());
		assertEquals(systemID, calendar.getLibrarySystem().getSystemID());
	}
	
}
