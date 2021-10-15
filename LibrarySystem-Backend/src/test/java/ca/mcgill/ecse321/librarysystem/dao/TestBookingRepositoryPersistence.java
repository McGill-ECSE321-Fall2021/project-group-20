package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Booking.BookingType;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest 
public class TestBookingRepositoryPersistence {

	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private HourRepository hourRepository;
	
	@AfterEach
	public void clearDatabase() {
		bookingRepository.deleteAll();
		eventRepository.deleteAll();
		hourRepository.deleteAll();
	}
	

	
	
	
	
	
	
	
	
	
	
}
