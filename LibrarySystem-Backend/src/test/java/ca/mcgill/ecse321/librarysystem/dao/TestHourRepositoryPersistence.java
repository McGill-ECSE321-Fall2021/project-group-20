package ca.mcgill.ecse321.librarysystem.dao;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Booking.BookingType;
import ca.mcgill.ecse321.librarysystem.model.Employee.Role;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.User;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.Employee;
import ca.mcgill.ecse321.librarysystem.model.Event;
import ca.mcgill.ecse321.librarysystem.model.Hour;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;
import ca.mcgill.ecse321.librarysystem.model.Title;

@ExtendWith(SpringExtension.class)
@SpringBootTest 
public class TestHourRepositoryPersistence {

	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private HourRepository hourRepository;
	@Autowired
	private CalendarRepository calendarRepository;
	@Autowired
	private AddressRepository addressRepository;
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
		hourRepository.deleteAll();
		eventRepository.deleteAll();
		bookingRepository.deleteAll();
        itemRepository.deleteAll();
        titleRepository.deleteAll();
        authorRepository.deleteAll();
        userRepository.deleteAll();
        addressRepository.deleteAll();
        calendarRepository.deleteAll();
        librarySystemRepository.deleteAll();

	}
	
	@Test
	public void TestPersisitenceAndLoadHourByCalendar() {
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
		Hour myhour1 = new Hour("lundi", sTime, dTime , aUser, mycalendar); 

		hourRepository.save(myhour);
		hourRepository.save(myhour1);
		Event myevent = new Event("LasFiesta", sdate , myhour);
		Event myevent1= new Event("LasFiestaMcGill", sdate , myhour1);

		eventRepository.save(myevent);
		eventRepository.save(myevent1);
		myadress=null;
		mycalendar=null;
		myLibrary=null;
		aUser=null;
		myevent=null;
		myevent1=null;
		
		List <Hour> ListofHour = hourRepository.findBycalendar(mycalendar);
		for (Hour PerHour : ListofHour) {

			assertTrue(PerHour.getCalendar().getCalendarID().equals(myhour.getCalendar().getCalendarID()) || PerHour.getCalendar().getCalendarID().equals(myhour1.getCalendar().getCalendarID()));
			assertTrue(PerHour.getEmployee().getAddress().getAddressID().equals(myhour.getEmployee().getAddress().getAddressID()) || PerHour.getEmployee().getAddress().getAddressID().equals(myhour1.getEmployee().getAddress().getAddressID()));
			assertTrue(PerHour.getEndTime().equals(myhour.getEndTime()) ||PerHour.getEndTime().equals(myhour1.getEndTime()) );
			assertTrue(PerHour.getStartTime().equals(myhour.getStartTime()) ||PerHour.getStartTime().equals(myhour1.getStartTime()) );
			assertTrue(PerHour.getWeekday().equals(myhour.getWeekday()) ||PerHour.getWeekday().equals(myhour1.getWeekday()) );
			assertTrue(PerHour.getEmployee().getDemeritPts()==(myhour.getEmployee().getDemeritPts()) || PerHour.getEmployee().getDemeritPts()==(myhour1.getEmployee().getDemeritPts()));
			assertTrue(PerHour.getEmployee().getFirstName().equals(myhour.getEmployee().getFirstName()) ||PerHour.getEmployee().getFirstName().equals(myhour1.getEmployee().getFirstName()) );
			assertTrue(PerHour.getEmployee().getLibraryCardID()==(myhour.getEmployee().getLibraryCardID()) ||PerHour.getEmployee().getLibraryCardID()==(myhour1.getEmployee().getLibraryCardID()) );
			assertTrue(PerHour.getEmployee().getIsOnlineAcc()==(myhour.getEmployee().getIsOnlineAcc()) ||PerHour.getEmployee().getIsOnlineAcc()==(myhour1.getEmployee().getIsOnlineAcc()) );
			assertTrue(PerHour.getEmployee().getIsVerified()==(myhour.getEmployee().getIsVerified()) ||PerHour.getEmployee().getIsVerified()==(myhour1.getEmployee().getIsVerified()) );
			assertTrue(PerHour.getEmployee().getLibrarySystem().getSystemID().equals(myhour.getEmployee().getLibrarySystem().getSystemID()) ||PerHour.getEmployee().getLibrarySystem().getSystemID().equals(myhour1.getEmployee().getLibrarySystem().getSystemID()) );
			assertTrue(PerHour.getEmployee().getRole().equals(myhour.getEmployee().getRole()) ||PerHour.getEmployee().getRole().equals(myhour1.getEmployee().getRole()) );

			
		}
		

		
		
	}
	
	

	@Test
	public void TestPersistenceAndLoadByEmployee() {
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
		Hour myhour1 = new Hour("lundi", sTime, dTime , aUser, mycalendar); 

		hourRepository.save(myhour);
		hourRepository.save(myhour1);
		Event myevent = new Event("LasFiesta", sdate , myhour);
		Event myevent1= new Event("LasFiestaMcGill", sdate , myhour1);

		eventRepository.save(myevent);
		eventRepository.save(myevent1);
		myadress=null;
		mycalendar=null;
		myLibrary=null;
		myevent=null;
		myevent1=null;
		List <Hour> ListofHour = hourRepository.findByemployee(aUser);
		for (Hour PerHour : ListofHour) {

			assertTrue(PerHour.getCalendar().getCalendarID().equals(myhour.getCalendar().getCalendarID()) || PerHour.getCalendar().getCalendarID().equals(myhour1.getCalendar().getCalendarID()));
			assertTrue(PerHour.getEmployee().getAddress().getAddressID().equals(myhour.getEmployee().getAddress().getAddressID()) || PerHour.getEmployee().getAddress().getAddressID().equals(myhour1.getEmployee().getAddress().getAddressID()));
			assertTrue(PerHour.getEndTime().equals(myhour.getEndTime()) ||PerHour.getEndTime().equals(myhour1.getEndTime()) );
			assertTrue(PerHour.getStartTime().equals(myhour.getStartTime()) ||PerHour.getStartTime().equals(myhour1.getStartTime()) );
			assertTrue(PerHour.getWeekday().equals(myhour.getWeekday()) ||PerHour.getWeekday().equals(myhour1.getWeekday()) );
			assertTrue(PerHour.getEmployee().getDemeritPts()==(myhour.getEmployee().getDemeritPts()) || PerHour.getEmployee().getDemeritPts()==(myhour1.getEmployee().getDemeritPts()));
			assertTrue(PerHour.getEmployee().getFirstName().equals(myhour.getEmployee().getFirstName()) ||PerHour.getEmployee().getFirstName().equals(myhour1.getEmployee().getFirstName()) );
			assertTrue(PerHour.getEmployee().getLibraryCardID()==(myhour.getEmployee().getLibraryCardID()) ||PerHour.getEmployee().getLibraryCardID()==(myhour1.getEmployee().getLibraryCardID()) );
			assertTrue(PerHour.getEmployee().getIsOnlineAcc()==(myhour.getEmployee().getIsOnlineAcc()) ||PerHour.getEmployee().getIsOnlineAcc()==(myhour1.getEmployee().getIsOnlineAcc()) );
			assertTrue(PerHour.getEmployee().getIsVerified()==(myhour.getEmployee().getIsVerified()) ||PerHour.getEmployee().getIsVerified()==(myhour1.getEmployee().getIsVerified()) );
			assertTrue(PerHour.getEmployee().getLibrarySystem().getSystemID().equals(myhour.getEmployee().getLibrarySystem().getSystemID()) ||PerHour.getEmployee().getLibrarySystem().getSystemID().equals(myhour1.getEmployee().getLibrarySystem().getSystemID()) );
			assertTrue(PerHour.getEmployee().getRole().equals(myhour.getEmployee().getRole()) ||PerHour.getEmployee().getRole().equals(myhour1.getEmployee().getRole()) );

			
		}
		
		
		
		
		
		
		
		
		
		
		
	}
	
	@Test
	public void TestPersistenceAndLoadByEndTime() {
		
		String str1 = "2015-03-31";
		Date sdate= Date.valueOf(str1);
		Time sTime = new Time(1, 30, 50);
		Time dTime = new Time(1, 30, 50);
		
		String str2 = "2015-03-25";
		Date sdate1= Date.valueOf(str2);
		Time sTime1 = new Time(0, 30, 50);
		Time dTime1 = new Time(1, 30, 50);
		Address myadress = new Address("51","Parkekx","Montreal","H5H6H7","Quebec","Canada");
		addressRepository.save(myadress);
		Calendar mycalendar = new Calendar();
		calendarRepository.save(mycalendar);
		LibrarySystem myLibrary = new LibrarySystem(myadress, mycalendar);
		librarySystemRepository.save(myLibrary);
		Employee aUser = new Employee(true, "Alex", "Bangala", true, 0, myadress, myLibrary, Role.Librarian);
		userRepository.save(aUser);
		Hour myhour = new Hour("mardi", sTime, dTime , aUser, mycalendar); 
		Hour myhour1 = new Hour("lundi", sTime1, dTime1 , aUser, mycalendar); 

		hourRepository.save(myhour);
		hourRepository.save(myhour1);
		Event myevent = new Event("LasFiesta", sdate , myhour);
		Event myevent1= new Event("LasFiestaMcGill", sdate1 , myhour1);

		eventRepository.save(myevent);
		eventRepository.save(myevent1);
		
		Time EndTimeToQueryFrom = new Time(1, 30, 50);
		myadress=null;
		mycalendar=null;
		myLibrary=null;
		myevent=null;
		myevent1=null;
		List <Hour> ListofHour = hourRepository.findByendTime(EndTimeToQueryFrom);
		for (Hour PerHour : ListofHour) {

			assertTrue(PerHour.getCalendar().getCalendarID().equals(myhour.getCalendar().getCalendarID()) || PerHour.getCalendar().getCalendarID().equals(myhour1.getCalendar().getCalendarID()));
			assertTrue(PerHour.getEmployee().getAddress().getAddressID().equals(myhour.getEmployee().getAddress().getAddressID()) || PerHour.getEmployee().getAddress().getAddressID().equals(myhour1.getEmployee().getAddress().getAddressID()));
			assertTrue(PerHour.getEndTime().equals(myhour.getEndTime()) ||PerHour.getEndTime().equals(myhour1.getEndTime()) );
			assertTrue(PerHour.getStartTime().equals(myhour.getStartTime()) ||PerHour.getStartTime().equals(myhour1.getStartTime()) );
			assertTrue(PerHour.getWeekday().equals(myhour.getWeekday()) ||PerHour.getWeekday().equals(myhour1.getWeekday()) );
			assertTrue(PerHour.getEmployee().getDemeritPts()==(myhour.getEmployee().getDemeritPts()) || PerHour.getEmployee().getDemeritPts()==(myhour1.getEmployee().getDemeritPts()));
			assertTrue(PerHour.getEmployee().getFirstName().equals(myhour.getEmployee().getFirstName()) ||PerHour.getEmployee().getFirstName().equals(myhour1.getEmployee().getFirstName()) );
			assertTrue(PerHour.getEmployee().getLibraryCardID()==(myhour.getEmployee().getLibraryCardID()) ||PerHour.getEmployee().getLibraryCardID()==(myhour1.getEmployee().getLibraryCardID()) );
			assertTrue(PerHour.getEmployee().getIsOnlineAcc()==(myhour.getEmployee().getIsOnlineAcc()) ||PerHour.getEmployee().getIsOnlineAcc()==(myhour1.getEmployee().getIsOnlineAcc()) );
			assertTrue(PerHour.getEmployee().getIsVerified()==(myhour.getEmployee().getIsVerified()) ||PerHour.getEmployee().getIsVerified()==(myhour1.getEmployee().getIsVerified()) );
			assertTrue(PerHour.getEmployee().getLibrarySystem().getSystemID().equals(myhour.getEmployee().getLibrarySystem().getSystemID()) ||PerHour.getEmployee().getLibrarySystem().getSystemID().equals(myhour1.getEmployee().getLibrarySystem().getSystemID()) );
			assertTrue(PerHour.getEmployee().getRole().equals(myhour.getEmployee().getRole()) ||PerHour.getEmployee().getRole().equals(myhour1.getEmployee().getRole()) );

			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	@Test
	public void TestPersistenceAndLoadByStartTime() {
		
		String str1 = "2015-03-31";
		Date sdate= Date.valueOf(str1);
		Time sTime = new Time(1, 30, 50);
		Time dTime = new Time(4, 30, 50);
		
		String str2 = "2015-03-25";
		Date sdate1= Date.valueOf(str2);
		Time sTime1 = new Time(1, 30, 50);
		Time dTime1 = new Time(5, 30, 50);
		Address myadress = new Address("51","Parkekx","Montreal","H5H6H7","Quebec","Canada");
		addressRepository.save(myadress);
		Calendar mycalendar = new Calendar();
		calendarRepository.save(mycalendar);
		LibrarySystem myLibrary = new LibrarySystem(myadress, mycalendar);
		librarySystemRepository.save(myLibrary);
		Employee aUser = new Employee(true, "Alex", "Bangala", true, 0, myadress, myLibrary, Role.Librarian);
		userRepository.save(aUser);
		Hour myhour = new Hour("mardi", sTime, dTime , aUser, mycalendar); 
		Hour myhour1 = new Hour("lundi", sTime1, dTime1 , aUser, mycalendar); 

		hourRepository.save(myhour);
		hourRepository.save(myhour1);
		Event myevent = new Event("LasFiesta", sdate , myhour);
		Event myevent1= new Event("LasFiestaMcGill", sdate1 , myhour1);

		eventRepository.save(myevent);
		eventRepository.save(myevent1);
		
		Time StartTimeToQueryFrom = new Time(1, 30, 50);
		myadress=null;
		mycalendar=null;
		myLibrary=null;
		myevent=null;
		myevent1=null;
		List <Hour> ListofHour = hourRepository.findByendTime(StartTimeToQueryFrom);
		for (Hour PerHour : ListofHour) {

			assertTrue(PerHour.getCalendar().getCalendarID().equals(myhour.getCalendar().getCalendarID()) || PerHour.getCalendar().getCalendarID().equals(myhour1.getCalendar().getCalendarID()));
			assertTrue(PerHour.getEmployee().getAddress().getAddressID().equals(myhour.getEmployee().getAddress().getAddressID()) || PerHour.getEmployee().getAddress().getAddressID().equals(myhour1.getEmployee().getAddress().getAddressID()));
			assertTrue(PerHour.getEndTime().equals(myhour.getEndTime()) ||PerHour.getEndTime().equals(myhour1.getEndTime()) );
			assertTrue(PerHour.getStartTime().equals(myhour.getStartTime()) ||PerHour.getStartTime().equals(myhour1.getStartTime()) );
			assertTrue(PerHour.getWeekday().equals(myhour.getWeekday()) ||PerHour.getWeekday().equals(myhour1.getWeekday()) );
			assertTrue(PerHour.getEmployee().getDemeritPts()==(myhour.getEmployee().getDemeritPts()) || PerHour.getEmployee().getDemeritPts()==(myhour1.getEmployee().getDemeritPts()));
			assertTrue(PerHour.getEmployee().getFirstName().equals(myhour.getEmployee().getFirstName()) ||PerHour.getEmployee().getFirstName().equals(myhour1.getEmployee().getFirstName()) );
			assertTrue(PerHour.getEmployee().getLibraryCardID()==(myhour.getEmployee().getLibraryCardID()) ||PerHour.getEmployee().getLibraryCardID()==(myhour1.getEmployee().getLibraryCardID()) );
			assertTrue(PerHour.getEmployee().getIsOnlineAcc()==(myhour.getEmployee().getIsOnlineAcc()) ||PerHour.getEmployee().getIsOnlineAcc()==(myhour1.getEmployee().getIsOnlineAcc()) );
			assertTrue(PerHour.getEmployee().getIsVerified()==(myhour.getEmployee().getIsVerified()) ||PerHour.getEmployee().getIsVerified()==(myhour1.getEmployee().getIsVerified()) );
			assertTrue(PerHour.getEmployee().getLibrarySystem().getSystemID().equals(myhour.getEmployee().getLibrarySystem().getSystemID()) ||PerHour.getEmployee().getLibrarySystem().getSystemID().equals(myhour1.getEmployee().getLibrarySystem().getSystemID()) );
			assertTrue(PerHour.getEmployee().getRole().equals(myhour.getEmployee().getRole()) ||PerHour.getEmployee().getRole().equals(myhour1.getEmployee().getRole()) );

			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	@Test
	public void TestPersisitenceAndLoadHourByEvent() {
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
	
	
		
		userRepository.save(aUser);
		librarySystemRepository.save(myLibrary);
		calendarRepository.save(mycalendar);
		addressRepository.save(myadress);
		hourRepository.save(myhour);


		
		
//		myhour.setEvent(myevent);
//		myevent.setEventhour(myhour);
//		eventRepository.save(myevent);
//		hourRepository.save(myhour);

		myadress=null;
		mycalendar=null;
		myLibrary=null;
		aUser=null;
		
		Event e = eventRepository.findByEventID(myevent.getEventID());
		Hour HourofEvent = hourRepository.getByevent(e);
		assertEquals(myevent.getEventDate(),HourofEvent.getEvent().getEventDate());
		assertEquals(myevent.getEventhour().getStartTime(),HourofEvent.getEvent().getEventhour().getStartTime());
		assertEquals(myevent.getEventhour().getEndTime(),HourofEvent.getEvent().getEventhour().getEndTime());
		assertEquals(myevent.getEventhour().getCalendar().getCalendarID(),HourofEvent.getEvent().getEventhour().getCalendar().getCalendarID());
		assertEquals(myevent.getEventID(),HourofEvent.getEvent().getEventID());
		assertEquals(myevent.getName(),HourofEvent.getEvent().getName());
		assertEquals(myevent.getEventhour().getWeekday(),HourofEvent.getEvent().getEventhour().getWeekday());
		assertEquals(myevent.getEventhour().getEmployee().getLibraryCardID(),HourofEvent.getEvent().getEventhour().getEmployee().getLibraryCardID());
		assertEquals(myevent.getEventhour().getCalendar().getLibrarySystem().getSystemID(),HourofEvent.getEvent().getEventhour().getCalendar().getLibrarySystem().getSystemID());
		assertEquals(myevent.getEventhour().getEmployee().getAddress().getAddressID(),HourofEvent.getEvent().getEventhour().getEmployee().getAddress().getAddressID());
		assertEquals(myevent.getEventhour().getEmployee().getDemeritPts(),HourofEvent.getEvent().getEventhour().getEmployee().getDemeritPts());
		assertEquals(myevent.getEventhour().getEmployee().getFirstName(),HourofEvent.getEvent().getEventhour().getEmployee().getFirstName());
		assertEquals(myevent.getEventhour().getEmployee().getLastName(),HourofEvent.getEvent().getEventhour().getEmployee().getLastName());
		assertEquals(myevent.getEventhour().getEmployee().getIsVerified(),HourofEvent.getEvent().getEventhour().getEmployee().getIsVerified());
		assertEquals(myevent.getEventhour().getEmployee().getIsOnlineAcc(),HourofEvent.getEvent().getEventhour().getEmployee().getIsOnlineAcc());
		assertEquals(myevent.getEventhour().getEmployee().getRole(),HourofEvent.getEvent().getEventhour().getEmployee().getRole());


		
		
	}
	

	
	
	
	
	@Test
	public void TestPersisitenceAndLoadHourByWeekday() {
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
		
		Hour HourofEvent = hourRepository.findByweekday("mardi");
	
		assertEquals(myhour.getWeekday(),HourofEvent.getWeekday());
		assertEquals(myhour.getStartTime(),HourofEvent.getStartTime());
		assertEquals(myhour.getCalendar().getCalendarID(),HourofEvent.getCalendar().getCalendarID());
		assertEquals(myhour.getEndTime(),HourofEvent.getEndTime());
		assertEquals(myhour.getEmployee().getLibraryCardID(),HourofEvent.getEmployee().getLibraryCardID());

		
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
