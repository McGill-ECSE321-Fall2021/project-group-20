package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Booking.BookingType;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.model.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestUserRepositoryPersistence {
	
	private User aUser;
	private User user2;
	private Address aAddress;
	private Address aAddress2;
	private Calendar aCalendar;
	private LibrarySystem aLibrary;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private LibrarySystemRepository librarySystemRepository;
	@Autowired
	private CalendarRepository calendarRepository;
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private TitleRepository titleRepository;
	@Autowired
	private ItemRepository itemRepository;
	
	@BeforeEach
	public void setupTest() {
		aAddress = new Address(100,"University St.","Montreal","H4X1A5","Quebec","Canada");
		addressRepository.save(aAddress);
		aCalendar = new Calendar();
		calendarRepository.save(aCalendar);
		aLibrary = new LibrarySystem(aAddress, aCalendar);
		librarySystemRepository.save(aLibrary);
		aUser = new User(true, "Alex", "Bangala", true, 0, aAddress, aLibrary);
		userRepository.save(aUser);
		aUser.setUsername("ab");
		aUser.setPassword("test123");
		aUser.setEmail("test@test.ca");
		userRepository.save(aUser);
		
		aAddress2 = new Address(50,"University St.","Montreal","H4X1A5","Quebec","Canada");
		addressRepository.save(aAddress2);
		user2 = new User(true, "Test", "Brown", true, 0, aAddress2, aLibrary);
		userRepository.save(user2);
		user2.setUsername("test2");
		user2.setPassword("test456");
		user2.setEmail("t@t.com");
		userRepository.save(user2);
	}
	
	@AfterEach
	public void clearDatabase() {
		aUser.delete();
		user2.delete();
		itemRepository.deleteAll();
		titleRepository.deleteAll();
		authorRepository.deleteAll();
		bookingRepository.deleteAll();
		userRepository.deleteAll();
		librarySystemRepository.deleteAll();
		addressRepository.deleteAll();
		calendarRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadUserByLibraryCardID() {
		aAddress = null;
		aCalendar = null;
		aLibrary = null;
		
		User PersistedUser = userRepository.findUserByLibraryCardID(aUser.getLibraryCardID());
		aAddress = PersistedUser.getAddress();
		aLibrary = PersistedUser.getLibrarySystem();
		
		assertNotNull(PersistedUser);
		assertEquals(aAddress.getAddressID(), aUser.getAddress().getAddressID());
		assertEquals(aLibrary.getSystemID(), aUser.getLibrarySystem().getSystemID());
		assertEquals(PersistedUser.getDemeritPts(), aUser.getDemeritPts());
		assertEquals(PersistedUser.getEmail(), aUser.getEmail());
		assertEquals(PersistedUser.getFirstName(), aUser.getFirstName());
		assertEquals(PersistedUser.getIsOnlineAcc(), aUser.getIsOnlineAcc());
		assertEquals(PersistedUser.getIsVerified(), aUser.getIsVerified());
		assertEquals(PersistedUser.getLastName(), aUser.getLastName());
		assertEquals(PersistedUser.getLibraryCardID(), aUser.getLibraryCardID());
		assertEquals(PersistedUser.getUsername(), aUser.getUsername());
		assertEquals(PersistedUser.getPassword(), aUser.getPassword());
	}
	
	@Test
	public void testPersistAndLoadUsersByFirstName() {
		
	}
	
	@Test
	public void testPersistAndLoadUsersByLastName() {
		
	}
	
	@Test
	public void testPersistAndLoadUsersByIsVerified() {
		
	}
	
	@Test
	public void testPersistAndLoadUsersByDemeritPts() {
		
	}
	
	@Test
	public void testPersistAndLoadUsersByAddress() {
		List<User> PersistedUsers = userRepository.findUserByAddress(aAddress);
		List<User> createdUsers = new ArrayList<User>();
		createdUsers.add(aUser);
		
		assertNotNull(PersistedUsers);
		assertEquals(PersistedUsers.size(), createdUsers.size());
		for (User u : PersistedUsers) {
			assertEquals(u.getAddress().getAddressID(), createdUsers.get(0).getAddress().getAddressID());
			createdUsers.remove(0);
		}
	}
	
	@Test
	public void testPersistAndLoadUsersByLibrarySystem() {
		List<User> PersistedUsers = userRepository.findUserByLibrarySystem(aLibrary);
		List<User> createdUsers = new ArrayList<User>();
		createdUsers.add(aUser);
		createdUsers.add(user2);
		
		assertNotNull(PersistedUsers);
		assertEquals(PersistedUsers.size(), createdUsers.size());
		for (User u : PersistedUsers) {
			assertEquals(u.getLibrarySystem().getSystemID(), createdUsers.get(0).getLibrarySystem().getSystemID());
			createdUsers.remove(0);
		}
	}
	
	@Test
	public void testPersistAndLoadUsersByIsOnlineAcc() {
		User notOnlineUser = new User(false, "Test", "Brown", true, 0, aAddress, aLibrary);
		userRepository.save(notOnlineUser);
		
		List<User> createdUsers = new ArrayList<User>();
		createdUsers.add(aUser);
		createdUsers.add(user2);
		createdUsers.add(notOnlineUser);
		
		aAddress = null;
		aCalendar = null;
		aLibrary = null;
		
		List<User> PersistedUsers = userRepository.findUserByIsOnlineAcc(true);
		assertNotNull(PersistedUsers);
		
		for (User u : PersistedUsers) {
			assertNotNull(u);
			assertEquals(u.getIsOnlineAcc(), createdUsers.get(0).getIsOnlineAcc());
			assertEquals(u.getAddress().getAddressID(), createdUsers.get(0).getAddress().getAddressID());
			assertEquals(u.getLibrarySystem().getSystemID(), createdUsers.get(0).getLibrarySystem().getSystemID());
			assertEquals(u.getDemeritPts(), createdUsers.get(0).getDemeritPts());
			assertEquals(u.getEmail(), createdUsers.get(0).getEmail());
			assertEquals(u.getFirstName(), createdUsers.get(0).getFirstName());
			assertEquals(u.getIsVerified(), createdUsers.get(0).getIsVerified());
			assertEquals(u.getLastName(), createdUsers.get(0).getLastName());
			assertEquals(u.getLibraryCardID(), createdUsers.get(0).getLibraryCardID());
			assertEquals(u.getUsername(), createdUsers.get(0).getUsername());
			assertEquals(u.getPassword(), createdUsers.get(0).getPassword());
			createdUsers.remove(0);
		}
		
		notOnlineUser.delete();
	}
	
	@Test
	public void testPersistAndLoadUserByUsername() {		
		aAddress = null;
		aCalendar = null;
		aLibrary = null;
		
		User u = userRepository.findUserByUsername("test2");
		assertNotNull(u);
		
		assertEquals(u.getAddress().getAddressID(), user2.getAddress().getAddressID());
		assertEquals(u.getLibrarySystem().getSystemID(), user2.getLibrarySystem().getSystemID());
		assertEquals(u.getDemeritPts(), user2.getDemeritPts());
		assertEquals(u.getEmail(), user2.getEmail());
		assertEquals(u.getFirstName(), user2.getFirstName());
		assertEquals(u.getIsOnlineAcc(), user2.getIsOnlineAcc());
		assertEquals(u.getIsVerified(), user2.getIsVerified());
		assertEquals(u.getLastName(), user2.getLastName());
		assertEquals(u.getLibraryCardID(), user2.getLibraryCardID());
		assertEquals(u.getUsername(), user2.getUsername());
		assertEquals(u.getPassword(), user2.getPassword());
	}
	
	@Test
	public void testPersistAndLoadUserByEmail() {
		aAddress = null;
		aCalendar = null;
		aLibrary = null;
		
		User u = userRepository.findUserByEmail("test@test.ca");
		assertNotNull(u);
		
		assertEquals(u.getAddress().getAddressID(), aUser.getAddress().getAddressID());
		assertEquals(u.getLibrarySystem().getSystemID(), aUser.getLibrarySystem().getSystemID());
		assertEquals(u.getDemeritPts(), aUser.getDemeritPts());
		assertEquals(u.getEmail(), aUser.getEmail());
		assertEquals(u.getFirstName(), aUser.getFirstName());
		assertEquals(u.getIsOnlineAcc(), aUser.getIsOnlineAcc());
		assertEquals(u.getIsVerified(), aUser.getIsVerified());
		assertEquals(u.getLastName(), aUser.getLastName());
		assertEquals(u.getLibraryCardID(), aUser.getLibraryCardID());
		assertEquals(u.getUsername(), aUser.getUsername());
		assertEquals(u.getPassword(), aUser.getPassword());
	}
	
	@Test
	public void testPersistAndLoadUserByBooking() {
		Author aAuthor = new Author("J.K.", "Rowling");
		authorRepository.save(aAuthor);
		Title aTitle = new Title("Test", "October 50th, 2021", aAuthor);
		titleRepository.save(aTitle);
		Item aItem = new Item(Status.Available, aLibrary, aTitle);
		itemRepository.save(aItem);
		Booking aBooking = new Booking(null, null, BookingType.Borrow, aItem, aUser);
		bookingRepository.save(aBooking);
		aUser.addUserbooking(aBooking);
		
		User u = userRepository.findUserByUserbooking(aBooking);
		
		assertNotNull(u);
		assertEquals(u.getAddress().getAddressID(), aUser.getAddress().getAddressID());
		assertEquals(u.getLibrarySystem().getSystemID(), aUser.getLibrarySystem().getSystemID());
		assertEquals(u.getDemeritPts(), aUser.getDemeritPts());
		assertEquals(u.getEmail(), aUser.getEmail());
		assertEquals(u.getFirstName(), aUser.getFirstName());
		assertEquals(u.getIsOnlineAcc(), aUser.getIsOnlineAcc());
		assertEquals(u.getIsVerified(), aUser.getIsVerified());
		assertEquals(u.getLastName(), aUser.getLastName());
		assertEquals(u.getLibraryCardID(), aUser.getLibraryCardID());
		assertEquals(u.getUsername(), aUser.getUsername());
		assertEquals(u.getPassword(), aUser.getPassword());
	}
}
