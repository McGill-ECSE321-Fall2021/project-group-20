package ca.mcgill.ecse321.librarysystem.dao;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Booking.BookingType;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.User;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "booking_data", path = "booking_data")
public interface BookingRepository extends CrudRepository <Booking , String>{
	//Find the booking by BookingID
	Booking findBookingByBookingID(String bookingId);
	
	//Find list of Bookings by start date
	List<Booking> findBookingByStartDate (Date startDate);
	
	//Find list of Bookings by end date
	List<Booking> findBookingByEndDate (Date endDate);
	
	//Find Booking by Item that was booked
	Booking findBookingByItem(Item item);
	
	//Find list of Bookings by user
	List<Booking> findBookingByUser(User user);
	
	//Find list of Bookings by Booking Type
	List<Booking> findBookingByType(BookingType type);
	
	//Booking with Booking ID in question exists
	boolean existsByBookingID(String bookingID);
	
	//Booking associated with a certain item exists
	boolean existsByItem(Item item);
}



