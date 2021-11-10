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
	public List<MovieDto> getAllitems() {
		return movieService.getAllMovies().stream().map(p -> convertToMovieDto(p)).collect(Collectors.toList());
	}

	@GetMapping(value = { "/movies/status/{status}", "/movies/status/{status}" })
	public List<MovieDto> getAllitemsBystatus(@PathVariable("status") String name) throws IllegalArgumentException {
		List<Movie> item = movieService.getItemByStat(Status.valueOf(name));
		List<MovieDto> itemDtoList = convertToItem(item);
		return itemDtoList;

	}

	@GetMapping(value = { "/movies/title", "/movies/title/" })
	public List<MovieDto> getAllItemsByTitleName(@RequestParam String titleName) throws IllegalArgumentException {
		List<Title> titles = titleService.getTitlesByName(titleName);
		List<Movie> movie = new ArrayList<Movie>();
		for (Title title: titles) {
			if (title.getName().equals(titleName)) {
				movie.addAll(movieService.getItemByTitle(title));
			}
		}
		List<MovieDto> items = convertToItem(movie);
		return items;
	}
	
	
	
	@GetMapping(value = {"/movies/booking","/movies/booking/"})
	public MovieDto getItemByBooking(@RequestParam String bookingId) throws IllegalArgumentException {
		Booking b = bookingService.getBookingbyId(bookingId);
		Movie item = movieService.getItemByItemBooking(b);
		MovieDto itemDto = convertToMovieDto(item);
		return itemDto; 
	}
	
	
	@GetMapping(value = {"/movies/TitleId","/movies/TitleId/"})
	public List<MovieDto> getItemsByTitleId(@RequestParam String titleId) throws IllegalArgumentException {
		Title t = titleService.getTitleByTitleID(titleId);
		List<Movie> item = movieService.getItemByTitle(t);
		List<MovieDto> itemDto = convertToItem(item);
		return itemDto; 
	}
	
	@GetMapping(value = {"/movies/Id","/movies/Id/"})
	public MovieDto getItemById(@RequestParam String itemId) throws IllegalArgumentException {
		Movie item = movieService.getItemById(Long.valueOf(itemId));
		MovieDto itemDto = convertToMovieDto(item);
		return itemDto; 
	}
	
	
	
	@PostMapping(value = { "/movies/create", "/items/create/" })
	public MovieDto createItem(@RequestParam String ItemBarcode, @RequestParam String status,
			@RequestParam String titleId, @RequestParam String length) throws Exception {
		Title title = titleService.getTitleByTitleID(titleId);
		if (title==null) {
			throw new Exception ("this returned null");
		}
		Movie item = movieService.createItem(Status.valueOf(status), Long.valueOf(ItemBarcode), title,Integer.valueOf(length));
		MovieDto itemDto = convertToMovieDto(item);
		return itemDto;
	}
	
	@PutMapping(value = { "/movies/updateall", "/movies/updateall/" })
	public MovieDto updateItem(@RequestParam String ItemBarcode, @RequestParam String status,
			@RequestParam String titleId, @RequestParam String length) throws Exception {
		Title title = titleService.getTitleByTitleID(titleId);
		if (title==null) {
			throw new Exception ("this returned null");
		}
		movieService.updateItem(Status.valueOf(status), Long.valueOf(ItemBarcode), title,Integer.valueOf(length));
		Movie item = movieService.getItemById(Long.valueOf(ItemBarcode));
		MovieDto itemDto = convertToMovieDto(item);
		return itemDto;
	}
	
	@PutMapping(value = { "/movies/upstatus", "/movies/upstatus/" })
	public MovieDto updateItemStatus(@RequestParam String itemBarcode, @RequestParam String status) throws Exception {
		movieService.updateItem(Status.valueOf(status), Long.valueOf(itemBarcode));
		Movie upItem = movieService.getItemById(Long.valueOf(itemBarcode));
		if (upItem==null) {
			throw new Exception ("this returned null");
		}
		MovieDto itemDto = convertToMovieDto(upItem);
		return itemDto;
	}
	
	@PutMapping(value = { "/movies/uptitle", "/movies/uptitle/" })
	public MovieDto updateItemTitle(@RequestParam String itemBarcode, @RequestParam String titleId) throws Exception {
		movieService.updateItem(Long.valueOf(itemBarcode),titleService.getTitleByTitleID(titleId));
		Movie upItem = movieService.getItemById(Long.valueOf(itemBarcode));
		if (upItem==null) {
			throw new Exception ("this returned null");
		}
		MovieDto itemDto = convertToMovieDto(upItem);
		return itemDto;
	}
	
	@PutMapping(value = { "/movies/uplength", "/movies/uplength/" })
	public MovieDto updateItemLength(@RequestParam String itemBarcode, @RequestParam String length) throws Exception {
		movieService.updateItem(Integer.valueOf(length),Long.valueOf(itemBarcode));
		Movie upItem = movieService.getItemById(Long.valueOf(itemBarcode));
		if (upItem==null) {
			throw new Exception ("this returned null");
		}
		MovieDto itemDto = convertToMovieDto(upItem);
		return itemDto;
	}
	
	@DeleteMapping(value = { "/movies/delitem", "/movies/delitem/"})
	public void deleatItem(@RequestParam String itemBarcode) {
		movieService.deleatItemById(Long.valueOf(itemBarcode));
	}
	
	@DeleteMapping(value = { "/movies/delitemstat", "/movies/delitemstat/"})
	public void deleatItemsByStatus(@RequestParam String status) {
		movieService.deleatItemByStat(Status.valueOf(status));
	}
	
	@DeleteMapping(value = { "/movies/delitemByBooking", "/movies/delitemByBooking/"})
	public void deleatItemsByBooking(@RequestParam String bookingId) {
		movieService.deleatItemByItemBooking(bookingService.getBookingbyId(bookingId));
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
