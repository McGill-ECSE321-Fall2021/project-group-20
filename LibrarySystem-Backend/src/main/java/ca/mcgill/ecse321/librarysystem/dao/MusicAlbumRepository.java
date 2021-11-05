package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "music_data", path = "music_data")
public interface MusicAlbumRepository extends ItemRepository {

}
