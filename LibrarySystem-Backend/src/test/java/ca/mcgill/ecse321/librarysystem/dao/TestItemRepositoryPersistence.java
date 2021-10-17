package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	

	@AfterEach
	public void clearDatabase() {
		itemRepository.deleteAll();
		titleRepository.deleteAll();
		authorRepository.deleteAll();
		addressRepository.deleteAll();
		librarySystemRepository.deleteAll();
		calendarRepository.deleteAll();
		
		
		

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
		myadress=null;
		mycalendar=null;
		//myLibrary=null;
		myAuthor=null;
		mytitle=null;
		
		List<Item> PersistItem = itemRepository.findItemByLibrarySystem(myLibrary);
		assertNotNull (PersistItem);
		for (Item PersitanceItem : PersistItem){
			assertTrue(PersitanceItem.getItemBarcode()==(myItem.getItemBarcode()) || PersitanceItem.getItemBarcode()==(hisItem.getItemBarcode()));
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
		myadress=null;
		mycalendar=null;
		myLibrary=null;
		myAuthor=null;
		//mytitle=null;
		
		List<Item> PersistItem = itemRepository.findItemByTitle(mytitle);
		assertNotNull (PersistItem);
		
		for (Item PersitanceItem : PersistItem){
			assertTrue(PersitanceItem.getItemBarcode()==(myItem.getItemBarcode()) || PersitanceItem.getItemBarcode()==(hisItem.getItemBarcode()));
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
