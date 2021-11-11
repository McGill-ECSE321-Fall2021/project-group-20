package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.model.Employee.Role;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestEventRepositoryPersistence {

	@Autowired
	private CalendarRepository calendarRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private LibrarySystemRepository librarySystemRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private HourRepository hourRepository;
	@Autowired
	private UserRepository userRepository;

	/**
	 * Delete all the persisted object after each test
	 * 
	 */

	@AfterEach
	public void clearDatabase() {
		eventRepository.deleteAll();
		hourRepository.deleteAll();
		librarySystemRepository.deleteAll();
		userRepository.deleteAll();
		addressRepository.deleteAll();
		calendarRepository.deleteAll();

	}

	/**
	 * This is a test to see if the Event gets persisted in a good way. We also see
	 * if we can fetch the Event by its Date and that all the attributes are
	 * preserved
	 * 
	 */

	@Test
	public void testPersistAndLoadByeventDate() {
		String str1 = "2015-03-31";
		Date sDate = Date.valueOf(str1);
		@SuppressWarnings("deprecation")
		Time sTime = new Time(1, 30, 50);
		@SuppressWarnings("deprecation")
		Time dTime = new Time(1, 30, 50);
		Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
		addressRepository.save(myAdress);
		Calendar myCalendar = new Calendar();
		calendarRepository.save(myCalendar);
		LibrarySystem myLibrary = new LibrarySystem(myAdress, myCalendar);
		librarySystemRepository.save(myLibrary);
		Employee aUser = new Employee(true, true, "Alex", "Bangala", true, 0, myAdress, Role.Librarian);
		userRepository.save(aUser);
		Hour myHour = new Hour("Friday", sTime, dTime, aUser, myCalendar);
		Event myEvent = new Event("LasFiesta", sDate, myHour);
		eventRepository.save(myEvent);
		myAdress = null;
		myCalendar = null;
		myLibrary = null;
		aUser = null;
		myHour = null;

		Event evento = eventRepository.findByEventDate(sDate);
		assertNotNull(evento);

		assertEquals(evento.getEventDate(), myEvent.getEventDate());
		assertEquals(evento.getName(), myEvent.getName());
		assertEquals(evento.getEventID(), myEvent.getEventID());

	}

	/**
	 * This is a test to see if the Event gets persisted in a good way. We also see
	 * if we can fetch the Event by its ID and that all the attributes are preserved
	 * 
	 */

	@Test
	public void testPersistAndLoadByeventID() {
		String str1 = "2015-03-31";
		Date sDate = Date.valueOf(str1);
		@SuppressWarnings("deprecation")
		Time sTime = new Time(1, 30, 50);
		@SuppressWarnings("deprecation")
		Time dTime = new Time(1, 30, 50);
		Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
		addressRepository.save(myAdress);
		Calendar myCalendar = new Calendar();
		calendarRepository.save(myCalendar);
		LibrarySystem myLibrary = new LibrarySystem(myAdress, myCalendar);
		librarySystemRepository.save(myLibrary);
		Employee aUser = new Employee(true, true, "Alex", "Bangala", true, 0, myAdress, Role.Librarian);
		userRepository.save(aUser);
		Hour myHour = new Hour("Tuesday", sTime, dTime, aUser, myCalendar);
		Event myEvent = new Event("LasFiesta", sDate, myHour);
		eventRepository.save(myEvent);
		myAdress = null;
		myCalendar = null;
		myLibrary = null;
		aUser = null;
		myHour = null;

		Event evento = eventRepository.findByEventID(myEvent.getEventID());
		assertNotNull(evento);

		assertEquals(evento.getEventDate(), myEvent.getEventDate());
		assertEquals(evento.getName(), myEvent.getName());
		assertEquals(evento.getEventID(), myEvent.getEventID());
	}

	/**
	 * This is a test to see if the Event gets persisted in a good way. We also see
	 * if we can fetch the Event by its Hour and that all the attributes are
	 * preserved
	 * 
	 */
	@Test
	public void testPersistAndLoadByEventHour() {
		String str1 = "2015-03-31";
		Date sDate = Date.valueOf(str1);
		@SuppressWarnings("deprecation")
		Time sTime = new Time(1, 30, 50);
		@SuppressWarnings("deprecation")
		Time dTime = new Time(1, 30, 50);
		Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
		addressRepository.save(myAdress);
		Calendar myCalendar = new Calendar();
		calendarRepository.save(myCalendar);
		LibrarySystem myLibrary = new LibrarySystem(myAdress, myCalendar);
		librarySystemRepository.save(myLibrary);
		Employee aUser = new Employee(true, true, "Alex", "Bangala", true, 0, myAdress, Role.Librarian);
		userRepository.save(aUser);
		Hour myHour = new Hour("Wednesday", sTime, dTime, aUser, myCalendar);
		Event myEvent = new Event("LasFiesta", sDate, myHour);
		eventRepository.save(myEvent);
		myHour.setEvent(myEvent);
		hourRepository.save(myHour);
		myAdress = null;
		myCalendar = null;
		myLibrary = null;
		aUser = null;
		// myHour=null;

		Event evento = eventRepository.findByEventhour(myHour);
		assertNotNull(evento);
		assertEquals(evento.getEventDate(), myEvent.getEventDate());
		assertEquals(evento.getName(), myEvent.getName());
		assertEquals(evento.getEventID(), myEvent.getEventID());

	}

	/**
	 * This is a test to see if the Event gets persisted in a good way. We also see
	 * if we can fetch the Event by its Date and that all the attributes are
	 * preserved
	 * 
	 */

	@Test
	public void testPersistAndLoadByName() {
		String str1 = "2015-03-31";
		Date sDate = Date.valueOf(str1);
		@SuppressWarnings("deprecation")
		Time sTime = new Time(1, 30, 50);
		@SuppressWarnings("deprecation")
		Time dTime = new Time(1, 30, 50);
		Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
		addressRepository.save(myAdress);
		Calendar myCalendar = new Calendar();
		calendarRepository.save(myCalendar);
		LibrarySystem myLibrary = new LibrarySystem(myAdress, myCalendar);
		librarySystemRepository.save(myLibrary);
		Employee aUser = new Employee(true, true,"Alex", "Bangala", true, 0, myAdress, Role.Librarian);
		userRepository.save(aUser);
		Hour myHour = new Hour("Thursday", sTime, dTime, aUser, myCalendar);
		Event myEvent = new Event("LasFiesta", sDate, myHour);
		eventRepository.save(myEvent);
		myAdress = null;
		myCalendar = null;
		myLibrary = null;
		aUser = null;
		myHour = null;

		List<Event> evento = eventRepository.findByName("LasFiesta");
		assertNotNull(evento);
		for (Event Eventila : evento) {
			assertEquals(Eventila.getEventDate(), myEvent.getEventDate());
			assertEquals(Eventila.getName(), myEvent.getName());
			assertEquals(Eventila.getEventID(), myEvent.getEventID());
		}
	}

}