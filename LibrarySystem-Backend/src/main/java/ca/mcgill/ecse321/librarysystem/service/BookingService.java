package ca.mcgill.ecse321.librarysystem.service;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.BookingRepository;
import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.Employee;
import ca.mcgill.ecse321.librarysystem.model.Event;
import ca.mcgill.ecse321.librarysystem.model.Hour;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.User;
import ca.mcgill.ecse321.librarysystem.model.Booking.BookingType;
import ca.mcgill.ecse321.librarysystem.model.Booking;

@Service
public class BookingService {
	
	@Autowired
	private BookingRepository bookingRepository;

	
	@Transactional
	public Booking createBooking() {
		Booking newBooking = new Booking();
		bookingRepository.save(newBooking);
		return newBooking;
	}
	
	
	@Transactional
	public Booking createBooking(Date aStartDate, Date aEndDate, BookingType aType, Item aitem, User aUser) {
		if (aStartDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		if (aEndDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		if (aType != BookingType.Borrow || aType != BookingType.Reservation) throw new IllegalArgumentException ("Please enter a valid booking type");
		if (aitem == null) throw new IllegalArgumentException ("Please enter valid item");
		if (aUser == null) throw new IllegalArgumentException ("Please enter a valid user");
		Booking myBooking = new Booking (aStartDate, aEndDate,aType,aitem,aUser);
		bookingRepository.save(myBooking);
		return myBooking;
		
	}
	
	

	@Transactional
	public Booking createBooking(String aBookingId,Date aStartDate, Date aEndDate, BookingType aType, Item aitem, User aUser) {
		if (aStartDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		if (aEndDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		if (aType != BookingType.Borrow || aType != BookingType.Reservation) throw new IllegalArgumentException ("Please enter a valid booking type");
		if (aitem == null) throw new IllegalArgumentException ("Please enter valid item");
		if (aUser == null) throw new IllegalArgumentException ("Please enter a valid user");
		if (aBookingId == null || aBookingId.length() == 0) throw new IllegalArgumentException ("Please enter valid Booking ID");
		Booking myBooking = new Booking (aBookingId,aStartDate, aEndDate,aType,aitem,aUser);
		bookingRepository.save(myBooking);
		return myBooking;
		
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
		if (aUser == null) throw new IllegalArgumentException("Please enter valid item");
		List<Booking> bookings = bookingRepository.findBookingByUser(aUser);
		if (bookings.size()== 0 ) throw new NullPointerException("Bookings not found");
		return bookings;
	}
	
	
	@Transactional
	public List<Booking> getBookingListbyBookingType (BookingType aBookingType){
		if (aBookingType != BookingType.Borrow || aBookingType != BookingType.Reservation) throw new IllegalArgumentException ("Please enter a valid booking type");
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
	public boolean deleteBookingByStartDate(Date aStartDate) {
		if (aStartDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		List<Booking> bookings = bookingRepository.findBookingByStartDate(aStartDate);
		if (bookings.size() == 0) throw new NullPointerException("Bookings not found");
		for (Booking booking : bookings) {
			bookingRepository.delete(booking);
			booking.delete();
		}
		List<Booking> myBooking = bookingRepository.findBookingByStartDate(aStartDate);
		return (myBooking.size() == 0);
	}
	
	@Transactional
	public boolean deleteBookingByEndDate (Date aEndDate) {
		if (aEndDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		List<Booking> bookings = bookingRepository.findBookingByEndDate(aEndDate);
		if (bookings.size() == 0) throw new NullPointerException("Bookings not found");
		for (Booking booking : bookings) {
			bookingRepository.delete(booking);
			booking.delete();
		}
		List<Booking> myBooking = bookingRepository.findBookingByStartDate(aEndDate);
		return (myBooking.size() == 0);
	}
	
	@Transactional
	public void deleteBookingByitem (Item aitem) {
	if (aitem == null) throw new IllegalArgumentException ("Please enter valid item");
	Booking myBooking = bookingRepository.findBookingByItem(aitem);
	bookingRepository.delete(myBooking);
	myBooking.delete();

		
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
	public boolean deleteBookingbyBookingType(BookingType aType) {
		if (aType != BookingType.Borrow || aType != BookingType.Reservation) throw new IllegalArgumentException ("Please enter a valid booking type");
		List<Booking> bookings = bookingRepository.findBookingByType(aType);
		if (bookings.size() == 0) throw new IllegalArgumentException("Bookings not found");
		for (Booking booking : bookings) {
			bookingRepository.delete(booking);
			booking.delete();
		}
		List<Booking> myBooking = bookingRepository.findBookingByType(aType);
		return (myBooking.size() == 0);
		
	}
	
	@Transactional
	public boolean updateStartDateBookingbyItem(Item aItem, Date updatedStartDate) {
		if (aItem == null) throw new IllegalArgumentException ("Please enter a valid item");
		if (updatedStartDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		Booking myBooking = bookingRepository.findBookingByItem(aItem);
		return myBooking.setStartDate(updatedStartDate);


	}
	
	@Transactional
	public boolean updateEndDateBookingbyItem(Item aItem, Date updatedEndDate) {
		if (aItem == null) throw new IllegalArgumentException ("Please enter a valid item");
		if (updatedEndDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		Booking myBooking = bookingRepository.findBookingByItem(aItem);
		return myBooking.setStartDate(updatedEndDate);


	}
	
	@Transactional
	public boolean updateBookingTypeofBookingbyItem(Item aItem, BookingType updatedType) {
		if (aItem == null) throw new IllegalArgumentException ("Please enter a valid item");
		if (updatedType != BookingType.Borrow || updatedType != BookingType.Reservation) throw new IllegalArgumentException ("Please enter a valid booking type");
		Booking myBooking = bookingRepository.findBookingByItem(aItem);
		return myBooking.setType(updatedType);
		

	}
	
	@Transactional
	public boolean updateUserofBookingByItem (Item aItem , User updatedUser) {
		if (aItem == null) throw new IllegalArgumentException ("Please enter a valid item");
		if (updatedUser == null) throw new IllegalArgumentException ("Please enter a valid user");
		Booking myBooking = bookingRepository.findBookingByItem(aItem);
		return myBooking.setUser(updatedUser);

	}
	@Transactional
	public boolean updateStartDateBookingbyID(String  BID, Date updatedStartDate) {
		if (BID == null || BID.length() == 0) throw new IllegalArgumentException ("Please enter valid Booking ID");
		if (updatedStartDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		Booking myBooking = bookingRepository.findBookingByBookingID(BID);
		return myBooking.setStartDate(updatedStartDate);


	}
	
	@Transactional
	public boolean updateEndDateBookingbyID (String BID, Date updatedEndDate) {
		if (BID == null || BID.length() == 0) throw new IllegalArgumentException ("Please enter valid Booking ID");
		if (updatedEndDate == null) throw new IllegalArgumentException("Please enter valid date yyyy-[m]m-[d]d");
		Booking myBooking = bookingRepository.findBookingByBookingID(BID);
		return myBooking.setStartDate(updatedEndDate);


	}
	
	@Transactional
	public boolean updateBookingTypeofBookingbyID(String  BID, BookingType updatedType) {
		if (BID == null || BID.length() == 0) throw new IllegalArgumentException ("Please enter valid Booking ID");
		if (updatedType != BookingType.Borrow || updatedType != BookingType.Reservation) throw new IllegalArgumentException ("Please enter a valid booking type");
		Booking myBooking = bookingRepository.findBookingByBookingID(BID);
		return myBooking.setType(updatedType);
		

	}
	
	@Transactional
	public boolean updateUserofBookingByID (String  BID, User updatedUser) {
		if (BID == null || BID.length() == 0) throw new IllegalArgumentException ("Please enter valid Booking ID");
		if (updatedUser == null) throw new IllegalArgumentException ("Please enter a valid user");
		Booking myBooking = bookingRepository.findBookingByBookingID(BID);
		return myBooking.setUser(updatedUser);

	}
}
