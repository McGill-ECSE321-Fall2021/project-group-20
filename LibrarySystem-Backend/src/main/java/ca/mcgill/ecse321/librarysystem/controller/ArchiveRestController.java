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
import ca.mcgill.ecse321.librarysystem.dto.ArchiveDto;
import ca.mcgill.ecse321.librarysystem.dto.TitleDto;
import ca.mcgill.ecse321.librarysystem.service.BookingService;
import ca.mcgill.ecse321.librarysystem.service.ArchiveService;
import ca.mcgill.ecse321.librarysystem.service.TitleService;

@CrossOrigin(origins = "*")
@RestController
public class ArchiveRestController {

	@Autowired
	private ArchiveService ArchiveService;
	@Autowired
	private TitleService titleService;
	@Autowired
	private BookingService bookingService;

	@GetMapping(value = { "/Archives", "/Archives/" })
	public ResponseEntity getAllArchives() {
		List<ArchiveDto> Archives = new ArrayList<>();
		List<Archive> ArchiveList;
		try {
			ArchiveList = ArchiveService.getAllArchives();
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		for (Archive i : ArchiveList) {
			Archives.add(convertToArchiveDto(i));
		}
		if (Archives.size() == 0)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find any Archives in System");
		return new ResponseEntity<>(Archives, HttpStatus.OK);
	}

	@GetMapping(value = { "/Archives/status/{status}", "/Archives/status/{status}" })
	public ResponseEntity getAllArchivesBystatus(@PathVariable("status") String name) {
		List<ArchiveDto> Archives = new ArrayList<>();
		List<Archive> ArchiveList;
		try {
			ArchiveList = ArchiveService.getArchiveByStat(Status.valueOf(name));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		for (Archive i : ArchiveList) {
			Archives.add(convertToArchiveDto(i));
		}
		if (Archives.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Archives matching this Status");
		return new ResponseEntity<>(Archives, HttpStatus.OK);
	}

	@GetMapping(value = { "/Archives/title", "/Archives/title/" })
	public ResponseEntity getAllArchivesByTitleName(@RequestParam String titlename) {
		List<Title> titles;
		try {
			titles = titleService.getTitlesByName(titlename);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		List<Archive> Archive = new ArrayList<>();
		for (Title title : titles) {
			if (title.getName().equals(titlename)) {
				try {
					Archive.addAll(ArchiveService.getArchiveByTitle(title));
				} catch (IllegalArgumentException | NullPointerException msg) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
				}
			}
		}
		List<ArchiveDto> Archives = convertToArchive(Archive);
		if (Archives.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any Archives matching this Title name");
		return new ResponseEntity<>(Archives, HttpStatus.OK);
	}

	@GetMapping(value = { "/Archives/booking", "/Archives/booking/" })
	public ResponseEntity getArchiveByBooking(@RequestParam String bookingId) {
		Booking b;
		Archive Archive;

		try {
			b = bookingService.getBookingbyId(bookingId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			Archive = ArchiveService.getArchiveByArchiveBooking(b);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		ArchiveDto ArchiveDto = convertToArchiveDto(Archive);
		return new ResponseEntity<>(ArchiveDto, HttpStatus.OK);
	}

	@GetMapping(value = { "/Archives/titleId", "/Archives/titleId/" })
	public ResponseEntity getArchivesByTitleId(@RequestParam String titleId) {
		Title t;
		List<Archive> Archive;
		List<ArchiveDto> ArchiveDto;

		try {
			t = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			Archive = ArchiveService.getArchiveByTitle(t);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		ArchiveDto = convertToArchive(Archive);
		return new ResponseEntity<>(ArchiveDto, HttpStatus.OK);
	}

	@GetMapping(value = { "/Archives/id", "/Archives/id/" })
	public ResponseEntity getArchiveById(@RequestParam String ArchiveId) {
		Archive Archive;

		try {
			Archive = ArchiveService.getArchiveById(Long.valueOf(ArchiveId));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		ArchiveDto ArchiveDto = convertToArchiveDto(Archive);
		return new ResponseEntity<>(ArchiveDto, HttpStatus.OK);
	}

	@PostMapping(value = { "/Archives/create", "/Archives/create/" })
	public ResponseEntity createArchive(@RequestParam String status,
										@RequestParam String titleId) {
		Title title;
		Archive Archive;
		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (title == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Title returned null");

		try {
			Archive = ArchiveService.createArchive(Status.valueOf(status),title);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (Archive == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Archive returned null");
		return new ResponseEntity<>(convertToArchiveDto(Archive), HttpStatus.OK);
	}

	@PutMapping(value = { "/Archives/updateall", "/Archives/updateall/" })
	public ResponseEntity updateArchive(@RequestParam String ArchiveBarcode, @RequestParam String status,
										@RequestParam String titleId) {
		Title title;
		Archive Archive;
		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (title == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Title returned null");

		try {
			ArchiveService.updateArchive(Status.valueOf(status), Long.valueOf(ArchiveBarcode), title);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			Archive = ArchiveService.getArchiveById(Long.valueOf(ArchiveBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (Archive == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Archive returned null");
		return new ResponseEntity<>(convertToArchiveDto(Archive), HttpStatus.OK);
	}

	@PutMapping(value = { "/Archives/upstatus", "/Archives/upstatus/" })
	public ResponseEntity updateArchiveStatus(@RequestParam String ArchiveBarcode, @RequestParam String status) {
		Archive Archive;

		try {
			ArchiveService.updateArchive(Status.valueOf(status), Long.valueOf(ArchiveBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			Archive = ArchiveService.getArchiveById(Long.valueOf(ArchiveBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (Archive == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Archive returned null");
		return new ResponseEntity<>(convertToArchiveDto(Archive), HttpStatus.OK);
	}

	@PutMapping(value = { "/Archives/uptitle", "/Archives/uptitle/" })
	public ResponseEntity updateArchiveTitle(@RequestParam String ArchiveBarcode, @RequestParam String titleId) {
		Title title;
		Archive Archive;

		try {
			title = titleService.getTitleByTitleID(titleId);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		try {
			ArchiveService.updateArchive(Long.valueOf(ArchiveBarcode), title);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());

		}

		try {
			Archive = ArchiveService.getArchiveById(Long.valueOf(ArchiveBarcode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (Archive == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Archive returned null");
		return new ResponseEntity<>(convertToArchiveDto(Archive), HttpStatus.OK);
	}

	@DeleteMapping(value = { "/Archives/delArchive", "/Archives/delArchive/" })
	public ResponseEntity deleteArchive(@RequestParam String ArchiveBarcode) {
		try {
			ArchiveService.deleatArchiveById(Long.valueOf(ArchiveBarcode));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		if (ArchiveService.getexistanceByArchiveBarcode(Long.valueOf(ArchiveBarcode)))
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not delete Archive");
		return ResponseEntity.status(HttpStatus.OK).body("Archive deleted on " + new Date());
	}

	@DeleteMapping(value = { "/Archives/delArchivestat", "/Archives/delArchivestat/" })
	public ResponseEntity deleteArchivesByStatus(@RequestParam String status) {
		try {
			ArchiveService.deleatArchiveByStat(Status.valueOf(status));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body("Archive deleted on " + new Date());
	}

	@DeleteMapping(value = { "/Archives/delArchiveByBooking", "/Archives/delArchiveByBooking/" })
	public ResponseEntity deleteArchivesByBooking(@RequestParam String bookingId) {
		try {
			ArchiveService.deleatArchiveByArchiveBooking(bookingService.getBookingbyId(bookingId));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body("Archive deleted on " + new Date());
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

	private ArchiveDto convertToArchiveDto(Archive i) {
		if (i == null) {
			throw new IllegalArgumentException("There is no such Item!");
		}
		Status mystatus = i.getStatus();
		TitleDto title = convertToTitleDto(i.getTitle());
		ArchiveDto item = new ArchiveDto(mystatus, i.getItemBarcode(), title);
		return item;
	}
	private List<ArchiveDto> convertToArchive(List<Archive> i) {
		List<ArchiveDto> ArchiveDtoList = new ArrayList<>();
		for (Archive c : i) {
			ArchiveDto ArchiveDto = convertToArchiveDto(c);
			ArchiveDtoList.add(ArchiveDto);
		}
		return ArchiveDtoList;
	}

}
