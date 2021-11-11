package ca.mcgill.ecse321.librarysystem.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

import ca.mcgill.ecse321.librarysystem.dao.BookingRepository;
import ca.mcgill.ecse321.librarysystem.dao.EmployeeRepository;
import ca.mcgill.ecse321.librarysystem.dao.EventRepository;
import ca.mcgill.ecse321.librarysystem.dao.HourRepository;
import ca.mcgill.ecse321.librarysystem.dao.ItemRepository;
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
public class TestBookingService {
	
	@Mock
	private BookingRepository Brepo;
	
	@Mock
	private EmployeeRepository Erepo;
	
	@Mock 
	private ItemRepository Irepo;
	
	@InjectMocks
	private BookingService booking;
	
	@Mock
	private EmployeeService service;
	
    private static final String FIRSTNAME_1 = "John";
    private static final String LASTNAME_1 = "Doe";
    private static final String CIVIC_1 = "1";
    private static final String STREET_1 = "University St";
    private static final String CITY_1 = "Montreal";
    private static final String POST_1 = "A1B C2C";
    private static final String PROV_1 = "QC";
    private static final String COUNTRY_1 = "Canada";
    private static final String EMAIL_1 = "a@a.ca";
    private static final String USERNAME_1 = "johndoe";
    private static final String PASS_1 = "12345678";

    private static final String EMAIL_KEY = "a@b.com";
    private static final String USER_KEY = "johnnydoet";
    private static final int ID_1 = 1;
    private static final int ID_KEY = 2;

	
	private static final Long itemId = (long) 12;
	private Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
	private static final Author myAuthor = new Author("J.K.", "Rowling");
	private static final Title myTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
	private static final Status status = Status.Available;
	private Employee myUser = new Employee(true, true, "Harsh", "Patel", true, 0, myAdress,Employee.Role.Librarian);
	private static final String str1 = "2015-03-31";
	private static final String str2 = "2015-04-05";
	private static final Date sDate = Date.valueOf(str1);
	private static final Date eDate = Date.valueOf(str2);
	private Item myItem = new Item(status, itemId, myTitle);
	private Booking myBooking = new Booking(sDate, eDate, BookingType.Reservation, myItem, (User) myUser);
	
	
	@BeforeEach
	public void setMockOutput() {
		
		myItem.setBooking(myBooking);
		myBooking.setUser(myUser);
		myBooking.setBookingID("HII");
	       lenient().when(Brepo.findBookingByBookingID(anyString())).thenAnswer((InvocationOnMock invocation) -> {
	            if (invocation.getArgument(0).equals("HII")) {
	                return myBooking;
	            }
	            return false;
	        });
	        
		
	       lenient().when(Brepo.findBookingByStartDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> {
	   		if (invocation.getArgument(0).equals(sDate)){
	   			
	   			List<Booking> myList = new ArrayList<Booking>();
	   			myList.add(myBooking);
	   			return myList;
	   		}
	   		return null;	
	   	});
	   	
	       lenient().when(Brepo.findBookingByEndDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> {
		   		if (invocation.getArgument(0).equals(eDate)){
		   			List<Booking> myList = new ArrayList<Booking>();
		   			myList.add(myBooking);
		   			return myList;
		   		}
		   		return null;	
		   	});
		   	
		   	
	       lenient().when(Brepo.findBookingByItem(any(Item.class))).thenAnswer((InvocationOnMock invocation) -> {
	            if (invocation.getArgument(0).equals(myItem)) {
	                return myBooking;
	            }
	            return false;
	        });
	        
	     	
	       lenient().when(Brepo.findBookingByUser(any(User.class))).thenAnswer((InvocationOnMock invocation) -> {
		   		if (invocation.getArgument(0).equals(myUser)){
		   			List<Booking> myList = new ArrayList<Booking>();
		   			myList.add(myBooking);
		   			return myList;
		   		}
		   		return null;	
		   	});
		   	
	       lenient().when(Brepo.findBookingByType(any(BookingType.class))).thenAnswer((InvocationOnMock invocation) -> {
			   		if (invocation.getArgument(0).equals(BookingType.Reservation)){
			   			List<Booking> myList = new ArrayList<Booking>();
			   			myList.add(myBooking);
			   			return myList;
			   		}
			   		return null;	
			   	});
			   	
	       lenient().when(Erepo.findEmployeeByLibraryCardID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
	           if (invocation.getArgument(0).equals(ID_1) || invocation.getArgument(0).equals(3)) {
	               Address a = new Address(CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
	               Employee e = new Employee(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a, Employee.Role.Librarian);
	               e.setLibraryCardID(ID_1);
	               e.setEmail(EMAIL_1);
	               e.setPassword(PASS_1);
	               e.setUsername(USERNAME_1);
	               
	               return e;
	           }
	           return null;
	        });
	       Answer<?> returnParamAsAnswer = (InvocationOnMock invocation) -> {return invocation.getArgument(0);};
	        lenient().when(Irepo.save(any(Item.class))).thenAnswer(returnParamAsAnswer);
	        //lenient().when(Brepo.save(any(Booking.class))).thenAnswer(returnParamAsAnswer);

		
	}
	
	
	
	
	@Test
	public void testCreateBooking() {
		assertEquals(0, booking.getAllBookings().size());
	 Long itemId = (long) 12;
		 Address myAdress = new Address("5891", "ParcJarry", "Montreal", "H5H6H7", "Quebec", "Canada");
		Author myAuthor = new Author("J.K.", "Rowling");
		 Title myTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		 Status status = Status.Available;
		 User myUser = new User(true, true, "Abdel", "Patel", true, 0, myAdress);
	 String str1 = "2015-03-31";
		 String str2 = "2015-04-05";
		 Date sDate = Date.valueOf(str1);
		 Date eDate = Date.valueOf(str2);
		Item myItem = new Item(status, itemId, myTitle);
	
		Booking myBooking = null;
		try {
			myBooking = booking.createBooking(sDate, eDate, BookingType.Reservation, myItem, myUser);
			myBooking.setBookingID("HII");
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(myBooking);
		assertEquals(eDate,myBooking.getEndDate());
		assertEquals("HII",myBooking.getBookingID());
		assertEquals(myItem,myBooking.getItem());
		assertEquals( BookingType.Reservation,myBooking.getType());
		assertEquals(myUser,myBooking.getUser());
		assertEquals(sDate,myBooking.getStartDate());

	}
	
	
	@Test
	public void testCreateBookingStartDateNull() {
		assertEquals(0, booking.getAllBookings().size());

	 Long itemId = (long) 12;
		 Address myAdress = new Address("5891", "ParcJarry", "Montreal", "H5H6H7", "Quebec", "Canada");
		Author myAuthor = new Author("J.K.", "Rowling");
		 Title myTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		 Status status = Status.Available;
		 User myUser = new User(true, true, "Abdel", "Patel", true, 0, myAdress);
	 String str1 = "2015-03-31";
		 String str2 = "2015-04-05";
		 Date sDate = Date.valueOf(str1);
		 Date eDate = Date.valueOf(str2);
		Item myItem = new Item(status, itemId, myTitle);
		String error = null;
	
		Booking myBooking = null;
		try {
			myBooking = booking.createBooking(null, eDate, BookingType.Reservation, myItem, myUser);
			myBooking.setBookingID("HII");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(myBooking);
		assertEquals("Please enter valid date yyyy-[m]m-[d]d", error);
	}
	
	@Test
	public void testCreateBookingEndDateNull() {
		assertEquals(0, booking.getAllBookings().size());

	 Long itemId = (long) 12;
		 Address myAdress = new Address("5891", "ParcJarry", "Montreal", "H5H6H7", "Quebec", "Canada");
		Author myAuthor = new Author("J.K.", "Rowling");
		 Title myTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		 Status status = Status.Available;
		 User myUser = new User(true, true, "Abdel", "Patel", true, 0, myAdress);
	 String str1 = "2015-03-31";
		 String str2 = "2015-04-05";
		 Date sDate = Date.valueOf(str1);
		 Date eDate = Date.valueOf(str2);
		Item myItem = new Item(status, itemId, myTitle);
		String error = null;
	
		Booking myBooking = null;
		try {
			myBooking = booking.createBooking(sDate, null, BookingType.Reservation, myItem, myUser);
			myBooking.setBookingID("HII");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(myBooking);
		assertEquals("Please enter valid date yyyy-[m]m-[d]d", error);
	}
	
	
	
	@Test
	public void testCreateBookingItemNull() {
		assertEquals(0, booking.getAllBookings().size());

	 Long itemId = (long) 12;
		 Address myAdress = new Address("5891", "ParcJarry", "Montreal", "H5H6H7", "Quebec", "Canada");
		Author myAuthor = new Author("J.K.", "Rowling");
		 Title myTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		 Status status = Status.Available;
		 User myUser = new User(true, true, "Abdel", "Patel", true, 0, myAdress);
	 String str1 = "2015-03-31";
		 String str2 = "2015-04-05";
		 Date sDate = Date.valueOf(str1);
		 Date eDate = Date.valueOf(str2);
		Item myItem = new Item(status, itemId, myTitle);
		String error = null;
	
		Booking myBooking = null;
		try {
			myBooking = booking.createBooking(sDate, eDate, BookingType.Reservation, null, myUser);
			myBooking.setBookingID("HII");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(myBooking);
		assertEquals("Please enter valid item", error);
	}
	
	
	
	
	@Test
	public void testCreateBookingUserNull() {
		assertEquals(0, booking.getAllBookings().size());

	 Long itemId = (long) 12;
		 Address myAdress = new Address("5891", "ParcJarry", "Montreal", "H5H6H7", "Quebec", "Canada");
		Author myAuthor = new Author("J.K.", "Rowling");
		 Title myTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		 Status status = Status.Available;
		 User myUser = new User(true, true, "Abdel", "Patel", true, 0, myAdress);
	 String str1 = "2015-03-31";
		 String str2 = "2015-04-05";
		 Date sDate = Date.valueOf(str1);
		 Date eDate = Date.valueOf(str2);
		Item myItem = new Item(status, itemId, myTitle);
		String error = null;
	
		Booking myBooking = null;
		try {
			myBooking = booking.createBooking(sDate, eDate, BookingType.Reservation, myItem, null);
			myBooking.setBookingID("HII");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(myBooking);
		assertEquals("Please enter a valid user", error);
	}
	
	
	
	@Test
	public void testgetBookingbyId() {
		Booking myBooking =null;
		String error = null;
		
		try {
			myBooking = booking.getBookingbyId("HII");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		
		assertNotNull(myBooking);
		assertEquals(eDate,myBooking.getEndDate());
		assertEquals("HII",myBooking.getBookingID());
		assertEquals(myItem,myBooking.getItem());
		assertEquals( BookingType.Reservation,myBooking.getType());
		assertEquals(myUser,myBooking.getUser());
		assertEquals(sDate,myBooking.getStartDate());
	}
	
	
	
	@Test
	public void testgetBookingbyIdFail() {
		Booking myBooking =null;
		String error = null;
		
		try {
			myBooking = booking.getBookingbyId(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		
		assertNull(myBooking);
		assertEquals("Please enter valid Booking ID", error);
	}
	
	
	
	@Test
	public void testgetBookingbyIdFailLength() {
		Booking myBooking =null;
		String error = null;
		
		try {
			myBooking = booking.getBookingbyId("");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		
		assertNull(myBooking);
		assertEquals("Please enter valid Booking ID", error);
	}
	
	
	@Test
	public void testgetBookingListbyStartDate()
	{
		List<Booking> myBooking = null;
		String error = null;
		try {
			myBooking = booking.getBookingListbystartDate(sDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(myBooking);
		for (Booking bookings : myBooking) {
			assertEquals(eDate,bookings.getEndDate());
			assertEquals("HII",bookings.getBookingID());
			assertEquals(myItem,bookings.getItem());
			assertEquals( BookingType.Reservation,bookings.getType());
			assertEquals(myUser,bookings.getUser());
			assertEquals(sDate,bookings.getStartDate());
		}
	}
	
	@Test
	public void testgetBookingListbyStartDateFail() {
		List<Booking> myBooking = null;
		String error = null;
		try {
			myBooking = booking.getBookingListbystartDate(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(myBooking);
		assertEquals("Please enter valid date yyyy-[m]m-[d]d", error);
	}
	
	

	@Test
	public void testgetBookingListbyEndDate()
	{
		List<Booking> myBooking = null;
		String error = null;
		try {
			myBooking = booking.getBookingListbyendDate(eDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(myBooking);
		for (Booking bookings : myBooking) {
			assertEquals(eDate,bookings.getEndDate());
			assertEquals("HII",bookings.getBookingID());
			assertEquals(myItem,bookings.getItem());
			assertEquals( BookingType.Reservation,bookings.getType());
			assertEquals(myUser,bookings.getUser());
			assertEquals(sDate,bookings.getStartDate());
		}
	}
	
	@Test
	public void testgetBookingListbyEndDateFail() {
		List<Booking> myBooking = null;
		String error = null;
		try {
			myBooking = booking.getBookingListbyendDate(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(myBooking);
		assertEquals("Please enter valid date yyyy-[m]m-[d]d", error);
	}
	
	@Test
	public void testgetBookingbyItem() {
		Booking myBooking =null;
		String error = null;
		
		try {
			myBooking = booking.getBookingbyItem(myItem);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		
		assertNotNull(myBooking);
		assertEquals(eDate,myBooking.getEndDate());
		assertEquals("HII",myBooking.getBookingID());
		assertEquals(myItem,myBooking.getItem());
		assertEquals( BookingType.Reservation,myBooking.getType());
		assertEquals(myUser,myBooking.getUser());
		assertEquals(sDate,myBooking.getStartDate());

	}
	
	@Test
	public void testgetBookingbyItemFail() {
		Booking myBooking =null;
		String error = null;
		
		try {
			myBooking = booking.getBookingbyItem(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		
		assertNull(myBooking);
		assertEquals("Please enter a valid item", error);

	}

	
	

	@Test
	public void testgetBookingListbyUser()
	{
		List<Booking> myBooking = null;
		String error = null;
		try {
			myBooking = booking.getBookingListbyUser(myUser);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(myBooking);
		for (Booking bookings : myBooking) {
			assertEquals(eDate,bookings.getEndDate());
			assertEquals("HII",bookings.getBookingID());
			assertEquals(myItem,bookings.getItem());
			assertEquals( BookingType.Reservation,bookings.getType());
			assertEquals(myUser,bookings.getUser());
			assertEquals(sDate,bookings.getStartDate());
		}
	}
	
	
	
	@Test
	public void testgetBookingListbyUserFail()
	{
		List<Booking> myBooking = null;
		String error = null;
		try {
			myBooking = booking.getBookingListbyUser(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(myBooking);
		assertEquals("Please enter valid user", error);
	}
	
	
	@Test
	public void testgetBookingListbyType()
	{
		List<Booking> myBooking = null;
		String error = null;
		try {
			myBooking = booking.getBookingListbyBookingType(BookingType.Reservation);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(myBooking);
		for (Booking bookings : myBooking) {
			assertEquals(eDate,bookings.getEndDate());
			assertEquals("HII",bookings.getBookingID());
			assertEquals(myItem,bookings.getItem());
			assertEquals( BookingType.Reservation,bookings.getType());
			assertEquals(myUser,bookings.getUser());
			assertEquals(sDate,bookings.getStartDate());
		}
	}
	@Test
	public void testgetBookingListbyTypeFail()
	{
		List<Booking> myBooking = null;
		String error = null;
		try {
			myBooking = booking.getBookingListbyBookingType(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(myBooking);
		assertEquals("Please enter a valid booking type", error);
	}
	
	
	@Test 
	public void testdeleteBookingbyID() {
		String error = null;
		try {
			booking.deleteBookingbyID("HII");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	
	}
	

	@Test 
	public void testdeleteBookingbyIDFAIL() {
		String error = null;
		try {
			booking.deleteBookingbyID(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(myBooking);
		assertEquals("Please enter valid Booking ID", error);
	
	}
	
	@Test 
	public void testdeleteBookingbyUser() {
		String error = null;
		try {
			booking.deleteBookingByUser(myUser);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	
	}
	
	
	@Test 
	public void testdeleteBookingbyUserFail() {
		String error = null;
		try {
			booking.deleteBookingByUser(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(myBooking);
		assertEquals("Please enter a valid user", error);
	
	
	}
	
	@Test 
	public void testupdateStartDateBookingbyItem() {
		String error = null;
		String str2 = "2011-03-31";
		 Date upDate = Date.valueOf(str2);
	
		try {
			booking.updateStartDateBookingbyItem(myItem,upDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Booking testbooking = booking.getBookingbyItem(myItem);
		assertNull(error);
		assertNotNull(testbooking);
		assertEquals(testbooking.getStartDate(),upDate);
		
		//All I'm doing is reset it to the true value so the other tests passes accordingly 
		booking.updateStartDateBookingbyItem(myItem,sDate);
	
	
	}
	
	@Test 
	public void testupdateStartDateBookingbyItemFailByDate() {
		String str2 = "2011-03-31";
		 Date upDate = Date.valueOf(str2);
			String error = null;

		try {
			booking.updateStartDateBookingbyItem(myItem,null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter valid date yyyy-[m]m-[d]d", error);

	
	}
	
	@Test 
	public void testupdateStartDateBookingbyItemFailByItem() {
		String str2 = "2011-03-31";
		 Date upDate = Date.valueOf(str2);
			String error = null;

		try {
			booking.updateStartDateBookingbyItem(null,upDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter a valid item", error);

	
	}
	

	@Test 
	public void testupdateEndDateBookingbyItem() {
		String error = null;
		String str2 = "2019-03-31";
		 Date upDate = Date.valueOf(str2);
	
		try {
			booking.updateEndDateBookingbyItem(myItem,upDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Booking testbooking = booking.getBookingbyItem(myItem);
		assertNull(error);
		assertNotNull(testbooking);
		assertEquals(testbooking.getEndDate(),upDate);
		
		//All I'm doing is reset it to the true value so the other tests passes accordingly 
		booking.updateEndDateBookingbyItem(myItem,eDate);
	
	
	}
	
	@Test 
	public void testupdateEndDateBookingbyItemFailByDate() {
		String str2 = "2019-03-31";
		 Date upDate = Date.valueOf(str2);
			String error = null;

		try {
			booking.updateEndDateBookingbyItem(myItem,null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter valid date yyyy-[m]m-[d]d", error);

	
	}
	
	@Test 
	public void testupdateEndDateBookingbyItemFailByItem() {
		String str2 = "2019-03-31";
		 Date upDate = Date.valueOf(str2);
			String error = null;

		try {
			booking.updateEndDateBookingbyItem(null,upDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter a valid item", error);

	
	}
	
	@Test 
	public void testupdateBookingTypeofBookingbyItem() {
		String error = null;
		BookingType type = Booking.BookingType.Borrow;
	
		try {
			booking.updateBookingTypeofBookingbyItem(myItem,type);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Booking testbooking = booking.getBookingbyItem(myItem);
		assertNull(error);
		assertNotNull(testbooking);
		assertEquals(testbooking.getType(),type);
		
		//All I'm doing is reset it to the true value so the other tests passes accordingly 
		booking.updateBookingTypeofBookingbyItem(myItem,Booking.BookingType.Reservation);
		
	}
	
	@Test 
	public void testupdateBookingTypeofBookingbyItemFailType() {
		String error = null;
		BookingType type = Booking.BookingType.Borrow;
	
		try {
			booking.updateBookingTypeofBookingbyItem(myItem,null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter a valid booking type", error);

		
	}
	
	@Test 
	public void testupdateBookingTypeofBookingbyItemFailItem() {
		String error = null;
		BookingType type = Booking.BookingType.Borrow;
	
		try {
			booking.updateBookingTypeofBookingbyItem(null,type);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter a valid item", error);

		
	}
	
	@Test 
	public void testupdateUserofBookingByItem() {
		String error = null;
		Address myAdress = new Address("511", "Parkx", "Montreal", "H7H6H7", "Saguenay", "Canada");
		Employee aUser = new Employee(true, true, "Abdel", "Madjid", true, 0, myAdress, Role.Librarian);
		try {
			booking.updateUserofBookingByItem(myItem,aUser);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Booking testbooking = booking.getBookingbyItem(myItem);
		assertNull(error);
		assertNotNull(testbooking);
		assertEquals(testbooking.getUser(),aUser);
		
		//All I'm doing is reset it to the true value so the other tests passes accordingly 
		booking.updateUserofBookingByItem(myItem,myUser);
		
	}
	
	@Test 
	public void testupdateUserofBookingByItemFailitem() {
		String error = null;
		Address myAdress = new Address("511", "Parkx", "Montreal", "H7H6H7", "Saguenay", "Canada");
		Employee aUser = new Employee(true, true, "Abdel", "Madjid", true, 0, myAdress, Role.Librarian);
		try {
			booking.updateUserofBookingByItem(null,aUser);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter a valid item", error);

		
	}
	
	@Test 
	public void testupdateUserofBookingByItemFailuser() {
		String error = null;
		Address myAdress = new Address("511", "Parkx", "Montreal", "H7H6H7", "Saguenay", "Canada");
		Employee aUser = new Employee(true, true, "Abdel", "Madjid", true, 0, myAdress, Role.Librarian);
		try {
			booking.updateUserofBookingByItem(myItem,null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter a valid user", error);

		
	}
	
	
	@Test 
	public void testupdateStartDateBookingbyID() {
		String error = null;
		String str2 = "2011-03-31";
		 Date upDate = Date.valueOf(str2);
		try {
			booking.updateStartDateBookingbyID("HII",upDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Booking testbooking = booking.getBookingbyItem(myItem);
		assertNull(error);
		assertNotNull(testbooking);
		assertEquals(testbooking.getStartDate(),upDate);
		
		//All I'm doing is reset it to the true value so the other tests passes accordingly 
		booking.updateStartDateBookingbyID("HII",sDate);
		
	}
	
	@Test 
	public void testupdateStartDateBookingbyIDFailID() {
		String error = null;
		String str2 = "2011-03-31";
		 Date upDate = Date.valueOf(str2);
		try {
			booking.updateStartDateBookingbyID(null,upDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter valid Booking ID", error);

		
	}
	@Test 
	public void testupdateStartDateBookingbyIDFailID2() {
		String error = null;
		String str2 = "2011-03-31";
		 Date upDate = Date.valueOf(str2);
		try {
			booking.updateStartDateBookingbyID("",upDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter valid Booking ID", error);

		
	}
	
	@Test 
	public void testupdateStartDateBookingbyIDFailDate() {
		String error = null;
		String str2 = "2011-03-31";
		 Date upDate = Date.valueOf(str2);
		try {
			booking.updateStartDateBookingbyID("HII",null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter valid date yyyy-[m]m-[d]d", error);

		
	}
	
	@Test 
	public void testupdateEndDateBookingbyID() {
		String error = null;
		String str2 = "2019-03-31";
		 Date upDate = Date.valueOf(str2);
		try {
			booking.updateEndDateBookingbyID("HII",upDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Booking testbooking = booking.getBookingbyItem(myItem);
		assertNull(error);
		assertNotNull(testbooking);
		assertEquals(testbooking.getStartDate(),upDate);
		
		//All I'm doing is reset it to the true value so the other tests passes accordingly 
		booking.updateEndDateBookingbyID("HII",eDate);
		
	}
	
	@Test 
	public void testupdateEndDateBookingbyIDFailID() {
		String error = null;
		String str2 = "2011-03-31";
		 Date upDate = Date.valueOf(str2);
		try {
			booking.updateEndDateBookingbyID(null,upDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter valid Booking ID", error);
		
	}
	@Test 
	public void testupdateEndDateBookingbyIDFailID2() {
		String error = null;
		String str2 = "2011-03-31";
		 Date upDate = Date.valueOf(str2);
		try {
			booking.updateEndDateBookingbyID("",upDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter valid Booking ID", error);
		
	}
	
	@Test 
	public void testupdateEndDateBookingbyIDFailDate() {
		String error = null;
	
		try {
			booking.updateEndDateBookingbyID("HII",null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter valid date yyyy-[m]m-[d]d", error);
		
	}
	
	@Test 
	public void testupdateBookingTypeofBookingbyID() {
		String error = null;
		BookingType type = Booking.BookingType.Borrow;
	
		try {
			booking.updateBookingTypeofBookingbyID("HII",type);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Booking testbooking = booking.getBookingbyId("HII");
		assertNull(error);
		assertNotNull(testbooking);
		assertEquals(testbooking.getType(),type);
		
		//All I'm doing is reset it to the true value so the other tests passes accordingly 
		booking.updateBookingTypeofBookingbyID("HII",Booking.BookingType.Reservation);
		
	}
	
	@Test 
	public void testupdateBookingTypeofBookingbyIDFailType() {
		String error = null;
		BookingType type = Booking.BookingType.Borrow;
	
		try {
			booking.updateBookingTypeofBookingbyID("HII",null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter a valid booking type", error);

		
	}
	
	@Test 
	public void testupdateBookingTypeofBookingbyIDFailID() {
		String error = null;
		BookingType type = Booking.BookingType.Borrow;
	
		try {
			booking.updateBookingTypeofBookingbyID(null,type);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter valid Booking ID", error);

		
	}
	@Test 
	public void testupdateBookingTypeofBookingbyIDFailID2() {
		String error = null;
		BookingType type = Booking.BookingType.Borrow;
	
		try {
			booking.updateBookingTypeofBookingbyID("",type);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter valid Booking ID", error);

		
	}
	
	@Test 
	public void testupdateUserofBookingByID() {
		String error = null;
		Address myAdress = new Address("511", "Parkx", "Montreal", "H7H6H7", "Saguenay", "Canada");
		Employee aUser = new Employee(true, true, "Abdel", "Madjid", true, 0, myAdress, Role.Librarian);
		try {
			booking.updateUserofBookingByID("HII",aUser);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Booking testbooking = booking.getBookingbyItem(myItem);
		assertNull(error);
		assertNotNull(testbooking);
		assertEquals(testbooking.getUser(),aUser);
		
		//All I'm doing is reset it to the true value so the other tests passes accordingly 
		booking.updateUserofBookingByID("HII",myUser);
		
	}
	
	@Test 
	public void testupdateUserofBookingByIDFailid() {
		String error = null;
		Address myAdress = new Address("511", "Parkx", "Montreal", "H7H6H7", "Saguenay", "Canada");
		Employee aUser = new Employee(true, true, "Abdel", "Madjid", true, 0, myAdress, Role.Librarian);
		try {
			booking.updateUserofBookingByID(null,aUser);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter valid Booking ID", error);

		
	}
	
	@Test 
	public void testupdateUserofBookingByIDFailuser() {
		String error = null;
		Address myAdress = new Address("511", "Parkx", "Montreal", "H7H6H7", "Saguenay", "Canada");
		Employee aUser = new Employee(true, true, "Abdel", "Madjid", true, 0, myAdress, Role.Librarian);
		try {
			booking.updateUserofBookingByID("HII",null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Please enter a valid user", error);

		
	}
	
	@Test
	public void testreturnItembyItem() {
		String error = null;
		try {
			booking.returnItemByItem(myItem);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(myItem.getStatus(), Item.Status.Available);
			}
	
	@Test
	public void testreturnItembyItemFailITEM() {
		String error = null;
		try {
			booking.returnItemByItem(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Please enter a valid Item", error);
		
	}
	

}
