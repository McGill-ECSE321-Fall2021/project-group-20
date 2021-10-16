package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Title;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestAuthorRepositoryPersistence {
	
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
	public void testPersistAndLoadAuthorByAuthorID() {
		
		Author a1 = new Author("J.K.", "Rowling");
		Title t1 = new Title("Harry Potter and The Philosopher's Stone", "October 31th, 2021", a1);
		Author a3 = new Author("Harsh", "Patel");
		authorRepository.save(a1);
		authorRepository.save(a3);
		titleRepository.save(t1);
		
		String id = a1.getAuthorID();

		a1 = null;
		a3 = null;
		t1=null;
		
		List<Author> listAuthors = authorRepository.findByAuthorID(id);
		assertEquals(id, listAuthors.get(0).getAuthorID());
		
	}
	
	@Test
	public void testPersistAndLoadAuthorByFirstName() {
		Author a1 = new Author("Ehsan", "Ahmed");
		Title t1 = new Title("Merlin's beard", "October 22nd, 2021", a1);
		Author a2 = new Author("Abdel", "Majid");
		authorRepository.save(a1);
		authorRepository.save(a2);
		titleRepository.save(t1);
		
		String firstName = "Ehsan";
		
		String fakeFirstName = "Ferrie";

		a1 = null;
		a2 = null;
		t1 = null;
		
		List<Author> listAuthors = authorRepository.findByFirstName(firstName);
		assertEquals(firstName, listAuthors.get(0).getFirstName());
		
		listAuthors = authorRepository.findByFirstName(fakeFirstName);
		assertEquals(0, listAuthors.size());
	}
	 
	@Test
	public void testPersistAndLoadAuthorByLastName() {
		Author a1 = new Author("Ehsan", "Ahmed");
		Title t1 = new Title("Merlin's beard", "October 22nd, 2021", a1);
		Author a2 = new Author("Abdel", "Majid");
		authorRepository.save(a1);
		authorRepository.save(a2);
		titleRepository.save(t1);
		
		String lastName = "Ahmed";
		
		String fakeLastName = "Lover";

		a1 = null;
		a2 = null;
		t1 = null;
		
		List<Author> listAuthors = authorRepository.findByLastName(lastName);
		assertEquals(lastName, listAuthors.get(0).getLastName());
		
		listAuthors = authorRepository.findByFirstName(fakeLastName);
		assertEquals(0, listAuthors.size());
	}
	
	@Test
	public void testPersistAndLoadAuthorByFirstNameAndLastName() {
		Author a1 = new Author("Ehsan", "Ahmed");
		Title t1 = new Title("Black Chaos", "October 22nd, 2021", a1);
		Author a2 = new Author("Dan", "Hosi");
		authorRepository.save(a1);
		authorRepository.save(a2);
		titleRepository.save(t1);
		
		String firstName = "Ehsan";
		String fakeFirstName = "Ferrie";
		String lastName = "Ahmed";
		String fakeLastName = "Lover";

		a1 = null;
		a2 = null;
		t1 = null;
		
		List<Author> listAuthors = authorRepository.findByFirstNameAndLastName(firstName, lastName);
		assertEquals(firstName, listAuthors.get(0).getFirstName());
		assertEquals(lastName, listAuthors.get(0).getLastName());
		
		listAuthors = authorRepository.findByFirstNameAndLastName(fakeFirstName, fakeLastName);
		assertEquals(0, listAuthors.size());
	}
	
	@Test
	public void testPersistAndLoadAuthorByTitles() {
		//List<Author> findByTitlesIn(List<Title> titles);
		Author a1 = new Author("Alexandru", "Bangala");
		authorRepository.save(a1);
		Title t1 = new Title("The Goat", "June 1rst, 2021", a1);
		titleRepository.save(t1);
		Title t2 = new Title("The Genius", "July 1rst, 2021", a1);
		titleRepository.save(t2);
		Author a2 = new Author("Harsh", "Patel");
		authorRepository.save(a2);
		Title t3 = new Title("The Crazy", "January 1rst, 2021", a2);
		titleRepository.save(t3);
		
		List<Title> titles = new ArrayList<Title>();
		
		titles.add(t1);
		titles.add(t3);
		List<Author> listAuthors = authorRepository.findByTitlesIn(titles);
		
		String firstNameFirstAuthor = "Alexandru";
		String lastNameFirstAuthor = "Bangala";
		
		String firstNameSecondAuthor = "Harsh";
		String lastNameSecondAuthor = "Patel";
		
		assertEquals(firstNameFirstAuthor,listAuthors.get(0).getFirstName());
		assertEquals(lastNameFirstAuthor,listAuthors.get(0).getLastName());
		assertEquals(firstNameSecondAuthor,listAuthors.get(1).getFirstName());
		assertEquals(lastNameSecondAuthor,listAuthors.get(1).getLastName());
	} 
	
	@Test
	public void testPersistAndLoadAuthorAndSeeExistsByFirstName() {
		Author a1 = new Author("Ehsan", "Ahmed");
		authorRepository.save(a1);
		String firstName = "Ehsan";
		boolean checking = authorRepository.existsByFirstName(firstName);
		assertTrue(checking);
	}
	 
	@Test
	public void testPersistAndLoadAuthorAndSeeExistsByLastName() {
		Author a1 = new Author("Ehsan", "Ahmed");
		authorRepository.save(a1);
		String lastName = "Ahmed";
		boolean checking = authorRepository.existsByLastName(lastName);
		assertTrue(checking);
	}
	
	@Test
	public void testPersistAndLoadAuthorAndSeeExistsByFirstNameAndLastName() {	
		Author a1 = new Author("Ehsan", "Ahmed");
		authorRepository.save(a1);
		String firstName = "Ehsan";
		String lastName = "Ahmed";
		boolean checking = authorRepository.existsByFirstNameAndLastName(firstName, lastName);
		assertTrue(checking);
	}

}
