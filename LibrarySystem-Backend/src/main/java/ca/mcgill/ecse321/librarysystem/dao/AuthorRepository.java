package ca.mcgill.ecse321.librarysystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Title;

public interface AuthorRepository extends CrudRepository<Author, String>{
	List<Author> findByAuthorID(String authorID);
	List<Author> findByFirstName(String firstName);
	List<Author> findByLastName(String lastName);
	List<Author> findByFirstNameAndLastName(String firstName,String lastName);
	List<Author> findByTitlesIn(List<Title> titles);
} 