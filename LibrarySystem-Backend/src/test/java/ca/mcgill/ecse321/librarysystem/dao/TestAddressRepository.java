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
public class TestAddressRepository {
	
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
		itemRepository.deleteAll();
		titleRepository.deleteAll();
		authorRepository.deleteAll();
		librarySystemRepository.deleteAll();
		addressRepository.deleteAll();
		calendarRepository.deleteAll();
	}
	
	
	@Test
	public void testPersistAndLoadAddress() {
		int civicNumber = 4609;
		String street = "Sherbrooke";
		String city = "Montreal";
		String postalCode = "H7T 2N6";
		String province = "Quebec";
		String country = "Canada";
		Address address = new Address();
		address.setCivicNumber(civicNumber);
		address.setStreet(street);
		address.setCity(city);
		address.setPostalCode(postalCode);
		address.setProvince(province);
		address.setCountry(country);
		
		addressRepository.save(address);
		
		String addressID = address.getAddressID();
		
		address = null;
		
		address = addressRepository.findByaddressID(addressID);
		assertNotNull(address);
		assertEquals(addressID, address.getAddressID());
		assertEquals(civicNumber, address.getCivicNumber());
		assertEquals(street, address.getStreet());
		assertEquals(city, address.getCity());
		assertEquals(postalCode, address.getPostalCode());
		assertEquals(province, address.getProvince());
		assertEquals(country, address.getCountry());
		
	}
	
	@Test
	public void testPersistAndLoadAddressWithLibrarySystem() {
		LibrarySystem ls = new LibrarySystem();
		librarySystemRepository.save(ls);
		
		// Address Attributes
		int civicNumber = 4609;
		String street = "Sherbrooke";
		String city = "Montreal";
		String postalCode = "H7T 2N6";
		String province = "Quebec";
		String country = "Canada";
		Address address = new Address();
		address.setCivicNumber(civicNumber);
		address.setStreet(street);
		address.setCity(city);
		address.setPostalCode(postalCode);
		address.setProvince(province);
		address.setCountry(country);
		address.setLibrarySystem(ls);
		addressRepository.save(address);
				
		String addressID = address.getAddressID();
		String systemID = ls.getSystemID();
		
		address = null;
		
		address = addressRepository.findBysystemID(systemID);
		assertNotNull(address);
		assertEquals(addressID, address.getAddressID());
		assertEquals(civicNumber, address.getCivicNumber());
		assertEquals(street, address.getStreet());
		assertEquals(city, address.getCity());
		assertEquals(postalCode, address.getPostalCode());
		assertEquals(province, address.getProvince());
		assertEquals(country, address.getCountry());
		assertEquals(systemID, address.getLibrarySystem().getSystemID());
	}
	
	@Test
	public void testPersistAndLoadAddressWithCivicNumber() {
		// Address Attributes
		int civicNumber = 4609;
		String street = "Sherbrooke";
		String city = "Montreal";
		String postalCode = "H7T 2N6";
		String province = "Quebec";
		String country = "Canada";
		Address address = new Address();
		address.setCivicNumber(civicNumber);
		address.setStreet(street);
		address.setCity(city);
		address.setPostalCode(postalCode);
		address.setProvince(province);
		address.setCountry(country);
		addressRepository.save(address);
		
		String addressID = address.getAddressID();
		
		address = null;
		
		address = addressRepository.findBycivicNumber(civicNumber);
		assertNotNull(address);
		assertEquals(addressID, address.getAddressID());
		assertEquals(civicNumber, address.getCivicNumber());
		assertEquals(street, address.getStreet());
		assertEquals(city, address.getCity());
		assertEquals(postalCode, address.getPostalCode());
		assertEquals(province, address.getProvince());
		assertEquals(country, address.getCountry());
	}
	
	@Test
	public void testPersistAndLoadAddressWithStreet() {
		// Address Attributes
		int civicNumber = 4609;
		String street = "Sherbrooke";
		String city = "Montreal";
		String postalCode = "H7T 2N6";
		String province = "Quebec";
		String country = "Canada";
		Address address = new Address();
		address.setCivicNumber(civicNumber);
		address.setStreet(street);
		address.setCity(city);
		address.setPostalCode(postalCode);
		address.setProvince(province);
		address.setCountry(country);
		addressRepository.save(address);
		
		String addressID = address.getAddressID();
		
		address = null;
		
		address = addressRepository.findBystreet(street);
		assertNotNull(address);
		assertEquals(addressID, address.getAddressID());
		assertEquals(civicNumber, address.getCivicNumber());
		assertEquals(street, address.getStreet());
		assertEquals(city, address.getCity());
		assertEquals(postalCode, address.getPostalCode());
		assertEquals(province, address.getProvince());
		assertEquals(country, address.getCountry());
	}
	
	@Test
	public void testPersistAndLoadAddressWithCity() {
		// Address Attributes
		int civicNumber = 4609;
		String street = "Sherbrooke";
		String city = "Montreal";
		String postalCode = "H7T 2N6";
		String province = "Quebec";
		String country = "Canada";
		Address address = new Address();
		address.setCivicNumber(civicNumber);
		address.setStreet(street);
		address.setCity(city);
		address.setPostalCode(postalCode);
		address.setProvince(province);
		address.setCountry(country);
		addressRepository.save(address);
		
		String addressID = address.getAddressID();
		
		address = null;
		
		address = addressRepository.findBycity(city);
		assertNotNull(address);
		assertEquals(addressID, address.getAddressID());
		assertEquals(civicNumber, address.getCivicNumber());
		assertEquals(street, address.getStreet());
		assertEquals(city, address.getCity());
		assertEquals(postalCode, address.getPostalCode());
		assertEquals(province, address.getProvince());
		assertEquals(country, address.getCountry());
	}
	
	@Test
	public void testPersistAndLoadAddressWitPostalCode() {
		// Address Attributes
		int civicNumber = 4609;
		String street = "Sherbrooke";
		String city = "Montreal";
		String postalCode = "H7T 2N6";
		String province = "Quebec";
		String country = "Canada";
		Address address = new Address();
		address.setCivicNumber(civicNumber);
		address.setStreet(street);
		address.setCity(city);
		address.setPostalCode(postalCode);
		address.setProvince(province);
		address.setCountry(country);
		addressRepository.save(address);
		
		String addressID = address.getAddressID();
		
		address = null;
		
		address = addressRepository.findBypostalCode(postalCode);
		assertNotNull(address);
		assertEquals(addressID, address.getAddressID());
		assertEquals(civicNumber, address.getCivicNumber());
		assertEquals(street, address.getStreet());
		assertEquals(city, address.getCity());
		assertEquals(postalCode, address.getPostalCode());
		assertEquals(province, address.getProvince());
		assertEquals(country, address.getCountry());
	}

	@Test
	public void testPersistAndLoadAddressWithProvince() {
		// Address Attributes
		int civicNumber = 4609;
		String street = "Sherbrooke";
		String city = "Montreal";
		String postalCode = "H7T 2N6";
		String province = "Quebec";
		String country = "Canada";
		Address address = new Address();
		address.setCivicNumber(civicNumber);
		address.setStreet(street);
		address.setCity(city);
		address.setPostalCode(postalCode);
		address.setProvince(province);
		address.setCountry(country);
		addressRepository.save(address);
		
		String addressID = address.getAddressID();
		
		address = null;
		
		address = addressRepository.findByprovince(province);
		assertNotNull(address);
		assertEquals(addressID, address.getAddressID());
		assertEquals(civicNumber, address.getCivicNumber());
		assertEquals(street, address.getStreet());
		assertEquals(city, address.getCity());
		assertEquals(postalCode, address.getPostalCode());
		assertEquals(province, address.getProvince());
		assertEquals(country, address.getCountry());
	}
	
	@Test
	public void testPersistAndLoadAddressWithCountry() {
		// Address Attributes
		int civicNumber = 4609;
		String street = "Sherbrooke";
		String city = "Montreal";
		String postalCode = "H7T 2N6";
		String province = "Quebec";
		String country = "Canada";
		Address address = new Address();
		address.setCivicNumber(civicNumber);
		address.setStreet(street);
		address.setCity(city);
		address.setPostalCode(postalCode);
		address.setProvince(province);
		address.setCountry(country);
		addressRepository.save(address);
		
		String addressID = address.getAddressID();
		
		address = null;
		
		address = addressRepository.findBycountry(country);
		assertNotNull(address);
		assertEquals(addressID, address.getAddressID());
		assertEquals(civicNumber, address.getCivicNumber());
		assertEquals(street, address.getStreet());
		assertEquals(city, address.getCity());
		assertEquals(postalCode, address.getPostalCode());
		assertEquals(province, address.getProvince());
		assertEquals(country, address.getCountry());
	}
}
