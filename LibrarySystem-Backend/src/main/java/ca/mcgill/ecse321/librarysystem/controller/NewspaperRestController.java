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
import ca.mcgill.ecse321.librarysystem.dto.NewspaperDto;
import ca.mcgill.ecse321.librarysystem.dto.TitleDto;
import ca.mcgill.ecse321.librarysystem.service.BookingService;
import ca.mcgill.ecse321.librarysystem.service.NewspaperService;
import ca.mcgill.ecse321.librarysystem.service.TitleService;

@CrossOrigin(origins = "*")
@RestController
public class NewspaperRestController {

	@Autowired
	private NewspaperService newspaperService;
	@Autowired
	private TitleService titleService;
	@Autowired
	private BookingService bookingService;

	@GetMapping(value = { "/Newspapers", "/Newspapers/" })
	public ResponseEntity getAllNewspapers() {
		List<NewspaperDto> newspapers = new ArrayList<>();
		List<Newspaper> newspaperList;
		try {
			newspaperList = newspaperService.getAllNewspapers();
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		for (Newspaper i : newspaperList) {
			newspapers.add(convertToNewspaperDto(i));
		}
		if (newspapers.size() == 0)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find any Newspapers in System");
		return new ResponseEntity<>(newspapers, HttpStatus.OK);
	}

	@GetMapping(value = { "/Newspapers/status/{status}", "/Newspapers/status/{status}" })
	public ResponseEntity getAllNewspapersBystatus(@PathVariable("status") String name) {
		List<NewspaperDto> newspapers = new ArrayList<>();
		List<Newspaper> newspaperList;
		try {
			newspaperList = newspaperService.getNewspaperByStat(Status.valueOf(name));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		for (Newspaper i : newspaperList) {
			newspapers.add(convertToNewspaperDto(i));
		}
		if (newspapers.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Newspapers matching this Status");
		return new ResponseEntity<>(newspapers, HttpStatus.OK);
	}

	@GetMapping(value = { "/Newspapers/title", "/Newspapers/title/" })
	public ResponseEntity getAllNewspapersByTitleName(@RequestParam String titlename) {
		List<Title> titles;
		try {
			titles = titleService.getTitlesByName(titlename);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		List<Newspaper> newspaper = new ArrayList<>();
		for (Title title : titles) {
			if (title.getName().equals(titlename)) {
				try {
					newspaper.addAll(newspaperService.getNewspaperByTitle(title));
				} catch (IllegalArgumentException | NullPointerException msg) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
				}
			}
		}
		List<NewspaperDto> newspapers = convertToNewspaper(newspaper);
		if (newspapers.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Newspapers matching this Title name");
		return new ResponseEntity<>(newspapers, HttpStatus.OK);
	}

	@GetMapping(value = { "/Newspapers/booking", "/Newspapers/booking/" })
	public ResponseEntity getNewspaperByBooking(@RequestParam String bookingId) {
		Booking b;
		Newspaper newspaper;

		try {
			b = bookingService.getBookingbyId(bookingId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			newspaper = newspaperService.getNewspaperByNewspaperBooking(b);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		NewspaperDto NewspaperDto = convertToNewspaperDto(newspaper);
		return new ResponseEntity<>(NewspaperDto, HttpStatus.OK);
	}

	@GetMapping(value = { "/Newspapers/titleId", "/Newspapers/titleId/" })
	public ResponseEntity getNewspapersByTitleId(@RequestParam String titleId) {
		Title t;
		List<Newspaper> newspaper;
		List<NewspaperDto> newspaperDto;

		try {
			t = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			newspaper = newspaperService.getNewspaperByTitle(t);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		newspaperDto = convertToNewspaper(newspaper);
		return new ResponseEntity<>(newspaperDto, HttpStatus.OK);
	}

	@GetMapping(value = { "/Newspapers/id", "/Newspapers/id/" })
	public ResponseEntity getNewspaperById(@RequestParam String newspaperId) {
		Newspaper newspaper;

		try {
			newspaper = newspaperService.getNewspaperById(Long.valueOf(newspaperId));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		NewspaperDto newspaperDto = convertToNewspaperDto(newspaper);
		return new ResponseEntity<>(newspaperDto, HttpStatus.OK);
	}

	@PostMapping(value = { "/Newspapers/create", "/Newspapers/create/" })
	public ResponseEntity createNewspaper(@RequestParam String newspaperBarcode, @RequestParam String status,
			@RequestParam String titleId) {
		Title title;
		Newspaper newspaper;
		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (title == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Title returned null");

		try {
			newspaper = newspaperService.createNewspaper(Status.valueOf(status), Long.valueOf(newspaperBarcode), title);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (newspaper == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Newspaper returned null");
		return new ResponseEntity<>(convertToNewspaperDto(newspaper), HttpStatus.OK);
	}

	@PutMapping(value = { "/Newspapers/updateall", "/Newspapers/updateall/" })
	public ResponseEntity updateNewspaper(@RequestParam String newspaperBarcode, @RequestParam String status,
			@RequestParam String titleId) {
		Title title;
		Newspaper newspaper;
		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (title == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Title returned null");

		try {
			newspaperService.updateNewspaper(Status.valueOf(status), Long.valueOf(newspaperBarcode), title);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			newspaper = newspaperService.getNewspaperById(Long.valueOf(newspaperBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (newspaper == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Newspaper returned null");
		return new ResponseEntity<>(convertToNewspaperDto(newspaper), HttpStatus.OK);
	}

	@PutMapping(value = { "/Newspapers/upstatus", "/Newspapers/upstatus/" })
	public ResponseEntity updateNewspaperStatus(@RequestParam String newspaperBarcode, @RequestParam String status) {
		Newspaper newspaper;

		try {
			newspaperService.updateNewspaper(Status.valueOf(status), Long.valueOf(newspaperBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			newspaper = newspaperService.getNewspaperById(Long.valueOf(newspaperBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (newspaper == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Newspaper returned null");
		return new ResponseEntity<>(convertToNewspaperDto(newspaper), HttpStatus.OK);
	}

	@PutMapping(value = { "/Newspapers/uptitle", "/Newspapers/uptitle/" })
	public ResponseEntity updateNewspaperTitle(@RequestParam String newspaperBarcode, @RequestParam String titleId) {
		Title title;
		Newspaper newspaper;

		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			newspaperService.updateNewspaper(Long.valueOf(newspaperBarcode), title);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());

		}

		try {
			newspaper = newspaperService.getNewspaperById(Long.valueOf(newspaperBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (newspaper == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Newspaper returned null");
		return new ResponseEntity<>(convertToNewspaperDto(newspaper), HttpStatus.OK);
	}

	@DeleteMapping(value = { "/Newspapers/delNewspaper", "/Newspapers/delNewspaper/" })
	public ResponseEntity deleteNewspaper(@RequestParam String newspaperBarcode) {
		try {
			newspaperService.deleatNewspaperById(Long.valueOf(newspaperBarcode));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (newspaperService.getexistanceByNewspaperBarcode(Long.valueOf(newspaperBarcode)))
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not delete Newspaper");
		return ResponseEntity.status(HttpStatus.OK).body("Newspaper deleted on " + new Date());
	}

	@DeleteMapping(value = { "/Newspapers/delNewspaperstat", "/Newspapers/delNewspaperstat/" })
	public ResponseEntity deleteNewspapersByStatus(@RequestParam String status) {
		try {
			newspaperService.deleatNewspaperByStat(Status.valueOf(status));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body("Newspaper deleted on " + new Date());
	}

	@DeleteMapping(value = { "/Newspapers/delNewspaperByBooking", "/Newspapers/delNewspaperByBooking/" })
	public ResponseEntity deleteNewspapersByBooking(@RequestParam String bookingId) {
		try {
			newspaperService.deleatNewspaperByNewspaperBooking(bookingService.getBookingbyId(bookingId));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body("Newspaper deleted on " + new Date());
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

	private NewspaperDto convertToNewspaperDto(Newspaper i) {
		if (i == null) {
			throw new IllegalArgumentException("There is no such Newspaper!");
		}
		Status mystatus = i.getStatus();
		return new NewspaperDto(mystatus, i.getItemBarcode(), convertToTitleDto(i.getTitle()));
	}

	private List<NewspaperDto> convertToNewspaper(List<Newspaper> i) {
		List<NewspaperDto> NewspaperDtoList = new ArrayList<>();
		for (Newspaper c : i) {
			NewspaperDto NewspaperDto = convertToNewspaperDto(c);
			NewspaperDtoList.add(NewspaperDto);
		}
		return NewspaperDtoList;
	}

}
