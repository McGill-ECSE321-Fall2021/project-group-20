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
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.User;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;
import ca.mcgill.ecse321.librarysystem.model.Title;

@ExtendWith(SpringExtension.class)
@SpringBootTest 
public class TestBookingRepositoryPersistence {

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
//		
//		bookingRepository.deleteAll();
//        itemRepository.deleteAll();
//        titleRepository.deleteAll();
//        authorRepository.deleteAll();
//        userRepository.deleteAll();
//        librarySystemRepository.deleteAll();
//        addressRepository.deleteAll();
//        calendarRepository.deleteAll();
        
        
        
        
		
		bookingRepository.deleteAll();
        itemRepository.deleteAll();
        titleRepository.deleteAll();
        authorRepository.deleteAll();
        userRepository.deleteAll();
        addressRepository.deleteAll();
        librarySystemRepository.deleteAll();
        calendarRepository.deleteAll();
	}
	

	@Test
	public void testPersistenceAndLoadBookingBybookingID() {
		Address myadress = new Address("51","Parkekx","Montreal","H5H6H7","Quebec","Canada");
		addressRepository.save(myadress);
		Calendar mycalendar = new Calendar();
		calendarRepository.save(mycalendar);
		LibrarySystem myLibrary = new LibrarySystem(myadress, mycalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("J.K.", "Rowling");
		authorRepository.save(myAuthor);
		Title mytitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		titleRepository.save(mytitle);
		Item myItem = new Item(Status.Available, myLibrary, mytitle);
		itemRepository.save(myItem);
		User myUser = new User(true,"Harsh","Patel",true,0,myadress,myLibrary);
		userRepository.save(myUser);
		//public Booking(Date aStartDate, Date aEndDate, BookingType aType, Item aItembooked, User aUser)
		String str1 = "2015-03-31";
		String str2 ="2015-04-05";
		Date sdate= Date.valueOf(str1);
		Date edate = Date.valueOf(str2);
		Booking myBooking = new Booking (sdate,edate,BookingType.Reservation,myItem,myUser);
		bookingRepository.save(myBooking);
		
		myadress = null;
		mycalendar= null;
		myLibrary=null;
		myAuthor=null;
		mytitle=null;
		myItem=null;
		myUser=null;
		
		Booking FindBookingByBookingID = bookingRepository.findBookingByBookingID(myBooking.getBookingID());
		assertEquals(myBooking.getStartDate(),FindBookingByBookingID.getStartDate());
		assertEquals(myBooking.getEndDate(),FindBookingByBookingID.getEndDate());
		assertEquals(myBooking.getBookingID(),FindBookingByBookingID.getBookingID());
		assertEquals(myBooking.getType(),FindBookingByBookingID.getType());
		
	}
	
	@Test 
	public void testPersistenceAndLoadBookingByStartDate() {
		Address myadress = new Address("51","Parkekx","Montreal","H5H6H7","Quebec","Canada");
		addressRepository.save(myadress);
		Calendar mycalendar = new Calendar();
		calendarRepository.save(mycalendar);
		LibrarySystem myLibrary = new LibrarySystem(myadress, mycalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("J.K.", "Rowling");
		Author myAuthor1 = new Author("J.K.", "Rowling22");

		authorRepository.save(myAuthor);
		authorRepository.save(myAuthor1);

		Title mytitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		Title mytitle1 = new Title("Kakaoko", "October 30th, 2021", myAuthor1);

		titleRepository.save(mytitle);
		titleRepository.save(mytitle1);

		Item myItem = new Item(Status.Available, myLibrary, mytitle);
		Item myItem1 = new Item(Status.Available, myLibrary, mytitle1);

		itemRepository.save(myItem);
		itemRepository.save(myItem1);

		User myUser = new User(true,"Harsh","Patel",true,0,myadress,myLibrary);
		User myUser1 = new User(true,"Hershey","Patel",true,0,myadress,myLibrary);

		userRepository.save(myUser);
		userRepository.save(myUser1);

		//public Booking(Date aStartDate, Date aEndDate, BookingType aType, Item aItembooked, User aUser)
		String str1 = "2015-03-31";
		String str2 ="2015-04-05";
		Date sdate= Date.valueOf(str1);
		Date edate = Date.valueOf(str2);
		Booking myBooking = new Booking (sdate,edate,BookingType.Reservation,myItem,myUser);
		Booking myBooking1 = new Booking (sdate,edate,BookingType.Reservation,myItem1,myUser1);

		bookingRepository.save(myBooking);
		bookingRepository.save(myBooking1);

		
		myadress = null;
		mycalendar= null;
		myLibrary=null;
		myAuthor=null;
		mytitle=null;
		myItem=null;
		myUser=null;

		List<Booking> FindBookingByStartDate = bookingRepository.findBookingByStartDate(sdate);
		for (Booking PersitanceBook : FindBookingByStartDate){

			assertTrue(PersitanceBook.getStartDate().equals(myBooking.getStartDate()) || PersitanceBook.getStartDate().equals(myBooking1.getStartDate()));
			assertTrue(PersitanceBook.getEndDate().equals(myBooking.getEndDate()) || PersitanceBook.getEndDate().equals(myBooking1.getEndDate()));
			assertTrue(PersitanceBook.getBookingID().equals(myBooking.getBookingID()) ||PersitanceBook.getBookingID().equals(myBooking1.getBookingID()) );
			assertTrue(PersitanceBook.getType().equals(myBooking.getType()) ||PersitanceBook.getType().equals(myBooking1.getType()) );
		}
	}
	
	
	
	
	
	
	
	@Test 
	public void testPersistenceAndLoadBookingByEndDate() {
		Address myadress = new Address("51","Parkekx","Montreal","H5H6H7","Quebec","Canada");
		addressRepository.save(myadress);
		Calendar mycalendar = new Calendar();
		calendarRepository.save(mycalendar);
		LibrarySystem myLibrary = new LibrarySystem(myadress, mycalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("J.K.", "Rowling");
		Author myAuthor1 = new Author("J.K.", "Rowling22");

		authorRepository.save(myAuthor);
		authorRepository.save(myAuthor1);

		Title mytitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		Title mytitle1 = new Title("Kakaoko", "October 30th, 2021", myAuthor1);

		titleRepository.save(mytitle);
		titleRepository.save(mytitle1);

		Item myItem = new Item(Status.Available, myLibrary, mytitle);
		Item myItem1 = new Item(Status.Available, myLibrary, mytitle1);

		itemRepository.save(myItem);
		itemRepository.save(myItem1);

		User myUser = new User(true,"Harsh","Patel",true,0,myadress,myLibrary);
		User myUser1 = new User(true,"Hershey","Patel",true,0,myadress,myLibrary);

		userRepository.save(myUser);
		userRepository.save(myUser1);

		//public Booking(Date aStartDate, Date aEndDate, BookingType aType, Item aItembooked, User aUser)
		String str1 = "2015-03-31";
		String str2 ="2015-04-05";
		Date sdate= Date.valueOf(str1);
		Date edate = Date.valueOf(str2);
		Booking myBooking = new Booking (sdate,edate,BookingType.Reservation,myItem,myUser);
		Booking myBooking1 = new Booking (sdate,edate,BookingType.Reservation,myItem1,myUser1);

		bookingRepository.save(myBooking);
		bookingRepository.save(myBooking1);

		
		myadress = null;
		mycalendar= null;
		myLibrary=null;
		myAuthor=null;
		mytitle=null;
		myItem=null;
		myUser=null;
		
		List<Booking> FindBookingByEndDate = bookingRepository.findBookingByEndDate(edate);
		for (Booking PersitanceBook : FindBookingByEndDate){

			assertTrue(PersitanceBook.getStartDate().equals(myBooking.getStartDate()) || PersitanceBook.getStartDate().equals(myBooking1.getStartDate()));
			assertTrue(PersitanceBook.getEndDate().equals(myBooking.getEndDate()) || PersitanceBook.getEndDate().equals(myBooking1.getEndDate()));
			assertTrue(PersitanceBook.getBookingID().equals(myBooking.getBookingID()) ||PersitanceBook.getBookingID().equals(myBooking1.getBookingID()) );
			assertTrue(PersitanceBook.getType().equals(myBooking.getType()) ||PersitanceBook.getType().equals(myBooking1.getType()) );
		}
	}
	
	
	@Test
	public void testPersistenceAndLoadBookingByItembooked() {
		Address myadress = new Address("51","Parkekx","Montreal","H5H6H7","Quebec","Canada");
		addressRepository.save(myadress);
		Calendar mycalendar = new Calendar();
		calendarRepository.save(mycalendar);
		LibrarySystem myLibrary = new LibrarySystem(myadress, mycalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("J.K.", "Rowling");
		authorRepository.save(myAuthor);
		Title mytitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		titleRepository.save(mytitle);
		Item myItem = new Item(Status.Available, myLibrary, mytitle);
		itemRepository.save(myItem);
		User myUser = new User(true,"Harsh","Patel",true,0,myadress,myLibrary);
		userRepository.save(myUser);
		//public Booking(Date aStartDate, Date aEndDate, BookingType aType, Item aItembooked, User aUser)
		String str1 = "2015-03-31";
		String str2 ="2015-04-05";
		Date sdate= Date.valueOf(str1);
		Date edate = Date.valueOf(str2);
		Booking myBooking = new Booking (sdate,edate,BookingType.Reservation,myItem,myUser);
		bookingRepository.save(myBooking);
		myBooking.setItem(myItem);
		myItem.setBooking(myBooking);
		itemRepository.save(myItem);
		bookingRepository.save(myBooking);
		
//		myadress = null;
//		mycalendar= null;
//		myLibrary=null;
//		myAuthor=null;
//		mytitle=null;
//		myUser=null;
		
		Item i = itemRepository.findItemByItemBarcode(myItem.getItemBarcode());	
		Booking b = bookingRepository.findBookingByItem(i);
		assertEquals(myBooking.getItem().getTitle().getName(),b.getItem().getTitle().getName());
		assertEquals(myBooking.getItem().getItemBarcode(),b.getItem().getItemBarcode());
		assertEquals(myBooking.getItem().getLibrarySystem().getSystemID(),b.getItem().getLibrarySystem().getSystemID());
		assertEquals(myBooking.getItem().getStatus(),b.getItem().getStatus());
	}
	
	
	
	
	@Test 
	public void testPersistenceAndLoadBookingByUser() {
		Address myadress = new Address("51","Parkekx","Montreal","H5H6H7","Quebec","Canada");
		addressRepository.save(myadress);
		Calendar mycalendar = new Calendar();
		calendarRepository.save(mycalendar);
		LibrarySystem myLibrary = new LibrarySystem(myadress, mycalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("J.K.", "Rowling");
		Author myAuthor1 = new Author("J.K.", "Rowling22");

		authorRepository.save(myAuthor);
		authorRepository.save(myAuthor1);

		Title mytitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		Title mytitle1 = new Title("Kakaoko", "October 30th, 2021", myAuthor1);

		titleRepository.save(mytitle);
		titleRepository.save(mytitle1);

		Item myItem = new Item(Status.Available, myLibrary, mytitle);
		Item myItem1 = new Item(Status.Available, myLibrary, mytitle1);

		itemRepository.save(myItem);
		itemRepository.save(myItem1);

		User myUser = new User(true,"Harsh","Patel",true,0,myadress,myLibrary);

		userRepository.save(myUser);

		//public Booking(Date aStartDate, Date aEndDate, BookingType aType, Item aItembooked, User aUser)
		String str1 = "2015-03-31";
		String str2 ="2015-04-05";
		Date sdate= Date.valueOf(str1);
		Date edate = Date.valueOf(str2);
		Booking myBooking = new Booking (sdate,edate,BookingType.Reservation,myItem,myUser);
		Booking myBooking1 = new Booking (sdate,edate,BookingType.Reservation,myItem1,myUser);

		bookingRepository.save(myBooking);
		bookingRepository.save(myBooking1);

		
		myadress = null;
		mycalendar= null;
		myLibrary=null;
		myAuthor=null;
		mytitle=null;
		myItem=null;
		myUser=null;
		
		List<Booking> FindBookingByUser = bookingRepository.findBookingByUser(myUser);
		for (Booking PersitanceUser : FindBookingByUser){

			assertTrue(PersitanceUser.getUser().getAddress().equals(myBooking.getUser().getAddress()) || PersitanceUser.getUser().getAddress().equals(myBooking1.getUser().getAddress()));
			assertTrue(PersitanceUser.getUser().getFirstName().equals(myBooking.getUser().getFirstName()) || PersitanceUser.getUser().getFirstName().equals(myBooking1.getUser().getFirstName()));
			assertTrue(PersitanceUser.getUser().getLastName().equals(myBooking.getUser().getLastName()) ||PersitanceUser.getUser().getLastName().equals(myBooking1.getUser().getLastName()) );
			assertTrue(PersitanceUser.getUser().getLibraryCardID()==(myBooking.getUser().getLibraryCardID()) ||PersitanceUser.getUser().getLibraryCardID()==(myBooking1.getUser().getLibraryCardID()) );
			assertTrue(PersitanceUser.getUser().getIsOnlineAcc()==(myBooking.getUser().getIsOnlineAcc()) ||PersitanceUser.getUser().getIsOnlineAcc()==(myBooking1.getUser().getIsOnlineAcc()) );
			assertTrue(PersitanceUser.getUser().getLibraryCardID()==(myBooking.getUser().getLibraryCardID()) ||PersitanceUser.getUser().getLibraryCardID()==(myBooking1.getUser().getLibraryCardID()) );
			assertTrue(PersitanceUser.getUser().getIsVerified()==(myBooking.getUser().getIsVerified()) ||PersitanceUser.getUser().getIsVerified()==(myBooking1.getUser().getIsVerified()) );
			assertTrue(PersitanceUser.getUser().getLibrarySystem().getSystemID().equals(myBooking.getUser().getLibrarySystem().getSystemID()) ||PersitanceUser.getUser().getLibrarySystem().getSystemID().equals(myBooking1.getUser().getLibrarySystem().getSystemID()) );

			
			
			
			
			
		}
				
	}
	
	
	@Test
	public void testPersistenceAndLoadBookingByStatus() {
		
		Address myadress = new Address("51","Parkekx","Montreal","H5H6H7","Quebec","Canada");
		addressRepository.save(myadress);
		Calendar mycalendar = new Calendar();
		calendarRepository.save(mycalendar);
		LibrarySystem myLibrary = new LibrarySystem(myadress, mycalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("J.K.", "Rowling");
		Author myAuthor1 = new Author("J.K.", "Rowling22");

		authorRepository.save(myAuthor);
		authorRepository.save(myAuthor1);

		Title mytitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		Title mytitle1 = new Title("Kakaoko", "October 30th, 2021", myAuthor1);

		titleRepository.save(mytitle);
		titleRepository.save(mytitle1);

		Item myItem = new Item(Status.Available, myLibrary, mytitle);
		Item myItem1 = new Item(Status.Available, myLibrary, mytitle1);

		itemRepository.save(myItem);
		itemRepository.save(myItem1);

		User myUser = new User(true,"Harsh","Patel",true,0,myadress,myLibrary);
		User myUser1 = new User(true,"Hershey","Patel",true,0,myadress,myLibrary);

		userRepository.save(myUser);
		userRepository.save(myUser1);

		//public Booking(Date aStartDate, Date aEndDate, BookingType aType, Item aItembooked, User aUser)
		String str1 = "2015-03-31";
		String str2 ="2015-04-05";
		Date sdate= Date.valueOf(str1);
		Date edate = Date.valueOf(str2);
		Booking myBooking = new Booking (sdate,edate,BookingType.Reservation,myItem,myUser);
		Booking myBooking1 = new Booking (sdate,edate,BookingType.Reservation,myItem1,myUser1);

		bookingRepository.save(myBooking);
		bookingRepository.save(myBooking1);

		
		myadress = null;
		mycalendar= null;
		myLibrary=null;
		myAuthor=null;
		mytitle=null;
		myItem=null;
		myUser=null;
		
		List<Booking> FindBookingByEndDate = bookingRepository.findBookingByType(BookingType.Reservation);
		for (Booking PersitanceBook : FindBookingByEndDate){

			assertTrue(PersitanceBook.getStartDate().equals(myBooking.getStartDate()) || PersitanceBook.getStartDate().equals(myBooking1.getStartDate()));
			assertTrue(PersitanceBook.getEndDate().equals(myBooking.getEndDate()) || PersitanceBook.getEndDate().equals(myBooking1.getEndDate()));
			assertTrue(PersitanceBook.getBookingID().equals(myBooking.getBookingID()) ||PersitanceBook.getBookingID().equals(myBooking1.getBookingID()) );
			assertTrue(PersitanceBook.getType().equals(myBooking.getType()) ||PersitanceBook.getType().equals(myBooking1.getType()) );
		}
		
			
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
