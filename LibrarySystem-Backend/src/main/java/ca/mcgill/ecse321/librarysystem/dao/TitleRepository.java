package ca.mcgill.ecse321.librarysystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Title;

public interface TitleRepository extends CrudRepository<Title, String>{
	Title findByTitleID (String titleID);
	List<Title> findByAuthor(Author authorID);
	List<Title> findByAuthorIn(List<Author> authorID);
	Title findByItem(Item itemBarcode);
	List<Title> findByItemIn(List<Item> itemBarcode);
	List<Title> findByName(String name);
	List<Title> findByPubDate(String pubDate); 
	Title findByNameAndPubDate(String name, String pubDate);
}
  
