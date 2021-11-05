package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "movie_data", path = "movie_data")
public interface MovieRepository extends ItemRepository {

}
