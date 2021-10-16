package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
		librarySystemRepository.deleteAll();
		addressRepository.deleteAll();
		calendarRepository.deleteAll();
		
		
		

	}

	@Test
	public void testPersistAndLoadItem() {
		
		Address myadress = new Address(51,"Parkekx","Montreal","H5H6H7","Quebec","Canada");
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
		
		Item PersistItem = itemRepository.findItemByItemBarcode(myItem.getItemBarcode());
		assertNotNull (PersistItem);
		assertEquals(PersistItem.getTitle().getName(),(myItem.getTitle().getName()));
		assertEquals(PersistItem.getTitle().getAuthor().get(0).getFirstName(),(myItem.getTitle().getAuthor().get(0).getFirstName()));
		assertEquals(PersistItem.getLibrarySystem().getBusinessaddress().getCity(),(myItem.getLibrarySystem().getBusinessaddress().getCity()));
		
			
	}
	
	

}
