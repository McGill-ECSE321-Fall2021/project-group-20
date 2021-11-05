package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "archive_data", path = "archive_data")
public interface ArchiveRepository extends ItemRepository {
	
}
