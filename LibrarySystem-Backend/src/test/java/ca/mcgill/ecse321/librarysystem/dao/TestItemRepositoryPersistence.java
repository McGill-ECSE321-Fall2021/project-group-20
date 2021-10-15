package ca.mcgill.ecse321.librarysystem.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.librarysystem.model.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest

public class TestItemRepositoryPersistence {

	@Autowired
	private ItemRepository itemRepository;
	
	@AfterEach
	public void clearDatabase() {
		itemRepository.deleteAll();
	}
	@Test
	public void testPersistAndLoadItem() {
		
		//Item item1 = new Item (Available, int aItemBarcode, LibrarySystem aLibrarySystem, Title aTitle)
		
	}
	

}
