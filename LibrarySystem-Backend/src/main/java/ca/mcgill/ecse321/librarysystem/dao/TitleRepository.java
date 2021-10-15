package ca.mcgill.ecse321.librarysystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Title;

public interface TitleRepository extends CrudRepository<Title, String>{
	List<Title> findByAuthor(Author author);
	List<Title> findByAuthorIn(List<Author> author);
	List<Title> findByItem(Item item);
	List<Title> findByItemIn(List<Item> item);
	List<Title> findByName(String name);
	List<Title> findByPubDate(String pubDate); 
	List<Title> findByNameAndPubDate(String name, String pubDate);
}

