package ca.mcgill.ecse321.librarysystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.ItemRepository;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Employee;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.Title;

@Service
public class ItemService {
	@Autowired
	private ItemRepository itemRepository;

	@Transactional
	public Item createItem(Status aStatus, long aItemBarcode, Title aTitle) {
		if (aStatus == null || aTitle == null) throw new IllegalArgumentException("Please enter a valid status, title or Id");
		Item item = new Item(aStatus, aItemBarcode, aTitle);
		itemRepository.save(item);
		return item;
	}

	@Transactional
	public Item getItemById(Long itemBarcode) {
		Item item = itemRepository.findItemByItemBarcode(itemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		
		return item;
	}
	
	@Transactional
	public List<Item> getItemByStat(Status status) {
		List<Item> item = itemRepository.findItemByStatus(status);
		for (Item iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Item found");
		}	
		return item;
	}

	@Transactional
	public List<Item> getItemByTitle(Title title) {
		List<Item> item = itemRepository.findItemByTitle(title);
		for (Item iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Item found");
		}	
		return item;
	}

	@Transactional
	public boolean getexistanceByItemBarcode(Long itemBarcode) {
		boolean booleanOfItem = itemRepository.existsByItemBarcode(itemBarcode);
		return booleanOfItem;
	}

	@Transactional
	public Item getItemByItemBooking(Booking booking) {
		Item item = itemRepository.findItemByBooking(booking);
		if (item == null) throw new IllegalArgumentException("No Item found");
		return item;
	}
	
	@Transactional
	public void deleatItemById(Long itemBarcode) {
		Item item = itemRepository.findItemByItemBarcode(itemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		itemRepository.delete(item);
		item.delete();

	}

	@Transactional
	public void deleatItemByStat(Status status) {
		List<Item> item = itemRepository.findItemByStatus(status);
		for (Item iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Item found");
		}	
		itemRepository.deleteAll(item);
		
		for (Item iteme : item) {
			iteme.delete();;
		}
	}

	@Transactional
	public void deleatItemByTitle(Title title) {
		List<Item> item = itemRepository.findItemByTitle(title);
		for (Item iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Item found");
		}	
		itemRepository.deleteAll(item);
		for (Item iteme : item) {
			iteme.delete();;
		}	
		
	}


	@Transactional
	public void deleatItemByItemBooking(Booking booking) {
		Item item = itemRepository.findItemByBooking(booking);
		if (item == null) throw new IllegalArgumentException("No Item found");
		itemRepository.delete(item);
		item.delete();
	}

	@Transactional 
	public void updateItem(Status aStatus, long aItemBarcode, Title aTitle) {
		Item item = itemRepository.findItemByItemBarcode(aItemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		item.setStatus(aStatus);
		item.setTitle(aTitle);
		itemRepository.save(item);
	}
	
	@Transactional 
	public void updateItem(long aItemBarcode, Title aTitle) {
		Item item = itemRepository.findItemByItemBarcode(aItemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		item.setTitle(aTitle);
		itemRepository.save(item);
	}
	
	@Transactional 
	public void updateItem(Status aStatus,long aItemBarcode) {
		Item item = itemRepository.findItemByItemBarcode(aItemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		item.setStatus(aStatus);
		itemRepository.save(item);
	}

	@Transactional
	public List<Item> getAllItems() {
		List<Item> items = new ArrayList<>();
		for (Item i : itemRepository.findAll()) {
			items.add(i);
		}
		return items;
	}

}
