package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
	
	/*
	 * Clears the database after each test.
	 */
	@AfterEach
	public void clearDatabase() {
		itemRepository.deleteAll();
		titleRepository.deleteAll();
		authorRepository.deleteAll();
		librarySystemRepository.deleteAll();
		addressRepository.deleteAll();
		calendarRepository.deleteAll();
	}
	
	/*
	 * Test 1: Persists an address to the database, then query the database by the addressID.
	 */
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
	
	/*
	 * Test 2: Persists an address to the database, then query the database by the library system.
	 */
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
		
//		assertTrue(addressRepository.existsByLibrarySystem(ls));
	}
	
	/*
	 * Test 3: Persists an address to the database, then queries the database based on the civic number,
	 * 		   street, city, postal code, province and country.
	 */
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
	
	/*
	 * Test 4: Persists an address and checks if the queried address by addressID is the same as the persisted address.
	 */
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
	
	/*
	 * Test 4: Persists an address and checks if the queried address by library system is the same as the persisted address.
	 */
//	@Test
//	public void testPersistAndLoadAddressWithLibrarySystem() {
//		// Address Attributes
//		String civicNumber = "4609";
//		String street = "Sherbrooke";
//		String city = "Montreal";
//		String postalCode = "H7T 2N6";
//		String province = "Quebec";
//		String country = "Canada";
//		Address address = new Address();
//		address.setCivicNumber(civicNumber);
//		address.setStreet(street);
//		address.setCity(city);
//		address.setPostalCode(postalCode);
//		address.setProvince(province);
//		address.setCountry(country);
//		addressRepository.save(address);
//
//		Calendar c = new Calendar();
//		calendarRepository.save(c);
//
//		LibrarySystem ls = new LibrarySystem(address, c);
//		librarySystemRepository.save(ls);
//
//		addressRepository.save(address);
//
//		String addressID = address.getAddressID();
//		String systemID = ls.getSystemID();
//
//		address = null;
//
//		address = addressRepository.findAddressByLibrarySystem(ls);
//		assertNotNull(address);
//		assertEquals(addressID, address.getAddressID());
//		assertEquals(civicNumber, address.getCivicNumber());
//		assertEquals(street, address.getStreet());
//		assertEquals(city, address.getCity());
//		assertEquals(postalCode, address.getPostalCode());
//		assertEquals(province, address.getProvince());
//		assertEquals(country, address.getCountry());
////		assertEquals(systemID, address.getLibrarySystem().getSystemID());
//	}
	
	/*
	 * Test 4: Persists an address and checks if the queried address by civic number is the same as the persisted address.
	 */
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
	
	/*
	 * Test 4: Persists an address and checks if the queried address by street is the same as the persisted address.
	 */
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
	
	/*
	 * Test 4: Persists an address and checks if the queried address by city is the same as the persisted address.
	 */
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
	
	/*
	 * Test 4: Persists an address and checks if the queried address by postal code is the same as the persisted address.
	 */
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
	
	/*
	 * Test 4: Persists an address and checks if the queried address by province is the same as the persisted address.
	 */
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
	
	/*
	 * Test 4: Persists an address and checks if the queried address by country is the same as the persisted address.
	 */
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
	
	/*
	 * Test 4: Persists an address and checks if the queried address by the full address info is the same as the persisted address.
	 */
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
}
