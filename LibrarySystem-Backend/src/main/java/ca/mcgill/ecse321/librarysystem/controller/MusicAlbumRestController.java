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
import ca.mcgill.ecse321.librarysystem.dto.MusicAlbumDto;
import ca.mcgill.ecse321.librarysystem.dto.TitleDto;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.MusicAlbum;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.service.BookingService;
import ca.mcgill.ecse321.librarysystem.service.MusicAlbumService;
import ca.mcgill.ecse321.librarysystem.service.TitleService;

@CrossOrigin(origins = "*")
@RestController
public class MusicAlbumRestController {

	@Autowired
	private MusicAlbumService musicAlbumService;
	@Autowired
	private TitleService titleService;
	@Autowired 
	private BookingService bookingService;
	
	@GetMapping(value = { "/musicAlbums", "/musicAlbums/" })
	public List<MusicAlbumDto> getAllitems() {
		return musicAlbumService.getAllAlbums().stream().map(p -> convertToMusicAlbumDto(p)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/musicAlbums/status/{status}", "/musicAlbums/status/{status}" })
	public List<MusicAlbumDto> getAllitemsBystatus(@PathVariable("status") String name) throws IllegalArgumentException {
		List<MusicAlbum> item = musicAlbumService.getItemByStat(Status.valueOf(name));
		List<MusicAlbumDto> itemDtoList = convertToItem(item);
		return itemDtoList;

	}
	
	@GetMapping(value = { "/musicAlbums/title", "/musicAlbums/title/" })
	public List<MusicAlbumDto> getAllItemsByTitleName(@RequestParam String titleName) throws IllegalArgumentException {
		List<Title> titles = titleService.getTitlesByName(titleName);
		List<MusicAlbum> movie = new ArrayList<MusicAlbum>();
		for (Title title: titles) {
			if (title.getName().equals(titleName)) {
				movie.addAll(musicAlbumService.getItemByTitle(title));
			}
		}
		List<MusicAlbumDto> items = convertToItem(movie);
		return items;
	}
	
	@GetMapping(value = {"/musicAlbums/booking","/musicAlbums/booking/"})
	public MusicAlbumDto getItemByBooking(@RequestParam String bookingId) throws IllegalArgumentException {
		Booking b = bookingService.getBookingbyId(bookingId);
		MusicAlbum item = musicAlbumService.getItemByItemBooking(b);
		MusicAlbumDto itemDto = convertToMusicAlbumDto(item);
		return itemDto; 
	}
	
	@GetMapping(value = {"/musicAlbums/TitleId","/musicAlbums/TitleId/"})
	public List<MusicAlbumDto> getItemsByTitleId(@RequestParam String titleId) throws IllegalArgumentException {
		Title t = titleService.getTitleByTitleID(titleId);
		List<MusicAlbum> item = musicAlbumService.getItemByTitle(t);
		List<MusicAlbumDto> itemDto = convertToItem(item);
		return itemDto; 
	}
	
	@GetMapping(value = {"/musicAlbums/Id","/musicAlbums/Id/"})
	public MusicAlbumDto getItemById(@RequestParam String itemId) throws IllegalArgumentException {
		MusicAlbum item = musicAlbumService.getItemById(Long.valueOf(itemId));
		MusicAlbumDto itemDto = convertToMusicAlbumDto(item);
		return itemDto; 
	}
	
	
	
	@PostMapping(value = { "/musicAlbum/create", "/musicAlbum/create/" })
	public MusicAlbumDto createItem(@RequestParam String ItemBarcode, @RequestParam String status,
			@RequestParam String titleId, @RequestParam String length) throws Exception {
		Title title = titleService.getTitleByTitleID(titleId);
		if (title==null) {
			throw new Exception ("this returned null");
		}
		MusicAlbum item = musicAlbumService.createItem(Status.valueOf(status), Long.valueOf(ItemBarcode), title,Integer.valueOf(length));
		MusicAlbumDto itemDto = convertToMusicAlbumDto(item);
		return itemDto;
	}
	
	
	
	@PutMapping(value = { "/musicAlbum/updateall", "/musicAlbumDto/updateall/" })
	public MusicAlbumDto updateItem(@RequestParam String ItemBarcode, @RequestParam String status,
			@RequestParam String titleId, @RequestParam String length) throws Exception {
		Title title = titleService.getTitleByTitleID(titleId);
		if (title==null) {
			throw new Exception ("this returned null");
		}
		musicAlbumService.updateItem(Status.valueOf(status), Long.valueOf(ItemBarcode), title,Integer.valueOf(length));
		MusicAlbum item = musicAlbumService.getItemById(Long.valueOf(ItemBarcode));
		MusicAlbumDto itemDto = convertToMusicAlbumDto(item);
		return itemDto;
	}
	
	@PutMapping(value = { "/musicAlbum/upstatus", "/musicAlbum/upstatus/" })
	public MusicAlbumDto updateItemStatus(@RequestParam String itemBarcode, @RequestParam String status) throws Exception {
		musicAlbumService.updateItem(Status.valueOf(status), Long.valueOf(itemBarcode));
		MusicAlbum upItem = musicAlbumService.getItemById(Long.valueOf(itemBarcode));
		if (upItem==null) {
			throw new Exception ("this returned null");
		}
		MusicAlbumDto itemDto = convertToMusicAlbumDto(upItem);
		return itemDto;
	}
	
	@PutMapping(value = { "/musicAlbum/uptitle", "/musicAlbum/uptitle/" })
	public MusicAlbumDto updateItemTitle(@RequestParam String itemBarcode, @RequestParam String titleId) throws Exception {
		musicAlbumService.updateItem(Long.valueOf(itemBarcode),titleService.getTitleByTitleID(titleId));
		MusicAlbum upItem = musicAlbumService.getItemById(Long.valueOf(itemBarcode));
		if (upItem==null) {
			throw new Exception ("this returned null");
		}
		MusicAlbumDto itemDto = convertToMusicAlbumDto(upItem);
		return itemDto;
	}
	
	@PutMapping(value = { "/musicAlbum/upduration", "/musicAlbum/upduration/" })
	public MusicAlbumDto updateItemLength(@RequestParam String itemBarcode, @RequestParam String length) throws Exception {
		musicAlbumService.updateItem(Integer.valueOf(length),Long.valueOf(itemBarcode));
		MusicAlbum upItem = musicAlbumService.getItemById(Long.valueOf(itemBarcode));
		if (upItem==null) {
			throw new Exception ("this returned null");
		}
		MusicAlbumDto itemDto = convertToMusicAlbumDto(upItem);
		return itemDto;
	}
	
	@DeleteMapping(value = { "/musicAlbum/delitem", "/musicAlbum/delitem/"})
	public void deleatItem(@RequestParam String itemBarcode) {
		musicAlbumService.deleatItemById(Long.valueOf(itemBarcode));
	}
	
	@DeleteMapping(value = { "/musicAlbum/delitemstat", "/musicAlbum/delitemstat/"})
	public void deleatItemsByStatus(@RequestParam String status) {
		musicAlbumService.deleatItemByStat(Status.valueOf(status));
	}
	
	@DeleteMapping(value = { "/musicAlbum/delitemByBooking", "/musicAlbum/delitemByBooking/"})
	public void deleatItemsByBooking(@RequestParam String bookingId) {
		musicAlbumService.deleatItemByItemBooking(bookingService.getBookingbyId(bookingId));
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
