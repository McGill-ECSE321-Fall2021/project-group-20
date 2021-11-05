package ca.mcgill.ecse321.librarysystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Title;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "title_data", path = "title_data")
public interface TitleRepository extends CrudRepository<Title, String>{
	Title findByTitleID (String titleID);
	List<Title> findByAuthor(Author authorID);
	List<Title> findByAuthorIn(List<Author> authorID);
	Title findByItem(Item itemBarcode);
	List<Title> findByItemIn(List<Item> itemBarcode);
	List<Title> findByName(String name);
	List<Title> findByPubDate(String pubDate); 
	Title findByNameAndPubDate(String name, String pubDate);
	boolean existsByTitleID(String titleID);
	boolean existsByItem(Item itemBarcode);
	boolean existsByNameAndPubDate(String name, String pubDate);
}
  
