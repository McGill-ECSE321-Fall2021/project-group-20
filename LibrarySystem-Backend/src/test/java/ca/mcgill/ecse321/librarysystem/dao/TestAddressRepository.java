package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

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
	public void testPersistAndExistByID() {
		String civicNumber = "4609";
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
		
		assertTrue(addressRepository.existsByAddressID(address.getAddressID()));
	}
	
	@Test
	public void testPersistAndExistByLibrary() {
		String civicNumber = "4609";
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
		
		Calendar c = new Calendar();
		calendarRepository.save(c);
		
		LibrarySystem ls = new LibrarySystem(address, c);
		librarySystemRepository.save(ls);
		
		addressRepository.save(address);
		
		assertTrue(addressRepository.existsByLibrarySystem(ls));
	}
	
	@Test
	public void testPersistAndExistByStringAddress() {
		String civicNumber = "4609";
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
		
		assertTrue(addressRepository.existsByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(civicNumber, street, city, postalCode, province, country));
	}
	
	
	@Test
	public void testPersistAndLoadAddress() {
		String civicNumber = "4609";
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
		
		address = addressRepository.findByAddressID(addressID);
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
		// Address Attributes
		String civicNumber = "4609";
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
		
		Calendar c = new Calendar();
		calendarRepository.save(c);
		
		LibrarySystem ls = new LibrarySystem(address, c);
		librarySystemRepository.save(ls);
		
		addressRepository.save(address);
				
		String addressID = address.getAddressID();
		String systemID = ls.getSystemID();
		
		address = null;
		
		address = addressRepository.findAddressByLibrarySystem(ls);
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
		String civicNumber = "4609";
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
		
		List<Address> addresses = addressRepository.findAddressByCivicNumber(civicNumber);
		assertNotNull(addresses);
		assertEquals(addressID, addresses.get(0).getAddressID());
		assertEquals(civicNumber, addresses.get(0).getCivicNumber());
		assertEquals(street, addresses.get(0).getStreet());
		assertEquals(city, addresses.get(0).getCity());
		assertEquals(postalCode, addresses.get(0).getPostalCode());
		assertEquals(province, addresses.get(0).getProvince());
		assertEquals(country, addresses.get(0).getCountry());
	}
	
	@Test
	public void testPersistAndLoadAddressWithStreet() {
		// Address Attributes
		String civicNumber = "4609";
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
		
		List<Address> addresses = addressRepository.findAddressByStreet(street);
		assertNotNull(addresses);
		assertEquals(addressID, addresses.get(0).getAddressID());
		assertEquals(civicNumber, addresses.get(0).getCivicNumber());
		assertEquals(street, addresses.get(0).getStreet());
		assertEquals(city, addresses.get(0).getCity());
		assertEquals(postalCode, addresses.get(0).getPostalCode());
		assertEquals(province, addresses.get(0).getProvince());
		assertEquals(country, addresses.get(0).getCountry());
	}
	
	@Test
	public void testPersistAndLoadAddressWithCity() {
		// Address Attributes
		String civicNumber = "4609";
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
		
		List<Address> addresses = addressRepository.findAddressByCity(city);
		assertNotNull(addresses);
		assertEquals(addressID, addresses.get(0).getAddressID());
		assertEquals(civicNumber, addresses.get(0).getCivicNumber());
		assertEquals(street, addresses.get(0).getStreet());
		assertEquals(city, addresses.get(0).getCity());
		assertEquals(postalCode, addresses.get(0).getPostalCode());
		assertEquals(province, addresses.get(0).getProvince());
		assertEquals(country, addresses.get(0).getCountry());
	}
	
	@Test
	public void testPersistAndLoadAddressWitPostalCode() {
		// Address Attributes
		String civicNumber = "4609";
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
		
		List<Address> addresses = addressRepository.findAddressByPostalCode(postalCode);
		assertNotNull(addresses);
		assertEquals(addressID, addresses.get(0).getAddressID());
		assertEquals(civicNumber, addresses.get(0).getCivicNumber());
		assertEquals(street, addresses.get(0).getStreet());
		assertEquals(city, addresses.get(0).getCity());
		assertEquals(postalCode, addresses.get(0).getPostalCode());
		assertEquals(province, addresses.get(0).getProvince());
		assertEquals(country, addresses.get(0).getCountry());
	}

	@Test
	public void testPersistAndLoadAddressWithProvince() {
		// Address Attributes
		String civicNumber = "4609";
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
		
		List<Address> addresses = addressRepository.findAddressByProvince(province);
		assertNotNull(addresses);
		assertEquals(addressID, addresses.get(0).getAddressID());
		assertEquals(civicNumber, addresses.get(0).getCivicNumber());
		assertEquals(street, addresses.get(0).getStreet());
		assertEquals(city, addresses.get(0).getCity());
		assertEquals(postalCode, addresses.get(0).getPostalCode());
		assertEquals(province, addresses.get(0).getProvince());
		assertEquals(country, addresses.get(0).getCountry());
	}
	
	@Test
	public void testPersistAndLoadAddressWithStringAddress() {
		// Address Attributes
		String civicNumber = "4609";
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

		List<Address> addresses = addressRepository
				.findAddressByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(civicNumber, street, city,
						postalCode, province, country);
		assertNotNull(addresses);
		assertEquals(addressID, addresses.get(0).getAddressID());
		assertEquals(civicNumber, addresses.get(0).getCivicNumber());
		assertEquals(street, addresses.get(0).getStreet());
		assertEquals(city, addresses.get(0).getCity());
		assertEquals(postalCode, addresses.get(0).getPostalCode());
		assertEquals(province, addresses.get(0).getProvince());
		assertEquals(country, addresses.get(0).getCountry());
	}
	
	@Test
	public void testPersistAndLoadAddressWithCountry() {
		// Address Attributes
		String civicNumber = "4609";
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
		
		List<Address> addresses = addressRepository.findAddressByCountry(country);
		assertNotNull(addresses);
		assertEquals(addressID, addresses.get(0).getAddressID());
		assertEquals(civicNumber, addresses.get(0).getCivicNumber());
		assertEquals(street, addresses.get(0).getStreet());
		assertEquals(city, addresses.get(0).getCity());
		assertEquals(postalCode, addresses.get(0).getPostalCode());
		assertEquals(province, addresses.get(0).getProvince());
		assertEquals(country, addresses.get(0).getCountry());
	}
}
