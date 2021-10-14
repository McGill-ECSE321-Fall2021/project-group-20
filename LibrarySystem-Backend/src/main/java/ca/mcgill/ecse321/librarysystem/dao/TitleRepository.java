package ca.mcgill.ecse321.librarysystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Title;

public interface TitleRepository extends CrudRepository<Title, String>{
	List<Title> findByAuthor(Author author);
	//List<Title> findByAuthor(List<Author> author);
	
	List<Title> findByItem(Item item);
	//List<Title> findByItem(List<Item> item);
	List<Title> findByName(String name);
	List<Title> findByPubDate(String pubDate);
}

