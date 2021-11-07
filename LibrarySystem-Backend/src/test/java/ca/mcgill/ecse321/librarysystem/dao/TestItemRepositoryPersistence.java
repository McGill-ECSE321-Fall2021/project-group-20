package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.model.Booking.BookingType;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestItemRepositoryPersistence {

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
	private ArchiveRepository archiveRepository;
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private MusicAlbumRepository musicAlbumRepository;
	@Autowired
	private NewspaperRepository newspaperRepository;
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private UserRepository userRepository;

	/**
	 * Delete all the persisted object after each test
	 * 
	 */

	@AfterEach
	public void clearDatabase() {
		bookingRepository.deleteAll();
		movieRepository.deleteAll();
		musicAlbumRepository.deleteAll();
		newspaperRepository.deleteAll();
		bookRepository.deleteAll();
		archiveRepository.deleteAll();
		itemRepository.deleteAll();
		titleRepository.deleteAll();
		authorRepository.deleteAll();
		librarySystemRepository.deleteAll();
		userRepository.deleteAll();
		addressRepository.deleteAll();
		calendarRepository.deleteAll();
	}

	/**
	 * This is a test to see if the Item gets persisted in a good way. We also see
	 * if we can fetch the item by its booking and that all the attributes are
	 * preserved
	 * 
	 */

	@Test
	public void testPersistAndLoadByBooking() {
		Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
		addressRepository.save(myAdress);
		Calendar myCalendar = new Calendar();
		calendarRepository.save(myCalendar);
		LibrarySystem myLibrary = new LibrarySystem(myAdress, myCalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("J.K.", "Rowling");
		authorRepository.save(myAuthor);
		Title myTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		titleRepository.save(myTitle);
		Item myItem = new Item(Status.Available, myTitle);
		itemRepository.save(myItem);
		User myUser = new User(true, true, "Harsh", "Patel", true, 0, myAdress);
		userRepository.save(myUser);
		String str1 = "2015-03-31";
		String str2 = "2015-04-05";
		Date sDate = Date.valueOf(str1);
		Date eDate = Date.valueOf(str2);
		Booking myBooking = new Booking(sDate, eDate, BookingType.Reservation, myItem, myUser);
		bookingRepository.save(myBooking);
		itemRepository.save(myItem);

		Item retrieved = itemRepository.findItemByBooking(myBooking);
		assertNotNull(retrieved);
		assertEquals(retrieved.getItemBarcode(), myItem.getItemBarcode());
		assertEquals(retrieved.getTitle().getName(), (myItem.getTitle().getName()));
		assertEquals(retrieved.getTitle().getAuthor().get(0).getFirstName(),
				(myItem.getTitle().getAuthor().get(0).getFirstName()));
//		assertEquals(retrieved.getLibrarySystem().getBusinessaddress().getCity(),
//				(myItem.getLibrarySystem().getBusinessaddress().getCity()));

	}

	/**
	 * This is a test to see if an Archive gets persisted in a good way. We also see
	 * if we can fetch the Archive by its barcode and that all the attributes are
	 * preserved
	 * 
	 */

	@Test
	public void testPersistAndLoadOfAnArchive() {
		Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
		addressRepository.save(myAdress);
		Calendar myCalendar = new Calendar();
		calendarRepository.save(myCalendar);
		LibrarySystem myLibrary = new LibrarySystem(myAdress, myCalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("J.K.", "Rowling");
		authorRepository.save(myAuthor);
		Title myTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		titleRepository.save(myTitle);
		Archive myArchive = new Archive(Status.Available, myTitle);
		archiveRepository.save(myArchive);
		addressRepository.save(myAdress);
		myAdress = null;
		myCalendar = null;
		myLibrary = null;
		myAuthor = null;
		myTitle = null;

		Archive persistItem = (Archive) archiveRepository.findItemByItemBarcode(myArchive.getItemBarcode());
		assertNotNull(persistItem);
		assertEquals(persistItem.getItemBarcode(), (myArchive.getItemBarcode()));
		assertEquals(persistItem.getTitle().getName(), (myArchive.getTitle().getName()));
		assertEquals(persistItem.getTitle().getAuthor().get(0).getFirstName(),
				(myArchive.getTitle().getAuthor().get(0).getFirstName()));
//		assertEquals(persistItem.getLibrarySystem().getBusinessaddress().getCity(),
//				(myArchive.getLibrarySystem().getBusinessaddress().getCity()));
	}

	/**
	 * This is a test to see if a book gets persisted in a good way. We also see if
	 * we can fetch the book by its barcode and that all the attributes are
	 * preserved
	 * 
	 */

	@Test
	public void testPersistAndLoadOfABook() {
		Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
		addressRepository.save(myAdress);
		Calendar myCalendar = new Calendar();
		calendarRepository.save(myCalendar);
		LibrarySystem myLibrary = new LibrarySystem(myAdress, myCalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("John", "Times");
		authorRepository.save(myAuthor);
		Title myTitle = new Title("The Doe Movie", "October 31th, 2021", myAuthor);
		titleRepository.save(myTitle);
		Book myBook = new Book(Status.Available, myTitle, "123-456-789", "200");
		bookRepository.save(myBook);
		addressRepository.save(myAdress);

		myAdress = null;
		myCalendar = null;
		myLibrary = null;
		myAuthor = null;
		myTitle = null;

		Book persistItem = (Book) bookRepository.findItemByItemBarcode(myBook.getItemBarcode());
		assertNotNull(persistItem);
		assertEquals(persistItem.getItemBarcode(), (myBook.getItemBarcode()));
		assertEquals(persistItem.getTitle().getName(), (myBook.getTitle().getName()));
		assertEquals(persistItem.getTitle().getAuthor().get(0).getFirstName(),
				(myBook.getTitle().getAuthor().get(0).getFirstName()));
//		assertEquals(persistItem.getLibrarySystem().getBusinessaddress().getCity(),
//				(myBook.getLibrarySystem().getBusinessaddress().getCity()));
	}

	/**
	 * This is a test to see if a Movie gets persisted in a good way. We also see if
	 * we can fetch the Movie by its barcode and that all the attributes are
	 * preserved
	 * 
	 */

	@Test
	public void testPersistAndLoadOfAMovie() {
		Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
		addressRepository.save(myAdress);
		Calendar myCalendar = new Calendar();
		calendarRepository.save(myCalendar);
		LibrarySystem myLibrary = new LibrarySystem(myAdress, myCalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("John", "Doe");
		authorRepository.save(myAuthor);
		Title myTitle = new Title("Newspaper Life", "October 31th, 2021", myAuthor);
		titleRepository.save(myTitle);
		Movie myMovie = new Movie(Status.Available, myTitle, 200);
		movieRepository.save(myMovie);
		addressRepository.save(myAdress);

		myAdress = null;
		myCalendar = null;
		myLibrary = null;
		myAuthor = null;
		myTitle = null;
		Movie persistItem = (Movie) movieRepository.findItemByItemBarcode(myMovie.getItemBarcode());
		assertNotNull(persistItem);
		assertEquals(persistItem.getItemBarcode(), (myMovie.getItemBarcode()));
		assertEquals(persistItem.getTitle().getName(), (myMovie.getTitle().getName()));
		assertEquals(persistItem.getTitle().getAuthor().get(0).getFirstName(),
				(myMovie.getTitle().getAuthor().get(0).getFirstName()));
//		assertEquals(persistItem.getLibrarySystem().getBusinessaddress().getCity(),
//				(myMovie.getLibrarySystem().getBusinessaddress().getCity()));
	}

	/**
	 * This is a test to see if a MusicAlbum gets persisted in a good way. We also
	 * see if we can fetch the MusicAlbum by its barcode and that all the attributes
	 * are preserved
	 * 
	 */

	@Test
	public void testPersistAndLoadOfAMusicAlbum() {
		Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
		addressRepository.save(myAdress);
		Calendar myCalendar = new Calendar();
		calendarRepository.save(myCalendar);
		LibrarySystem myLibrary = new LibrarySystem(myAdress, myCalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("Justin", "Bieber");
		authorRepository.save(myAuthor);
		Title myTitle = new Title("Classics", "October 31th, 2021", myAuthor);
		titleRepository.save(myTitle);
		MusicAlbum myMusicAlbum = new MusicAlbum(Status.Available, myTitle, 60);
		musicAlbumRepository.save(myMusicAlbum);
		addressRepository.save(myAdress);

		myAdress = null;
		myCalendar = null;
		myLibrary = null;
		myAuthor = null;
		myTitle = null;

		MusicAlbum persistItem = (MusicAlbum) musicAlbumRepository.findItemByItemBarcode(myMusicAlbum.getItemBarcode());
		assertNotNull(persistItem);
		assertEquals(persistItem.getItemBarcode(), (myMusicAlbum.getItemBarcode()));
		assertEquals(persistItem.getTitle().getName(), (myMusicAlbum.getTitle().getName()));
		assertEquals(persistItem.getTitle().getAuthor().get(0).getFirstName(),
				(myMusicAlbum.getTitle().getAuthor().get(0).getFirstName()));
		assertEquals(persistItem.getDuration(),myMusicAlbum.getDuration());
//		assertEquals(persistItem.getLibrarySystem().getBusinessaddress().getCity(),
//				(myMusicAlbum.getLibrarySystem().getBusinessaddress().getCity()));
	}

	/**
	 * This is a test to see if a Newspaper gets persisted in a good way. We also
	 * see if we can fetch the Newspaper by its barcode and that all the attributes
	 * are preserved
	 * 
	 */

	@Test
	public void testPersistAndLoadOfANewspaper() {
		Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
		addressRepository.save(myAdress);
		Calendar myCalendar = new Calendar();
		calendarRepository.save(myCalendar);
		LibrarySystem myLibrary = new LibrarySystem(myAdress, myCalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("NY", "Times");
		authorRepository.save(myAuthor);
		Title myTitle = new Title("Newspaper Story", "October 31th, 2021", myAuthor);
		titleRepository.save(myTitle);
		Newspaper myNewspaper = new Newspaper(Status.Available, myTitle);
		newspaperRepository.save(myNewspaper);
		addressRepository.save(myAdress);

		myAdress = null;
		myCalendar = null;
		myLibrary = null;
		myAuthor = null;
		myTitle = null;

		Newspaper persistItem = (Newspaper) newspaperRepository.findItemByItemBarcode(myNewspaper.getItemBarcode());
		assertNotNull(persistItem);
		assertEquals(persistItem.getItemBarcode(), (myNewspaper.getItemBarcode()));
		assertEquals(persistItem.getTitle().getName(), (myNewspaper.getTitle().getName()));
		assertEquals(persistItem.getTitle().getAuthor().get(0).getFirstName(),
				(myNewspaper.getTitle().getAuthor().get(0).getFirstName()));
//		assertEquals(persistItem.getLibrarySystem().getBusinessaddress().getCity(),
//				(myNewspaper.getLibrarySystem().getBusinessaddress().getCity()));
	}

	/**
	 * This is a test to see if an Item gets persisted in a good way. We also see if
	 * we can fetch the Item by its barcode and that all the attributes are
	 * preserved
	 * 
	 */

	@Test
	public void testPersistAndLoadItemByBarCode() {

		Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
		addressRepository.save(myAdress);
		Calendar myCalendar = new Calendar();
		calendarRepository.save(myCalendar);
		LibrarySystem myLibrary = new LibrarySystem(myAdress, myCalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("J.K.", "Rowling");
		authorRepository.save(myAuthor);
		Title myTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		titleRepository.save(myTitle);
		Item myItem = new Item(Status.Available, myTitle);
		itemRepository.save(myItem);
		addressRepository.save(myAdress);
		myAdress = null;
		myCalendar = null;
		myLibrary = null;
		myAuthor = null;
		myTitle = null;

		Item persistItem = itemRepository.findItemByItemBarcode(myItem.getItemBarcode());
		assertNotNull(persistItem);
		assertEquals(persistItem.getItemBarcode(), (myItem.getItemBarcode()));
		assertEquals(persistItem.getTitle().getName(), (myItem.getTitle().getName()));
		assertEquals(persistItem.getTitle().getAuthor().get(0).getFirstName(),
				(myItem.getTitle().getAuthor().get(0).getFirstName()));
//		assertEquals(persistItem.getLibrarySystem().getBusinessaddress().getCity(),
//				(myItem.getLibrarySystem().getBusinessaddress().getCity()));

	}

	/**
	 * This is a test to see if an Item gets persisted in a good way. We also see if
	 * we can fetch the Item by its Status and that all the attributes are preserved
	 * 
	 */

	@Test
	public void testPersistAndLoadItemByStatus() {

		Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
		addressRepository.save(myAdress);
		Calendar myCalendar = new Calendar();
		calendarRepository.save(myCalendar);
		LibrarySystem myLibrary = new LibrarySystem(myAdress, myCalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("J.K.", "Rowling");
		authorRepository.save(myAuthor);
		Title myTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		titleRepository.save(myTitle);
		Item myItem = new Item(Status.Available, myTitle);
		itemRepository.save(myItem);
		Item hisItem = new Item(Status.Borrowed, myTitle);
		itemRepository.save(hisItem);
		addressRepository.save(myAdress);
		myAdress = null;
		myCalendar = null;
		myLibrary = null;
		myAuthor = null;
		myTitle = null;

		List<Item> persistItem = itemRepository.findItemByStatus(Status.Available);
		assertNotNull(persistItem);
		for (Item persitanceItem : persistItem) {
			assertEquals(persitanceItem.getItemBarcode(), (myItem.getItemBarcode()));
			assertEquals(persitanceItem.getStatus(), (myItem.getStatus()));
			assertEquals(persitanceItem.getTitle().getAuthor().get(0).getFirstName(),
					(myItem.getTitle().getAuthor().get(0).getFirstName()));
//			assertEquals(persitanceItem.getLibrarySystem().getBusinessaddress().getCity(),
//					(myItem.getLibrarySystem().getBusinessaddress().getCity()));
		}

	}

	/**
	 * This is a test to see if Items gets persisted in a good way. We also see if
	 * we can fetch the Items by their LibrarySystem and that all the attributes are
	 * preserved
	 * 
	 */

//	@Test
//	public void testPersistAndLoadItemByLibrarySystem() {
//
//		Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
//		addressRepository.save(myAdress);
//		Calendar myCalendar = new Calendar();
//		calendarRepository.save(myCalendar);
//		LibrarySystem myLibrary = new LibrarySystem(myAdress, myCalendar);
//		librarySystemRepository.save(myLibrary);
//		Author myAuthor = new Author("J.K.", "Rowling");
//		authorRepository.save(myAuthor);
//		Title myTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
//		titleRepository.save(myTitle);
//		Item myItem = new Item(Status.Available, myLibrary, myTitle);
//		itemRepository.save(myItem);
//		Item hisItem = new Item(Status.Borrowed, myLibrary, myTitle);
//		itemRepository.save(hisItem);
//		addressRepository.save(myAdress);
//		myAdress = null;
//		myCalendar = null;
//		// myLibrary=null;
//		myAuthor = null;
//		myTitle = null;
//
////		List<Item> persistItem = itemRepository.findItemByLibrarySystem(myLibrary);
//		assertNotNull(persistItem);
//		for (Item persitanceItem : persistItem) {
//			assertTrue(persitanceItem.getItemBarcode().equals(myItem.getItemBarcode())
//					|| persitanceItem.getItemBarcode().equals(hisItem.getItemBarcode()));
//			assertTrue(persitanceItem.getStatus().equals(myItem.getStatus())
//					|| persitanceItem.getStatus().equals(hisItem.getStatus()));
//			assertTrue(persitanceItem.getTitle().getAuthor().get(0).getFirstName()
//					.equals(myItem.getTitle().getAuthor().get(0).getFirstName())
//					|| persitanceItem.getTitle().getAuthor().get(0).getFirstName()
//							.equals(hisItem.getTitle().getAuthor().get(0).getFirstName()));
//			assertTrue(persitanceItem.getLibrarySystem().getBusinessaddress().getCity()
//					.equals(myItem.getLibrarySystem().getBusinessaddress().getCity())
//					|| persitanceItem.getLibrarySystem().getBusinessaddress().getCity()
//							.equals(hisItem.getLibrarySystem().getBusinessaddress().getCity()));
//		}
//
//	}

	/**
	 * This is a test to see if Items gets persisted in a good way. We also see if
	 * we can fetch the Items by their Title and that all the attributes are
	 * preserved
	 * 
	 */

	@Test
	public void testPersistAndLoadItemByTitle() {

		Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
		addressRepository.save(myAdress);
		Calendar myCalendar = new Calendar();
		calendarRepository.save(myCalendar);
		LibrarySystem myLibrary = new LibrarySystem(myAdress, myCalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("J.K.", "Rowling");
		authorRepository.save(myAuthor);
		Title myTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		titleRepository.save(myTitle);
		Item myItem = new Item(Status.Available, myTitle);
		itemRepository.save(myItem);
		Item hisItem = new Item(Status.Borrowed, myTitle);
		itemRepository.save(hisItem);
		addressRepository.save(myAdress);
		myAdress = null;
		myCalendar = null;
		myLibrary = null;
		myAuthor = null;
		// myTitle=null;

		List<Item> persistItem = itemRepository.findItemByTitle(myTitle);
		assertNotNull(persistItem);

		for (Item persitanceItem : persistItem) {
			assertTrue(persitanceItem.getItemBarcode() == (myItem.getItemBarcode())
					|| persitanceItem.getItemBarcode() == (hisItem.getItemBarcode()));
			assertTrue(persitanceItem.getStatus().equals(myItem.getStatus())
					|| persitanceItem.getStatus().equals(hisItem.getStatus()));
			assertTrue(persitanceItem.getTitle().getAuthor().get(0).getFirstName()
					.equals(myItem.getTitle().getAuthor().get(0).getFirstName())
					|| persitanceItem.getTitle().getAuthor().get(0).getFirstName()
							.equals(hisItem.getTitle().getAuthor().get(0).getFirstName()));
//			assertTrue(persitanceItem.getLibrarySystem().getBusinessaddress().getCity()
//					.equals(myItem.getLibrarySystem().getBusinessaddress().getCity())
//					|| persitanceItem.getLibrarySystem().getBusinessaddress().getCity()
//							.equals(hisItem.getLibrarySystem().getBusinessaddress().getCity()));
		}

	}

	/**
	 * This is a test to see if the Item gets persisted in a good way. We also see
	 * if we can look for the existence of the Item by their Title and that it
	 * returns true which means it exists
	 * 
	 */

	@Test
	public void testExistanceOfPersistance() {

		Address myAdress = new Address("51", "Parkekx", "Montreal", "H5H6H7", "Quebec", "Canada");
		addressRepository.save(myAdress);
		Calendar myCalendar = new Calendar();
		calendarRepository.save(myCalendar);
		LibrarySystem myLibrary = new LibrarySystem(myAdress, myCalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("J.K.", "Rowling");
		authorRepository.save(myAuthor);
		Title myTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
		titleRepository.save(myTitle);
		Item myItem = new Item(Status.Available, myTitle);
		itemRepository.save(myItem);
		myAdress = null;
		myCalendar = null;
		myLibrary = null;
		myAuthor = null;
		myTitle = null;

		boolean persistItem = itemRepository.existsByItemBarcode(myItem.getItemBarcode());
		assertNotNull(persistItem);
		assertTrue(persistItem);

	}

}
