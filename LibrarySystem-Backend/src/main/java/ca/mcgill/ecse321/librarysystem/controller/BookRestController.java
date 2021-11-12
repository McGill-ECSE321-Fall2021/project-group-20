package ca.mcgill.ecse321.librarysystem.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import ca.mcgill.ecse321.librarysystem.dto.BookDto;
import ca.mcgill.ecse321.librarysystem.dto.TitleDto;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.Book;
import ca.mcgill.ecse321.librarysystem.service.BookingService;
import ca.mcgill.ecse321.librarysystem.service.BookService;
import ca.mcgill.ecse321.librarysystem.service.TitleService;

@CrossOrigin(origins = "*")

@RestController
public class BookRestController {

	@Autowired
	private BookService BookService;
	@Autowired
	private TitleService titleService;
	@Autowired 
	private BookingService bookingService;
	
	@GetMapping(value = { "/Books", "/Books/" })
	public ResponseEntity getAllitems() {
		List<BookDto> items = new ArrayList<>();
		List<Book> itemList;
		try {
			itemList = BookService.getBooks();
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		for (Book i : itemList) {
			items.add(convertToBookDto(i));
		}
		if (items.size() == 0)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find any Items in System");
		return new ResponseEntity<>(items, HttpStatus.OK);
	}

	@GetMapping(value = { "/Books/status/{status}", "/Books/status/{status}" })
	public ResponseEntity getAllitemsBystatus(@PathVariable("status") String name) throws IllegalArgumentException {
		List<BookDto> items = new ArrayList<>();
		List<Book> itemList;
		try {
			itemList = BookService.getBookByStat(Status.valueOf(name));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		items= convertToItem(itemList);
		
		if (items.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Items matching this Status");
		return new ResponseEntity<>(items, HttpStatus.OK);

	}

	@GetMapping(value = { "/Books/title", "/Books/title/" })
	public ResponseEntity getAllItemsByTitleName(@RequestParam String titleName) throws IllegalArgumentException {
		List<Title> titles;
		try {
			titles = titleService.getTitlesByName(titleName);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		List<Book> item = new ArrayList<>();
		for (Title title : titles) {
			if (title.getName().equals(titleName)) {
				try {
					item.addAll(BookService.getBookByTitle(title));
				} catch (IllegalArgumentException | NullPointerException msg) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
				}
			}
		}
		List<BookDto> items = convertToItem(item);
		if (items.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Items matching this Title name");
		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	
	
	@GetMapping(value = {"/Books/booking","/Books/booking/"})
	public ResponseEntity getItemByBooking(@RequestParam String bookingId) throws IllegalArgumentException {
		Booking b;
		Book item;

		try {
			b = bookingService.getBookingbyId(bookingId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			item = BookService.getBookByBookBooking(b);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		BookDto itemDto = convertToBookDto(item);
		return new ResponseEntity<>(itemDto, HttpStatus.OK);
	}
	
	
	@GetMapping(value = {"/Books/TitleId","/Books/TitleId/"})
	public ResponseEntity getItemsByTitleId(@RequestParam String titleId) throws IllegalArgumentException {
		Title t;
		List<Book> item;
		List<BookDto> itemDto;

		try {
			t = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			item = BookService.getBookByTitle(t);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		itemDto = convertToItem(item);
		return new ResponseEntity<>(itemDto, HttpStatus.OK);
	}
	
	@GetMapping(value = {"/Books/Id","/Books/Id/"})
	public ResponseEntity getItemById(@RequestParam String itemId) throws IllegalArgumentException {
		Book item;

		try {
			item = BookService.getBookById(Long.valueOf(itemId));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		BookDto itemDto = convertToBookDto(item);
		return new ResponseEntity<>(itemDto, HttpStatus.OK);
	}
	
	
	
	@PostMapping(value = { "/Books/create", "/items/create/" })
	public ResponseEntity createItem(@RequestParam String status,
			@RequestParam String titleId, @RequestParam String isbn, @RequestParam String pages) throws Exception {
		Title title;
		Book item;
		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (title == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Title returned null");

		try {
			item = BookService.createBook(Status.valueOf(status),title,isbn, pages);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (item == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		return new ResponseEntity<>(convertToBookDto(item), HttpStatus.OK);
	}
	

	@PutMapping(value = { "/Books/upstatus", "/Books/upstatus/" })
	public ResponseEntity updateItemStatus(@RequestParam String itemBarcode, @RequestParam String status) throws Exception {
		Book item;

		try {
			BookService.updateBook(Status.valueOf(status), Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			item = BookService.getBookById(Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (item == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		return new ResponseEntity<>(convertToBookDto(item), HttpStatus.OK);
	}
	
	@PutMapping(value = { "/Books/uptitle", "/Books/uptitle/" })
	public ResponseEntity updateItemTitle(@RequestParam String itemBarcode, @RequestParam String titleId) throws Exception {
		Title title;
		Book item;

		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			BookService.updateBook(Long.valueOf(itemBarcode), title);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());

		}

		try {
			item = BookService.getBookById(Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (item == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		return new ResponseEntity<>(convertToBookDto(item), HttpStatus.OK);
	}
	
	@PutMapping(value = { "/Books/upisbn", "/Books/upisbn/" })
	public ResponseEntity updateItemIsbn(@RequestParam String itemBarcode, @RequestParam String isbn) throws Exception {
		Book upItem;
		try {
		BookService.updateBookIsbn(isbn,Long.valueOf(itemBarcode));
		}catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		
		try {
			upItem = BookService.getBookById(Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (upItem == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		
		BookDto itemDto = convertToBookDto(upItem);
		
		return new ResponseEntity<>(convertToBookDto(upItem), HttpStatus.OK);
	}
	
	@PutMapping(value = { "/Books/upage", "/Books/upage/" })
	public ResponseEntity updateItemPage(@RequestParam String itemBarcode, @RequestParam String page) throws Exception {
		Book upItem;
		try {
		BookService.updateBookPages(page,Long.valueOf(itemBarcode));
		}catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		
		try {
			upItem = BookService.getBookById(Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (upItem == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		
		BookDto itemDto = convertToBookDto(upItem);
		
		return new ResponseEntity<>(convertToBookDto(upItem), HttpStatus.OK);
	}
	
	@DeleteMapping(value = { "/Books/delitem", "/Books/delitem/"})
	public ResponseEntity deleatItem(@RequestParam String itemBarcode) {
		try {
			BookService.deleatBookById(Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (BookService.getexistanceByBookBarcode(Long.valueOf(itemBarcode)))
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not delete Item");
		return ResponseEntity.status(HttpStatus.OK).body("Item deleted on " + new Date());
	}
	
	@DeleteMapping(value = { "/Books/delitemstat", "/Books/delitemstat/"})
	public ResponseEntity deleatItemsByStatus(@RequestParam String status) {
		try {
			BookService.deleatBookByStat(Status.valueOf(status));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body("Item deleted on " + new Date());
	}
	
	@DeleteMapping(value = { "/Books/delitemByBooking", "/Books/delitemByBooking/"})
	public ResponseEntity deleatItemsByBooking(@RequestParam String bookingId) {
		try {
			BookService.deleatBookByBookBooking(bookingService.getBookingbyId(bookingId));
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

	private BookDto convertToBookDto(Book i) {
		if (i == null) {
			throw new IllegalArgumentException("There is no such Item!");
		}
		Status mystatus = i.getStatus();
		BookDto itemDto = new BookDto(mystatus, i.getItemBarcode(), convertToTitleDto(i.getTitle()),i.getIsbn(),i.getNumPages());
		return itemDto;
	}

	private List<BookDto> convertToItem(List<Book> i) {
		List<BookDto> itemDtoList = new ArrayList<BookDto>();
		for (Book c : i) {
			BookDto itemDto = convertToBookDto(c);
			itemDtoList.add(itemDto);
		}
		return itemDtoList;
	}

	
}
