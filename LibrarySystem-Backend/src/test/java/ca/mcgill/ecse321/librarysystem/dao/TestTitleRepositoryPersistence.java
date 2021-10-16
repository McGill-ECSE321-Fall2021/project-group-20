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

import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Title;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestTitleRepositoryPersistence {
	
	@Autowired
	private TitleRepository titleRepository;
	@Autowired
	private AuthorRepository authorRepository;
	
	@AfterEach
	public void clearDatabase() {
		titleRepository.deleteAll();
		authorRepository.deleteAll();
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
//		Author a1 = new Author("J.K.", "Rowling");
//		Title t1 = new Title("Harry Potter and The Philosopher's Stone", "October 31th, 2021", a1);
	}
	
	@Test 
	public void testPersistAndLoadTitleByItemBarCodes() {
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
		
	}
	
	@Test 
	public void testPersistAndLoadTitleNameAndPubDate() {
	}
}
