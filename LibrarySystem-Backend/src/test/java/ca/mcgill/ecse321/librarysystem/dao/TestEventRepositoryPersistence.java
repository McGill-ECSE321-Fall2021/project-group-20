package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	

	@AfterEach
	public void clearDatabase() {
		eventRepository.deleteAll();
		hourRepository.deleteAll();
		userRepository.deleteAll();
		librarySystemRepository.deleteAll();
		addressRepository.deleteAll();
		calendarRepository.deleteAll();	
		
		
		
	}
	@Test
	public void testPersistAndLoadByeventDate() {
	String str1 = "2015-03-31";
	Date sdate= Date.valueOf(str1);
	Time sTime = new Time(1, 30, 50);
	Time dTime = new Time(1, 30, 50);
	Address myadress = new Address("51","Parkekx","Montreal","H5H6H7","Quebec","Canada");
	addressRepository.save(myadress);
	Calendar mycalendar = new Calendar();
	calendarRepository.save(mycalendar);
	LibrarySystem myLibrary = new LibrarySystem(myadress, mycalendar);
	librarySystemRepository.save(myLibrary);
	Employee aUser = new Employee(true, "Alex", "Bangala", true, 0, myadress, myLibrary, Role.Librarian);
	userRepository.save(aUser);
	Hour myhour = new Hour("mardi", sTime, dTime , aUser, mycalendar); 
	hourRepository.save(myhour);
	Event myevent = new Event("LasFiesta", sdate , myhour);
	eventRepository.save(myevent);
	myadress=null;
	mycalendar=null;
	myLibrary=null;
	aUser=null;
	myhour=null;
	
	Event evento = eventRepository.findByeventDate(sdate);
	assertNotNull (evento);

		assertEquals (evento.getEventDate(),myevent.getEventDate());
		assertEquals (evento.getName(),myevent.getName());
		assertEquals (evento.getEventID(),myevent.getEventID());
	
	
	}
	
	@Test
	public void testPersistAndLoadByeventID() {
	String str1 = "2015-03-31";
	Date sdate= Date.valueOf(str1);
	Time sTime = new Time(1, 30, 50);
	Time dTime = new Time(1, 30, 50);
	Address myadress = new Address("51","Parkekx","Montreal","H5H6H7","Quebec","Canada");
	addressRepository.save(myadress);
	Calendar mycalendar = new Calendar();
	calendarRepository.save(mycalendar);
	LibrarySystem myLibrary = new LibrarySystem(myadress, mycalendar);
	librarySystemRepository.save(myLibrary);
	Employee aUser = new Employee(true, "Alex", "Bangala", true, 0, myadress, myLibrary, Role.Librarian);
	userRepository.save(aUser);
	Hour myhour = new Hour("mardi", sTime, dTime , aUser, mycalendar); 
	hourRepository.save(myhour);
	Event myevent = new Event("LasFiesta", sdate , myhour);
	eventRepository.save(myevent);
	myadress=null;
	mycalendar=null;
	myLibrary=null;
	aUser=null;
	myhour=null;
	
	Event evento = eventRepository.findByeventID(myevent.getEventID());
	assertNotNull (evento);

		assertEquals (evento.getEventDate(),myevent.getEventDate());
		assertEquals (evento.getName(),myevent.getName());
		assertEquals (evento.getEventID(),myevent.getEventID());
	}
	
	@Test
	public void testPersistAndLoadByeventhour() {
	String str1 = "2015-03-31";
	Date sdate= Date.valueOf(str1);
	Time sTime = new Time(1, 30, 50);
	Time dTime = new Time(1, 30, 50);
	Address myadress = new Address("51","Parkekx","Montreal","H5H6H7","Quebec","Canada");
	addressRepository.save(myadress);
	Calendar mycalendar = new Calendar();
	calendarRepository.save(mycalendar);
	LibrarySystem myLibrary = new LibrarySystem(myadress, mycalendar);
	librarySystemRepository.save(myLibrary);
	Employee aUser = new Employee(true, "Alex", "Bangala", true, 0, myadress, myLibrary, Role.Librarian);
	userRepository.save(aUser);
	Hour myhour = new Hour("mardi", sTime, dTime , aUser, mycalendar); 
	hourRepository.save(myhour);
	Event myevent = new Event("LasFiesta", sdate , myhour);
	eventRepository.save(myevent);
	myadress=null;
	mycalendar=null;
	myLibrary=null;
	aUser=null;
	//myhour=null;
	
	Event evento = eventRepository.findByeventhour(myhour);
		assertNotNull (evento);
		assertEquals (evento.getEventDate(),myevent.getEventDate());
		assertEquals (evento.getName(),myevent.getName());
		assertEquals (evento.getEventID(),myevent.getEventID());
	}
	
	@Test
	public void testPersistAndLoadByName() {
	String str1 = "2015-03-31";
	Date sdate= Date.valueOf(str1);
	Time sTime = new Time(1, 30, 50);
	Time dTime = new Time(1, 30, 50);
	Address myadress = new Address("51","Parkekx","Montreal","H5H6H7","Quebec","Canada");
	addressRepository.save(myadress);
	Calendar mycalendar = new Calendar();
	calendarRepository.save(mycalendar);
	LibrarySystem myLibrary = new LibrarySystem(myadress, mycalendar);
	librarySystemRepository.save(myLibrary);
	Employee aUser = new Employee(true, "Alex", "Bangala", true, 0, myadress, myLibrary, Role.Librarian);
	userRepository.save(aUser);
	Hour myhour = new Hour("mardi", sTime, dTime , aUser, mycalendar); 
	hourRepository.save(myhour);
	Event myevent = new Event("LasFiesta", sdate , myhour);
	eventRepository.save(myevent);
	myadress=null;
	mycalendar=null;
	myLibrary=null;
	aUser=null;
	myhour=null;
	
	List <Event> evento = eventRepository.findByName("LasFiesta");
	for (Event Eventila : evento) {
		assertNotNull (evento);
		assertEquals (Eventila.getEventDate(),myevent.getEventDate());
		assertEquals (Eventila.getName(),myevent.getName());
		assertEquals (Eventila.getEventID(),myevent.getEventID());
	}
	}
	
	
	
	
}