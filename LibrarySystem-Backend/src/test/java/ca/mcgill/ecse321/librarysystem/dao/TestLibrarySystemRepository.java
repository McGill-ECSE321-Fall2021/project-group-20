package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestLibrarySystemRepository {

	@Autowired
	private CalendarRepository calendarRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private LibrarySystemRepository librarySystemRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private TitleRepository titleRepository;
	@Autowired
	private ItemRepository itemRepository;

	
	@AfterEach
	public void clearDatabase() {
		// Clear the library system, calendar and address in the persistence. 
		itemRepository.deleteAll();
		titleRepository.deleteAll();
		authorRepository.deleteAll();
		userRepository.deleteAll();
		librarySystemRepository.deleteAll();
		addressRepository.deleteAll();
		calendarRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadLibrarySystem() {
		Calendar calendar = new Calendar();
		Address busAddress = new Address();
		calendarRepository.save(calendar);
		addressRepository.save(busAddress);
		
		LibrarySystem ls = new LibrarySystem(busAddress, calendar);
		librarySystemRepository.save(ls);
		
		String systemID = ls.getSystemID();
	
		ls = null;
		
		ls = librarySystemRepository.findLibrarySystemBySystemID(systemID);
		assertNotNull(ls);
		assertEquals(systemID, ls.getSystemID());
	}
	
	@Test
	public void testPersistAndLoadLibrarySystemWithCalendar() {	
		Calendar calendar = new Calendar();
		Address busAddress = new Address();
		calendarRepository.save(calendar);
		addressRepository.save(busAddress);
		
		LibrarySystem ls = new LibrarySystem(busAddress, calendar);
		librarySystemRepository.save(ls);
		
		String systemID = ls.getSystemID();
		String calendarID = calendar.getCalendarID();
		
		ls = null;
		
		ls = librarySystemRepository.findLibrarySystemByCalendar(calendar);
		assertNotNull(ls);
		assertEquals(systemID, ls.getSystemID());
	    assertEquals(calendarID, ls.getCalendar().getCalendarID());
	}

	@Test
	public void testPersistAndLoadLibrarySystemWithUser() {
		LibrarySystem ls = new LibrarySystem();
		librarySystemRepository.save(ls);
		
		// Setup address and library link to u1
		Address address1 = new Address();
		address1.setCivicNumber("4609");
		address1.setCity("Laval");
		address1.setCountry("Canada");
		address1.setPostalCode("H7T 2X6");
		address1.setProvince("Quebec");
		address1.setStreet("Sherbrooke");
		addressRepository.save(address1);
		
		User u1 = new User();
		u1.setUsername("u1");
		u1.setPassword("u1");
		u1.setFirstName("Dan");
		u1.setLastName("lastName");
		u1.setAddress(address1);
		u1.setLibrarySystem(ls);
		userRepository.save(u1);
		
		// Setup address and library link for u2
		Address address2 = new Address();
		address2.setCivicNumber("4160");
		address2.setCity("Laval");
		address2.setCountry("Canada");
		address2.setPostalCode("H7T 2L6");
		address2.setProvince("Quebec");
		address2.setStreet("Sherbrooke");
		addressRepository.save(address2);
		
		User u2 = new User();
		u2.setUsername("u2");
		u2.setPassword("u2");
		u2.setFirstName("Abdel");
		u2.setLastName("Madjid");
		u2.setAddress(address2);
		u2.setLibrarySystem(ls);
		userRepository.save(u2);
		
		// Setup address and library link for u3
		Address address3 = new Address();
		address3.setCivicNumber("4200");
		address3.setCity("Laval");
		address3.setCountry("Canada");
		address3.setPostalCode("H7T 2P6");
		address3.setProvince("Quebec");
		address3.setStreet("Sherbrooke");
		addressRepository.save(address3);
		
		User u3 = new User();
		u3.setUsername("u3");
		u3.setPassword("u3");
		u3.setFirstName("Harsh");
		u3.setLastName("Patel");
		u3.setAddress(address3);
		u3.setLibrarySystem(ls);
		userRepository.save(u3);
		
		List<User> users = new ArrayList<User>();
		users.add(u1);
		users.add(u2);
		users.add(u3);
		
		ls.addUser(u1);
		ls.addUser(u2);
		ls.addUser(u3);
		librarySystemRepository.save(ls);
		String systemID = ls.getSystemID();
		int libraryCardID1 = u1.getLibraryCardID();
		int libraryCardID2 = u2.getLibraryCardID();
		int libraryCardID3 = u3.getLibraryCardID();

		ls = null;
		
		ls = librarySystemRepository.findLibrarySystemByUsers(u2);
		assertNotNull(ls);
		assertEquals(systemID, ls.getSystemID());
		assertEquals(libraryCardID1, ls.getUsers().get(0).getLibraryCardID());
		assertEquals(libraryCardID2, ls.getUsers().get(1).getLibraryCardID());
		assertEquals(libraryCardID3, ls.getUsers().get(2).getLibraryCardID());

	}
	
	@Test
	public void testPersistAndLoadLibrarySystemWithAddress() {
		Address businessAddress = new Address();
		String civicNumber = "4609";
		String street = "Sherbrooke";
		String city = "Montreal";
		String postalCode = "H7T 2N6";
		String province = "Quebec";
		String country = "Canada";
		businessAddress.setCivicNumber(civicNumber);
		businessAddress.setStreet(street);
		businessAddress.setCity(city);
		businessAddress.setPostalCode(postalCode);
		businessAddress.setProvince(province);
		businessAddress.setCountry(country);
		addressRepository.save(businessAddress);
		
		LibrarySystem ls = new LibrarySystem();
		ls.setBusinessaddress(businessAddress);
		librarySystemRepository.save(ls);
		
		String systemID = ls.getSystemID();
		String addressID = businessAddress.getAddressID();
		
		ls = null;
				
		ls = librarySystemRepository.findLibrarySystemByBusinessaddress(businessAddress);
		assertNotNull(ls);
		assertEquals(systemID, ls.getSystemID());
		assertEquals(addressID, ls.getBusinessaddress().getAddressID());
	}

	@Test
	public void testPersistAndLoadLibrarySystemWithItem() {
		LibrarySystem ls = new LibrarySystem();
		librarySystemRepository.save(ls);
		
		// Setup title and author for the item book
		Title bookTitle = new Title();
		bookTitle.setName("Harry Potter");
		Author bookAuthor = new Author();
		bookAuthor.setFirstName("Dan");
		bookAuthor.setLastName("Hosi");
		bookAuthor.addTitle(bookTitle);
		authorRepository.save(bookAuthor);
		bookTitle.setAuthor(bookAuthor);
		titleRepository.save(bookTitle);

		
		Item book = new Item();
		book.setStatus(Status.Borrowed);
		book.setLibrarySystem(ls);
		book.setTitle(bookTitle);
		itemRepository.save(book);
		
		// Setup title and author for the item movie
		Title movieTitle = new Title();
		movieTitle.setName("Titanic");
		Author movieAuthor = new Author();
		bookAuthor.setFirstName("Harsh");
		bookAuthor.setLastName("Patel");
		authorRepository.save(movieAuthor);
		bookTitle.setAuthor(movieAuthor);
		titleRepository.save(movieTitle);



		Item movie = new Item();
		movie.setStatus(Status.Borrowed);
		movie.setLibrarySystem(ls);
		movie.setTitle(movieTitle);
		itemRepository.save(movie);
		
		List<Item> items = new ArrayList<>();
		items.add(book);
		items.add(movie);
		
		ls.addItem(book);
		ls.addItem(movie);
		librarySystemRepository.save(ls);
		
		String itemBarcode1 = book.getItemBarcode();
		String itemBarcode2 = movie.getItemBarcode();
		String systemID = ls.getSystemID();
		
		ls = null;
		
		ls = librarySystemRepository.findLibrarySystemByItems(movie);
		assertNotNull(ls);
		assertEquals(systemID, ls.getSystemID());
		assertEquals(itemBarcode1, ls.getItems().get(0).getItemBarcode());
		assertEquals(itemBarcode2, ls.getItems().get(1).getItemBarcode());
	}
}
