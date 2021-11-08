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
import ca.mcgill.ecse321.librarysystem.dto.ItemDto;
import ca.mcgill.ecse321.librarysystem.dto.MusicAlbumDto;
import ca.mcgill.ecse321.librarysystem.dto.NewspaperDto;
import ca.mcgill.ecse321.librarysystem.dto.TitleDto;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.Newspaper;
import ca.mcgill.ecse321.librarysystem.service.BookingService;
import ca.mcgill.ecse321.librarysystem.service.TitleService;
import ca.mcgill.ecse321.librarysystem.service.NewspaperService;

@CrossOrigin(origins = "*")
@RestController
public class NewspaperRestController {

	
	@Autowired
	private NewspaperService newspaperService;
	@Autowired
	private TitleService titleService;
	@Autowired 
	private BookingService bookingService;
	
	
	@GetMapping(value = { "/newspaper", "/newspaper/" })
	public List<NewspaperDto> getAllitems() {
		return newspaperService.getAllNewspapers().stream().map(p -> convertToItemDto(p)).collect(Collectors.toList());
	}

	@GetMapping(value = { "/newspaper/status/{status}", "/newspaper/status/{status}" })
	public List<NewspaperDto> getAllitemsBystatus(@PathVariable("status") String name) throws IllegalArgumentException {
		List<Newspaper> item = newspaperService.getNewspaperByStat(Status.valueOf(name));
		List<NewspaperDto> itemDtoList = convertToItem(item);
		return itemDtoList;

	}

	@GetMapping(value = { "/newspaper/title", "/newspaper/title/" })
	public List<NewspaperDto> getAllItemsByTitleName(@RequestParam String titleName) throws IllegalArgumentException {
		List<Title> titles = titleService.getTitlesByName(titleName);
		List<Newspaper> item = new ArrayList<Newspaper>();
		for (Title title: titles) {
			if (title.getName().equals(titleName)) {
				item.addAll(newspaperService.getNewspaperByTitle(title));
			}
		}
		List<NewspaperDto> items = convertToItem(item);
		return items;
	}

	@GetMapping(value = {"/newspaper/booking","/newspaper/booking/"})
	public NewspaperDto getItemByBooking(@RequestParam String bookingId) throws IllegalArgumentException {
		Booking b = bookingService.getBookingbyId(bookingId);
		Newspaper item = newspaperService.getNewspaperByNewspaperBooking(b);
		NewspaperDto itemDto = convertToItemDto(item);
		return itemDto; 
	}
	
	@GetMapping(value = {"/newspaper/TitleId","/newspaper/TitleId/"})
	public List<NewspaperDto> getItemsByTitleId(@RequestParam String titleId) throws IllegalArgumentException {
		Title t = titleService.getTitleByTitleID(titleId);
		List<Newspaper> item = newspaperService.getNewspaperByTitle(t);
		List<NewspaperDto> itemDto = convertToItem(item);
		return itemDto; 
	}
	
	@GetMapping(value = {"/newspaper/Id","/newspaper/Id/"})
	public NewspaperDto getItemById(@RequestParam String itemId) throws IllegalArgumentException {
		Newspaper item = newspaperService.getNewspaperById(Long.valueOf(itemId));
		NewspaperDto itemDto = convertToItemDto(item);
		return itemDto; 
	}
	
 
	@PostMapping(value = { "/newspaper/create", "/newspaper/create/" })
	public NewspaperDto createItem(@RequestParam String ItemBarcode, @RequestParam String status,
			@RequestParam String titleId) throws Exception {
		Title title = titleService.getTitleByTitleID(titleId);
		if (title==null) {
			throw new Exception ("this returned null");
		}
		Newspaper item = newspaperService.createNewspaper(Status.valueOf(status), Long.valueOf(ItemBarcode), title);
		NewspaperDto itemDto = convertToItemDto(item);
		return itemDto;
	}
	
	@PutMapping(value = { "/newspaper/updateall", "/newspaper/updateall/" })
	public NewspaperDto updateItem(@RequestParam String ItemBarcode, @RequestParam String status,
			@RequestParam String titleId) throws Exception {
		Title title = titleService.getTitleByTitleID(titleId);
		if (title==null) {
			throw new Exception ("this returned null");
		}
		newspaperService.updateNewspaper(Status.valueOf(status), Long.valueOf(ItemBarcode), title);
		Newspaper item = newspaperService.getNewspaperById(Long.valueOf(ItemBarcode));
		NewspaperDto itemDto = convertToItemDto(item);
		return itemDto;
	}
	
	@PutMapping(value = { "/newspaper/upstatus", "/newspaper/upstatus/" })
	public NewspaperDto updateItemStatus(@RequestParam String itemBarcode, @RequestParam String status) throws Exception {
		newspaperService.updateNewspaper(Status.valueOf(status), Long.valueOf(itemBarcode));
		Newspaper upItem = newspaperService.getNewspaperById(Long.valueOf(itemBarcode));
		if (upItem==null) {
			throw new Exception ("this returned null");
		}
		NewspaperDto itemDto = convertToItemDto(upItem);
		return itemDto;
	}
	
	@PutMapping(value = { "/newspaper/uptitle", "/newspaper/uptitle/" })
	public NewspaperDto updateItemTitle(@RequestParam String itemBarcode, @RequestParam String titleId) throws Exception {
		newspaperService.updateNewspaper(Long.valueOf(itemBarcode),titleService.getTitleByTitleID(titleId));
		Newspaper upItem = newspaperService.getNewspaperById(Long.valueOf(itemBarcode));
		if (upItem==null) {
			throw new Exception ("this returned null");
		}
		NewspaperDto itemDto = convertToItemDto(upItem);
		return itemDto;
	}
	@DeleteMapping(value = { "/newspaper/delitem", "/newspaper/delitem/"})
	public void deleatItem(@RequestParam String itemBarcode) {
		newspaperService.deleatNewspaperById(Long.valueOf(itemBarcode));
	}
	
	@DeleteMapping(value = { "/newspaper/delitemstat", "/newspaper/delitemstat/"})
	public void deleatItemsByStatus(@RequestParam String status) {
		newspaperService.deleatNewspaperByStat(Status.valueOf(status));
	}
	
	@DeleteMapping(value = { "/newspaper/delitemByBooking", "/newspaper/delitemByBooking/"})
	public void deleatItemsByBooking(@RequestParam String bookingId) {
		newspaperService.deleatNewspaperByNewspaperBooking(bookingService.getBookingbyId(bookingId));
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

	private NewspaperDto convertToItemDto(Newspaper i) {
		if (i == null) {
			throw new IllegalArgumentException("There is no such Item!");
		}
		Status mystatus = i.getStatus();
		NewspaperDto itemDto = new NewspaperDto(mystatus, i.getItemBarcode(), convertToTitleDto(i.getTitle()));
		return itemDto;
	}

	private List<NewspaperDto> convertToItem(List<Newspaper> i) {
		List<NewspaperDto> itemDtoList = new ArrayList<NewspaperDto>();
		for (Newspaper c : i) {
			NewspaperDto itemDto = convertToItemDto(c);
			itemDtoList.add(itemDto);
		}
		return itemDtoList;
	}

}
