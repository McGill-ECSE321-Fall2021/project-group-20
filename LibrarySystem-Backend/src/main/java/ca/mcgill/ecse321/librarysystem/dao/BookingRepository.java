package ca.mcgill.ecse321.librarysystem.dao;
import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Booking.BookingType;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.User;

public interface BookingRepository extends CrudRepository <Booking , String>{
	Booking findBybookingID(String bookingId);
	List<Booking> findBystartDate (Date startDate);
	List<Booking> findByendDate (Date endDate);
	Booking findByItembooked(Item itembooked);
	List<Booking> findByUser(User libraryCardID);
	List<Booking> findByType(BookingType type);
//  List<Booking> findByStatus (Item status);
//	List<Booking> findByfirstNameAndlastName (User firstName, User lastName );
//	List<Booking> findByfirstName (User firstName);
//	List<Booking> findBylastName (User lastName);
//	List<Booking> findBylibraryCardID (User libraryCardID);
	
	
	
	
	
	
	
	
	
	
	
	

}



