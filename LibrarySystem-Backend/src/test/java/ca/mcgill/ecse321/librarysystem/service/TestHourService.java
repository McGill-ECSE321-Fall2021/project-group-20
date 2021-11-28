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

import ca.mcgill.ecse321.librarysystem.dao.HourRepository;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;
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
	
	private static final Hour myHour = new Hour("tuesday", sTime, dTime, aUser, myCalendar, Hour.Type.System);
	private static final Event myEvent = new Event(Ename, sDate, myHour);
	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(Hrepo.findBycalendar(any(Calendar.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(myCalendar)) {
				//Hour myHour = new Hour("tuesday", sTime, dTime, aUser, myCalendar);


				List<Hour> myList = new ArrayList<Hour>();
				myList.add(myHour);
				return myList;			}
			return null;
		});
		
		lenient().when(Hrepo.findByemployee(any(Employee.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(aUser)) {
				//Hour myHour = new Hour("tuesday", sTime, dTime, aUser, myCalendar);
				List<Hour> myList = new ArrayList<Hour>();
				myList.add(myHour);
				return myList;			}
			return null;
		});
		
		
		lenient().when(Hrepo.findByendTime(any(Time.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(dTime)) {
				//Hour myHour = new Hour("tuesday", sTime, dTime, aUser, myCalendar);

				List<Hour> myList = new ArrayList<Hour>();
				myList.add(myHour);
				return myList;			}
			return null;
		});
		
		lenient().when(Hrepo.findBystartTime(any(Time.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(sTime)) {
				//Hour myHour = new Hour("tuesday", sTime, dTime, aUser, myCalendar);

				List<Hour> myList = new ArrayList<Hour>();
				myList.add(myHour);
				return myList;			}
			return null;
		});
		
		lenient().when(Hrepo.getByevent(any(Event.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(myEvent)) {
			//	Hour myHour = new Hour("tuesday", sTime, dTime, aUser, myCalendar);

				return myHour;
			}
			return null;
		});
		
		lenient().when(Hrepo.findByweekday(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals("tuesday")) {
				//Hour myHour = new Hour("tuesday", sTime, dTime, aUser, myCalendar);

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
			myHour = hour.createHour("monday", sTime, dTime, aUser, myCalendar, "System");
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(myHour);
		assertEquals(myHour.getCalendar(), myCalendar);
		assertEquals(myHour.getEmployee(),aUser);
		assertEquals(myHour.getEndTime(),dTime);
		assertEquals(myHour.getStartTime(),sTime);
		assertEquals(myHour.getWeekday(),"monday");
		

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
			myHour = hour.createHour("monday", sTime, dTime, null, myCalendar, "System");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		assertNull(myHour);
		assertEquals("Please enter a valid employee", error);
		

	}

	@Test 
	public void testgetHourListByCalendar() {
		List<Hour> myHourTest = null;
		String error = null;
		lenient().when(Hrepo.findBycalendar(any(Calendar.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(myCalendar)) {
				Hour myHour = new Hour("tuesday", sTime, dTime, aUser, myCalendar, Hour.Type.System);


				List<Hour> myList = new ArrayList<Hour>();
				myList.add(myHour);
				return myList;			}
			return null;
		});
		try {
			myHourTest = hour.getHourlistBycalendar(myCalendar);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(myHour);
		for (Hour hours : myHourTest) {
			assertEquals(hours.getCalendar(), myCalendar);
			assertEquals(hours.getEmployee(),aUser);
			assertEquals(hours.getEndTime(),dTime);
			assertEquals(hours.getStartTime(),sTime);
			assertEquals(hours.getWeekday(),"tuesday");
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
		List<Hour> myHourTest = null;

		lenient().when(Hrepo.findByemployee(any(Employee.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(aUser)) {
				Hour myHour = new Hour("tuesday", sTime, dTime, aUser, myCalendar, Hour.Type.System);
				List<Hour> myList = new ArrayList<Hour>();
				myList.add(myHour);
				return myList;			}
			return null;
		});
		
		String error = null;
		try {
			myHourTest = hour.getHourlistByEmployee(aUser);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(myHourTest);
		for (Hour hours : myHourTest) {
			assertEquals(hours.getCalendar(), myCalendar);
			assertEquals(hours.getEmployee(),aUser);
			assertEquals(hours.getEndTime(),dTime);
			assertEquals(hours.getStartTime(),sTime);
			assertEquals(hours.getWeekday(),"tuesday");
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
		List<Hour> myHourTest = null;
		String error = null;
		
		lenient().when(Hrepo.findByendTime(any(Time.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(dTime)) {
				Hour myHour = new Hour("tuesday", sTime, dTime, aUser, myCalendar, Hour.Type.System);

				List<Hour> myList = new ArrayList<Hour>();
				myList.add(myHour);
				return myList;			}
			return null;
		});
		try {
			myHourTest = hour.getHourListbyendTime(dTime);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(myHourTest);
		for (Hour hours : myHourTest) {
			assertEquals(hours.getCalendar(), myCalendar);
			assertEquals(hours.getEmployee(),aUser);
			assertEquals(hours.getEndTime(),dTime);
			assertEquals(hours.getStartTime(),sTime);
			assertEquals(hours.getWeekday(),"tuesday");
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
		List<Hour> myHourTEST = null;
		lenient().when(Hrepo.findBystartTime(any(Time.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(sTime)) {
				Hour myHour = new Hour("tuesday", sTime, dTime, aUser, myCalendar, Hour.Type.System);

				List<Hour> myList = new ArrayList<Hour>();
				myList.add(myHour);
				return myList;			}
			return null;
		});
		String error = null;
		try {
			myHourTEST = hour.getHourListbystartTime(sTime);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(myHourTEST);
		for (Hour hours : myHourTEST) {
			assertEquals(hours.getCalendar(), myCalendar);
			assertEquals(hours.getEmployee(),aUser);
			assertEquals(hours.getEndTime(),dTime);
			assertEquals(hours.getStartTime(),sTime);
			assertEquals(hours.getWeekday(),"tuesday");
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
	
	@Test
	public void testgetHourbyEvent() {
		Hour myHourTEST = null;
		String error = null;
		lenient().when(Hrepo.getByevent(any(Event.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(myEvent)) {
				Hour myHour = new Hour("tuesday", sTime, dTime, aUser, myCalendar, Hour.Type.System);

				return myHour;
			}
			return null;
		});
		try {
			myHourTEST = hour.getHourbyEvent(myEvent);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
	}
		
		assertEquals(myHourTEST.getCalendar(), myCalendar);
		assertEquals(myHourTEST.getEmployee(),aUser);
		assertEquals(myHourTEST.getEndTime(),dTime);
		assertEquals(myHourTEST.getStartTime(),sTime);
		assertEquals(myHourTEST.getWeekday(),"tuesday");
	
}
	
	
	@Test
	public void testgetHourbyEventFail() {
		Hour myHour = null;
		String error = null;
		try {
			myHour = hour.getHourbyEvent(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
	}
		
		assertNull(myHour);
		assertEquals("Please enter a valid Event", error);
}
	
	@Test
	public void testgetHourbyWeekday() {
		Hour myHourTest = null;
		String error = null;
		lenient().when(Hrepo.findByweekday(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals("tuesday")) {
				Hour myHour = new Hour("tuesday", sTime, dTime, aUser, myCalendar, Hour.Type.System);

				return myHour;
			}
			return null;
		});
		
		try {
			myHourTest = hour.getHourbyWeekday("tuesday");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
	}
		
		assertEquals(myHourTest.getCalendar(), myCalendar);
		assertEquals(myHourTest.getEmployee(),aUser);
		assertEquals(myHourTest.getEndTime(),dTime);
		assertEquals(myHourTest.getStartTime(),sTime);
		assertEquals(myHourTest.getWeekday(),"tuesday");
		

	
}
	

	@Test
	public void testgetHourbyWeekdayFail() {
		Hour myHour = null;
		String error = null;
		try {
			myHour = hour.getHourbyWeekday("bbuPIFB");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
	}
		
		assertNull(myHour);
		assertEquals("Please enter a valid Weekday, with either a Capital first letter or all lowercase", error);
		

	
}
	@Test 
	public void testDeleteHourByEmployee() {
		String error = null;
		try {
			hour.deleteHourbyEmployee(aUser);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		
	
	}
	
	@Test
	public void testDeleteHourEmployeeFail() {
		String error = null;
		try {
			hour.deleteHourbyEmployee(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter a valid Employee", error);	
		
	}
	
	@Test 
	public void testDeleteHourByEvent() {
		String error = null;
		try {
			hour.deleteHourbyEvent(myEvent);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		
	
	}
	
	@Test 
	public void testDeleteHourByEventFail() {
		String error = null;
		try {
			hour.deleteHourbyEvent(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter a valid event", error);	
		
	
	}
	
	@Test 
	public void testDeleteHourByWeekday() {
		String error = null;
		try {
			hour.deleteHourbyWeekday("tuesday");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		
	
	}
	

	@Test
	public void testUpdateHourStartTimeByWeekday() {
		 String str1 = "2012-03-31";
		 Date sDate = Date.valueOf(str1);
		 Address myAdress = new Address("511", "Parkx", "Montreal", "H7H6H7", "Saguenay", "Canada");
		 Employee aUser = new Employee(true, true, "Abdel", "Madjid", true, 0, myAdress, Role.Librarian);
		 Time dTime = new Time(1, 50, 50);
		Time sTime = new Time(1, 15, 50);	
		Time newTime = new Time(1, 00, 50);	
		 Calendar myCalendar = new Calendar();
		 String error = null;
		 Hour myHour = new Hour ("tuesday", sTime, dTime, aUser, myCalendar, Hour.Type.System);
		try {
			hour.updateHourStartTimebyWeekday("tuesday",newTime);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		
		}
		
		Hour testhour = hour.getHourbyWeekday("tuesday");
		assertNull(error);
		assertNotNull(testhour);
		assertEquals(testhour.getStartTime(), newTime);
		
		
				
	}
	
	@Test
	public void testUpdateHourEndTimeByWeekday() {
		 String str1 = "2012-03-31";
		 Date sDate = Date.valueOf(str1);
		 Address myAdress = new Address("511", "Parkx", "Montreal", "H7H6H7", "Saguenay", "Canada");
		 Employee aUser = new Employee(true, true, "Abdel", "Madjid", true, 0, myAdress, Role.Librarian);
		 Time dTime = new Time(1, 50, 50);
		Time sTime = new Time(1, 15, 50);	
		Time newTime = new Time(1, 00, 50);	
		 Calendar myCalendar = new Calendar();
		 String error = null;
		 Hour myHour = new Hour ("tuesday", sTime, dTime, aUser, myCalendar, Hour.Type.System);
		try {
			hour.updateHourEndTimebyWeekday("tuesday",newTime);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		
		}
		
		Hour testhour = hour.getHourbyWeekday("tuesday");
		assertNull(error);
		assertNotNull(testhour);
		assertEquals(testhour.getEndTime(), newTime);
		
		
				
	}
	

	@Test
	public void testUpdateHourEmployeeByWeekday() {
		 String str1 = "2012-03-31";
		 Date sDate = Date.valueOf(str1);
		 Address myAdress = new Address("511", "Parkx", "Montreal", "H7H6H7", "Saguenay", "Canada");
		 Employee aUser = new Employee(true, true, "Abdel", "Madjid", true, 0, myAdress, Role.Librarian);
		 Employee newUser = new Employee(true, true, "Carey", "Price", true, 0, myAdress, Role.Librarian);

		 
		 Time dTime = new Time(1, 50, 50);
		Time sTime = new Time(1, 15, 50);	
		Time newTime = new Time(1, 00, 50);	
		 Calendar myCalendar = new Calendar();
		 String error = null;
		 Hour myHour = new Hour ("tuesday", sTime, dTime, aUser, myCalendar, Hour.Type.System);
		try {
			hour.updateHourEmployeebyWeekday("tuesday",newUser);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		
		}
		
		Hour testhour = hour.getHourbyWeekday("tuesday");
		assertNull(error);
		assertNotNull(testhour);
		assertEquals(testhour.getEmployee(), newUser);
		
		
				
	}
	
	@Test
	public void testUpdateEventatThisHourbyWeekday() {
		 

	
		 Calendar myCalendar = new Calendar();
		 String error = null;
		
		 
		 String newEventD = "2012-03-31";
		 Date newDofEvent = Date.valueOf(newEventD);
		 
		 Time newTime = new Time(1, 00, 50);
		 Time dTime = new Time(1, 50, 50);
		 
		 Employee newUser = new Employee(true, true, "Carey", "Price", true, 0, myAdress, Role.Librarian);

		 Hour newHour = new Hour ("tuesday", newTime, dTime, newUser, myCalendar, Hour.Type.System);
		 Event updatedEvent = new Event("PLeaseWORK", newDofEvent, newHour);	
			try {
				hour.updateEventatThisHourbyWeekday("tuesday",updatedEvent);
			} catch (IllegalArgumentException e) {
				error = e.getMessage();
			
			}
			
			Hour testhour = hour.getHourbyWeekday("tuesday");
			assertNull(error);
			assertNotNull(testhour);
			assertEquals(testhour.getWeekday(), "tuesday");
			assertEquals(testhour.getEvent(), updatedEvent);
			assertEquals(testhour.getEvent().getEventhour().getStartTime() ,updatedEvent.getEventhour().getStartTime());




	}
	
}

