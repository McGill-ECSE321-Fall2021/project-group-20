package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "newspaper_data", path = "newspaper_data")
public interface NewspaperRepository extends ItemRepository {

}
