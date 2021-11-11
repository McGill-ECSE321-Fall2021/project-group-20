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
	    public List<BookingDto> getAllBookings() {
	        List<BookingDto> bookingList = new ArrayList<>();
	        for (Booking b : bookingService.getAllBookings()) {
	        	getAllBookings().add(convertToDto(b));
	        }
	        return bookingList;
	    }
	
	 
	 @GetMapping(value = {"/booking/id","/booking/id/"})
	 public BookingDto getBookingbyId(@RequestParam String bID) {
		 return convertToDto(bookingService.getBookingbyId(bID));
	 }
	 
	 
	 @PostMapping(value= {"/booking/create","/booking/create/"})
	 public BookingDto createBooking(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  String startDate,@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  String endDate,@RequestParam String type,@RequestParam String barcode,@RequestParam String LibraryId) {
		 Item i = itemService.getItemById(Long.valueOf(barcode));
		 Customer c = customerService.getCustomer(Integer.valueOf(LibraryId));
		 return convertToDto(bookingService.createBooking(Date.valueOf(startDate),Date.valueOf(endDate),Booking.BookingType.valueOf(type),(i),(c)));

	 }
	 
	 @GetMapping(value = {"/booking/startDate", "/booking/startDate/"})
	 public List<BookingDto> getBookingListbystartDate(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  String startDate){
		 List<BookingDto> bookingDtos = new ArrayList<>();
		 for (Booking b : bookingService.getBookingListbystartDate(Date.valueOf(startDate))) {
			 bookingDtos.add(convertToDto(b));
		 }
		 return bookingDtos;
	 }
	 
	 @GetMapping(value = {"/booking/endDate", "/booking/endDate/"})
	 public List<BookingDto> getBookingListbyendDate(@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  String endDate){
		 List<BookingDto> bookingDtos = new ArrayList<>();
		 for (Booking b : bookingService.getBookingListbystartDate(Date.valueOf(endDate))) {
			 bookingDtos.add(convertToDto(b));
		 }
		 return bookingDtos;
	 }
	 
	 @GetMapping(value = {"/booking/item","/booking/item/"})
	 public BookingDto getBookingbyItem (@RequestParam String barcode) {
		 Item i = itemService.getItemById(Long.valueOf(barcode));
		 return convertToDto(bookingService.getBookingbyItem(i));
	 }
	 
	 @GetMapping(value = {"/booking/user/", "/booking/user/"})
	 public List<BookingDto> getBookingListbyUser(@RequestParam String LibraryId){
		 List<BookingDto> bookingDtos = new ArrayList<>();
		 Customer c = customerService.getCustomer(Integer.valueOf(LibraryId));
		 for (Booking b : bookingService.getBookingListbyUser(c)) {
			 bookingDtos.add(convertToDto(b));
		 }
		 return bookingDtos;
	 }
	 
	 @GetMapping(value = {"/booking/type", "/booking/type/"})
	 public List<BookingDto> getBookingListbyBookingType(@RequestParam String type){
		 List<BookingDto> bookingDtos = new ArrayList<>();
		 for (Booking b : bookingService.getBookingListbyBookingType(Booking.BookingType.valueOf(type))) {
			 bookingDtos.add(convertToDto(b));
		 }
		 return bookingDtos;
	 }
	 
	 @DeleteMapping(value = {"/booking/id", "/booking/id/"})
	 public void deleteBookingbyID(@RequestParam String Bid) {
		 bookingService.deleteBookingbyID(Bid);
		 return;
	 }
	 
	 @PutMapping(value = {"/booking/return/item", "/booking/return/item/"})
	 public void returnItemByItem(@RequestParam String Barcode) {
		 Item i = itemService.getItemById(Long.valueOf(Barcode));
		 bookingService.returnItemByItem(i);
		 return ;
		
	 }
	 

	 
	 @DeleteMapping(value = {"/booking/User", "/booking/User/"})
	 public boolean deleteBookingByUser(@RequestParam String LibID) {
		 Customer c = customerService.getCustomer(Integer.valueOf(LibID));
		 return bookingService.deleteBookingByUser(c);
	 }
	 
	 @PutMapping(value = {"/booking/updatebyItem/startDate", "/booking/updatebyItem/startDate/"})
	 public BookingDto updateStartDateBookingbyItem(@RequestParam String Barcode, @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  String startDate) {
		 Item i = itemService.getItemById(Long.valueOf(Barcode));
		 if (bookingService.updateStartDateBookingbyItem(i, Date.valueOf(startDate))) {
			 return convertToDto(bookingService.getBookingbyItem(i));
		 }
		 return null;
	 }
	 
	 @PutMapping(value = {"/booking/updatebyItem/endDate", "/booking/updatebyItem/endDate/"})
	 public BookingDto updateEndDateBookingbyItem(@RequestParam String Barcode, @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  String endDate) {
		 Item i = itemService.getItemById(Long.valueOf(Barcode));
		 if (bookingService.updateEndDateBookingbyItem(i, Date.valueOf(endDate))) {
			 return convertToDto(bookingService.getBookingbyItem(i));
		 }
		 return null;
	 }
	 
	 @PutMapping(value = {"/booking/updatebyItem/type", "/booking/updatebyItem/type/"})
	 public BookingDto updateBookingTypeofBookingbyItem(@RequestParam String Barcode, @RequestParam String type) {
		 Item i = itemService.getItemById(Long.valueOf(Barcode));
		 if (bookingService.updateBookingTypeofBookingbyItem(i, Booking.BookingType.valueOf(type))) {
			 return convertToDto(bookingService.getBookingbyItem(i));
		 }
		 return null;
	 }
	 
	 @PutMapping(value = {"/booking/updatebyItem/user", "/booking/updatebyItem/user/"})
	 public BookingDto updateUserofBookingByItem(@RequestParam String Barcode, @RequestParam String LibID) {
		 Item i = itemService.getItemById(Long.valueOf(Barcode));
		 Customer c = customerService.getCustomer(Integer.valueOf(LibID));
		 if (bookingService.updateUserofBookingByItem(i, c)) {
			 return convertToDto(bookingService.getBookingbyItem(i));
		 }
		 return null;
	 }
	 
	 @PutMapping(value = {"/booking/updatebyID/startDate", "/booking/updatebyID/startDate/"})
	 public BookingDto updateStartDateBookingbyID(@RequestParam String BID, @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  String startDate) {
		 if (bookingService.updateStartDateBookingbyID(BID, Date.valueOf(startDate))) {
			 return convertToDto(bookingService.getBookingbyId(BID));
		 }
		 return null;
	 }
	 
	 @PutMapping(value = {"/booking/updatebyID/endDate", "/booking/updatebyID/endDate/"})
	 public BookingDto updateEndDateBookingbyID(@RequestParam String BID, @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  String endDate) {
		 if (bookingService.updateEndDateBookingbyID(BID, Date.valueOf(endDate))) {
			 return convertToDto(bookingService.getBookingbyId(BID));
		 }
		 return null;
	 }
	 
	 
	 @PutMapping(value = {"/booking/updatebyID/type", "/booking/updatebyID/type/"})
	 public BookingDto updateBookingTypeofBookingbyID(@RequestParam String BID, @RequestParam String type) {
		 if (bookingService.updateBookingTypeofBookingbyID(BID, Booking.BookingType.valueOf(type))) {
			 return convertToDto(bookingService.getBookingbyId(BID));
		 }
		 return null;
	 }
	 @PutMapping(value = {"/booking/updatebyID/user", "/booking/updatebyID/user/"})
	 public BookingDto updateUserofBookingByID(@RequestParam String BID, @RequestParam String LIBID) {
		 Customer c = customerService.getCustomer(Integer.valueOf(LIBID));
		 if (bookingService.updateUserofBookingByID(BID, c)) {
			 return convertToDto(bookingService.getBookingbyId(BID));
		 }
		 return null;
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
