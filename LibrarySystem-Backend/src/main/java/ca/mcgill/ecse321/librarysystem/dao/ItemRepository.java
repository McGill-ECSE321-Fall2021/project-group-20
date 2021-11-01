package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.Title;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long>{
	//find an item by its barcode
	Item findItemByItemBarcode(Long itemBarcode);
	//find a list of items by the status given
	List<Item> findItemByStatus(Status status);
	//find a list of items by the title given
	List<Item> findItemByTitle (Title name);
	//return a boolean depending on the existence of an item when we give it the barcode
	boolean existsByItemBarcode(Long itemBarcode);
	//find the item by its booking 
	Item findItemByBooking(Booking booking);
	

	
}
