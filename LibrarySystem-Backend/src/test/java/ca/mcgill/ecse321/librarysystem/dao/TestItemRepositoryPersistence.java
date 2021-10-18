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
		userRepository.deleteAll();
		librarySystemRepository.deleteAll();
		addressRepository.deleteAll();
		calendarRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadByBooking() {
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
		String str1 = "2015-03-31";
		String str2 ="2015-04-05";
		Date sdate= Date.valueOf(str1);
		Date edate = Date.valueOf(str2);
		Booking myBooking = new Booking (sdate,edate,BookingType.Reservation,myItem,myUser);
		bookingRepository.save(myBooking);
		itemRepository.save(myItem);
		
		Item retrieved = itemRepository.findItemByBooking(myBooking);
		assertNotNull(retrieved);
		assertEquals(retrieved.getItemBarcode(), myItem.getItemBarcode());
	}
	
	@Test
	public void testPersistAndLoadOfAnArchive() {
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
		Archive myArchive = new Archive(Status.Available, myLibrary, mytitle);
		archiveRepository.save(myArchive);
		addressRepository.save(myadress);
		myadress=null;
		mycalendar=null;
		myLibrary=null;
		myAuthor=null;
		mytitle=null;
		
		Archive PersistItem = (Archive) archiveRepository.findItemByItemBarcode(myArchive.getItemBarcode());
		assertNotNull (PersistItem);
		assertEquals(PersistItem.getItemBarcode(),(myArchive.getItemBarcode()));
		assertEquals(PersistItem.getTitle().getName(),(myArchive.getTitle().getName()));
		assertEquals(PersistItem.getTitle().getAuthor().get(0).getFirstName(),(myArchive.getTitle().getAuthor().get(0).getFirstName()));
		assertEquals(PersistItem.getLibrarySystem().getBusinessaddress().getCity(),(myArchive.getLibrarySystem().getBusinessaddress().getCity()));
	}
	
	@Test
	public void testPersistAndLoadOfABook() {
		Address myadress = new Address("51","Parkekx","Montreal","H5H6H7","Quebec","Canada");
		addressRepository.save(myadress);
		Calendar mycalendar = new Calendar();
		calendarRepository.save(mycalendar);
		LibrarySystem myLibrary = new LibrarySystem(myadress, mycalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("John", "Times");
		authorRepository.save(myAuthor);
		Title mytitle = new Title("The Doe Movie", "October 31th, 2021", myAuthor);
		titleRepository.save(mytitle);
		Book myBook = new Book(Status.Available, myLibrary, mytitle, "123-456-789", "200");
		bookRepository.save(myBook);
		addressRepository.save(myadress);
		
		myadress=null;
		mycalendar=null;
		myLibrary=null;
		myAuthor=null;
		mytitle=null;
		
		Book PersistItem = (Book) bookRepository.findItemByItemBarcode(myBook.getItemBarcode());
		assertNotNull (PersistItem);
		assertEquals(PersistItem.getItemBarcode(),(myBook.getItemBarcode()));
		assertEquals(PersistItem.getTitle().getName(),(myBook.getTitle().getName()));
		assertEquals(PersistItem.getTitle().getAuthor().get(0).getFirstName(),(myBook.getTitle().getAuthor().get(0).getFirstName()));
		assertEquals(PersistItem.getLibrarySystem().getBusinessaddress().getCity(),(myBook.getLibrarySystem().getBusinessaddress().getCity()));
	}
	
	@Test
	public void testPersistAndLoadOfAMovie() {
		Address myadress = new Address("51","Parkekx","Montreal","H5H6H7","Quebec","Canada");
		addressRepository.save(myadress);
		Calendar mycalendar = new Calendar();
		calendarRepository.save(mycalendar);
		LibrarySystem myLibrary = new LibrarySystem(myadress, mycalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("John", "Doe");
		authorRepository.save(myAuthor);
		Title mytitle = new Title("Newspaper Life", "October 31th, 2021", myAuthor);
		titleRepository.save(mytitle);
		Movie myMovie = new Movie(Status.Available, myLibrary, mytitle, 200);
		movieRepository.save(myMovie);
		addressRepository.save(myadress);
		
		myadress=null;
		mycalendar=null;
		myLibrary=null;
		myAuthor=null;
		mytitle=null;
		
		Movie PersistItem = (Movie) movieRepository.findItemByItemBarcode(myMovie.getItemBarcode());
		assertNotNull (PersistItem);
		assertEquals(PersistItem.getItemBarcode(),(myMovie.getItemBarcode()));
		assertEquals(PersistItem.getTitle().getName(),(myMovie.getTitle().getName()));
		assertEquals(PersistItem.getTitle().getAuthor().get(0).getFirstName(),(myMovie.getTitle().getAuthor().get(0).getFirstName()));
		assertEquals(PersistItem.getLibrarySystem().getBusinessaddress().getCity(),(myMovie.getLibrarySystem().getBusinessaddress().getCity()));
	}
	
	@Test
	public void testPersistAndLoadOfAMusicAlbum() {
		Address myadress = new Address("51","Parkekx","Montreal","H5H6H7","Quebec","Canada");
		addressRepository.save(myadress);
		Calendar mycalendar = new Calendar();
		calendarRepository.save(mycalendar);
		LibrarySystem myLibrary = new LibrarySystem(myadress, mycalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("Justin", "Bieber");
		authorRepository.save(myAuthor);
		Title mytitle = new Title("Classics", "October 31th, 2021", myAuthor);
		titleRepository.save(mytitle);
		MusicAlbum myMusicAlbum = new MusicAlbum(Status.Available, myLibrary, mytitle, 60);
		musicAlbumRepository.save(myMusicAlbum);
		addressRepository.save(myadress);
		
		myadress=null;
		mycalendar=null;
		myLibrary=null;
		myAuthor=null;
		mytitle=null;
		
		MusicAlbum PersistItem = (MusicAlbum) musicAlbumRepository.findItemByItemBarcode(myMusicAlbum.getItemBarcode());
		assertNotNull (PersistItem);
		assertEquals(PersistItem.getItemBarcode(),(myMusicAlbum.getItemBarcode()));
		assertEquals(PersistItem.getTitle().getName(),(myMusicAlbum.getTitle().getName()));
		assertEquals(PersistItem.getTitle().getAuthor().get(0).getFirstName(),(myMusicAlbum.getTitle().getAuthor().get(0).getFirstName()));
		assertEquals(PersistItem.getLibrarySystem().getBusinessaddress().getCity(),(myMusicAlbum.getLibrarySystem().getBusinessaddress().getCity()));
	}
	
	@Test
	public void testPersistAndLoadOfANewspaper() {
		Address myadress = new Address("51","Parkekx","Montreal","H5H6H7","Quebec","Canada");
		addressRepository.save(myadress);
		Calendar mycalendar = new Calendar();
		calendarRepository.save(mycalendar);
		LibrarySystem myLibrary = new LibrarySystem(myadress, mycalendar);
		librarySystemRepository.save(myLibrary);
		Author myAuthor = new Author("NY", "Times");
		authorRepository.save(myAuthor);
		Title mytitle = new Title("Newspaper Story", "October 31th, 2021", myAuthor);
		titleRepository.save(mytitle);
		Newspaper myNewspaper = new Newspaper(Status.Available, myLibrary, mytitle);
		newspaperRepository.save(myNewspaper);
		addressRepository.save(myadress);
		
		myadress=null;
		mycalendar=null;
		myLibrary=null;
		myAuthor=null;
		mytitle=null;
		
		Newspaper PersistItem = (Newspaper) newspaperRepository.findItemByItemBarcode(myNewspaper.getItemBarcode());
		assertNotNull (PersistItem);
		assertEquals(PersistItem.getItemBarcode(),(myNewspaper.getItemBarcode()));
		assertEquals(PersistItem.getTitle().getName(),(myNewspaper.getTitle().getName()));
		assertEquals(PersistItem.getTitle().getAuthor().get(0).getFirstName(),(myNewspaper.getTitle().getAuthor().get(0).getFirstName()));
		assertEquals(PersistItem.getLibrarySystem().getBusinessaddress().getCity(),(myNewspaper.getLibrarySystem().getBusinessaddress().getCity()));
	}

	@Test
	public void testPersistAndLoadItemByBarCode() {
		
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
		addressRepository.save(myadress);
		myadress=null;
		mycalendar=null;
		myLibrary=null;
		myAuthor=null;
		mytitle=null;
		
		Item PersistItem = itemRepository.findItemByItemBarcode(myItem.getItemBarcode());
		assertNotNull (PersistItem);
		assertEquals(PersistItem.getItemBarcode(),(myItem.getItemBarcode()));
		assertEquals(PersistItem.getTitle().getName(),(myItem.getTitle().getName()));
		assertEquals(PersistItem.getTitle().getAuthor().get(0).getFirstName(),(myItem.getTitle().getAuthor().get(0).getFirstName()));
		assertEquals(PersistItem.getLibrarySystem().getBusinessaddress().getCity(),(myItem.getLibrarySystem().getBusinessaddress().getCity()));
		
			
	}
	
	@Test
	public void testPersistAndLoadItemByStatus() {
		
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
		Item hisItem = new Item(Status.Borrowed, myLibrary, mytitle);
		itemRepository.save(hisItem);
		addressRepository.save(myadress);
		myadress=null;
		mycalendar=null;
		myLibrary=null;
		myAuthor=null;
		mytitle=null;
		
		List<Item> PersistItem = itemRepository.findItemByStatus(Status.Available);
		assertNotNull (PersistItem);
		for (Item PersitanceItem : PersistItem){
			assertEquals(PersitanceItem.getItemBarcode(),(myItem.getItemBarcode()));
			assertEquals(PersitanceItem.getStatus(),(myItem.getStatus()));
			assertEquals(PersitanceItem.getTitle().getAuthor().get(0).getFirstName(),(myItem.getTitle().getAuthor().get(0).getFirstName()));
			assertEquals(PersitanceItem.getLibrarySystem().getBusinessaddress().getCity(),(myItem.getLibrarySystem().getBusinessaddress().getCity()));
		}
		
		
	}
	
	@Test
	public void testPersistAndLoadItemByLibrarySystem() {
		
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
		Item hisItem = new Item(Status.Borrowed, myLibrary, mytitle);
		itemRepository.save(hisItem);
		addressRepository.save(myadress);
		myadress=null;
		mycalendar=null;
		//myLibrary=null;
		myAuthor=null;
		mytitle=null;
		
		List<Item> PersistItem = itemRepository.findItemByLibrarySystem(myLibrary);
		assertNotNull (PersistItem);
		for (Item PersitanceItem : PersistItem){
			assertTrue(PersitanceItem.getItemBarcode().equals(myItem.getItemBarcode()) || PersitanceItem.getItemBarcode().equals(hisItem.getItemBarcode()));
			assertTrue(PersitanceItem.getStatus().equals(myItem.getStatus()) || PersitanceItem.getStatus().equals(hisItem.getStatus()));
			assertTrue(PersitanceItem.getTitle().getAuthor().get(0).getFirstName().equals(myItem.getTitle().getAuthor().get(0).getFirstName()) ||PersitanceItem.getTitle().getAuthor().get(0).getFirstName().equals(hisItem.getTitle().getAuthor().get(0).getFirstName()) );
			assertTrue(PersitanceItem.getLibrarySystem().getBusinessaddress().getCity().equals(myItem.getLibrarySystem().getBusinessaddress().getCity()) ||PersitanceItem.getLibrarySystem().getBusinessaddress().getCity().equals(hisItem.getLibrarySystem().getBusinessaddress().getCity()) );
		}
		
		
		
		
	}
	
	@Test
	public void testPersistAndLoadItemByTitle() {
		
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
		Item hisItem = new Item(Status.Borrowed, myLibrary, mytitle);
		itemRepository.save(hisItem);
		addressRepository.save(myadress);
		myadress=null;
		mycalendar=null;
		myLibrary=null;
		myAuthor=null;
		//mytitle=null;
		
		List<Item> PersistItem = itemRepository.findItemByTitle(mytitle);
		assertNotNull (PersistItem);
		
		for (Item PersitanceItem : PersistItem){
			assertTrue(PersitanceItem.getItemBarcode().equals(myItem.getItemBarcode()) || PersitanceItem.getItemBarcode().equals(hisItem.getItemBarcode()));
			assertTrue(PersitanceItem.getStatus().equals(myItem.getStatus()) || PersitanceItem.getStatus().equals(hisItem.getStatus()));
			assertTrue(PersitanceItem.getTitle().getAuthor().get(0).getFirstName().equals(myItem.getTitle().getAuthor().get(0).getFirstName()) ||PersitanceItem.getTitle().getAuthor().get(0).getFirstName().equals(hisItem.getTitle().getAuthor().get(0).getFirstName()) );
			assertTrue(PersitanceItem.getLibrarySystem().getBusinessaddress().getCity().equals(myItem.getLibrarySystem().getBusinessaddress().getCity()) ||PersitanceItem.getLibrarySystem().getBusinessaddress().getCity().equals(hisItem.getLibrarySystem().getBusinessaddress().getCity()) );
		}
		
		
	
	}
	
	@Test
	
	public void testExistanceOfPersistance() {
		
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
		myadress=null;
		mycalendar=null;
		myLibrary=null;
		myAuthor=null;
		mytitle=null;
		
		boolean PersistItem = itemRepository.existsByItemBarcode(myItem.getItemBarcode());
		assertNotNull (PersistItem);
		assertTrue (PersistItem);
			
	}
	
	

}
