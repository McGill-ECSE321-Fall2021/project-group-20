package ca.mcgill.ecse321.librarysystem.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.mcgill.ecse321.librarysystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.dto.AuthorDto;
import ca.mcgill.ecse321.librarysystem.dto.ItemDto;
import ca.mcgill.ecse321.librarysystem.dto.TitleDto;
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
	public ResponseEntity getAllitems() {
		List<ItemDto> items = new ArrayList<>();
		List<Item> itemList;
		try {
			itemList = itemService.getAllItems();
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		for (Item i : itemList) {
			items.add(convertToItemDto(i));
		}
		if (items.size() == 0)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find any Items in System");
		return new ResponseEntity<>(items, HttpStatus.OK);
	}

	@GetMapping(value = { "/items/status/{status}", "/items/status/{status}" })
	public ResponseEntity getAllitemsBystatus(@PathVariable("status") String name) {
		List<ItemDto> items = new ArrayList<>();
		List<Item> itemList;
		try {
			itemList = itemService.getItemByStat(Status.valueOf(name));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		for (Item i : itemList) {
			items.add(convertToItemDto(i));
		}
		if (items.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Items matching this Status");
		return new ResponseEntity<>(items, HttpStatus.OK);
	}

	@GetMapping(value = { "/items/title", "/items/title/" })
	public ResponseEntity getAllItemsByTitleName(@RequestParam String titlename) {
		List<Title> titles;
		try {
			titles = titleService.getTitlesByName(titlename);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		List<Item> item = new ArrayList<>();
		for (Title title : titles) {
			if (title.getName().equals(titlename)) {
				try {
					item.addAll(itemService.getItemByTitle(title));
				} catch (IllegalArgumentException | NullPointerException msg) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
				}
			}
		}
		List<ItemDto> items = convertToItem(item);
		if (items.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Items matching this Title name");
		return new ResponseEntity<>(items, HttpStatus.OK);
	}

	@GetMapping(value = { "/items/booking", "/items/booking/" })
	public ResponseEntity getItemByBooking(@RequestParam String bookingId) {
		Booking b;
		Item item;

		try {
			b = bookingService.getBookingbyId(bookingId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			item = itemService.getItemByItemBooking(b);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		ItemDto itemDto = convertToItemDto(item);
		return new ResponseEntity<>(itemDto, HttpStatus.OK);
	}

	@GetMapping(value = { "/items/titleId", "/items/titleId/" })
	public ResponseEntity getItemsByTitleId(@RequestParam String titleId) {
		Title t;
		List<Item> item;
		List<ItemDto> itemDto;

		try {
			t = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			item = itemService.getItemByTitle(t);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		itemDto = convertToItem(item);
		return new ResponseEntity<>(itemDto, HttpStatus.OK);
	}

	@GetMapping(value = { "/items/id", "/items/id/" })
	public ResponseEntity getItemById(@RequestParam String itemId) {
		Item item;

		try {
			item = itemService.getItemById(Long.valueOf(itemId));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		ItemDto itemDto = convertToItemDto(item);
		return new ResponseEntity<>(itemDto, HttpStatus.OK);
	}

	@PostMapping(value = { "/items/create", "/items/create/" })
	public ResponseEntity createItem(@RequestParam String status,
			@RequestParam String titleId) {
		Title title;
		Item item;
		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (title == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Title returned null");

		try {
			item = itemService.createItem(Status.valueOf(status), title);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (item == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		return new ResponseEntity<>(convertToItemDto(item), HttpStatus.OK);
	}

	@PutMapping(value = { "/items/updateall", "/items/updateall/" })
	public ResponseEntity updateItem(@RequestParam String itemBarcode, @RequestParam String status,
			@RequestParam String titleId) {
		Title title;
		Item item;
		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (title == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Title returned null");

		try {
			itemService.updateItem(Item.Status.valueOf(status), Long.valueOf(itemBarcode), title);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			item = itemService.getItemById(Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (item == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		return new ResponseEntity<>(convertToItemDto(item), HttpStatus.OK);
	}

	@PutMapping(value = { "/items/upstatus", "/items/upstatus/" })
	public ResponseEntity updateItemStatus(@RequestParam String itemBarcode, @RequestParam String status) {
		Item item;

		try {
			itemService.updateItem(Status.valueOf(status), Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			item = itemService.getItemById(Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (item == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		return new ResponseEntity<>(convertToItemDto(item), HttpStatus.OK);
	}

	@PutMapping(value = { "/items/uptitle", "/items/uptitle/" })
	public ResponseEntity updateItemTitle(@RequestParam String itemBarcode, @RequestParam String titleId) {
		Title title;
		Item item;

		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			itemService.updateItem(Long.valueOf(itemBarcode), title);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());

		}

		try {
			item = itemService.getItemById(Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (item == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		return new ResponseEntity<>(convertToItemDto(item), HttpStatus.OK);
	}

	@DeleteMapping(value = { "/items/delitem", "/items/delitem/" })
	public ResponseEntity deleteItem(@RequestParam String itemBarcode) {
		try {
			itemService.deleteItemById(Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (itemService.getexistanceByItemBarcode(Long.valueOf(itemBarcode)))
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not delete Item");
		return ResponseEntity.status(HttpStatus.OK).body("Item deleted on " + new Date());
	}

	@DeleteMapping(value = { "/items/delitemstat", "/items/delitemstat/" })
	public ResponseEntity deleteItemsByStatus(@RequestParam String status) {
		try {
			itemService.deleteItemByStat(Status.valueOf(status));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body("Item deleted on " + new Date());
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
		TitleDto titles = new TitleDto(title.getTitleID(), title.getName(), title.getPubDate(), convertToAuthorDto(title.getAuthor()));
		return titles;
	}

	private ItemDto convertToItemDto(Item i) {
		if (i == null) {
			throw new IllegalArgumentException("There is no such Item!");
		}
		Status mystatus = i.getStatus();
		TitleDto title = convertToTitleDto(i.getTitle());
		ItemDto item = new ItemDto(mystatus, i.getItemBarcode(), title, i.getBooking());
		return item;
	}

	private List<ItemDto> convertToItem(List<Item> i) {
		List<ItemDto> itemDtoList = new ArrayList<>();
		for (Item c : i) {
			ItemDto itemDto = convertToItemDto(c);
			itemDtoList.add(itemDto);
		}
		return itemDtoList;
	}

}
