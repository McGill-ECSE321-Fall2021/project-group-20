package ca.mcgill.ecse321.librarysystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Title;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "author_data", path = "author_data")
public interface AuthorRepository extends CrudRepository<Author, String>{
	Author findByAuthorID(String authorID);
	List<Author> findByFirstName(String firstName);
	List<Author> findByLastName(String lastName);
	List<Author> findByFirstNameAndLastName(String firstName,String lastName);
	List<Author> findByTitlesIn(List<Title> titles);
	boolean existsByFirstName(String firstName);
	boolean existsByLastName(String lastName);
	boolean existsByFirstNameAndLastName(String firstName, String lastName);
	boolean existsByAuthorID(String authorID);
} 