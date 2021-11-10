package ca.mcgill.ecse321.librarysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.mockito.Mockito.lenient;

import java.sql.Date;
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

import ca.mcgill.ecse321.librarysystem.dao.MovieRepository;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.Movie;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.model.User;
import ca.mcgill.ecse321.librarysystem.model.Booking.BookingType;

@ExtendWith(MockitoExtension.class)
public class TestMovie {

	@Mock
	private MovieRepository itemDao;

	@InjectMocks
	private MovieService service;

	private static final Long itemId = (long) 12;
	private Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
	private static final Author myAuthor = new Author("J.K.", "Rowling");
	private static final Title myTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
	private static final Status status = Status.Available;
	private User myUser = new User(true, true, "Harsh", "Patel", true, 0, myAdress);
	private static final String str1 = "2015-03-31";
	private static final String str2 = "2015-04-05";
	private static final Date sDate = Date.valueOf(str1);
	private static final Date eDate = Date.valueOf(str2);
	private static final int length = 15; 
	private Movie myItem = new Movie(status, itemId, myTitle,length);
	private Booking myBooking = new Booking(sDate, eDate, BookingType.Reservation, myItem, myUser);

	@BeforeEach
	public void setMockOutput() {
		myItem.setBooking(myBooking);
		myBooking.setUser(myUser);
		lenient().when(itemDao.findItemByItemBarcode(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(itemId)) {
				return myItem;
			}
			return null;
		});

		lenient().when(itemDao.findItemByBooking(any(Booking.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(myBooking)) {
				return myItem;
			}
			return null;
		});

		lenient().when(itemDao.findItemByStatus(any(Status.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(Status.Available)) {
				List<Item> myList = new ArrayList<Item>();
				myList.add(myItem);
				return myList;
			}
			return null;
		});

		lenient().when(itemDao.findItemByTitle(any(Title.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(myTitle)) {
				List<Item> theList = new ArrayList<Item>();
				theList.add(myItem);
				return theList;
			}
			return null;
		});
		
		lenient().when(itemDao.existsByItemBarcode(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
			boolean isIt=false;
			if (invocation.getArgument(0).equals(itemId)) {
				isIt=true;
				return isIt;
			}
				return isIt;
		});
		
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(itemDao.save(any(Item.class))).thenAnswer(returnParameterAsAnswer);

	}

	@Test
	public void testCreateItem() {
		assertEquals(0, service.getAllMovies().size());
		Long theId = (long) (1234);
		Status stat = Status.Damaged;
		Title zeTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		int lengthy = 15; 
		Movie item = null;
		try {
			item = service.createItem(stat, theId, zeTitle,lengthy);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(item);
		assertEquals(theId, item.getItemBarcode());
		assertEquals(theId, item.getItemBarcode());
		assertEquals(stat,item.getStatus());
		assertEquals(zeTitle,item.getTitle());
		assertEquals(lengthy,item.getLength());

	}

	@Test
	public void testCreateItemNull() {
		int lengthy = 0; 
		long theId = 01;
		Title zeTitle = null;
		Movie item = null;
		String error = null;
		try {
			item = service.createItem(status, theId, zeTitle,lengthy);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(item);
		assertEquals("Please enter a valid status, title or Id", error);

	}

	@Test
	public void TestgetItemById() {
		long theId = 12;
		String error = null;
		Movie item = null;
		int lengthy = 15;
		try {
			item = service.getItemById(theId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(item);
		assertEquals(item.getItemBarcode(), theId);
		assertEquals(item.getBooking(), myBooking);
		assertEquals(item.getStatus(), status);
		assertEquals(item.getTitle(), myTitle);
		assertEquals(lengthy,item.getLength());

	}

	@Test
	public void TestgetItemsByStat() {
		long theId = 12;
		String error = null;
		List<Movie> item = null;
		Status statu = Status.Available;
		int lengthy = 15; 
		try {
			item = service.getItemByStat(statu);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(item);
		assertNull(error);
		for (Movie itemus : item) {
			assertEquals(itemus.getItemBarcode(), theId);
			assertEquals(itemus.getBooking(), myBooking);
			assertEquals(itemus.getStatus(), status);
			assertEquals(itemus.getTitle(), myTitle);
			assertEquals(lengthy,itemus.getLength());
		}
	}
	
	
	@Test
	public void TestgetItemsByTitle() {
		int lengthy = 15; 
		long theId= 12;
		String error=null;
		List<Movie> item=null;
		try {
			item= service.getItemByTitle(myTitle);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		for(Movie itemus : item) {
			assertEquals (itemus.getItemBarcode(),theId);
			assertEquals (itemus.getBooking(),myBooking);
			assertEquals (itemus.getStatus(),status);
			assertEquals (itemus.getTitle(),myTitle);
			assertEquals(lengthy,itemus.getLength());
		}
		assertNull(error);

	}
	
	
	@Test
	public void TestgetExistance() {
		long theId= 12;
		String error=null;
		boolean item=false;
		try {
			item=service.getexistanceByItemBarcode(theId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertTrue(item);
	}
	
	

	
	
	@Test
	public void TestgetItemBybooking() {
		int lengthy = 15; 
		long theId = 12;
		String error = null;
		Movie item = null;
		try {
			item = service.getItemByItemBooking(myBooking);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(item);
		assertEquals(item.getItemBarcode(), theId);
		assertEquals(item.getBooking(), myBooking);
		assertEquals(item.getStatus(), status);
		assertEquals(item.getTitle(), myTitle);
		assertNull(error);
		assertEquals(lengthy,item.getLength());
	}

	@Test
	public void TestDeleatbyId() {
		long theId = 12;
		String error = null;
		try {
			service.deleatItemById(theId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	}
	
	@Test
	public void TestdeleatItemByStat() {
		String error = null;
		try {
			service.deleatItemByStat(status);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	}
	
	@Test
	public void TestdeleatItemByTitle() {
		String error = null;
		try {
			service.deleatItemByTitle(myTitle);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	}
	
	@Test
	public void TestdeleatItemByBooking() {
		String error = null;
		try {
			service.deleatItemByItemBooking(myBooking);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	}
	
	@Test
	public void TestupdateItem() {
		int lengthy = 15; 
		String error = null;
		Status herstat=Status.Borrowed;
		long barcode = 12;
		Author herAuthor = new Author("J.K.", "Bowling");
		Title titlu = new Title("LifeOfABeggar", "September 29th, 2021", herAuthor);
		
		try { 
			service.updateItem(herstat, barcode, titlu);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Movie itemu= service.getItemById(barcode);
		assertEquals(itemu.getStatus(),herstat);
		assertEquals(itemu.getTitle(),titlu);
		assertEquals(itemu.getTitle().getAuthor().get(0),herAuthor);
		assertNull (error);
		assertEquals(lengthy,itemu.getLength());
	}
	@Test
	public void TestupdateItemTitle() {
		int lengthy = 15; 
		String error = null;
		long barcode = 12;
		Author herAuthor = new Author("J.K.", "Bowling");
		Title titlu = new Title("LifeOfABeggar", "September 29th, 2021", herAuthor);
		
		try { 
			service.updateItem(barcode, titlu);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Movie itemu= service.getItemById(barcode);
		assertEquals(itemu.getTitle(),titlu);
		assertEquals(itemu.getTitle().getAuthor().get(0),herAuthor);
		assertEquals(lengthy,itemu.getLength());
		assertNull (error);
	}
	
	@Test
	public void TestUpdateItemStatus() {
		int lengthy = 15; 
		String error = null;
		Status herstat=Status.Borrowed;
		long barcode = 12;		
		try { 
			service.updateItem(herstat, barcode);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Movie itemu= service.getItemById(barcode);
		assertEquals(itemu.getStatus(),herstat);
		assertEquals(itemu.getTitle(),myTitle);
		assertEquals(itemu.getTitle().getAuthor().get(0),myAuthor);
		assertEquals(lengthy,itemu.getLength());
		assertNull (error);
	}
	
	

}
