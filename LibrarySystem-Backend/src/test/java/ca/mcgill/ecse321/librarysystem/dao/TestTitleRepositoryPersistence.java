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


import ca.mcgill.ecse321.librarysystem.dao.*;


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
		Title t1 = new Title("Harry Potter and The Philosopher's Stone", "October 31th, 2021", a1);
		
		this.authorRepository.save(a1);
		this.titleRepository.save(t1);

		t1 = null;
		
		List<Title> listTitlesByAuthor = titleRepository.findByAuthor(a1);
		
		for (Title t : listTitlesByAuthor) if (t.getAuthor().contains(a1)) t1 = t;
		
		assertNotNull(t1);
		assertEquals(a1,t1.getAuthor(0));
	}
}
