package ca.mcgill.ecse321.librarysystem.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import org.apache.tomcat.jni.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestLibrarySystemService {

    @Mock
    private LibrarySystemRepository librarySystemRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CalendarRepository calendarRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HourRepository hourRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private LibrarySystemService librarySystemService;

    /**
     * Setting up the mock library system.
     */
    private static final String systemID = "SYSTEM1";

    /**
     * Setting up the mock business address.
     */
    private static final String ADDRESS_ID = "ADDRESS1";
    private static final String CIVIC_NUM = "1000";
    private static final String STREET = "Sherbrooke";
    private static final String CITY = "Montreal";
    private static final String POSTAL_CODE = "ABC DEF";
    private static final String PROVINCE = "QC";
    private static final String COUNTRY = "Canada";

    /**
     * Setting up the mock business calendar
     */
    private static final String businessCalendarID = "CALENDAR1";
    private static final String weekday = "MONDAY";
    private static final Time startTime = new Time(9, 0, 0);
    private static final Time endTime = new Time(16, 0, 0);

    /**
     * Setting up the mock user
     */
    private static final String ADDRESSID_1 = "ADDRESS1";
    private static final String CIVIC_1 = "1";
    private static final String STREET_1 = "University St";
    private static final String CITY_1 = "Montreal";
    private static final String POST_1 = "A1B C2C";
    private static final String PROV_1 = "QC";
    private static final String COUNTRY_1 = "Canada";

    private static final String FIRSTNAME_1 = "John";
    private static final String LASTNAME_1 = "Doe";
    private static final String EMAIL_1 = "a@a.ca";
    private static final String USERNAME_1 = "johndoe";
    private static final String PASS_1 = "12345678";
    private static final int ID_1 = 1;

    private static List<User> list_users = new ArrayList<>();
    private static List<Item> list_items = new ArrayList<>();

    /**
     * Setting up the mock item.
     */
    private static final long itemBarcode = 123;


    @BeforeEach
    public void setMockOutput() {
        lenient().when(librarySystemRepository.findLibrarySystemBySystemID(anyString())).thenAnswer((InvocationOnMock
        invocation) -> {
            if (invocation.getArgument(0).equals(systemID)) {
                LibrarySystem ls = new LibrarySystem();

                // Create Business Address
                Address businessAddress = new Address();
                businessAddress.setCountry(COUNTRY);
                businessAddress.setAddressID(ADDRESS_ID);
                businessAddress.setCity(CITY);
                businessAddress.setStreet(STREET);
                businessAddress.setProvince(PROVINCE);
                businessAddress.setPostalCode(POSTAL_CODE);
                businessAddress.setCivicNumber(CIVIC_NUM);

                // Create User
                User u1 = new User();
                list_users.add(u1);

                // Create Item
                Item i1 = new Item();
                i1.setItemBarcode(itemBarcode);
                list_items.add(i1);

                // Create calendar
                Calendar calendar = new Calendar();
                calendar.setCalendarID(businessCalendarID);
                Hour hour = new Hour();
                hour.setCalendar(calendar);
                hour.setWeekday(weekday);
                hour.setStartTime(startTime);
                hour.setEndTime(endTime);
                calendar.addHour(hour);

                ls.setBusinessaddress(businessAddress);
                ls.setSystemID(systemID);
                ls.setCalendar(calendar);

                return ls;
            }
            return null;
        });

        lenient().when(librarySystemRepository.findLibrarySystemByUsers(any(User.class))).thenAnswer((InvocationOnMock
                invocation) -> {
            if (invocation.getArgument(0) != null) {
                LibrarySystem ls = new LibrarySystem();

                // Create Business Address
                Address businessAddress = new Address();
                businessAddress.setCountry(COUNTRY);
                businessAddress.setAddressID(ADDRESS_ID);
                businessAddress.setCity(CITY);
                businessAddress.setStreet(STREET);
                businessAddress.setProvince(PROVINCE);
                businessAddress.setPostalCode(POSTAL_CODE);
                businessAddress.setCivicNumber(CIVIC_NUM);

                // Create User
                User u1 = new User();
                list_users.add(u1);

                // Create Item
                Item i1 = new Item();
                i1.setItemBarcode(itemBarcode);
                list_items.add(i1);

                // Create calendar
                Calendar calendar = new Calendar();
                calendar.setCalendarID(businessCalendarID);
                Hour hour = new Hour();
                hour.setCalendar(calendar);
                hour.setWeekday(weekday);
                hour.setStartTime(startTime);
                hour.setEndTime(endTime);
                calendar.addHour(hour);

                ls.setBusinessaddress(businessAddress);
                ls.setSystemID(systemID);
                ls.setCalendar(calendar);
                ls.addUser(u1);
                ls.addItem(i1);

                return ls;
            }
            return null;
        });

        lenient().when(librarySystemRepository.findLibrarySystemByBusinessaddress(any(Address.class))).thenAnswer
                ((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0) != null) {
                        LibrarySystem ls = new LibrarySystem();

                        // Create Business Address
                        Address businessAddress = new Address();
                        businessAddress.setCountry(COUNTRY);
                        businessAddress.setAddressID(ADDRESS_ID);
                        businessAddress.setCity(CITY);
                        businessAddress.setStreet(STREET);
                        businessAddress.setProvince(PROVINCE);
                        businessAddress.setPostalCode(POSTAL_CODE);
                        businessAddress.setCivicNumber(CIVIC_NUM);

                        // Create User
                        User u1 = new User();
                        list_users.add(u1);

                        // Create Item
                        Item i1 = new Item();
                        i1.setItemBarcode(itemBarcode);
                        list_items.add(i1);

                        // Create calendar
                        Calendar calendar = new Calendar();
                        calendar.setCalendarID(businessCalendarID);
                        Hour hour = new Hour();
                        hour.setCalendar(calendar);
                        hour.setWeekday(weekday);
                        hour.setStartTime(startTime);
                        hour.setEndTime(endTime);
                        calendar.addHour(hour);

                        ls.setBusinessaddress(businessAddress);
                        ls.setSystemID(systemID);
                        ls.setCalendar(calendar);
                        ls.addItem(i1);
                        ls.addUser(u1);

                        return ls;
                    }
                    return null;
                });

        lenient().when(librarySystemRepository.findLibrarySystemByItems(any(Item.class))).thenAnswer
                ((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0) != null) {
                        LibrarySystem ls = new LibrarySystem();

                        // Create Business Address
                        Address businessAddress = new Address();
                        businessAddress.setCountry(COUNTRY);
                        businessAddress.setAddressID(ADDRESS_ID);
                        businessAddress.setCity(CITY);
                        businessAddress.setStreet(STREET);
                        businessAddress.setProvince(PROVINCE);
                        businessAddress.setPostalCode(POSTAL_CODE);
                        businessAddress.setCivicNumber(CIVIC_NUM);

                        // Create User
                        User u1 = new User();
                        list_users.add(u1);

                        // Create Item
                        Item i1 = new Item();
                        i1.setItemBarcode(itemBarcode);
                        list_items.add(i1);

                        // Create calendar
                        Calendar calendar = new Calendar();
                        calendar.setCalendarID(businessCalendarID);
                        Hour hour = new Hour();
                        hour.setCalendar(calendar);
                        hour.setWeekday(weekday);
                        hour.setStartTime(startTime);
                        hour.setEndTime(endTime);
                        calendar.addHour(hour);

                        ls.setBusinessaddress(businessAddress);
                        ls.setSystemID(systemID);
                        ls.setCalendar(calendar);
                        ls.addItem(i1);
                        ls.addUser(u1);

                        return ls;
                    }
                    return null;
                });

        lenient().when(librarySystemRepository.findLibrarySystemByCalendar(any(Calendar.class))).thenAnswer
                ((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0) != null) {
                        LibrarySystem ls = new LibrarySystem();

                        // Create Business Address
                        Address businessAddress = new Address();
                        businessAddress.setCountry(COUNTRY);
                        businessAddress.setAddressID(ADDRESS_ID);
                        businessAddress.setCity(CITY);
                        businessAddress.setStreet(STREET);
                        businessAddress.setProvince(PROVINCE);
                        businessAddress.setPostalCode(POSTAL_CODE);
                        businessAddress.setCivicNumber(CIVIC_NUM);

                        // Create User
                        User u1 = new User();
                        list_users.add(u1);

                        // Create Item
                        Item i1 = new Item();
                        i1.setItemBarcode(itemBarcode);
                        list_items.add(i1);

                        // Create calendar
                        Calendar calendar = new Calendar();
                        calendar.setCalendarID(businessCalendarID);
                        Hour hour = new Hour();
                        hour.setCalendar(calendar);
                        hour.setWeekday(weekday);
                        hour.setStartTime(startTime);
                        hour.setEndTime(endTime);
                        calendar.addHour(hour);

                        ls.setBusinessaddress(businessAddress);
                        ls.setSystemID(systemID);
                        ls.setCalendar(calendar);
                        ls.addItem(i1);
                        ls.addUser(u1);

                        return ls;
                    }
                    return null;
                });

        lenient().when(librarySystemRepository.existsById(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            return (invocation.getArgument(0).equals(systemID));
        });

        Answer<?> returnParamAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(calendarRepository.save(any(Calendar.class))).thenAnswer(returnParamAsAnswer);
        lenient().when(hourRepository.save(any(Hour.class))).thenAnswer(returnParamAsAnswer);
        lenient().when(addressRepository.save(any(Address.class))).thenAnswer(returnParamAsAnswer);
        lenient().when(userRepository.save(any(User.class))).thenAnswer(returnParamAsAnswer);
        lenient().when(itemRepository.save(any(Item.class))).thenAnswer(returnParamAsAnswer);
        lenient().when(librarySystemRepository.save(any(LibrarySystem.class))).thenAnswer(returnParamAsAnswer);
    }

    @Test
    public void testCreateLibrarySystemSuccessful() {
        LibrarySystem ls = null;

        try {
            ls = librarySystemService.createLibrarySystem();
        } catch (Exception msg) {
            fail(msg.getMessage());
        }
        assertNotNull(ls);
    }

    @Test
    public void testCreateLibrarySystemWithAddressSuccessful() {
        LibrarySystem ls = null;
        Address businessAddress = new Address();
        businessAddress.setCountry(COUNTRY);
        businessAddress.setAddressID(ADDRESS_ID);
        businessAddress.setCity(CITY);
        businessAddress.setStreet(STREET);
        businessAddress.setProvince(PROVINCE);
        businessAddress.setPostalCode(POSTAL_CODE);
        businessAddress.setCivicNumber(CIVIC_NUM);
        try {
            ls = librarySystemService.createLibrarySystem(businessAddress);
        } catch (Exception msg) {
            fail(msg.getMessage());
        }
        assertNotNull(ls);
        assertEquals(COUNTRY, ls.getBusinessaddress().getCountry());
        assertEquals(ADDRESS_ID, ls.getBusinessaddress().getAddressID());
        assertEquals(CITY, ls.getBusinessaddress().getCity());
        assertEquals(STREET, ls.getBusinessaddress().getStreet());
        assertEquals(PROVINCE, ls.getBusinessaddress().getProvince());
        assertEquals(POSTAL_CODE, ls.getBusinessaddress().getPostalCode());
        assertEquals(CIVIC_NUM, ls.getBusinessaddress().getCivicNumber());
    }

    @Test
    public void changeAddressWithLibraryAndAddressSuccessful() {
        LibrarySystem ls = new LibrarySystem();

        // Create Business Address
        Address businessAddress = new Address();
        businessAddress.setCountry(COUNTRY);
        businessAddress.setAddressID(ADDRESS_ID);
        businessAddress.setCity(CITY);
        businessAddress.setStreet(STREET);
        businessAddress.setProvince(PROVINCE);
        businessAddress.setPostalCode(POSTAL_CODE);
        businessAddress.setCivicNumber(CIVIC_NUM);

        // Create User
        User u1 = new User();
        list_users.add(u1);

        // Create Item
        Item i1 = new Item();
        i1.setItemBarcode(itemBarcode);
        list_items.add(i1);

        // Create calendar
        Calendar calendar = new Calendar();
        calendar.setCalendarID(businessCalendarID);
        Hour hour = new Hour();
        hour.setCalendar(calendar);
        hour.setWeekday(weekday);
        hour.setStartTime(startTime);
        hour.setEndTime(endTime);
        calendar.addHour(hour);

        ls.setBusinessaddress(businessAddress);
        ls.setSystemID(systemID);
        ls.setCalendar(calendar);
        ls.addItem(i1);
        ls.addUser(u1);

        // New Address
        Address newBusinessAddress = new Address();
        newBusinessAddress.setCountry("Canada");
        newBusinessAddress.setAddressID("ADDRESS2");
        newBusinessAddress.setCity("Montreal");
        newBusinessAddress.setStreet("Cesar");
        newBusinessAddress.setProvince("QC");
        newBusinessAddress.setPostalCode("ABD EFG");
        newBusinessAddress.setCivicNumber("0001");
        try {
            ls = librarySystemService.changeAddress(ls, newBusinessAddress);
        } catch (IllegalArgumentException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(ls);
        assertEquals("Canada", ls.getBusinessaddress().getCountry());
        assertEquals("ADDRESS2", ls.getBusinessaddress().getAddressID());
        assertEquals("Montreal", ls.getBusinessaddress().getCity());
        assertEquals("Cesar", ls.getBusinessaddress().getStreet());
        assertEquals("QC", ls.getBusinessaddress().getProvince());
        assertEquals("ABD EFG", ls.getBusinessaddress().getPostalCode());
        assertEquals("0001", ls.getBusinessaddress().getCivicNumber());
    }

    @Test
    public void changeAddressWithLibraryAndAddressFailNullArgs() {
        LibrarySystem ls = new LibrarySystem();

        // Create Business Address
        Address businessAddress = new Address();
        businessAddress.setCountry(COUNTRY);
        businessAddress.setAddressID(ADDRESS_ID);
        businessAddress.setCity(CITY);
        businessAddress.setStreet(STREET);
        businessAddress.setProvince(PROVINCE);
        businessAddress.setPostalCode(POSTAL_CODE);
        businessAddress.setCivicNumber(CIVIC_NUM);

        // Create User
        User u1 = new User();
        list_users.add(u1);

        // Create Item
        Item i1 = new Item();
        i1.setItemBarcode(itemBarcode);
        list_items.add(i1);

        // Create calendar
        Calendar calendar = new Calendar();
        calendar.setCalendarID(businessCalendarID);
        Hour hour = new Hour();
        hour.setCalendar(calendar);
        hour.setWeekday(weekday);
        hour.setStartTime(startTime);
        hour.setEndTime(endTime);
        calendar.addHour(hour);

        ls.setBusinessaddress(businessAddress);
        ls.setSystemID(systemID);
        ls.setCalendar(calendar);
        ls.addItem(i1);
        ls.addUser(u1);

        // New Address
        Address newBusinessAddress = new Address();
        newBusinessAddress.setCountry("Canada");
        newBusinessAddress.setAddressID("ADDRESS2");
        newBusinessAddress.setCity("Montreal");
        newBusinessAddress.setStreet("Cesar");
        newBusinessAddress.setProvince("QC");
        newBusinessAddress.setPostalCode("ABD EFG");
        newBusinessAddress.setCivicNumber("0001");
        String error = null;
        try {
            ls = librarySystemService.changeAddress(ls, null);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertEquals("Please enter a valid address", error);
    }

    @Test
    public void testDeleteLibrarySystemSuccessful() {
        boolean deleted = false;
        try {
            deleted = librarySystemService.deleteLibrarySystem(systemID);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertEquals(true, deleted);
    }

    @Test
    public void testDeleteLibrarySystemFailInvalidID() {
        boolean deleted = false;
        String error = null;
        try {
            deleted = librarySystemService.deleteLibrarySystem(null);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertEquals("SystemID cannot be null", error);
        assertEquals(false, deleted);
    }

    @Test
    public void testDeleteLibrarySystemFailInvalidLibrary() {
        boolean deleted = false;
        String error = null;
        try {
            deleted = librarySystemService.deleteLibrarySystem("123");
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertEquals("Library System not found", error);
        assertEquals(false, deleted);
    }

    @Test
    public void testGetLibrarySystemSuccessful() {
        LibrarySystem ls = null;
        try {
            ls = librarySystemService.getLibrarySystem(systemID);
        } catch (Exception msg) {
            fail(msg.getMessage());
        }
        assertNotNull(ls);
        assertEquals(systemID, ls.getSystemID());
    }

    @Test
    public void testGetLibrarySystemFailInvalidID() {
        LibrarySystem ls = null;
        String error = null;
        try {
            ls = librarySystemService.getLibrarySystem(null);
        } catch (Exception msg) {
            error = msg.getMessage();
        }
        assertNull(ls);
        assertEquals("SystemID cannot be null", error);
    }

    @Test
    public void testGetLibrarySystemFailInvalidLibrary() {
        LibrarySystem ls = null;
        String error = null;
        try {
            ls = librarySystemService.getLibrarySystem("123");
        } catch (Exception msg) {
            error = msg.getMessage();
        }
        assertNull(ls);
        assertEquals("Library System not found for this system ID", error);
    }
}
