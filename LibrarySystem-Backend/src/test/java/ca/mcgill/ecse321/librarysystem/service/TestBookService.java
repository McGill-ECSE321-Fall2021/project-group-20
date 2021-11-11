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

import ca.mcgill.ecse321.librarysystem.dao.BookRepository;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.Book;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.model.User;
import ca.mcgill.ecse321.librarysystem.model.Booking.BookingType;

@ExtendWith(MockitoExtension.class)
public class TestBookService {

	@Mock
	private BookRepository itemDao;

	@InjectMocks
	private BookService service;

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
	private static final String length = "15";
	private static final String pages = "80";
	private Book myItem = new Book(status, itemId, myTitle,length,pages);
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
		assertEquals(0, service.getBooks().size());
		Long theId = (long) (1234);
		Status stat = Status.Damaged;
		Title zeTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		String lengthy = "15";
		String pages = "100"; 
		Book item = null;
		try {
			item = service.createBook(stat, theId, zeTitle,lengthy,pages);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(item);
		assertEquals(theId, item.getItemBarcode());
		assertEquals(theId, item.getItemBarcode());
		assertEquals(stat,item.getStatus());
		assertEquals(zeTitle,item.getTitle());
		assertEquals(lengthy,item.getIsbn());

	}

	@Test
	public void testCreateItemNull() {
		String lengthy = "0"; 
		long theId = 01;
		Title zeTitle = null;
		Book item = null;
		String error = null;
		String pages = "0"; 
		try {
			item = service.createBook(status, theId, zeTitle,lengthy,pages);
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
		Book item = null;
		String lengthy = "15";
		String pages = "80"; 
		try {
			item = service.getBookById(theId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertNotNull(item);
		assertEquals(item.getItemBarcode(), theId);
		assertEquals(item.getBooking(), myBooking);
		assertEquals(item.getStatus(), status);
		assertEquals(item.getTitle(), myTitle);
		assertEquals(lengthy,item.getIsbn());
		assertEquals(pages,item.getNumPages());


	}

	@Test
	public void TestgetItemsByStat() {
		long theId = 12;
		String error = null;
		List<Book> item = null;
		String pages = "80"; 
		Status statu = Status.Available;
		String lengthy = "15"; 
		try {
			item = service.getBookByStat(statu);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(item);
		assertNull(error);
		for (Book itemus : item) {
			assertEquals(itemus.getItemBarcode(), theId);
			assertEquals(itemus.getBooking(), myBooking);
			assertEquals(itemus.getStatus(), status);
			assertEquals(itemus.getTitle(), myTitle);
			assertEquals(lengthy,itemus.getIsbn());
			assertEquals(pages,itemus.getNumPages());
		}
	}
	
	
	@Test
	public void TestgetItemsByTitle() {
		String lengthy = "15"; 
		long theId= 12;
		String error=null;
		String pages = "80"; 
		List<Book> item=null;
		try {
			item= service.getBookByTitle(myTitle);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		for(Book itemus : item) {
			assertEquals (itemus.getItemBarcode(),theId);
			assertEquals (itemus.getBooking(),myBooking);
			assertEquals (itemus.getStatus(),status);
			assertEquals(lengthy,itemus.getIsbn());
			assertEquals(pages,itemus.getNumPages());
		}
		assertNull(error);

	}
	
	
	@Test
	public void TestgetExistance() {
		long theId= 12;
		String error=null;
		boolean item=false;
		try {
			item=service.getexistanceByBookBarcode(theId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
		assertTrue(item);
	}
	
	

	
	
	@Test
	public void TestgetItemBybooking() {
		String lengthy = "15"; 
		String pages = "80"; 
		long theId = 12;
		String error = null;
		Book item = null;
		try {
			item = service.getBookByBookBooking(myBooking);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(item);
		assertEquals(item.getItemBarcode(), theId);
		assertEquals(item.getBooking(), myBooking);
		assertEquals(item.getStatus(), status);
		assertEquals(item.getTitle(), myTitle);
		assertNull(error);
		assertEquals(lengthy,item.getIsbn());
		assertEquals(pages,item.getNumPages());
	}

	@Test
	public void TestDeleatbyId() {
		long theId = 12;
		String error = null;
		try {
			service.deleatBookById(theId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	}
	
	@Test
	public void TestdeleatItemByStat() {
		String error = null;
		try {
			service.deleatBookByStat(status);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	}
	
	@Test
	public void TestdeleatItemByTitle() {
		String error = null;
		try {
			service.deleatBookByTitle(myTitle);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	}
	
	@Test
	public void TestdeleatItemByBooking() {
		String error = null;
		try {
			service.deleatBookByBookBooking(myBooking);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	}
	

	@Test
	public void TestupdateItemTitle() {
		String lengthy = "15"; 
		String pages = "80"; 
		String error = null;
		long barcode = 12;
		Author herAuthor = new Author("J.K.", "Bowling");
		Title titlu = new Title("LifeOfABeggar", "September 29th, 2021", herAuthor);
		
		try { 
			service.updateBook(barcode, titlu);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Book itemu= service.getBookById(barcode);
		assertEquals(itemu.getTitle(),titlu);
		assertEquals(itemu.getTitle().getAuthor().get(0),herAuthor);
		assertEquals(lengthy,itemu.getIsbn());
		assertEquals(pages,itemu.getNumPages());
		assertNull (error);
	}
	
	@Test
	public void TestUpdateItemStatus() {
		String lengthy = "15"; 
		String pages = "80"; 
		String error = null;
		Status herstat=Status.Borrowed;
		long barcode = 12;		
		try { 
			service.updateBook(herstat, barcode);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Book itemu= service.getBookById(barcode);
		assertEquals(itemu.getStatus(),herstat);
		assertEquals(itemu.getTitle(),myTitle);
		assertEquals(itemu.getTitle().getAuthor().get(0),myAuthor);
		assertEquals(lengthy,itemu.getIsbn());
		assertEquals(pages,itemu.getNumPages());
		assertNull (error);
	}
	@Test
	public void TestUpdateItemIsbn() {
		String lengthy = "150"; 
		String pages = "80"; 
		String error = null;
		Status herstat=Status.Available;
		long barcode = 12;		
		try { 
			service.updateBookIsbn(lengthy, barcode);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Book itemu= service.getBookById(barcode);
		assertEquals(itemu.getStatus(),herstat);
		assertEquals(itemu.getTitle(),myTitle);
		assertEquals(itemu.getTitle().getAuthor().get(0),myAuthor);
		assertEquals(lengthy,itemu.getIsbn());
		assertEquals(pages,itemu.getNumPages());
		assertNull (error);
	}
	@Test
	public void TestUpdateItemPage() {
		String lengthy = "15"; 
		String pages = "6"; 
		String error = null;
		Status herstat=Status.Available;
		long barcode = 12;		
		try { 
			service.updateBookPages(pages, barcode);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		Book itemu= service.getBookById(barcode);
		assertEquals(itemu.getStatus(),herstat);
		assertEquals(itemu.getTitle(),myTitle);
		assertEquals(itemu.getTitle().getAuthor().get(0),myAuthor);
		assertEquals(lengthy,itemu.getIsbn());
		assertEquals(pages,itemu.getNumPages());
		assertNull (error);
	}

}
