package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
	public void testPersistAndLoadTitle() {
		//Check if a title is stored in the repository 
		Author a1 = new Author("J.K.", "Rowling");
		Author a2 = new Author("Dan", "Hosi");
		Author a3 = new Author("Harsh", "Patel");
		Title t1 = new Title("Harry Potter and The Philosopher's Stone", "October 31th, 2021", a1);
		t1.addAuthor(a2);
		t1.addAuthor(a3);
		
		this.authorRepository.save(a1);
		this.authorRepository.save(a2);
		this.authorRepository.save(a3);
		this.titleRepository.save(t1);

		t1 = null;
		
		List<Title> listTitlesByAuthor = titleRepository.findByAuthor(a1);
		
		for (Title t : listTitlesByAuthor) {
			assertEquals(a1.getAuthorID(), t.getAuthor(0).getAuthorID());
			assertEquals(a2.getAuthorID(), t.getAuthor(1).getAuthorID());
			assertEquals(a3.getAuthorID(), t.getAuthor(2).getAuthorID());
			//if (t.getAuthor().get(0).getAuthorID().equals(a1.getAuthorID())) t1 = t;
		}
		
		//assertNotNull(t1);
		//assertEquals(a1.getAuthorID(),t1.getAuthor(0).getAuthorID());
	}
}
