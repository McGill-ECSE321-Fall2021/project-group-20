package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestTitleRepositoryPersistence {
	
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
		//clearing database
		itemRepository.deleteAll();
		titleRepository.deleteAll();
		authorRepository.deleteAll();
		librarySystemRepository.deleteAll();
		addressRepository.deleteAll();
		calendarRepository.deleteAll();
	}
	
	/*
	 * Test method that checks whether a Title object can be correctly created, persisted, and correctly loaded from the database
	 * testing the findByTitleID (String titleID) CRUD Method
	 * 
	 * Attribute tested: titleID
	 */
	@Test
	public void testPersistAndLoadTitleByTitleID() {
		//create and save data
		Author author1 = new Author("J.K.", "Rowling");
		authorRepository.save(author1);
		Title title1 = new Title("Harry Potter and The Philosopher's Stone", "October 31th, 2021", author1);
		titleRepository.save(title1);
		
		//testing whether title is stored properly by finding the title by its id
		Title PersistedTitle = titleRepository.findByTitleID(title1.getTitleID());
		
		assertNotNull(PersistedTitle);
		assertEquals(PersistedTitle.getTitleID(), title1.getTitleID());
	}
	
	/*
	 * Test method that checks whether a Title object can be correctly created, persisted, and correctly loaded from the database
	 * testing the findByAuthor(Author authorID) CRUD Method
	 * 
	 * Attribute tested: authorID
	 */
	@Test
	public void testPersistAndLoadTitleByAuthorID() {
		
		//Create data
		Author author1 = new Author("J.K.", "Rowling");
		Author author2 = new Author("Dan", "Hosi");
		Author author3 = new Author("Harsh", "Patel");
		Title title1 = new Title("Harry Potter and The Philosopher's Stone", "October 31th, 2021", author1);
		title1.addAuthor(author2);
		title1.addAuthor(author3);
		
		//Save data
		authorRepository.save(author1);
		authorRepository.save(author2);
		authorRepository.save(author3);
		titleRepository.save(title1);
		
		//Test if title is stored by checking its name 
		title1 = null;
		
		List<Title> listTitlesByAuthorID = titleRepository.findByAuthor(author1);
		
		//testing whether title is stored properly by finding the title by its author id
		for (Title t : listTitlesByAuthorID) {
			assertEquals(author1.getAuthorID(), t.getAuthor(0).getAuthorID());
			assertEquals(author2.getAuthorID(), t.getAuthor(1).getAuthorID());
			assertEquals(author3.getAuthorID(), t.getAuthor(2).getAuthorID());
		}
	}
	
	/*
	 * Test method that checks whether a Title object can be correctly created, persisted, and correctly loaded from the database
	 * testing the findByAuthorIn(List<Author> authorID) CRUD Method
	 * 
	 * Attribute tested: authorID
	 */
	@Test 
	public void testPersistAndLoadTitleByAuthorIDs() {
		
		//create data
		Author author1 = new Author("J.K.", "Rowling");
		Author author2 = new Author("Dan", "Hosi");
		Author author3 = new Author("Harsh", "Patel");
		Title title1 = new Title("Harry Potter and The Philosopher's Stone", "October 31th, 2021", author1);
		title1.addAuthor(author2);
		title1.addAuthor(author3);
		
		//save data
		authorRepository.save(author1);
		authorRepository.save(author2);
		authorRepository.save(author3);
		titleRepository.save(title1);
		
		//create and save other data
		Author author4 = new Author("Walt", "Disney");
		Title title2 = new Title("Mickey Mouse","", author4);
		authorRepository.save(author4);
		titleRepository.save(title2);
		
		Author author5 = new Author("Harry", "Leib");
		Title title3 = new Title("Probability and statistics", "September 19th, 2021", author5);
		authorRepository.save(author5);
		titleRepository.save(title3);
		
		Author author6 = new Author("Ehsan", "Ahmed");
		Title title4 = new Title("Guide to How to Screw Up in Life","October 22nd, 2021", author6);
		title4.addAuthor(author2);
		title4.addAuthor(author3);
		authorRepository.save(author6);
		titleRepository.save(title4);
		
		//Test if title is stored by checking its name 
		title1 = null;
		title2 = null;
		title3 = null;
		title4 = null;
		
		
		//Test to see if data is stored by looking through a list of authors
		List<Author> list_authors = new ArrayList<>();
		list_authors.add(author2);
		list_authors.add(author4);
		
		List<Title> listTitlesByAuthorIDs =  titleRepository.findByAuthorIn(list_authors);
		
		
		assertEquals(author2.getAuthorID(), listTitlesByAuthorIDs.get(0).getAuthor(1).getAuthorID());
		assertEquals(author4.getAuthorID(), listTitlesByAuthorIDs.get(1).getAuthor(0).getAuthorID());
		assertEquals(author2.getAuthorID(), listTitlesByAuthorIDs.get(2).getAuthor(1).getAuthorID());		
	}
	
	/*
	 * Test method that checks whether a Title object can be correctly created, persisted, and correctly loaded from the database
	 * testing the findByItem(Item itemBarcode) CRUD Method
	 * 
	 * Attribute tested: itemBarcode
	 */
	@Test 
	public void testPersistAndLoadTitleByItemBarCode() {	
		
		//create and save data
		Address myadress = new Address("13","Elmstreet","Montreal","HeLL666","Quebec","Canada");
		addressRepository.save(myadress);
		Calendar calendar1 = new Calendar();
		calendarRepository.save(calendar1);
		LibrarySystem library1 = new LibrarySystem(myadress, calendar1);
		librarySystemRepository.save(library1);
		Author author1 = new Author("J.K.", "Rowling");
		authorRepository.save(author1);
		Title title1 = new Title("Hell Dragon King", "October 31th, 1999", author1);
		titleRepository.save(title1);
		Item myItem = new Item(Status.Available, library1, title1);
		itemRepository.save(myItem);
		
		//Test if title is stored by checking its name 
		myadress=null; 
		calendar1=null;
		library1=null;
		String id = author1.getAuthorID();
		author1=null;
		title1=null;
		
		//Testing if data is stored successfully with respect to items
		
		Title t = titleRepository.findByItem(myItem);
		
		assertEquals(id, t.getAuthor(0).getAuthorID());
	}
	
	/*
	 * Test method that checks whether a Title object can be correctly created, persisted, and correctly loaded from the database
	 * testing the findByItemIn(List<Item> itemBarcode) CRUD Method
	 * 
	 * Attribute tested: itemBarcode
	 */
	@Test 
	public void testPersistAndLoadTitleByItemBarCodes() {
		
		//create and save data
		Address address1 = new Address("3","YellowBirckRoad","Montreal","Oz","Quebec","Canada");
		addressRepository.save(address1);
		
		Calendar calendar1 = new Calendar();
		calendarRepository.save(calendar1);
		
		LibrarySystem library1 = new LibrarySystem(address1, calendar1);
		librarySystemRepository.save(library1);
		
		Author author1 = new Author("J.K.", "Rowling");
		authorRepository.save(author1);
		
		Author author2 = new Author("Ehsan", "Ahmed");
		authorRepository.save(author2);
		
		Title title1 = new Title("Harry Potter", "October 22nd, 1999", author1);
		titleRepository.save(title1);
		
		Title title2 = new Title("Boy Wonder", "October 22nd, 2000", author2);
		titleRepository.save(title2);
		
		Item item1 = new Item(Status.Available, library1, title1);
		itemRepository.save(item1);
		
		Item item2 = new Item(Status.Available, library1, title1);
		itemRepository.save(item2);
		
		Item item3 = new Item(Status.Available, library1, title2);
		itemRepository.save(item3);
		
		//Test if title is stored by checking its name 
		address1=null; 
		calendar1=null;
		library1=null;
		author1=null;
		author2=null;
		title1=null;
		title2=null;
		
		//getting item bar codes
		String id1 = item1.getItemBarcode();
		String id2 = item2.getItemBarcode();
		String id3 = item3.getItemBarcode();
		
		List<Item> items = new ArrayList<>();
		items.add(item1);
		item1=null;
		items.add(item2);
		item2=null;
		items.add(item3);
		item3=null;
		
		//testing whether titles are stored in database properly through a list of items and their item bar codes
		List<Title> titles = titleRepository.findByItemIn(items);
		
		
		assertEquals(id1,titles.get(0).getItem(0).getItemBarcode());
		assertEquals(id2,titles.get(0).getItem(1).getItemBarcode());
		assertEquals(id3,titles.get(2).getItem(0).getItemBarcode());
		
	}
	
	/*
	 * Test method that checks whether a Title object can be correctly created, persisted, and correctly loaded from the database
	 * testing the findByName(String name) CRUD Method
	 * 
	 * Attribute tested: name
	 */
	@Test 
	public void testPersistAndLoadTitleName() {
		//create data
		Author author1 = new Author("J.K.", "Rowling");
		Title title1 = new Title("Harry Potter and The Philosopher's Stone", "October 31th, 2021", author1);
		Author author2 = new Author("Dan", "Hosi");
		Author author3 = new Author("Harsh", "Patel");
		
		//save data
		authorRepository.save(author1);
		authorRepository.save(author2);
		authorRepository.save(author3);
		titleRepository.save(title1);
		
		
		//preparing test
		String name = "Harry Potter and The Philosopher's Stone";
		title1 = null;
		//Test if title is stored by checking its name 
		List<Title> listTitlesByTitleName = titleRepository.findByName(name);
		assertEquals(name,listTitlesByTitleName.get(0).getName());
	}
	
	/*
	 * Test method that checks whether a Title object can be correctly created, persisted, and correctly loaded from the database
	 * testing the findByPubDate(String pubDate) CRUD Method
	 * 
	 * Attribute tested: pubDate
	 */
	@Test 
	public void testPersistAndLoadTitlePubDate() {
		//create data
		Author author1 = new Author("Ehsan", "Ahmed");
		Title title1 = new Title("Guide to How to Screw Up in Life","October 22nd, 2021", author1);
		//save data
		authorRepository.save(author1);		
		titleRepository.save(title1);
		
		//preparing test
		String pubDate = "October 22nd, 2021";
		
		//Test if title is stored by checking its published date
		title1 = null;
		List<Title> listTitlesByPubDate = titleRepository.findByPubDate(pubDate);
		assertEquals(pubDate,listTitlesByPubDate.get(0).getPubDate());
	}
	
	/*
	 * Test method that checks whether a Title object can be correctly created, persisted, and correctly loaded from the database
	 * testing the findByNameAndPubDate(String name, String pubDate) CRUD Method
	 * 
	 * Attribute tested: name and pubDate
	 */
	@Test 
	public void testPersistAndLoadTitleNameAndPubDate() {
		//create and save data
        Author author1 = new Author("Ehsan", "Ahmed");
        authorRepository.save(author1);
        Title title1 = new Title("Guide to How to Screw Up in Life","October 22nd, 2021", author1);
        titleRepository.save(title1);
        Title title2 = new Title("Guide to How to Screw Up in Life","October 31th, 2021", author1);
        titleRepository.save(title2);
        
      //preparing test
        String name = "Guide to How to Screw Up in Life";
        String pubDate = "October 22nd, 2021"; 

        title1 = null; 
        title2 = null;
        
        //Test if data is stored correctly by checking its name and publish date
        Title titleNamePub = titleRepository.findByNameAndPubDate(name, pubDate);

        assertEquals(name, titleNamePub.getName());
        assertEquals(pubDate, titleNamePub.getPubDate());
	}
	
	/*
	 * Test method that checks whether an Author object can be correctly created, persisted, and determine
	 * if it exists in the database by
	 * testing the existsByTitleID(String titleID) CRUD Method
	 * 
	 * Attribute tested: titleID
	 */
	@Test
	public void testPersistAndLoadAuthorAndSeeExistsByTitleID() {
		//create and save data
		Author author1 = new Author("Ehsan", "Ahmed");
        authorRepository.save(author1);
        Title title1 = new Title("Guide to How to Screw Up in Life","October 22nd, 2021", author1);
        titleRepository.save(title1);
		//preparing test
		String id = title1.getTitleID();
		author1=null;
		title1=null;
		//check if author exists in the database by titleID
		boolean checking = titleRepository.existsByTitleID(id);
		assertTrue(checking);
	}
	
	/*
	 * Test method that checks whether an Author object can be correctly created, persisted, and determine
	 * if it exists in the database by
	 * testing the existsByAuthorID(String authorID) CRUD Method
	 * 
	 * Attribute tested: name and pubDate
	 */
	@Test
	public void testPersistAndLoadTitleAndSeeExistsByTitleNameAndPubDate() {	
		//create and save data
		Author author1 = new Author("Ehsan", "Ahmed");
        authorRepository.save(author1);
        Title title1 = new Title("Guide to How to Screw Up in Life","October 22nd, 2021", author1);
        titleRepository.save(title1);
		//preparing test
		String name = title1.getName();
		String pubDate = title1.getPubDate();
		author1=null;
		title1=null;
		//check if author exists in the database by name and pubDate
		boolean checking = titleRepository.existsByNameAndPubDate(name, pubDate);
		assertTrue(checking);
	}
	
	/*
	 * Test method that checks whether an Author object can be correctly created, persisted, and determine
	 * if it exists in the database by
	 * testing the existsByFirstNameAndLastName(String firstName, String lastName) CRUD Method
	 * 
	 * Attribute tested: itemBarcode
	 */
	@Test
	public void testPersistAndLoadTitleAndSeeExistsByItem() {	
		//create and save data
		Address myadress = new Address("13","Elmstreet","Montreal","HeLL666","Quebec","Canada");
		addressRepository.save(myadress);
		Calendar calendar1 = new Calendar();
		calendarRepository.save(calendar1);
		LibrarySystem library1 = new LibrarySystem(myadress, calendar1);
		librarySystemRepository.save(library1);
		Author author1 = new Author("J.K.", "Rowling");
		authorRepository.save(author1);
		Title title1 = new Title("Hell Dragon King", "October 31th, 1999", author1);
		titleRepository.save(title1);
		Item myItem = new Item(Status.Available, library1, title1);
		itemRepository.save(myItem);
		
		//Test if title is stored by checking its name 
		myadress=null; 
		calendar1=null;
		library1=null;
		author1=null;
		title1=null;

		//check if author exists in the database by first and itemBarcode
		boolean checking = titleRepository.existsByItem(myItem);
		assertTrue(checking);
	}
	

}
