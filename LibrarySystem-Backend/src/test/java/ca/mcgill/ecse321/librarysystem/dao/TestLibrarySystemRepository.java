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
	private CalendarRepository calendarRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private LibrarySystemRepository librarySystemRepository;
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private TitleRepository titleRepository;
	@Autowired
	private ItemRepository itemRepository;
	
	@AfterEach
	public void clearDatabase() {
		// Clear the library system, calendar and address in the persistence. 
		itemRepository.deleteAll();
		titleRepository.deleteAll();
		authorRepository.deleteAll();
		librarySystemRepository.deleteAll();
		addressRepository.deleteAll();
		calendarRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadLibrarySystem() {
		Calendar calendar = new Calendar();
		Address busAddress = new Address();
		calendarRepository.save(calendar);
		addressRepository.save(busAddress);
		
		LibrarySystem ls = new LibrarySystem(busAddress, calendar);
		librarySystemRepository.save(ls);
		
		String systemID = ls.getSystemID();
	
		ls = null;
		
		ls = librarySystemRepository.findBysystemID(systemID);
		assertNotNull(ls);
		assertEquals(systemID, ls.getSystemID());
	}
	
	@Test
	public void testPersistAndLoadLibrarySystemWithCalendarAndAddress() {
		Calendar calendar = new Calendar();
		Address busAddress = new Address();
		calendarRepository.save(calendar);
		addressRepository.save(busAddress);
		
		LibrarySystem ls = new LibrarySystem(busAddress, calendar);
		librarySystemRepository.save(ls);
		
		
		String systemID = ls.getSystemID();
		String calendarID = calendar.getCalendarID();
		String addressID = busAddress.getAddressID();
		
		ls = null;
		calendar = null;
		busAddress = null;
		
		
		ls = librarySystemRepository.findBysystemID(systemID);
		calendar = ls.getCalendar();
		busAddress = ls.getBusinessaddress();
		assertNotNull(ls);
		assertNotNull(calendar);
		assertNotNull(busAddress);
		
		assertEquals(systemID, ls.getSystemID());
	    assertEquals(calendarID, ls.getCalendar().getCalendarID());
	    assertEquals(addressID, ls.getBusinessaddress().getAddressID());
	}


}
