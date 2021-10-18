package ca.mcgill.ecse321.librarysystem.dao;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;
import ca.mcgill.ecse321.librarysystem.model.Title;

public interface ItemRepository extends CrudRepository<Item, String>{
	Item findItemByItemBarcode(String itemBarcode);
	List<Item> findItemByStatus(Status status);
	List<Item> findItemByLibrarySystem(LibrarySystem systemID);
	List<Item> findItemByTitle (Title name);
	boolean existsByItemBarcode(String itemBarcode);
	Item findItemByBooking(Booking booking);
	

	
}
