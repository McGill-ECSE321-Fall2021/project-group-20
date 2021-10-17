package ca.mcgill.ecse321.librarysystem.dao;
import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Booking.BookingType;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.User;

public interface BookingRepository extends CrudRepository <Booking , String>{
	Booking findBookingByBookingID(String bookingId);
	List<Booking> findBookingByStartDate (Date startDate);
	List<Booking> findBookingByEndDate (Date endDate);
	Booking findBookingByItem(Item item);
	List<Booking> findBookingByUser(User user);
	List<Booking> findBookingByType(BookingType type);
	boolean existsByBookingID(String bookingID);
	boolean existsByItem(Item item);
}



