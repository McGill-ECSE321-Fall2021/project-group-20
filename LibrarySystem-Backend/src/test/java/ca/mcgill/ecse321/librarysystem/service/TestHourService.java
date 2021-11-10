package ca.mcgill.ecse321.librarysystem.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.librarysystem.dao.EventRepository;
import ca.mcgill.ecse321.librarysystem.dao.HourRepository;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.model.User;
import ca.mcgill.ecse321.librarysystem.model.Booking.BookingType;
import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.Employee;
import ca.mcgill.ecse321.librarysystem.model.Employee.Role;
import ca.mcgill.ecse321.librarysystem.model.Event;
import ca.mcgill.ecse321.librarysystem.model.Hour;


@ExtendWith(MockitoExtension.class)
public class TestHourService {
	@Mock
	private HourRepository Hrepo;
	
	@InjectMocks
	private HourService hour;

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
		lenient().when(Hrepo.findBycalendar(any(Calendar.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(myCalendar)) {
				List<Hour> myList = new ArrayList<Hour>();
				myList.add(myHour);
				return myList;			}
			return null;
		});
		
		lenient().when(Hrepo.findByemployee(any(Employee.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(aUser)) {
				List<Hour> myList = new ArrayList<Hour>();
				myList.add(myHour);
				return myList;			}
			return null;
		});
		
		
		lenient().when(Hrepo.findByendTime(any(Time.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(dTime)) {
				List<Hour> myList = new ArrayList<Hour>();
				myList.add(myHour);
				return myList;			}
			return null;
		});
		
		lenient().when(Hrepo.findBystartTime(any(Time.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(sTime)) {
				List<Hour> myList = new ArrayList<Hour>();
				myList.add(myHour);
				return myList;			}
			return null;
		});
		
		lenient().when(Hrepo.getByevent(any(Event.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(myEvent)) {
				return myHour;
			}
			return null;
		});
		
		lenient().when(Hrepo.findByweekday(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals("mardi")) {
				return myHour;
			}
			return null;
		});
		
	}
	
	
	@Test
	public void testCreateHour() {
		assertEquals(0, hour.getAllHours().size());
		 String str1 = "2012-03-31";
		 Date sDate = Date.valueOf(str1);
		 Address myAdress = new Address("511", "Parkx", "Montreal", "H7H6H7", "Saguenay", "Canada");
		 Employee aUser = new Employee(true, true, "Abdel", "Madjid", true, 0, myAdress, Role.Librarian);
		 Time dTime = new Time(1, 50, 50);
		Time sTime = new Time(1, 15, 50);		
		 Calendar myCalendar = new Calendar();
		 Hour myHour = null;
		try {
			myHour = hour.createHour("lundi", sTime, dTime, aUser, myCalendar);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(myHour);
		assertEquals(myHour.getCalendar(), myCalendar);
		assertEquals(myHour.getEmployee(),aUser);
		assertEquals(myHour.getEndTime(),dTime);
		assertEquals(myHour.getStartTime(),sTime);
		assertEquals(myHour.getWeekday(),"lundi");
		

	}

	
	@Test
	public void testCreateHourFail() {
		assertEquals(0, hour.getAllHours().size());
		 String str1 = "2012-03-31";
		 Date sDate = Date.valueOf(str1);
		 Address myAdress = new Address("511", "Parkx", "Montreal", "H7H6H7", "Saguenay", "Canada");
		 Employee aUser = new Employee(true, true, "Abdel", "Madjid", true, 0, myAdress, Role.Librarian);
		 Time dTime = new Time(1, 50, 50);
		Time sTime = new Time(1, 15, 50);		
		String error = null;
		 Calendar myCalendar = new Calendar();
		 Hour myHour = null;
		try {
			myHour = hour.createHour("lundi", sTime, dTime, null, myCalendar);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		assertNull(myHour);
		assertEquals("Please enter a valid employee", error);
		

	}

	@Test 
	public void testgetHourListByCalendar() {
		List<Hour> myHour = null;
		String error = null;
		try {
			myHour = hour.getHourlistBycalendar(myCalendar);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(myHour);
		for (Hour hours : myHour) {
			assertEquals(hours.getCalendar(), myCalendar);
			assertEquals(hours.getEmployee(),aUser);
			assertEquals(hours.getEndTime(),dTime);
			assertEquals(hours.getStartTime(),sTime);
			assertEquals(hours.getWeekday(),"mardi");
		}
	}
	
	
	@Test 
	public void testgetHourListByCalendarFail() {
		List<Hour> myHour = null;
		String error = null;
		try {
			myHour = hour.getHourlistBycalendar(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
	
		
			assertNull(myHour);
			assertEquals("Please enter a valid calendar", error);
			
	
	}
	
	@Test 
	public void testgetHourListByEmployee() {
		List<Hour> myHour = null;
		String error = null;
		try {
			myHour = hour.getHourlistByEmployee(aUser);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(myHour);
		for (Hour hours : myHour) {
			assertEquals(hours.getCalendar(), myCalendar);
			assertEquals(hours.getEmployee(),aUser);
			assertEquals(hours.getEndTime(),dTime);
			assertEquals(hours.getStartTime(),sTime);
			assertEquals(hours.getWeekday(),"mardi");
		}
	}
	
	@Test 
	public void testgetHourListByEmployeeFail() {
		List<Hour> myHour = null;
		String error = null;
		try {
			myHour = hour.getHourlistByEmployee(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
	
		
			assertNull(myHour);
			assertEquals("Please enter a valid employee", error);
			
	
	}
	
	@Test 
	public void testgetHourListByEndTime() {
		List<Hour> myHour = null;
		String error = null;
		try {
			myHour = hour.getHourListbyendTime(dTime);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(myHour);
		for (Hour hours : myHour) {
			assertEquals(hours.getCalendar(), myCalendar);
			assertEquals(hours.getEmployee(),aUser);
			assertEquals(hours.getEndTime(),dTime);
			assertEquals(hours.getStartTime(),sTime);
			assertEquals(hours.getWeekday(),"mardi");
		}
	}
	
	@Test 
	public void testgetHourListByEndTimeFail() {
		List<Hour> myHour = null;
		String error = null;
		try {
			myHour = hour.getHourListbyendTime(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
	
		
			assertNull(myHour);
			assertEquals("Please enter a valid end Time", error);
			
	
	}
	
	@Test 
	public void testgetHourListByStartTime() {
		List<Hour> myHour = null;
		String error = null;
		try {
			myHour = hour.getHourListbystartTime(sTime);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(myHour);
		for (Hour hours : myHour) {
			assertEquals(hours.getCalendar(), myCalendar);
			assertEquals(hours.getEmployee(),aUser);
			assertEquals(hours.getEndTime(),dTime);
			assertEquals(hours.getStartTime(),sTime);
			assertEquals(hours.getWeekday(),"mardi");
		}
	}
	@Test 
	public void testgetHourListByStartTimeFail() {
		List<Hour> myHour = null;
		String error = null;
		try {
			myHour = hour.getHourListbystartTime(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
	
		
			assertNull(myHour);
			assertEquals("Please enter a valid start Time", error);
			
	
	}
	
	
}

