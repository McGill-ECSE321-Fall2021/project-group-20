package ca.mcgill.ecse321.librarysystem.controller;
import ca.mcgill.ecse321.librarysystem.dto.AddressDto;
import ca.mcgill.ecse321.librarysystem.dto.AuthorDto;
import ca.mcgill.ecse321.librarysystem.dto.CalendarDto;
import ca.mcgill.ecse321.librarysystem.dto.CustomerDto;
import ca.mcgill.ecse321.librarysystem.dto.EmployeeDto;
import ca.mcgill.ecse321.librarysystem.dto.EventDto;
import ca.mcgill.ecse321.librarysystem.dto.HourDto;
import ca.mcgill.ecse321.librarysystem.dto.BookingDto;
import ca.mcgill.ecse321.librarysystem.dto.ItemDto;
import ca.mcgill.ecse321.librarysystem.dto.TitleDto;
import ca.mcgill.ecse321.librarysystem.dto.UserDto;
import ca.mcgill.ecse321.librarysystem.dto.BookingDto.BookingType;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.Customer;
import ca.mcgill.ecse321.librarysystem.model.Employee;
import ca.mcgill.ecse321.librarysystem.model.Event;
import ca.mcgill.ecse321.librarysystem.model.Booking;

import ca.mcgill.ecse321.librarysystem.model.Hour;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.model.User;
import ca.mcgill.ecse321.librarysystem.service.BookingService;
import ca.mcgill.ecse321.librarysystem.service.CalendarService;
import ca.mcgill.ecse321.librarysystem.service.CustomerService;
import ca.mcgill.ecse321.librarysystem.service.EmployeeService;
import ca.mcgill.ecse321.librarysystem.service.EventService;
import ca.mcgill.ecse321.librarysystem.service.HourService;
import ca.mcgill.ecse321.librarysystem.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class BookingRestController {

	@Autowired
	private HourService hourService;

	@Autowired
	private EventService eventService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private CalendarService calendarService;
	@Autowired
	private BookingService bookingService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private CustomerService customerService;

	 @GetMapping(value = {"/bookings", "/bookings/"})
	    public ResponseEntity getAllBookings() {
	        List<BookingDto> bookingList = new ArrayList<>();
			List<Booking> bookings;
			try {
				bookings = bookingService.getAllBookings();

				for (Booking b : bookings) {
					bookingList.add(convertToDto(b));
				}
				if (bookingList.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any bookings in system");
				return new ResponseEntity<>(bookingList, HttpStatus.OK);
			} catch (Exception msg) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
			}
	    }

	 @GetMapping(value = {"/booking/id","/booking/id/"})
	 public ResponseEntity getBookingbyId(@RequestParam String bID) {
		 try {
			 BookingDto bDto = convertToDto(bookingService.getBookingbyId(bID));
			 if (bDto == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find booking by ID");
			 return new ResponseEntity<>(bDto, HttpStatus.OK);
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	 }

	 @PostMapping(value= {"/booking/create","/booking/create/"})
	 public ResponseEntity createBooking(@RequestParam String startDate,@RequestParam String endDate,@RequestParam String type,@RequestParam String barcode,@RequestParam String LibraryId) {
		 try {
			 Item i = itemService.getItemById(Long.valueOf(barcode));
			 Customer c = customerService.getCustomer(Integer.valueOf(LibraryId));
			 String[] dateS = startDate.split("/");
			 Date s = Date.valueOf(dateS[2] + "-" + dateS[0] + "-" + dateS[1]);
			 String[] dateE = endDate.split("/");
			 Date e = Date.valueOf(dateE[2] + "-" + dateE[0] + "-" + dateE[1]);
			 Booking b = bookingService.createBooking(s, e, Booking.BookingType.valueOf(type), (i), (c));
			 if (b == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot create booking");
			 return new ResponseEntity<>(convertToDto(b), HttpStatus.OK);
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	 }

	 @GetMapping(value = {"/booking/startDate", "/booking/startDate/"})
	 public ResponseEntity getBookingListbystartDate(@RequestParam String startDate){
		 List<BookingDto> bookingDtos = new ArrayList<>();
		 List<Booking> bookings;
		 try {
			 String[] dateS = startDate.split("/");
			 Date d = Date.valueOf(dateS[2] + "-" + dateS[0] + "-" + dateS[1]);
			 bookings = bookingService.getBookingListbystartDate(d);
			 for (Booking b : bookings) {
				 bookingDtos.add(convertToDto(b));
			 }
			 if (bookingDtos.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find bookings by this date");
			 return new ResponseEntity<>(bookingDtos, HttpStatus.OK);
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	 }

	 @GetMapping(value = {"/booking/endDate", "/booking/endDate/"})
	 public ResponseEntity getBookingListbyendDate(@RequestParam String endDate){
		 List<BookingDto> bookingDtos = new ArrayList<>();
		 List<Booking> bookings;
		 try {
			 String[] dateS = endDate.split("/");
			 Date d = Date.valueOf(dateS[2] + "-" + dateS[0] + "-" + dateS[1]);
			 bookings = bookingService.getBookingListbyendDate(d);
			 for (Booking b : bookings) {
				 bookingDtos.add(convertToDto(b));
			 }
			 if (bookingDtos.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find bookings by this date");
			 return new ResponseEntity<>(bookingDtos, HttpStatus.OK);
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	 }

	 @GetMapping(value = {"/booking/item","/booking/item/"})
	 public ResponseEntity getBookingbyItem (@RequestParam String barcode) {
		 try {
			 Item i = itemService.getItemById(Long.valueOf(barcode));
			 Booking b = bookingService.getBookingbyItem(i);
			 if (b == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find booking by item");
			 return new ResponseEntity<>(convertToDto(b), HttpStatus.OK);
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	 }

	 @GetMapping(value = {"/booking/user/", "/booking/user/"})
	 public ResponseEntity getBookingListbyUser(@RequestParam String libraryId){
		 try {
			 List<BookingDto> bookingDtos = new ArrayList<>();
			 Customer c = customerService.getCustomer(Integer.valueOf(libraryId));
			 List<Booking> bookings = bookingService.getBookingListbyUser(c);
			 for (Booking b : bookings) {
				 bookingDtos.add(convertToDto(b));
			 }
			 if (bookingDtos.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find bookings by this user");
			 return new ResponseEntity<>(bookingDtos, HttpStatus.OK);
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	 }

	 @GetMapping(value = {"/booking/type", "/booking/type/"})
	 public ResponseEntity getBookingListbyBookingType(@RequestParam String type){
		 try {
			 List<BookingDto> bookingDtos = new ArrayList<>();
			 for (Booking b : bookingService.getBookingListbyBookingType(Booking.BookingType.valueOf(type))) {
				 bookingDtos.add(convertToDto(b));
			 }
			 if (bookingDtos.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find bookings by this type");
			 return new ResponseEntity<>(bookingDtos, HttpStatus.OK);
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	 }
	 
	 @DeleteMapping(value = {"/booking/id", "/booking/id/"})
	 public ResponseEntity deleteBookingbyID(@RequestParam String bid) {
		 try {
			 bookingService.deleteBookingbyID(bid);
			 return ResponseEntity.status(HttpStatus.OK).body("Booking has been deleted");
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	 }
	 
	 @PutMapping(value = {"/booking/return/item", "/booking/return/item/"})
	 public ResponseEntity returnItemByItem(@RequestParam String barcode) {
		 try {
			 Item i = itemService.getItemById(Long.valueOf(barcode));
			 bookingService.returnItemByItem(i);
			 return ResponseEntity.status(HttpStatus.OK).body("Item has been returned & booking removed");
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	 }
	 
	 @PutMapping(value = {"/booking/updatebyID/startDate", "/booking/updatebyID/startDate/"})
	 public ResponseEntity updateStartDateBookingbyID(@RequestParam String bid, String startDate) {
		 try {
			 String[] dateS = startDate.split("/");
			 Date d = Date.valueOf(dateS[2] + "-" + dateS[0] + "-" + dateS[1]);
			 if (bookingService.updateStartDateBookingbyID(bid, d)) {
				 return new ResponseEntity<>(convertToDto(bookingService.getBookingbyId(bid)), HttpStatus.OK);
			 }
			 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not update booking start date");
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	 }
	 
	 @PutMapping(value = {"/booking/updatebyID/endDate", "/booking/updatebyID/endDate/"})
	 public ResponseEntity updateEndDateBookingbyID(@RequestParam String bid, @RequestParam String endDate) {
		 try {
			 String[] dateS = endDate.split("/");
			 Date d = Date.valueOf(dateS[2] + "-" + dateS[0] + "-" + dateS[1]);
			 if (bookingService.updateEndDateBookingbyID(bid, d)) {
				 return new ResponseEntity<>(convertToDto(bookingService.getBookingbyId(bid)), HttpStatus.OK);
			 }
			 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not update booking end date");
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	 }

	 @PutMapping(value = {"/booking/updatebyID/type", "/booking/updatebyID/type/"})
	 public ResponseEntity updateBookingTypeofBookingbyID(@RequestParam String bid, @RequestParam String type) {
		 try {
			 if (bookingService.updateBookingTypeofBookingbyID(bid, Booking.BookingType.valueOf(type))) {
				 return new ResponseEntity<>(convertToDto(bookingService.getBookingbyId(bid)), HttpStatus.OK);
			 }
			 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not update booking type");
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	 }
	 @PutMapping(value = {"/booking/updatebyID/user", "/booking/updatebyID/user/"})
	 public ResponseEntity updateUserofBookingByID(@RequestParam String bid, @RequestParam String libId) {
		 try {
			 Customer c = customerService.getCustomer(Integer.valueOf(libId));
			 if (bookingService.updateUserofBookingByID(bid, c)) {
				 return new ResponseEntity<>(convertToDto(bookingService.getBookingbyId(bid)), HttpStatus.OK);
			 }
			 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not update booking user");
		 } catch (Exception msg) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		 }
	 }
	 
	 	private BookingDto convertToDto (Booking b) {
	 		if (b == null) throw new IllegalArgumentException("Cannot find this booking");
	 		return new BookingDto(b.getBookingID(),b.getStartDate(),b.getEndDate(),BookingDto.BookingType.valueOf(b.getType().name()),convertToItemDto(b.getItem()),convertToDto(b.getUser()));
	 			
	 		
	 		
	 	}
		private EventDto convertToDto (Event e ) {
			if (e == null) throw new IllegalArgumentException("Cannot find this event");
			return new EventDto(e.getEventID(),e.getName(),e.getEventDate(),convertToDto(e.getEventhour()));
		}
		
		private HourDto convertToDto(Hour h){
			if (h== null) throw new IllegalArgumentException("Cannot find this hour");
			return new HourDto(h.getWeekday(),h.getStartTime(),h.getEndTime(),convertToDto(h.getEmployee()),convertToDto(h.getCalendar()));
			
		}
		
		 private EmployeeDto convertToDto(Employee e) {
		        if (e == null) throw new IllegalArgumentException("Cannot find this Employee");
		        return new EmployeeDto(e.getLibraryCardID(), e.getIsLoggedIn(), e.getIsOnlineAcc(), e.getFirstName(),
		                e.getLastName(), e.getIsVerified(), e.getDemeritPts(), convertToDto(e.getAddress()),
		                EmployeeDto.Role.valueOf(e.getRole().name()), e.getOutstandingBalance());
		    }

		 private AddressDto convertToDto(Address a) {
		        if (a == null) throw new NullPointerException("Cannot find Address");
		        return new AddressDto(a.getCivicNumber(), a.getStreet(), a.getCity(), a.getPostalCode(), a.getProvince(), a.getCountry());
		    }
		
		private CalendarDto convertToDto (Calendar c) {
			if (c== null) throw new IllegalArgumentException("Cannot find this Calendar");
			return new CalendarDto(c.getCalendarID());
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

	    private ItemDto convertToItemDto(Item i) {
	        if (i == null) {
	            throw new IllegalArgumentException("There is no such Item!");
	        }
	        Status mystatus = i.getStatus();
	        ItemDto itemDto = new ItemDto(mystatus, i.getItemBarcode(), convertToTitleDto(i.getTitle()));
	        return itemDto;
	    }

	    private List<ItemDto> convertToItem(List<Item> i) {
	        List<ItemDto> itemDtoList = new ArrayList<ItemDto>();
	        for (Item c : i) {
	            ItemDto itemDto = convertToItemDto(c);
	            itemDtoList.add(itemDto);
	        }
	        return itemDtoList;
	    }
	    
	    private CustomerDto convertToDto(Customer c) {
	        if (c == null) throw new NullPointerException("Cannot find this Customer");
	        return new CustomerDto(c.getLibraryCardID(), c.getIsOnlineAcc(), c.getIsLoggedIn(), c.getFirstName(),
	                c.getLastName(), c.getIsVerified(), c.getDemeritPts(), convertToDto(c.getAddress()), c.getOutstandingBalance());
	    }

	    private UserDto convertToDto (User u ) {
	        if (u == null) throw new NullPointerException("Cannot find this User");
	        return new UserDto(u.getLibraryCardID(), u.getIsOnlineAcc(), u.getIsLoggedIn(), u.getFirstName(),
            u.getLastName(), u.getIsVerified(), u.getDemeritPts(), convertToDto(u.getAddress()), u.getOutstandingBalance());

	    }
	
}
