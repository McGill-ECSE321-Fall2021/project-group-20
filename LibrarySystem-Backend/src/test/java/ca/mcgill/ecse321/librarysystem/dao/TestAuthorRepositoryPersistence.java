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
		//clear database
		titleRepository.deleteAll();
		authorRepository.deleteAll();
	}
	
	/*
	 * Test method that checks whether an Author object can be correctly created, persisted, and correctly loaded from the database
	 * testing the findByAuthorID(String authorID) CRUD Method
	 * 
	 * Attribute tested: authorID
	 */
	@Test
	public void testPersistAndLoadAuthorByAuthorID() {
		
		//create data
		Author author1 = new Author("J.K.", "Rowling");
		Title title1 = new Title("Harry Potter and The Philosopher's Stone", "October 31th, 2021", author1);
		Author author3 = new Author("Harsh", "Patel");
		//store data
		authorRepository.save(author1);
		authorRepository.save(author3);
		titleRepository.save(title1);
		//preparing test
		String id = author1.getAuthorID();

		title1=null;
		//test if author is stored properly by author id 
		Author authorOutput = authorRepository.findByAuthorID(id);
		assertEquals(id, authorOutput.getAuthorID());
		
	}
	
	/*
	 * Test method that checks whether an Author object can be correctly created, persisted, and correctly loaded from the database
	 * testing the findByFirstName(String firstName) CRUD Method
	 * 
	 * Attribute tested: firstName 
	 */
	@Test
	public void testPersistAndLoadAuthorByFirstName() {
		
		//create data
		Author author1 = new Author("Ehsan", "Ahmed");
		Title title1 = new Title("Merlin's beard", "October 22nd, 2021", author1);
		Author author2 = new Author("Abdel", "Majid");
		
		//save data
		authorRepository.save(author1);
		authorRepository.save(author2);
		titleRepository.save(title1);
		
		//preparing test
		String firstName = "Ehsan";
		
		String fakeFirstName = "Ferrie";

		
		//test if data is saved by checking the author's first name and see if fake name is in database
		List<Author> listAuthors = authorRepository.findByFirstName(firstName);
		assertEquals(firstName, listAuthors.get(0).getFirstName());
		
		listAuthors = authorRepository.findByFirstName(fakeFirstName);
		assertEquals(0, listAuthors.size());
		
		//delete data
		title1.delete();
	}
	
	/*
	 * Test method that checks whether an Author object can be correctly created, persisted, and correctly loaded from the database
	 * testing the findByLastName(String lastName) CRUD Method
	 * 
	 * Attribute tested: lastName 
	 */
	@Test
	public void testPersistAndLoadAuthorByLastName() {
		//create data
		Author author1 = new Author("Ehsan", "Ahmed");
		Title title1 = new Title("Merlin's beard", "October 22nd, 2021", author1);
		Author author2 = new Author("Abdel", "Majid");
		
		//save data
		authorRepository.save(author1);
		authorRepository.save(author2);
		titleRepository.save(title1);
		
		//preparing test
		String lastName = "Ahmed";
		
		String fakeLastName = "Lover";

		
		//test if data is saved by checking the author's last name and see if fake name is in database
		List<Author> listAuthors = authorRepository.findByLastName(lastName);
		assertEquals(lastName, listAuthors.get(0).getLastName());
		//test if data is saved properly by checking if fake name is not in database
		listAuthors = authorRepository.findByFirstName(fakeLastName);
		assertEquals(0, listAuthors.size());
		
		//delete data
		title1.delete();
	}
	
	/*
	 * Test method that checks whether an Author object can be correctly created, persisted, and correctly loaded from the database
	 * testing the findByFirstNameAndLastName(String firstName,String lastName) CRUD Method
	 * 
	 * Attribute tested: firstName and lastName
	 */
	@Test
	public void testPersistAndLoadAuthorByFirstNameAndLastName() {
		
		//create data
		Author author1 = new Author("Ehsan", "Ahmed");
		Title title1 = new Title("Black Chaos", "October 22nd, 2021", author1);
		Author author2 = new Author("Dan", "Hosi");
		
		//save data
		authorRepository.save(author1);
		authorRepository.save(author2);
		titleRepository.save(title1);
		
		//preparing test
		String firstName = "Ehsan";
		String fakeFirstName = "Ferrie";
		String lastName = "Ahmed";
		String fakeLastName = "Lover";
		
		//test if data is saved by checking the author's first and last name and see if fake names are in database
		List<Author> listAuthors = authorRepository.findByFirstNameAndLastName(firstName, lastName);
		assertEquals(firstName, listAuthors.get(0).getFirstName());
		assertEquals(lastName, listAuthors.get(0).getLastName());
		
		listAuthors = authorRepository.findByFirstNameAndLastName(fakeFirstName, fakeLastName);
		assertEquals(0, listAuthors.size());
		
		//delete data
		title1.delete();
	}
	
	/*
	 * Test method that checks whether an Author object can be correctly created, persisted, and correctly loaded from the database
	 * testing the findByTitlesIn(List<Title> titles) CRUD Method
	 * 
	 * Attribute tested: titles
	 */
	@Test
	public void testPersistAndLoadAuthorByTitles() {
		//create and save data;
		Author author1 = new Author("Alexandru", "Bangala");
		authorRepository.save(author1);
		Title title1 = new Title("The Goat", "June 1rst, 2021", author1);
		titleRepository.save(title1);
		Title title2 = new Title("The Genius", "July 1rst, 2021", author1);
		titleRepository.save(title2);
		Author author2 = new Author("Harsh", "Patel");
		authorRepository.save(author2);
		Title title3 = new Title("The Crazy", "January 1rst, 2021", author2);
		titleRepository.save(title3);
		
		//preparing test
		List<Title> titles = new ArrayList<>();
		
		titles.add(title1);
		titles.add(title3);
		
		//test if author(s) is/are stored from a list of titles
		List<Author> listAuthors = authorRepository.findByTitlesIn(titles);
		
		//preparing test 
		String firstNameFirstAuthor = "Alexandru";
		String lastNameFirstAuthor = "Bangala";
		
		String firstNameSecondAuthor = "Harsh";
		String lastNameSecondAuthor = "Patel";
		
		
		assertEquals(firstNameFirstAuthor,listAuthors.get(0).getFirstName());
		assertEquals(lastNameFirstAuthor,listAuthors.get(0).getLastName());
		assertEquals(firstNameSecondAuthor,listAuthors.get(1).getFirstName());
		assertEquals(lastNameSecondAuthor,listAuthors.get(1).getLastName());
		
		//delete title data
		title1.delete();
		title2.delete();
		title3.delete();
	} 
	
	/*
	 * Test method that checks whether an Author object can be correctly created, persisted, and determine
	 * if it exists in the database by
	 * testing the existsByFirstName(String firstName) CRUD Method
	 * 
	 * Attribute tested: firstName
	 */
	@Test
	public void testPersistAndLoadAuthorAndSeeExistsByFirstName() {
		
		//create and save data
		Author author1 = new Author("Ehsan", "Ahmed");
		authorRepository.save(author1);
		//preparing test
		String firstName = "Ehsan";
		//check if author exists in the database by first name
		boolean checking = authorRepository.existsByFirstName(firstName);
		assertTrue(checking);
	}
	
	
	/*
	 * Test method that checks whether an Author object can be correctly created, persisted, and determine
	 * if it exists in the database by
	 * testing the existsByLastName(String lastName) CRUD Method
	 * 
	 * Attribute tested: lastName
	 */
	@Test
	public void testPersistAndLoadAuthorAndSeeExistsByLastName() {
		//create and save data
		Author author1 = new Author("Ehsan", "Ahmed");
		authorRepository.save(author1);
		//preparing test
		String lastName = "Ahmed";
		//check if author exists in the database by last name
		boolean checking = authorRepository.existsByLastName(lastName);
		assertTrue(checking);
	}
	
	/*
	 * Test method that checks whether an Author object can be correctly created, persisted, and determine
	 * if it exists in the database by
	 * testing the existsByFirstNameAndLastName(String firstName, String lastName) CRUD Method
	 * 
	 * Attribute tested: firstName and lastName
	 */
	@Test
	public void testPersistAndLoadAuthorAndSeeExistsByFirstNameAndLastName() {	
		//create and save data
		Author author1 = new Author("Ehsan", "Ahmed");
		authorRepository.save(author1);
		//preparing test
		String firstName = "Ehsan";
		String lastName = "Ahmed";
		//check if author exists in the database by first and last name
		boolean checking = authorRepository.existsByFirstNameAndLastName(firstName, lastName);
		assertTrue(checking);
	}
	
	/*
	 * Test method that checks whether an Author object can be correctly created, persisted, and determine
	 * if it exists in the database by
	 * testing the existsByAuthorID(String authorID) CRUD Method
	 * 
	 * Attribute tested: authorID
	 */
	@Test
	public void testPersistAndLoadAuthorAndSeeExistsByAuthorID() {	
		//create and save data
		Author author1 = new Author("Ehsan", "Ahmed");
		authorRepository.save(author1);
		//preparing test
		String id = author1.getAuthorID();
		
		//check if author exists in the database by authorID
		boolean checking = authorRepository.existsByAuthorID(id);
		assertTrue(checking);
	}
	
	

}
