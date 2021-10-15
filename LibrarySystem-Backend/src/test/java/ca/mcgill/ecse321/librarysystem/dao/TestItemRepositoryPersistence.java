package ca.mcgill.ecse321.librarysystem.dao;

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
	//Fields
	
	
	@Autowired
	private ItemRepository itemRepository;
	//private AdressRepository itemRepository;
	private LibrarySystemRepository librarySystemRepository ;
	private CalendarRepository calendarRepository;
	
	
	
	@AfterEach
	public void clearDatabase() {
		itemRepository.deleteAll();
	}

	/*	@Test
	public void testPersistAndLoadItem() {
		Address myadress = new Address("33", 51, "holu", "25", "postal", "Quebec", "Canada");
		Calendar mycalendar = new Calendar("Jeanpail");
		LibrarySystem myLibrary = new LibrarySystem ("hello",myadress,mycalendar);
		Author myAuthor = new Author("JK123", "J.K.", "Rowling");
	    Title mytitle = new Title("Harry Potter and The Philosopher's Stone", "October 31th, 2021", myAuthor);
	    Item myItem= new Item (Status.Available,521, myLibrary, mytitle);	
	    itemRepository.save(myItem);
	    
		
		
		
	}*/
	

}
