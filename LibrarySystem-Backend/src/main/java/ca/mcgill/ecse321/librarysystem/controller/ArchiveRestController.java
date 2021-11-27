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

	/**
	 * Retrieves all archives in the system.
	 * @return a list of archives if successful, else a message "Cannot find any Archives in System".
	 */
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

	/**
	 * Retrieves all archives by a given status.
	 * @param name
	 * @return a list of archives.
	 */
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

	/**
	 * Retrieves all archives by a given title.
	 * @param titlename
	 * @return a list of all archives if successful, else a message "Cannot find any Archives matching this Title name".
	 */
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

	/**
	 * Retrieves an archive given a booking.
	 * @param bookingId
	 * @return a n archive dto object.
	 */
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

	/**
	 * Retrieves all archives given the title ID.
	 * @param titleId
	 * @return a list of archives.
	 */
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

	/**
	 * Retrieves an archives given an id.
	 * @param ArchiveId
	 * @return a archive dto object.
	 */
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

	/**
	 * Creates an archive with given status and title ID.
	 * @param status
	 * @param titleId
	 * @return an archive dto object.
	 */
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

	/**
	 * Updates an archive's status and title id.
	 * @param ArchiveBarcode
	 * @param status
	 * @param titleId
	 * @return an archive dto object if successful, else a message "Archive returned null".
	 */
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
			ArchiveService.updateArchive(Status.valueOf(status), Long.parseLong(ArchiveBarcode), title);
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

	/**
	 * Updates an archive's barcode.
	 * @param ArchiveBarcode
	 * @param status
	 * @return an archive dto object if successful, else a message "Archive returned null".
	 */
	@PutMapping(value = { "/Archives/upstatus", "/Archives/upstatus/" })
	public ResponseEntity updateArchiveStatus(@RequestParam String ArchiveBarcode, @RequestParam String status) {
		Archive Archive;

		try {
			ArchiveService.updateArchive(Status.valueOf(status), Long.parseLong(ArchiveBarcode));
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

	/**
	 * Updates an archive's title id.
	 * @param ArchiveBarcode
	 * @param titleId
	 * @return an archive dto object if successful, else a message "Archive returned null".
	 */
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
			ArchiveService.updateArchive(Long.parseLong(ArchiveBarcode), title);
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

	/**
	 * Deletes an archive from the system.
	 * @param ArchiveBarcode
	 * @return a message "Archive deleted" if successful, else "Could not delete Archive".
	 */
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

	/**
	 * Deletes an archive based on the status.
	 * @param status
	 * @return a message "Archive deleted" if successful.
	 */
	@DeleteMapping(value = { "/Archives/delArchivestat", "/Archives/delArchivestat/" })
	public ResponseEntity deleteArchivesByStatus(@RequestParam String status) {
		try {
			ArchiveService.deleatArchiveByStat(Status.valueOf(status));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body("Archive deleted on " + new Date());
	}

	/**
	 * Deletes an archive based on a booking.
	 * @param bookingId
	 * @return a message "Archive Deleted".
	 */
	@DeleteMapping(value = { "/Archives/delArchiveByBooking", "/Archives/delArchiveByBooking/" })
	public ResponseEntity deleteArchivesByBooking(@RequestParam String bookingId) {
		try {
			ArchiveService.deleatArchiveByArchiveBooking(bookingService.getBookingbyId(bookingId));
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body("Archive deleted on " + new Date());
	}

	/**
	 * Converts a list of model objects to a list of dto objects.
	 * @param authors
	 * @return a list of dto authors.
	 */
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

	/**
	 * Converts a title model class to title dto class.
	 * @param title
	 * @return a title dto object.
	 */
	private TitleDto convertToTitleDto(Title title) {
		if (title == null)
			throw new NullPointerException("Cannot find this Title");
		return new TitleDto(title.getTitleID(), title.getName(), title.getPubDate(), convertToAuthorDto(title.getAuthor()));
	}

	/**
	 * Converts an archive model class to archive dto class.
	 * @param i
	 * @return an archive dto object.
	 */
	private ArchiveDto convertToArchiveDto(Archive i) {
		if (i == null) {
			throw new IllegalArgumentException("There is no such Item!");
		}
		Status mystatus = i.getStatus();
		TitleDto title = convertToTitleDto(i.getTitle());
		return new ArchiveDto(mystatus, i.getItemBarcode(), title, i.getBooking());
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
