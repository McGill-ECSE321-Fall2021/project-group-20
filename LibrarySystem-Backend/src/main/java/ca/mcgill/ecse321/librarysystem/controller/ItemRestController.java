package ca.mcgill.ecse321.librarysystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.dto.AuthorDto;
import ca.mcgill.ecse321.librarysystem.dto.ItemDto;
import ca.mcgill.ecse321.librarysystem.dto.TitleDto;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.service.BookingService;
import ca.mcgill.ecse321.librarysystem.service.ItemService;
import ca.mcgill.ecse321.librarysystem.service.TitleService;

@CrossOrigin(origins = "*")
@RestController
public class ItemRestController {

	@Autowired
	private ItemService itemService;
	@Autowired
	private TitleService titleService;
	@Autowired 
	private BookingService bookingService;


	@GetMapping(value = { "/items", "/items/" })
	public List<ItemDto> getAllitems() {
		return itemService.getAllItems().stream().map(p -> convertToItemDto(p)).collect(Collectors.toList());
	}

	@GetMapping(value = { "/items/status/{status}", "/items/status/{status}" })
	public List<ItemDto> getAllitemsBystatus(@PathVariable("status") String name) throws IllegalArgumentException {
		List<Item> item = itemService.getItemByStat(Status.valueOf(name));
		List<ItemDto> itemDtoList = convertToItem(item);
		return itemDtoList;

	}

	@GetMapping(value = { "/items/title", "/items/title/" })
	public List<ItemDto> getAllItemsByTitleName(@RequestParam String titleName) throws IllegalArgumentException {
		List<Title> titles = titleService.getTitlesByName(titleName);
		List<Item> item = new ArrayList<Item>();
		for (Title title: titles) {
			if (title.getName().equals(titleName)) {
				item.addAll(itemService.getItemByTitle(title));
			}
		}
		List<ItemDto> items = convertToItem(item);
		return items;
	}

	@GetMapping(value = {"/items/booking",".items/booking/"})
	public ItemDto getItemByBooking(@RequestParam String bookingId) throws IllegalArgumentException {
		Booking b = bookingService.getBookingbyId(bookingId);
		Item item = itemService.getItemByItemBooking(b);
		ItemDto itemDto = convertToItemDto(item);
		return itemDto; 
	}
	
	@GetMapping(value = {"/items/TitleId",".items/TitleId/"})
	public List<ItemDto> getItemsByTitleId(@RequestParam String titleId) throws IllegalArgumentException {
		Title t = titleService.getTitleByTitleID(titleId);
		List<Item> item = itemService.getItemByTitle(t);
		List<ItemDto> itemDto = convertToItem(item);
		return itemDto; 
	}
	
	@GetMapping(value = {"/items/Id",".items/Id/"})
	public ItemDto getItemById(@RequestParam String itemId) throws IllegalArgumentException {
		Item item = itemService.getItemById(Long.valueOf(itemId));
		ItemDto itemDto = convertToItemDto(item);
		return itemDto; 
	}
	
 
	@PostMapping(value = { "/items/create", "/items/create/" })
	public ItemDto createItem(@RequestParam String ItemBarcode, @RequestParam String status,
			@RequestParam String titleId) throws Exception {
		Title title = titleService.getTitleByTitleID(titleId);
		if (title==null) {
			throw new Exception ("this returned null");
		}
		Item item = itemService.createItem(Status.valueOf(status), Long.valueOf(ItemBarcode), title);
		ItemDto itemDto = convertToItemDto(item);
		return itemDto;
	}
	
	@PutMapping(value = { "/items/updateall", "/items/updateall/" })
	public ItemDto updateItem(@RequestParam String ItemBarcode, @RequestParam String status,
			@RequestParam String titleId) throws Exception {
		Title title = titleService.getTitleByTitleID(titleId);
		if (title==null) {
			throw new Exception ("this returned null");
		}
		itemService.updateItem(Status.valueOf(status), Long.valueOf(ItemBarcode), title);
		Item item = itemService.getItemById(Long.valueOf(ItemBarcode));
		ItemDto itemDto = convertToItemDto(item);
		return itemDto;
	}
	
	@PutMapping(value = { "/items/upstatus", "/items/upstatus/" })
	public ItemDto updateItemStatus(@RequestParam String itemBarcode, @RequestParam String status) throws Exception {
		itemService.updateItem(Status.valueOf(status), Long.valueOf(itemBarcode));
		Item upItem = itemService.getItemById(Long.valueOf(itemBarcode));
		if (upItem==null) {
			throw new Exception ("this returned null");
		}
		ItemDto itemDto = convertToItemDto(upItem);
		return itemDto;
	}
	
	@PutMapping(value = { "/items/uptitle", "/items/uptitle/" })
	public ItemDto updateItemTitle(@RequestParam String itemBarcode, @RequestParam String titleId) throws Exception {
		itemService.updateItem(Long.valueOf(itemBarcode),titleService.getTitleByTitleID(titleId));
		Item upItem = itemService.getItemById(Long.valueOf(itemBarcode));
		if (upItem==null) {
			throw new Exception ("this returned null");
		}
		ItemDto itemDto = convertToItemDto(upItem);
		return itemDto;
	}
	@DeleteMapping(value = { "/items/delitem", "/items/delitem/"})
	public void deleatItem(@RequestParam String itemBarcode) {
		itemService.deleatItemById(Long.valueOf(itemBarcode));
	}
	
	@DeleteMapping(value = { "/items/delitemstat", "/items/delitemstat/"})
	public void deleatItemsByStatus(@RequestParam String status) {
		itemService.deleatItemByStat(Status.valueOf(status));
	}
	
	@DeleteMapping(value = { "/items/delitemByBooking", "/items/delitemByBooking/"})
	public void deleatItemsByBooking(@RequestParam String bookingId) {
		itemService.deleatItemByItemBooking(bookingService.getBookingbyId(bookingId));
	}
	
	
	

	private AuthorDto[] convertToAuthorDto(List<Author> authors) {
		if (authors == null || authors.size() == 0)
			throw new IllegalArgumentException("Cannot find these authors");
		AuthorDto[] arrayAuthors = new AuthorDto[authors.size()];
		for (int i = 0; i < authors.size(); i++) {
			Author current_author = authors.get(i);
			arrayAuthors[i] = new AuthorDto(current_author.getAuthorID(), current_author.getFirstName(),
					current_author.getLastName());
		}
		return arrayAuthors;
	}

	private TitleDto convertToTitleDto(Title title) {
		if (title == null)
			throw new NullPointerException("Cannot find this Title");
		return new TitleDto(title.getName(), title.getPubDate(), convertToAuthorDto(title.getAuthor()));
	}

	private ItemDto convertToItemDto(Item i) {
		if (i == null) {
			throw new IllegalArgumentException("There is no such Item!");
		}
		Status mystatus = i.getStatus();
		ItemDto itemDto = new ItemDto(mystatus, i.getItemBarcode(), convertToTitleDto(i.getTitle()));
		return itemDto;
	}

	private List<ItemDto> convertToItem(List<Item> i) {
		List<ItemDto> itemDtoList = new ArrayList<ItemDto>();
		for (Item c : i) {
			ItemDto itemDto = convertToItemDto(c);
			itemDtoList.add(itemDto);
		}
		return itemDtoList;
	}


}
