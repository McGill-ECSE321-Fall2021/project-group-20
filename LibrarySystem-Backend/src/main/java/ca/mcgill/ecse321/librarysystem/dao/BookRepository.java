package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "book_data", path = "book_data")
public interface BookRepository extends ItemRepository {

}
