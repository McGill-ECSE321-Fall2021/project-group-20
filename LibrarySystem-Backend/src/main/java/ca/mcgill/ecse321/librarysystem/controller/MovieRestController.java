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
import ca.mcgill.ecse321.librarysystem.dto.MovieDto;
import ca.mcgill.ecse321.librarysystem.dto.TitleDto;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.Movie;
import ca.mcgill.ecse321.librarysystem.service.BookingService;
import ca.mcgill.ecse321.librarysystem.service.MovieService;
import ca.mcgill.ecse321.librarysystem.service.TitleService;

@CrossOrigin(origins = "*")

@RestController
public class MovieRestController {

	@Autowired
	private MovieService movieService;
	@Autowired
	private TitleService titleService;
	@Autowired 
	private BookingService bookingService;
	
	@GetMapping(value = { "/movies", "/movies/" })
	public ResponseEntity getAllitems() {
		List<MovieDto> items = new ArrayList<>();
		List<Movie> itemList;
		try {
			itemList = movieService.getAllMovies();
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		for (Movie i : itemList) {
			items.add(convertToMovieDto(i));
		}
		if (items.size() == 0)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find any Items in System");
		return new ResponseEntity<>(items, HttpStatus.OK);
	}

	@GetMapping(value = { "/movies/status/{status}", "/movies/status/{status}" })
	public ResponseEntity getAllitemsBystatus(@PathVariable("status") String name) throws IllegalArgumentException {
		List<MovieDto> items = new ArrayList<>();
		List<Movie> itemList;
		try {
			itemList = movieService.getItemByStat(Status.valueOf(name));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		items= convertToItem(itemList);
		
		if (items.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Items matching this Status");
		return new ResponseEntity<>(items, HttpStatus.OK);

	}

	@GetMapping(value = { "/movies/title", "/movies/title/" })
	public ResponseEntity getAllItemsByTitleName(@RequestParam String titleName) throws IllegalArgumentException {
		List<Title> titles;
		try {
			titles = titleService.getTitlesByName(titleName);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		List<Movie> item = new ArrayList<>();
		for (Title title : titles) {
			if (title.getName().equals(titleName)) {
				try {
					item.addAll(movieService.getItemByTitle(title));
				} catch (IllegalArgumentException | NullPointerException msg) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
				}
			}
		}
		List<MovieDto> items = convertToItem(item);
		if (items.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Items matching this Title name");
		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	
	
	@GetMapping(value = {"/movies/booking","/movies/booking/"})
	public ResponseEntity getItemByBooking(@RequestParam String bookingId) throws IllegalArgumentException {
		Booking b;
		Movie item;

		try {
			b = bookingService.getBookingbyId(bookingId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			item = movieService.getItemByItemBooking(b);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		MovieDto itemDto = convertToMovieDto(item);
		return new ResponseEntity<>(itemDto, HttpStatus.OK);
	}
	
	
	@GetMapping(value = {"/movies/TitleId","/movies/TitleId/"})
	public ResponseEntity getItemsByTitleId(@RequestParam String titleId) throws IllegalArgumentException {
		Title t;
		List<Movie> item;
		List<MovieDto> itemDto;

		try {
			t = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			item = movieService.getItemByTitle(t);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		itemDto = convertToItem(item);
		return new ResponseEntity<>(itemDto, HttpStatus.OK);
	}
	
	@GetMapping(value = {"/movies/Id","/movies/Id/"})
	public ResponseEntity getItemById(@RequestParam String itemId) throws IllegalArgumentException {
		Movie item;

		try {
			item = movieService.getItemById(Long.valueOf(itemId));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		MovieDto itemDto = convertToMovieDto(item);
		return new ResponseEntity<>(itemDto, HttpStatus.OK);
	}
	
	
	
	@PostMapping(value = { "/movies/create", "/items/create/" })
	public ResponseEntity createItem(@RequestParam String ItemBarcode, @RequestParam String status,
			@RequestParam String titleId, @RequestParam String length) throws Exception {
		Title title;
		Movie item;
		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (title == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Title returned null");

		try {
			item = movieService.createItem(Status.valueOf(status), Long.valueOf(ItemBarcode), title,Integer.valueOf(length));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (item == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		return new ResponseEntity<>(convertToMovieDto(item), HttpStatus.OK);
	}
	
	@PutMapping(value = { "/movies/updateall", "/movies/updateall/" })
	public ResponseEntity updateItem(@RequestParam String ItemBarcode, @RequestParam String status,
			@RequestParam String titleId, @RequestParam String length) throws Exception {
		Title title;
		Movie item;
		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (title == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Title returned null");

		try {
			movieService.updateItem(Status.valueOf(status), Long.valueOf(ItemBarcode), title,Integer.valueOf(length));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			item = movieService.getItemById(Long.valueOf(ItemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (item == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		return new ResponseEntity<>(convertToMovieDto(item), HttpStatus.OK);
	}
	
	@PutMapping(value = { "/movies/upstatus", "/movies/upstatus/" })
	public ResponseEntity updateItemStatus(@RequestParam String itemBarcode, @RequestParam String status) throws Exception {
		Movie item;

		try {
			movieService.updateItem(Status.valueOf(status), Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			item = movieService.getItemById(Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (item == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		return new ResponseEntity<>(convertToMovieDto(item), HttpStatus.OK);
	}
	
	@PutMapping(value = { "/movies/uptitle", "/movies/uptitle/" })
	public ResponseEntity updateItemTitle(@RequestParam String itemBarcode, @RequestParam String titleId) throws Exception {
		Title title;
		Movie item;

		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			movieService.updateItem(Long.valueOf(itemBarcode), title);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());

		}

		try {
			item = movieService.getItemById(Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (item == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		return new ResponseEntity<>(convertToMovieDto(item), HttpStatus.OK);
	}
	
	@PutMapping(value = { "/movies/uplength", "/movies/uplength/" })
	public ResponseEntity updateItemLength(@RequestParam String itemBarcode, @RequestParam String length) throws Exception {
		Movie upItem;
		try {
		movieService.updateItem(Integer.valueOf(length),Long.valueOf(itemBarcode));
		}catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		
		try {
			upItem = movieService.getItemById(Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (upItem == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item returned null");
		
		MovieDto itemDto = convertToMovieDto(upItem);
		
		return new ResponseEntity<>(convertToMovieDto(upItem), HttpStatus.OK);
	}
	
	@DeleteMapping(value = { "/movies/delitem", "/movies/delitem/"})
	public ResponseEntity deleatItem(@RequestParam String itemBarcode) {
		try {
			movieService.deleatItemById(Long.valueOf(itemBarcode));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (movieService.getexistanceByItemBarcode(Long.valueOf(itemBarcode)))
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not delete Item");
		return ResponseEntity.status(HttpStatus.OK).body("Item deleted on " + new Date());
	}
	
	@DeleteMapping(value = { "/movies/delitemstat", "/movies/delitemstat/"})
	public ResponseEntity deleatItemsByStatus(@RequestParam String status) {
		try {
			movieService.deleatItemByStat(Status.valueOf(status));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body("Item deleted on " + new Date());
	}
	
	@DeleteMapping(value = { "/movies/delitemByBooking", "/movies/delitemByBooking/"})
	public ResponseEntity deleatItemsByBooking(@RequestParam String bookingId) {
		try {
			movieService.deleatItemByItemBooking(bookingService.getBookingbyId(bookingId));
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
		return new TitleDto(title.getName(), title.getPubDate(), convertToAuthorDto(title.getAuthor()));
	}

	private MovieDto convertToMovieDto(Movie i) {
		if (i == null) {
			throw new IllegalArgumentException("There is no such Item!");
		}
		Status mystatus = i.getStatus();
		MovieDto itemDto = new MovieDto(mystatus, i.getItemBarcode(), convertToTitleDto(i.getTitle()),i.getLength());
		return itemDto;
	}

	private List<MovieDto> convertToItem(List<Movie> i) {
		List<MovieDto> itemDtoList = new ArrayList<MovieDto>();
		for (Movie c : i) {
			MovieDto itemDto = convertToMovieDto(c);
			itemDtoList.add(itemDto);
		}
		return itemDtoList;
	}

	
}
