package ca.mcgill.ecse321.librarysystem.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.mcgill.ecse321.librarysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.librarysystem.dao.EmployeeRepository;
import ca.mcgill.ecse321.librarysystem.dao.ItemRepository;
import ca.mcgill.ecse321.librarysystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.BookingRepository;
import ca.mcgill.ecse321.librarysystem.model.Booking.BookingType;

@Service
public class BookingService {
	
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ItemService itemService;
	
//	@Transactional
//	public Booking createBooking() {
//		Booking newBooking = new Booking();
//		bookingRepository.save(newBooking);
//		return newBooking;
//	}
//	
	
	@Transactional
	public Booking createBooking(Date aStartDate, Date aEndDate, BookingType aType, Item aitem, User aUser) {
		if (aStartDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		if (aEndDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		if (aType != BookingType.Borrow && aType != BookingType.Reservation) throw new IllegalArgumentException ("Please enter a valid booking type");
		if (aitem == null) throw new IllegalArgumentException ("Please enter valid item");
		if (aitem.hasBooking()) throw new IllegalArgumentException("Cannot book a borrowed/reserved item");
		if (aitem.getClass().getName().equals("Newspaper")) throw new IllegalArgumentException("Cannot book a newspaper/journal");
		if (aUser == null) throw new IllegalArgumentException ("Please enter a valid user");
		if (aUser.numberOfUserbooking() >= 5) throw new IllegalArgumentException("Cannot book more than 5 items at a time");
		if (aUser.getDemeritPts() >= 3) throw new IllegalArgumentException("Cannot book because of demerit points");
		Booking myBooking = new Booking (aStartDate, aEndDate,aType,aitem,aUser);
		bookingRepository.save(myBooking);
		return myBooking;
		
	}
	
	


	@Transactional
	public List<Booking> getAllBookings() {
		List<Booking> bookings = new ArrayList<>();
		for (Booking b : bookingRepository.findAll()) {
			bookings.add(b);
		}
		return bookings;
	}
	
	@Transactional
	public Booking getBookingbyId (String Bid) {
		if (Bid == null || Bid.length() == 0) throw new IllegalArgumentException ("Please enter valid Booking ID");
		return (Booking) bookingRepository.findBookingByBookingID(Bid);
	}
	
	@Transactional
	public List<Booking> getBookingListbystartDate (Date aStartDate){
		if (aStartDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		List<Booking> bookings = bookingRepository.findBookingByStartDate(aStartDate);
		if (bookings.size()== 0 ) throw new NullPointerException("Bookings not found");
		return bookings;
	}
	
	
	@Transactional
	public List<Booking> getBookingListbyendDate (Date aEndDate){
		if (aEndDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		List<Booking> bookings = bookingRepository.findBookingByEndDate(aEndDate);
		if (bookings.size()== 0 ) throw new NullPointerException("Bookings not found");
		return bookings;
	}
	
	@Transactional
	public Booking getBookingbyItem (Item getItem) {
		if (getItem == null) throw new IllegalArgumentException("Please enter a valid item");
		return (Booking) bookingRepository.findBookingByItem(getItem);
	}
	
	@Transactional
	public List<Booking> getBookingListbyUser (User aUser){
		if (aUser == null) throw new IllegalArgumentException("Please enter valid user");
		List<Booking> bookings = bookingRepository.findBookingByUser(aUser);
		if (bookings.size()== 0 ) throw new NullPointerException("Bookings not found");
		return bookings;
	}
	
	
	@Transactional
	public List<Booking> getBookingListbyBookingType (BookingType aBookingType){
		if (aBookingType != BookingType.Borrow && aBookingType != BookingType.Reservation) throw new IllegalArgumentException ("Please enter a valid booking type");
		List<Booking> bookings = bookingRepository.findBookingByType(aBookingType);
		if (bookings.size()== 0 ) throw new NullPointerException("Bookings not found");
		return bookings;
	}
	
	
	@Transactional 
	public void deleteBookingbyID (String BookingID) {
		if (BookingID == null || BookingID.length() == 0) throw new IllegalArgumentException ("Please enter valid Booking ID");
		Booking myBooking = bookingRepository.findBookingByBookingID(BookingID);
		bookingRepository.delete(myBooking);
		myBooking.delete();
		return;
	}

	

	@Transactional
	public void returnItemByItem(Item item) {
		if (item == null) throw new IllegalArgumentException("Please enter a valid Item");
		Booking myBooking = bookingRepository.findBookingByItem(item);
		if (myBooking == null) throw new NullPointerException("Cannot find Booking with Item");
		Date date = new Date();
		if (myBooking.getEndDate().before(new Date())) {
			if (myBooking.getUser().getClass().getName().equals("Customer")) customerService.changeDemeritPts(myBooking.getUser().getLibraryCardID(), 1);
			else employeeService.changeDemeritPts(myBooking.getUser().getLibraryCardID(), 1);
		}
		bookingRepository.delete(myBooking);
		myBooking.delete();
		item.setStatus(Item.Status.Available);
		itemRepository.save(item);
	}


	
	@Transactional
	public boolean deleteBookingByUser (User aUser) {
		if (aUser == null) throw new IllegalArgumentException ("Please enter a valid user");
		List<Booking> bookings = bookingRepository.findBookingByUser(aUser);
		if (bookings.size() == 0) throw new IllegalArgumentException("Bookings not found");
		for (Booking booking : bookings) {
			bookingRepository.delete(booking);
			booking.delete();
			
		}
		List<Booking> myBooking = bookingRepository.findBookingByUser(aUser);
		return (myBooking.size() == 0);

	}
	


	@Transactional
	public boolean updateStartDateBookingbyItem(Item aItem, Date updatedStartDate) {
		if (aItem == null) throw new IllegalArgumentException ("Please enter a valid item");
		if (updatedStartDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		Booking myBooking = bookingRepository.findBookingByItem(aItem);
		if (myBooking.setStartDate(updatedStartDate)) {
			bookingRepository.save(myBooking);
			return true;
		}
		return false;
	}
	
	@Transactional
	public boolean updateEndDateBookingbyItem(Item aItem, Date updatedEndDate) {
		if (aItem == null) throw new IllegalArgumentException ("Please enter a valid item");
		if (updatedEndDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		Booking myBooking = bookingRepository.findBookingByItem(aItem);
		if (myBooking.setEndDate(updatedEndDate)) {
			bookingRepository.save(myBooking);
			return true;
		}
		return false;


	}
	
	@Transactional
	public boolean updateBookingTypeofBookingbyItem(Item aItem, BookingType updatedType) {
		if (aItem == null) throw new IllegalArgumentException ("Please enter a valid item");
		if (updatedType != BookingType.Borrow && updatedType != BookingType.Reservation) throw new IllegalArgumentException ("Please enter a valid booking type");
		Booking myBooking = bookingRepository.findBookingByItem(aItem);
		if (myBooking.setType(updatedType)) {
			bookingRepository.save(myBooking);
			return true;
		}
		return false;
		

	}
	
	@Transactional
	public boolean updateUserofBookingByItem (Item aItem , User updatedUser) {
		if (aItem == null) throw new IllegalArgumentException ("Please enter a valid item");
		if (updatedUser == null) throw new IllegalArgumentException ("Please enter a valid user");
		Booking myBooking = bookingRepository.findBookingByItem(aItem);
		if (myBooking.setUser(updatedUser)) {
			bookingRepository.save(myBooking);
			return true;
		}
		return false;

	}
	@Transactional
	public boolean updateStartDateBookingbyID(String  BID, Date updatedStartDate) {
		if (BID == null || BID.length() == 0) throw new IllegalArgumentException ("Please enter valid Booking ID");
		if (updatedStartDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		Booking myBooking = bookingRepository.findBookingByBookingID(BID);
		if (myBooking.setStartDate(updatedStartDate)) {
			bookingRepository.save(myBooking);
			return true;
		}
		return false;


	}
	
	@Transactional
	public boolean updateEndDateBookingbyID (String BID, Date updatedEndDate) {
		if (BID == null || BID.length() == 0) throw new IllegalArgumentException ("Please enter valid Booking ID");
		if (updatedEndDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		Booking myBooking = bookingRepository.findBookingByBookingID(BID);
		if (myBooking.setStartDate(updatedEndDate)) {
			bookingRepository.save(myBooking);
			return true;
		}
		return false;


	}
	
	@Transactional
	public boolean updateBookingTypeofBookingbyID(String  BID, BookingType updatedType) {
		if (BID == null || BID.length() == 0) throw new IllegalArgumentException ("Please enter valid Booking ID");
		if (updatedType != BookingType.Borrow && updatedType != BookingType.Reservation) throw new IllegalArgumentException ("Please enter a valid booking type");
		Booking myBooking = bookingRepository.findBookingByBookingID(BID);
		if (myBooking.setType(updatedType)) {
			bookingRepository.save(myBooking);
			return true;
		}
		return false;
		

	}
	
	@Transactional
	public boolean updateUserofBookingByID (String  BID, User updatedUser) {
		if (BID == null || BID.length() == 0) throw new IllegalArgumentException ("Please enter valid Booking ID");
		if (updatedUser == null) throw new IllegalArgumentException ("Please enter a valid user");
		Booking myBooking = bookingRepository.findBookingByBookingID(BID);
		if (myBooking.setUser(updatedUser)) {
			bookingRepository.save(myBooking);
			return true;
		}
		return false;

	}
}
