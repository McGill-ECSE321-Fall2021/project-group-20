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
import ca.mcgill.ecse321.librarysystem.dto.NewspaperDto;
import ca.mcgill.ecse321.librarysystem.dto.TitleDto;
import ca.mcgill.ecse321.librarysystem.service.BookingService;
import ca.mcgill.ecse321.librarysystem.service.NewspaperService;
import ca.mcgill.ecse321.librarysystem.service.TitleService;

@CrossOrigin(origins = "*")
@RestController
public class NewspaperRestController {

	@Autowired
	private NewspaperService NewspaperService;
	@Autowired
	private TitleService titleService;
	@Autowired
	private BookingService bookingService;

	@GetMapping(value = { "/Newspapers", "/Newspapers/" })
	public ResponseEntity getAllNewspapers() {
		List<NewspaperDto> Newspapers = new ArrayList<>();
		List<Newspaper> NewspaperList;
		try {
			NewspaperList = NewspaperService.getAllNewspapers();
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		for (Newspaper i : NewspaperList) {
			Newspapers.add(convertToNewspaperDto(i));
		}
		if (Newspapers.size() == 0)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find any Newspapers in System");
		return new ResponseEntity<>(Newspapers, HttpStatus.OK);
	}

	@GetMapping(value = { "/Newspapers/status/{status}", "/Newspapers/status/{status}" })
	public ResponseEntity getAllNewspapersBystatus(@PathVariable("status") String name) {
		List<NewspaperDto> Newspapers = new ArrayList<>();
		List<Newspaper> NewspaperList;
		try {
			NewspaperList = NewspaperService.getNewspaperByStat(Status.valueOf(name));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		for (Newspaper i : NewspaperList) {
			Newspapers.add(convertToNewspaperDto(i));
		}
		if (Newspapers.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Newspapers matching this Status");
		return new ResponseEntity<>(Newspapers, HttpStatus.OK);
	}

	@GetMapping(value = { "/Newspapers/title", "/Newspapers/title/" })
	public ResponseEntity getAllNewspapersByTitleName(@RequestParam String titlename) {
		List<Title> titles;
		try {
			titles = titleService.getTitlesByName(titlename);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		List<Newspaper> Newspaper = new ArrayList<>();
		for (Title title : titles) {
			if (title.getName().equals(titlename)) {
				try {
					Newspaper.addAll(NewspaperService.getNewspaperByTitle(title));
				} catch (IllegalArgumentException | NullPointerException msg) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
				}
			}
		}
		List<NewspaperDto> Newspapers = convertToNewspaper(Newspaper);
		if (Newspapers.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Newspapers matching this Title name");
		return new ResponseEntity<>(Newspapers, HttpStatus.OK);
	}

	@GetMapping(value = { "/Newspapers/booking", "/Newspapers/booking/" })
	public ResponseEntity getNewspaperByBooking(@RequestParam String bookingId) {
		Booking b;
		Newspaper Newspaper;

		try {
			b = bookingService.getBookingbyId(bookingId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			Newspaper = NewspaperService.getNewspaperByNewspaperBooking(b);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		NewspaperDto NewspaperDto = convertToNewspaperDto(Newspaper);
		return new ResponseEntity<>(NewspaperDto, HttpStatus.OK);
	}

	@GetMapping(value = { "/Newspapers/titleId", "/Newspapers/titleId/" })
	public ResponseEntity getNewspapersByTitleId(@RequestParam String titleId) {
		Title t;
		List<Newspaper> Newspaper;
		List<NewspaperDto> NewspaperDto;

		try {
			t = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			Newspaper = NewspaperService.getNewspaperByTitle(t);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		NewspaperDto = convertToNewspaper(Newspaper);
		return new ResponseEntity<>(NewspaperDto, HttpStatus.OK);
	}

	@GetMapping(value = { "/Newspapers/id", "/Newspapers/id/" })
	public ResponseEntity getNewspaperById(@RequestParam String NewspaperId) {
		Newspaper Newspaper;

		try {
			Newspaper = NewspaperService.getNewspaperById(Long.valueOf(NewspaperId));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		NewspaperDto NewspaperDto = convertToNewspaperDto(Newspaper);
		return new ResponseEntity<>(NewspaperDto, HttpStatus.OK);
	}

	@PostMapping(value = { "/Newspapers/create", "/Newspapers/create/" })
	public ResponseEntity createNewspaper(@RequestParam String status,
			@RequestParam String titleId) {
		Title title;
		Newspaper Newspaper;
		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (title == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Title returned null");

		try {
			Newspaper = NewspaperService.createNewspaper(Status.valueOf(status),title);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (Newspaper == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Newspaper returned null");
		return new ResponseEntity<>(convertToNewspaperDto(Newspaper), HttpStatus.OK);
	}

	@PutMapping(value = { "/Newspapers/updateall", "/Newspapers/updateall/" })
	public ResponseEntity updateNewspaper(@RequestParam String NewspaperBarcode, @RequestParam String status,
			@RequestParam String titleId) {
		Title title;
		Newspaper Newspaper;
		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (title == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Title returned null");

		try {
			NewspaperService.updateNewspaper(Status.valueOf(status), Long.valueOf(NewspaperBarcode), title);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			Newspaper = NewspaperService.getNewspaperById(Long.valueOf(NewspaperBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (Newspaper == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Newspaper returned null");
		return new ResponseEntity<>(convertToNewspaperDto(Newspaper), HttpStatus.OK);
	}

	@PutMapping(value = { "/Newspapers/upstatus", "/Newspapers/upstatus/" })
	public ResponseEntity updateNewspaperStatus(@RequestParam String NewspaperBarcode, @RequestParam String status) {
		Newspaper Newspaper;

		try {
			NewspaperService.updateNewspaper(Status.valueOf(status), Long.valueOf(NewspaperBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			Newspaper = NewspaperService.getNewspaperById(Long.valueOf(NewspaperBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (Newspaper == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Newspaper returned null");
		return new ResponseEntity<>(convertToNewspaperDto(Newspaper), HttpStatus.OK);
	}

	@PutMapping(value = { "/Newspapers/uptitle", "/Newspapers/uptitle/" })
	public ResponseEntity updateNewspaperTitle(@RequestParam String NewspaperBarcode, @RequestParam String titleId) {
		Title title;
		Newspaper Newspaper;

		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			NewspaperService.updateNewspaper(Long.valueOf(NewspaperBarcode), title);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());

		}

		try {
			Newspaper = NewspaperService.getNewspaperById(Long.valueOf(NewspaperBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (Newspaper == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Newspaper returned null");
		return new ResponseEntity<>(convertToNewspaperDto(Newspaper), HttpStatus.OK);
	}

	@DeleteMapping(value = { "/Newspapers/delNewspaper", "/Newspapers/delNewspaper/" })
	public ResponseEntity deleteNewspaper(@RequestParam String NewspaperBarcode) {
		try {
			NewspaperService.deleatNewspaperById(Long.valueOf(NewspaperBarcode));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (NewspaperService.getexistanceByNewspaperBarcode(Long.valueOf(NewspaperBarcode)))
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not delete Newspaper");
		return ResponseEntity.status(HttpStatus.OK).body("Newspaper deleted on " + new Date());
	}

	@DeleteMapping(value = { "/Newspapers/delNewspaperstat", "/Newspapers/delNewspaperstat/" })
	public ResponseEntity deleteNewspapersByStatus(@RequestParam String status) {
		try {
			NewspaperService.deleatNewspaperByStat(Status.valueOf(status));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body("Newspaper deleted on " + new Date());
	}

	@DeleteMapping(value = { "/Newspapers/delNewspaperByBooking", "/Newspapers/delNewspaperByBooking/" })
	public ResponseEntity deleteNewspapersByBooking(@RequestParam String bookingId) {
		try {
			NewspaperService.deleatNewspaperByNewspaperBooking(bookingService.getBookingbyId(bookingId));
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
		TitleDto titles = new TitleDto(title.getTitleID(), title.getName(), title.getPubDate(), convertToAuthorDto(title.getAuthor()));
		return titles;
	}

	private NewspaperDto convertToNewspaperDto(Newspaper i) {
		if (i == null) {
			throw new IllegalArgumentException("There is no such Item!");
		}
		Status mystatus = i.getStatus();
		TitleDto title = convertToTitleDto(i.getTitle());
		NewspaperDto item = new NewspaperDto(mystatus, i.getItemBarcode(), title);
		return item;
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
