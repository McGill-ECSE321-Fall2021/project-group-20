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

import ca.mcgill.ecse321.librarysystem.dao.NewspaperRepository;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.Newspaper;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.model.User;
import ca.mcgill.ecse321.librarysystem.model.Booking.BookingType;

@ExtendWith(MockitoExtension.class)
public class TestNewspaper {

	@Mock
	private NewspaperRepository itemDao;

	@InjectMocks
	private NewspaperService service;

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
	private Newspaper myItem = new Newspaper(status, itemId, myTitle);
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
		assertEquals(0, service.getAllNewspapers().size());
		Long theId = (long) (1234);
		Status stat = Status.Damaged;
		Title zeTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		Newspaper item = null;
		try {
			item = service.createNewspaper(stat, theId, zeTitle);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(item);
		assertEquals(theId, item.getItemBarcode());
		assertEquals(stat,item.getStatus());
		assertEquals(zeTitle,item.getTitle());

	}

	@Test
	public void testCreateItemNull() {
		long theId = 01;
		Status stat = null;
		Title zeTitle = null;
		Newspaper item = null;
		String error = null;
		try {
			item = service.createNewspaper(stat, theId, zeTitle);
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
		Newspaper item = null;
		try {
			item = service.getNewspaperById(theId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(item);
		assertEquals(item.getItemBarcode(), theId);
		assertEquals(item.getBooking(), myBooking);
		assertEquals(item.getStatus(), status);
		assertEquals(item.getTitle(), myTitle);
	}

	@Test
	public void TestgetItemsByStat() {
		long theId = 12;
		String error = null;
		List<Newspaper> item = null;
		Status statu = Status.Available;
		try {
			item = service.getNewspaperByStat(statu);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(item);
		assertNull(error);
		for (Item itemus : item) {
			assertEquals(itemus.getItemBarcode(), theId);
			assertEquals(itemus.getBooking(), myBooking);
			assertEquals(itemus.getStatus(), status);
			assertEquals(itemus.getTitle(), myTitle);
		}
	}
	
	
	@Test
	public void TestgetItemsByTitle() {
		long theId= 12;
		String error=null;
		List<Newspaper> item=null;
		try {
			item= service.getNewspaperByTitle(myTitle);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		for(Item itemus : item) {
			assertEquals (itemus.getItemBarcode(),theId);
			assertEquals (itemus.getBooking(),myBooking);
			assertEquals (itemus.getStatus(),status);
			assertEquals (itemus.getTitle(),myTitle);
		}
		assertNull(error);

	}
	
	
	@Test
	public void TestgetExistance() {
		long theId= 12;
		String error=null;
		boolean item=false;
		try {
			item=service.getexistanceByNewspaperBarcode(theId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertTrue(item);
	}
	
	

	
	
	@Test
	public void TestgetItemBybooking() {
		long theId = 12;
		String error = null;
		Newspaper item = null;
		try {
			item = service.getNewspaperByNewspaperBooking(myBooking);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(item);
		assertEquals(item.getItemBarcode(), theId);
		assertEquals(item.getBooking(), myBooking);
		assertEquals(item.getStatus(), status);
		assertEquals(item.getTitle(), myTitle);
		assertNull(error);
	}

	@Test
	public void TestDeleatbyId() {
		long theId = 12;
		String error = null;
		try {
			service.deleatNewspaperById(theId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	}
	
	@Test
	public void TestdeleatItemByStat() {
		String error = null;
		try {
			service.deleatNewspaperByStat(status);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	}
	
	@Test
	public void TestdeleatItemByTitle() {
		String error = null;
		try {
			service.deleatNewspaperByTitle(myTitle);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	}
	
	@Test
	public void TestdeleatItemByBooking() {
		String error = null;
		try {
			service.deleatNewspaperByNewspaperBooking(myBooking);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	}
	
	@Test
	public void TestupdateItem() {
		String error = null;
		Status herstat=Status.Borrowed;
		long barcode = 12;
		Author herAuthor = new Author("J.K.", "Bowling");
		Title titlu = new Title("LifeOfABeggar", "September 29th, 2021", herAuthor);
		
		try { 
			service.updateNewspaper(herstat, barcode, titlu);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Newspaper itemu= service.getNewspaperById(barcode);
		assertEquals(itemu.getStatus(),herstat);
		assertEquals(itemu.getTitle(),titlu);
		assertEquals(itemu.getTitle().getAuthor().get(0),herAuthor);
		assertNull (error);
	}
	@Test
	public void TestupdateItemTitle() {
		String error = null;
		long barcode = 12;
		Author herAuthor = new Author("J.K.", "Bowling");
		Title titlu = new Title("LifeOfABeggar", "September 29th, 2021", herAuthor);
		
		try { 
			service.updateNewspaper(barcode, titlu);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Newspaper itemu= service.getNewspaperById(barcode);
		assertEquals(itemu.getTitle(),titlu);
		assertEquals(itemu.getTitle().getAuthor().get(0),herAuthor);
		assertNull (error);
	}
	
	@Test
	public void TestUpdateItemStatus() {
		String error = null;
		Status herstat=Status.Borrowed;
		long barcode = 12;		
		try { 
			service.updateNewspaper(herstat, barcode);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Newspaper itemu= service.getNewspaperById(barcode);
		assertEquals(itemu.getStatus(),herstat);
		assertEquals(itemu.getTitle(),myTitle);
		assertEquals(itemu.getTitle().getAuthor().get(0),myAuthor);
		assertNull (error);
	}
	
	

}
