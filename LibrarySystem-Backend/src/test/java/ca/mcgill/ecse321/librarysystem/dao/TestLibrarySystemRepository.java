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

import ca.mcgill.ecse321.librarysystem.model.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestLibrarySystemRepository {

	@Autowired
	private LibrarySystemRepository librarySystemRepository;
	
	@Autowired
	private CalendarRepository calendarRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@AfterEach
	public void clearDatabase() {
		// Clear the library system, calendar and address in the persistence. 
		librarySystemRepository.deleteAll();
		calendarRepository.deleteAll();
		addressRepository.deleteAll();
	}
	
	
	@Test
	public void testPersistAndLoadLibrarySystem() {
		String systemID = "LS01";
		String calendarID = "F2021";
		String addressID = "McGILL";
		Integer civicNumber = 0001;
		String street = "Sherbrook";
		String city = "Montreal";
		String postalCode = "XXXXXXX";
		String province = "Quebec";
		String country = "Canada";
		
		// Instantiate calendar
		Calendar calendar = new Calendar(calendarID);
		// Instantiate address
		Address businessAddress = new Address(addressID, civicNumber, street, city,
				postalCode, province, country);
		// Instantiate library system
		//LibrarySystem libSys = new LibrarySystem();
		LibrarySystem librarySystem = new LibrarySystem(null, businessAddress, calendar);
		librarySystem.setSystemID(systemID);
		librarySystemRepository.save(librarySystem);
		
		librarySystem = null;
		
		librarySystem = librarySystemRepository.findBysystemID(systemID);
		assertNotNull(librarySystem);
		assertNotNull(librarySystem.getSystemID());
		assertNotNull(librarySystem.getCalendar());
		assertEquals(systemID, librarySystem.getSystemID());
		assertEquals(calendar, librarySystem.getCalendar());
		assertEquals(businessAddress, librarySystem.getBusinessaddress());
	}

	
	
}
