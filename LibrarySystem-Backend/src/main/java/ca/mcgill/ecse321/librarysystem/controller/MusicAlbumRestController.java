package ca.mcgill.ecse321.librarysystem.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

import ca.mcgill.ecse321.librarysystem.dto.AuthorDto;
import ca.mcgill.ecse321.librarysystem.dto.MusicAlbumDto;
import ca.mcgill.ecse321.librarysystem.dto.TitleDto;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.MusicAlbum;
import ca.mcgill.ecse321.librarysystem.service.BookingService;
import ca.mcgill.ecse321.librarysystem.service.MusicAlbumService;
import ca.mcgill.ecse321.librarysystem.service.TitleService;

@CrossOrigin(origins = "*")

@RestController
public class MusicAlbumRestController {

	@Autowired
	private MusicAlbumService MusicAlbumService;
	@Autowired
	private TitleService titleService;
	@Autowired 
	private BookingService bookingService;
	
	@GetMapping(value = { "/MusicAlbums", "/MusicAlbums/" })
	public ResponseEntity getAllitems() {
		List<MusicAlbumDto> items = new ArrayList<>();
		List<MusicAlbum> itemList;
		try {
			itemList = MusicAlbumService.getAllAlbums();
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		for (MusicAlbum i : itemList) {
			items.add(convertToMusicAlbumDto(i));
		}
		if (items.size() == 0)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find any Items in System");
		return new ResponseEntity<>(items, HttpStatus.OK);
	}

	@GetMapping(value = { "/MusicAlbums/status/{status}", "/MusicAlbums/status/{status}" })
	public ResponseEntity getAllitemsBystatus(@PathVariable("status") String name) throws IllegalArgumentException {
		List<MusicAlbumDto> items = new ArrayList<>();
		List<MusicAlbum> itemList;
		try {
			itemList = MusicAlbumService.getItemByStat(Status.valueOf(name));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		items= convertToItem(itemList);
		
		if (items.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Items matching this Status");
		return new ResponseEntity<>(items, HttpStatus.OK);

	}

	@GetMapping(value = { "/MusicAlbums/title", "/MusicAlbums/title/" })
	public ResponseEntity getAllItemsByTitleName(@RequestParam String titleName) throws IllegalArgumentException {
		List<Title> titles;
		try {
			titles = titleService.getTitlesByName(titleName);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		List<MusicAlbum> item = new ArrayList<>();
		for (Title title : titles) {
			if (title.getName().equals(titleName)) {
				try {
					item.addAll(MusicAlbumService.getItemByTitle(title));
				} catch (IllegalArgumentException | NullPointerException msg) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
				}
			}
		}
		List<MusicAlbumDto> items = convertToItem(item);
		if (items.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Items matching this Title name");
		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	
	
	@GetMapping(value = {"/MusicAlbums/booking","/MusicAlbums/booking/"})
	public ResponseEntity getItemByBooking(@RequestParam String bookingId) throws IllegalArgumentException {
		Booking b;
		MusicAlbum item;

		try {
			b = bookingService.getBookingbyId(bookingId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			item = MusicAlbumService.getItemByItemBooking(b);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		MusicAlbumDto itemDto = convertToMusicAlbumDto(item);
		return new ResponseEntity<>(itemDto, HttpStatus.OK);
	}
	
	
	@GetMapping(value = {"/MusicAlbums/TitleId","/MusicAlbums/TitleId/"})
	public ResponseEntity getItemsByTitleId(@RequestParam String titleId) throws IllegalArgumentException {
		Title t;
		List<MusicAlbum> item;
		List<MusicAlbumDto> itemDto;

		try {
			t = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			item = MusicAlbumService.getItemByTitle(t);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		itemDto = convertToItem(item);
		return new ResponseEntity<>(itemDto, HttpStatus.OK);
	}
	
	@GetMapping(value = {"/MusicAlbums/Id","/MusicAlbums/Id/"})
	public ResponseEntity getItemById(@RequestParam String itemId) throws IllegalArgumentException {
		MusicAlbum item;

		try {
			item = MusicAlbumService.getItemById(Long.valueOf(itemId));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		MusicAlbumDto itemDto = convertToMusicAlbumDto(item);
		return new ResponseEntity<>(itemDto, HttpStatus.OK);
	}
	
	
	
	@PostMapping(value = { "/MusicAlbums/create", "/items/create/" })
	public ResponseEntity createItem(@RequestParam String status,
			@RequestParam String titleId, @RequestParam String length) throws Exception {
		Title title;
		MusicAlbum item;
		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (title == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Title returned null");

		try {
			item = MusicAlbumService.createItem(Status.valueOf(status),title,Integer.valueOf(length));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (item == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		return new ResponseEntity<>(convertToMusicAlbumDto(item), HttpStatus.OK);
	}
	
	@PutMapping(value = { "/MusicAlbums/updateall", "/MusicAlbums/updateall/" })
	public ResponseEntity updateItem(@RequestParam String ItemBarcode, @RequestParam String status,
			@RequestParam String titleId, @RequestParam String length) throws Exception {
		Title title;
		MusicAlbum item;
		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (title == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Title returned null");

		try {
			MusicAlbumService.updateItem(Status.valueOf(status), Long.valueOf(ItemBarcode), title,Integer.valueOf(length));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			item = MusicAlbumService.getItemById(Long.valueOf(ItemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (item == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		return new ResponseEntity<>(convertToMusicAlbumDto(item), HttpStatus.OK);
	}
	
	@PutMapping(value = { "/MusicAlbums/upstatus", "/MusicAlbums/upstatus/" })
	public ResponseEntity updateItemStatus(@RequestParam String itemBarcode, @RequestParam String status) throws Exception {
		MusicAlbum item;

		try {
			MusicAlbumService.updateItem(Status.valueOf(status), Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			item = MusicAlbumService.getItemById(Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (item == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		return new ResponseEntity<>(convertToMusicAlbumDto(item), HttpStatus.OK);
	}
	
	@PutMapping(value = { "/MusicAlbums/uptitle", "/MusicAlbums/uptitle/" })
	public ResponseEntity updateItemTitle(@RequestParam String itemBarcode, @RequestParam String titleId) throws Exception {
		Title title;
		MusicAlbum item;

		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			MusicAlbumService.updateItem(Long.valueOf(itemBarcode), title);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());

		}

		try {
			item = MusicAlbumService.getItemById(Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (item == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		return new ResponseEntity<>(convertToMusicAlbumDto(item), HttpStatus.OK);
	}
	
	@PutMapping(value = { "/MusicAlbums/uplength", "/MusicAlbums/uplength/" })
	public ResponseEntity updateItemLength(@RequestParam String itemBarcode, @RequestParam String length) throws Exception {
		MusicAlbum upItem;
		try {
		MusicAlbumService.updateItem(Integer.valueOf(length),Long.valueOf(itemBarcode));
		}catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		
		try {
			upItem = MusicAlbumService.getItemById(Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (upItem == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		
		MusicAlbumDto itemDto = convertToMusicAlbumDto(upItem);
		
		return new ResponseEntity<>(convertToMusicAlbumDto(upItem), HttpStatus.OK);
	}
	
	@DeleteMapping(value = { "/MusicAlbums/delitem", "/MusicAlbums/delitem/"})
	public ResponseEntity deleatItem(@RequestParam String itemBarcode) {
		try {
			MusicAlbumService.deleatItemById(Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (MusicAlbumService.getexistanceByItemBarcode(Long.valueOf(itemBarcode)))
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not delete Item");
		return ResponseEntity.status(HttpStatus.OK).body("Item deleted on " + new Date());
	}
	
	@DeleteMapping(value = { "/MusicAlbums/delitemstat", "/MusicAlbums/delitemstat/"})
	public ResponseEntity deleatItemsByStatus(@RequestParam String status) {
		try {
			MusicAlbumService.deleatItemByStat(Status.valueOf(status));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body("Item deleted on " + new Date());
	}
	
	@DeleteMapping(value = { "/MusicAlbums/delitemByBooking", "/MusicAlbums/delitemByBooking/"})
	public ResponseEntity deleatItemsByBooking(@RequestParam String bookingId) {
		try {
			MusicAlbumService.deleatItemByItemBooking(bookingService.getBookingbyId(bookingId));
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

	private MusicAlbumDto convertToMusicAlbumDto(MusicAlbum i) {
		if (i == null) {
			throw new IllegalArgumentException("There is no such Item!");
		}
		Status mystatus = i.getStatus();
		MusicAlbumDto itemDto = new MusicAlbumDto(mystatus, i.getItemBarcode(), convertToTitleDto(i.getTitle()),i.getDuration());
		return itemDto;
	}

	private List<MusicAlbumDto> convertToItem(List<MusicAlbum> i) {
		List<MusicAlbumDto> itemDtoList = new ArrayList<MusicAlbumDto>();
		for (MusicAlbum c : i) {
			MusicAlbumDto itemDto = convertToMusicAlbumDto(c);
			itemDtoList.add(itemDto);
		}
		return itemDtoList;
	}

	
}
