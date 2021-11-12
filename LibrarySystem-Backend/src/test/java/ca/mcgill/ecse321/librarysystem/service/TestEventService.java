package ca.mcgill.ecse321.librarysystem.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.librarysystem.dao.EventRepository;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;
import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.Employee;
import ca.mcgill.ecse321.librarysystem.model.Employee.Role;
import ca.mcgill.ecse321.librarysystem.model.Event;
import ca.mcgill.ecse321.librarysystem.model.Hour;

@ExtendWith(MockitoExtension.class)
public class TestEventService {
	
	@Mock
	private EventRepository Erepo;
	
	@InjectMocks
	private EventService event;
	
	private static final String Ename = "Harsh's Party";
	private static final String str1 = "2015-03-31";
	private static final Date sDate = Date.valueOf(str1);
	private static final Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
	private static final Calendar myCalendar = new Calendar();
	private static final LibrarySystem myLibrary = new LibrarySystem(myAdress, myCalendar);
	private static final Employee aUser = new Employee(true, true, "Alex", "Bangala", true, 0, myAdress, Role.Librarian);
	private static final Time dTime = new Time(1, 30, 50);
	private static final Time sTime = new Time(1, 10, 50);
	
	private static final Hour myHour = new Hour("mardi", sTime, dTime, aUser, myCalendar);
	private static final Event myEvent = new Event(Ename, sDate, myHour);

	@BeforeEach
	public void setMockOutput() {
		myEvent.setEventID("HIII");
	lenient().when(Erepo.findByEventDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> {
		if (invocation.getArgument(0).equals(sDate)) {
			return myEvent;
		}
		return null;
	});
	
	lenient().when(Erepo.findByEventID(anyString())).thenAnswer((InvocationOnMock invocation) -> {
		if (invocation.getArgument(0).equals("HIII") ) {
			return myEvent;
		}
		return null;	
	});
	
	
	
	lenient().when(Erepo.findByEventhour(any(Hour.class))).thenAnswer((InvocationOnMock invocation) -> {
		if (invocation.getArgument(0).equals(myHour)){
			return myEvent;
		}
		return null;	
	});
	
	
	
	lenient().when(Erepo.findByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
		if (invocation.getArgument(0).equals(Ename)){
			List<Event> myList = new ArrayList<Event>();
			myList.add(myEvent);
			return myList;
		}
		return null;	
	});
	
	

	
	

}
	
	@Test
	public void testCreateEvent() {
		assertEquals(0, event.getAllEvents().size());
		 String name = "Alex Party";
		 String str1 = "2012-03-31";
		 Date sDate = Date.valueOf(str1);
		 Address myAdress = new Address("511", "Parkx", "Montreal", "H7H6H7", "Saguenay", "Canada");
		 Employee aUser = new Employee(true, true, "Abdel", "Madjid", true, 0, myAdress, Role.Librarian);
		 Time dTime = new Time(1, 50, 50);
		Time sTime = new Time(1, 15, 50);		
		 Calendar myCalendar = new Calendar();
		 Hour myHour = new Hour("lundi", sTime, dTime, aUser, myCalendar);
		 Event myEvent = null;
		try {
			myEvent = event.createEvent(name, sDate, myHour);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(myEvent);
		assertEquals(name, myEvent.getName());
		assertEquals(sDate,myEvent.getEventDate());
		assertEquals(myHour,myEvent.getEventhour());

	}

	
	
	@Test
	public void testCreateEventNull() {
		assertEquals(0, event.getAllEvents().size());
		 String name = null;
		 String str1 = "2012-03-31";
		 Date sDate = Date.valueOf(str1);
		 Address myAdress = new Address("511", "Parkx", "Montreal", "H7H6H7", "Saguenay", "Canada");
		 Employee aUser = new Employee(true, true, "Abdel", "Madjid", true, 0, myAdress, Role.Librarian);
		 Time dTime = new Time(1, 50, 50);
		Time sTime = new Time(1, 15, 50);		
		 Calendar myCalendar = new Calendar();
		 Hour myHour = new Hour("lundi", sTime, dTime, aUser, myCalendar);
		 Event myEvent = null;
		 String error = null;
		try {
			myEvent = event.createEvent(name, sDate, myHour);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(myEvent);
		assertEquals("Please enter a valid name", error);

	}

	@Test 
	public void testgetEventByDate() {
		Event myEvent = null;
		String error = null;
		try {
			myEvent = event.getEventByDate(sDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(myEvent);
		assertEquals(myEvent.getEventDate(),sDate);
		assertEquals(myEvent.getEventhour(),myHour);
		assertEquals(myEvent.getName(),Ename);
	}
	
	
	@Test 
	public void testgetEventByID() {
		Event myEvent = null;
		String error = null;
		String ID = "HIII";
		try {
			myEvent = event.getEventByID(ID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(myEvent);
		assertEquals(myEvent.getEventID(),ID);
		assertEquals(myEvent.getEventDate(),sDate);
		assertEquals(myEvent.getEventhour(),myHour);
		assertEquals(myEvent.getName(),Ename);
	}
	
	
	@Test 
	public void testgetEventByHour() {
		Event myEvent = null;
		String error = null;
		String ID = "HIII";
		try {
			myEvent = event.getEventByHour(myHour);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(myEvent);
		assertEquals(myEvent.getEventID(),ID);
		assertEquals(myEvent.getEventDate(),sDate);
		assertEquals(myEvent.getEventhour(),myHour);
		assertEquals(myEvent.getName(),Ename);
	}
	
	
	
	@Test 
	public void testgetEventListByName() {
		List<Event> myEvent = null;
		String error = null;
		String ID = "HIII";
		try {
			myEvent = event.getEventlistByName(Ename);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(myEvent);
		for (Event events : myEvent) {
		assertEquals(events.getEventID(),ID);
		assertEquals(events.getEventDate(),sDate);
		assertEquals(events.getEventhour(),myHour);
		assertEquals(events.getName(),Ename);
		}
	}
	
	@Test 
	public void testdeleteEventByDate() {
		String error = null;
		String ID = "HIII";
		try {
			event.deleteEventbyDate(sDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	}
	
	@Test 
	public void testdeleteEventByID() {
		String error = null;
		String ID = "HIII";
		try {
			event.deleteEventByID(ID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	}
	
	@Test 
	public void testdeleteEventByHour() {
		String error = null;
		String ID = "HIII";
		try {
			event.deleteEventbyHour(myHour);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	}
	
//PLEASE SOMEBODY HEEEELLLLLLLPPPPPPP MEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
	@Test
	public void testdeleteEventByName() {
		String error = null;
		boolean b= false ; 
		String ID = "HIII";
		
		try {
			b=event.deleteEventByName(Ename);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	
	}
	
	
	@Test
	public void testupdateByDate() {
		 String name = "Alex Party";
		 String str1 = "2012-03-31";
		 Date oldDate = Date.valueOf(str1);
		 String str2 = "2019-03-31";
		 Date upDate = Date.valueOf(str2);
	
		String Id = "HIII";
		 String error = null;
		try {
			event.updateEventdate(Id,upDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Event testEvent = event.getEventByID(Id);
		assertNull(error);
		assertNotNull(testEvent);
		assertEquals(testEvent.getEventDate(),upDate);
		
		//All I'm doing is reset it to the true initial date so the other tests passes accordingly 
		event.updateEventdate(Id,sDate);

	}
	
	@Test
	public void testupdateByHour() {
		 String name = "Alex Party";
		 Hour zehour = new Hour("lundi", sTime, dTime, aUser, myCalendar);
		 String Id = "HIII";
		 String error = null;
		try {
			event.updateEventHour(Id,zehour);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Event testEvent = event.getEventByID(Id);
		assertNull(error);
		assertNotNull(testEvent);
		assertEquals(testEvent.getEventhour(),zehour);
		
		//All I'm doing is reset it to the true initial date so the other tests passes accordingly 
		event.updateEventHour(Id,myHour);
	}
	
	@Test
	public void testupdateEventNameByDayandID () {
		 String name = "Majid's party is the best";
		 Hour zehour = new Hour("lundi", sTime, dTime, aUser, myCalendar);
		 String str1 = "2012-03-31";
		 Date oldDate = Date.valueOf(str1);
		 String Id = "HIII";
		 String error = null;
		try {
			event.updateEventNameByID(Id,name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Event testEvent = event.getEventByID(Id);
		assertNull(error);
		assertNotNull(testEvent);
		assertEquals(testEvent.getName(),name);
		
		//All I'm doing is reset it to the true initial date so the other tests passes accordingly 
		event.updateEventHour(Id,myHour);
	}
}
