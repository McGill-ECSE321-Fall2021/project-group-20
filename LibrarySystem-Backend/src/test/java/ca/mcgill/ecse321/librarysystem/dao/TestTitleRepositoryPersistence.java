package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		itemRepository.deleteAll();
		titleRepository.deleteAll();
		authorRepository.deleteAll();
		librarySystemRepository.deleteAll();
		addressRepository.deleteAll();
		calendarRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadTitleByAuthorID() {

		Author a1 = new Author("J.K.", "Rowling");
		Author a2 = new Author("Dan", "Hosi");
		Author a3 = new Author("Harsh", "Patel");
		Title t1 = new Title("Harry Potter and The Philosopher's Stone", "October 31th, 2021", a1);
		t1.addAuthor(a2);
		t1.addAuthor(a3);
		
		authorRepository.save(a1);
		authorRepository.save(a2);
		authorRepository.save(a3);
		titleRepository.save(t1);

		t1 = null;
		
		List<Title> listTitlesByAuthorID = titleRepository.findByAuthor(a1);
		
		for (Title t : listTitlesByAuthorID) {
			assertEquals(a1.getAuthorID(), t.getAuthor(0).getAuthorID());
			assertEquals(a2.getAuthorID(), t.getAuthor(1).getAuthorID());
			assertEquals(a3.getAuthorID(), t.getAuthor(2).getAuthorID());
		}
	}
	
	@Test 
	public void testPersistAndLoadTitleByAuthorIDs() {
		
		Author a1 = new Author("J.K.", "Rowling");
		Author a2 = new Author("Dan", "Hosi");
		Author a3 = new Author("Harsh", "Patel");
		Title t1 = new Title("Harry Potter and The Philosopher's Stone", "October 31th, 2021", a1);
		t1.addAuthor(a2);
		t1.addAuthor(a3);
		authorRepository.save(a1);
		authorRepository.save(a2);
		authorRepository.save(a3);
		titleRepository.save(t1);
		
		Author a4 = new Author("Walt", "Disney");
		Title t2 = new Title("Mickey Mouse","", a4);
		authorRepository.save(a4);
		titleRepository.save(t2);
		
		Author a5 = new Author("Harry", "Leib");
		Title t3 = new Title("Probability and statistics", "September 19th, 2021", a5);
		authorRepository.save(a5);
		titleRepository.save(t3);
		
		Author a6 = new Author("Ehsan", "Ahmed");
		Title t4 = new Title("Guide to How to Screw Up in Life","October 22nd, 2021", a6);
		t4.addAuthor(a2);
		t4.addAuthor(a3);
		authorRepository.save(a6);
		titleRepository.save(t4);
		
		t1 = null;
		t2 = null;
		t3 = null;
		t4 = null;
		
		List<Author> list_authors = new ArrayList<Author>(); 
		list_authors.add(a2);
		list_authors.add(a4);
		
		List<Title> listTitlesByAuthorIDs =  titleRepository.findByAuthorIn(list_authors);
		
		
		assertEquals(a2.getAuthorID(), listTitlesByAuthorIDs.get(0).getAuthor(1).getAuthorID());
		assertEquals(a4.getAuthorID(), listTitlesByAuthorIDs.get(1).getAuthor(0).getAuthorID());
		assertEquals(a2.getAuthorID(), listTitlesByAuthorIDs.get(2).getAuthor(1).getAuthorID());		
	}
	
	@Test 
	public void testPersistAndLoadTitleByItemBarCode() {	
		Address myadress = new Address(13,"Elmstreet","Montreal","HeLL666","Quebec","Canada");
		addressRepository.save(myadress);
		Calendar c1 = new Calendar();
		calendarRepository.save(c1);
		LibrarySystem l1 = new LibrarySystem(myadress, c1);
		librarySystemRepository.save(l1);
		Author a1 = new Author("J.K.", "Rowling");
		authorRepository.save(a1);
		Title t1 = new Title("Hell Dragon King", "October 31th, 1999", a1);
		titleRepository.save(t1);
		Item myItem = new Item(Status.Available, l1, t1);
		itemRepository.save(myItem);
		myadress=null; 
		c1=null;
		l1=null;
		String id = a1.getAuthorID();
		a1=null;
		t1=null;
		
		
		List<Title> titles = titleRepository.findByItem(myItem);
		
		assertEquals(id, titles.get(0).getAuthor(0).getAuthorID());
	}
	
	@Test 
	public void testPersistAndLoadTitleByItemBarCodes() {

		Address ad1 = new Address(3,"YellowBirckRoad","Montreal","Oz","Quebec","Canada");
		addressRepository.save(ad1);
		
		Calendar c1 = new Calendar();
		calendarRepository.save(c1);
		
		LibrarySystem l1 = new LibrarySystem(ad1, c1);
		librarySystemRepository.save(l1);
		
		Author a1 = new Author("J.K.", "Rowling");
		authorRepository.save(a1);
		
		Author a2 = new Author("Ehsan", "Ahmed");
		authorRepository.save(a2);
		
		Title t1 = new Title("Harry Potter", "October 22nd, 1999", a1);
		titleRepository.save(t1);
		
		Title t2 = new Title("Boy Wonder", "October 22nd, 2000", a2);
		titleRepository.save(t2);
		
		Item i1 = new Item(Status.Available, l1, t1);
		itemRepository.save(i1);
		
		Item i2 = new Item(Status.Available, l1, t1);
		itemRepository.save(i2);
		
		Item i3 = new Item(Status.Available, l1, t2);
		itemRepository.save(i3);
		
		ad1=null; 
		c1=null;
		l1=null;
		a1=null;
		a2=null;
		t1=null;
		t2=null;
		int id1 = i1.getItemBarcode();
		int id2 = i2.getItemBarcode();
		int id3 = i3.getItemBarcode();
		
		List<Item> items = new ArrayList<Item>();
		items.add(i1);
		i1=null;
		items.add(i2);
		i2=null;
		items.add(i3);
		i3=null;
		List<Title> titles = titleRepository.findByItemIn(items);
		
		
		assertEquals(id1,titles.get(0).getItem(0).getItemBarcode());
		assertEquals(id2,titles.get(0).getItem(1).getItemBarcode());
		assertEquals(id3,titles.get(2).getItem(0).getItemBarcode());
		
	}
	
	@Test 
	public void testPersistAndLoadTitleName() {
		Author a1 = new Author("J.K.", "Rowling");
		Title t1 = new Title("Harry Potter and The Philosopher's Stone", "October 31th, 2021", a1);
		Author a2 = new Author("Dan", "Hosi");
		Author a3 = new Author("Harsh", "Patel");
		authorRepository.save(a1);
		authorRepository.save(a2);
		authorRepository.save(a3);
		titleRepository.save(t1);
		
		String name = "Harry Potter and The Philosopher's Stone";
		t1 = null;
		List<Title> listTitlesByTitleName = titleRepository.findByName(name);
		assertEquals(name,listTitlesByTitleName.get(0).getName());
	}
	
	@Test 
	public void testPersistAndLoadTitlePubDate() {
		
		Author a1 = new Author("Ehsan", "Ahmed");
		Title t1 = new Title("Guide to How to Screw Up in Life","October 22nd, 2021", a1);
		authorRepository.save(a1);		
		titleRepository.save(t1);
		String pubDate = "October 22nd, 2021";

		t1 = null;
		List<Title> listTitlesByPubDate = titleRepository.findByPubDate(pubDate);
		assertEquals(pubDate,listTitlesByPubDate.get(0).getPubDate());
		 
	}
	
	@Test 
	public void testPersistAndLoadTitleNameAndPubDate() {
        Author a1 = new Author("Ehsan", "Ahmed");
        authorRepository.save(a1);
        Title t1 = new Title("Guide to How to Screw Up in Life","October 22nd, 2021", a1);
        titleRepository.save(t1);
        Title t2 = new Title("Guide to How to Screw Up in Life 2","October 31th, 2021", a1);
        titleRepository.save(t2);

        String name = "Guide to How to Screw Up in Life";
        String pubDate = "October 22nd, 2021"; 

        t1 = null; 
        t2 = null;

        List<Title> listTitlesByTitleNameAndPubDate = titleRepository.findByNameAndPubDate(name, pubDate);

        assertEquals(name,listTitlesByTitleNameAndPubDate.get(0).getName());
        assertEquals(pubDate,listTitlesByTitleNameAndPubDate.get(0).getPubDate());
	}
}
