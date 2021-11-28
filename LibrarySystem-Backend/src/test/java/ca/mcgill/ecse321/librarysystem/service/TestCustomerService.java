package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.AddressRepository;
import ca.mcgill.ecse321.librarysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.librarysystem.dao.LibrarySystemRepository;
import ca.mcgill.ecse321.librarysystem.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestCustomerService {

    private static final String FIRSTNAME_1 = "John";
    private static final String LASTNAME_1 = "Doe";
    private static final String CIVIC_1 = "1";
    private static final String STREET_1 = "University St";
    private static final String CITY_1 = "Montreal";
    private static final String POST_1 = "A1B C2C";
    private static final String PROV_1 = "QC";
    private static final String COUNTRY_1 = "Canada";
    private static final String EMAIL_1 = "a@a.ca";
    private static final String USERNAME_1 = "johndoe";
    private static final String PASS_1 = "12345678";

    private static final String EMAIL_KEY = "a@b.com";
    private static final String USER_KEY = "johnnydoet";
    private static final int ID_KEY = 1;
    private static final int ID = 2;

    @Mock
    private CustomerRepository customerDao;

    @Mock
    private AddressRepository addressDao;

    @Mock
    private LibrarySystemRepository libraryDao;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setMockOutput() {
        // Basically intercept every possible repository call here!

        lenient().when(customerDao.existsByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0).equals(EMAIL_KEY));

        lenient().when(customerDao.existsByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0).equals(USER_KEY));

        lenient().when(customerDao.existsByLibraryCardID(anyInt())).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0).equals(ID) || invocation.getArgument(0).equals(3) || invocation.getArgument(0).equals(4) || invocation.getArgument(0).equals(5));

        lenient().when(customerDao.findUserByLibraryCardID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ID_KEY) || invocation.getArgument(0).equals(4)) {
                Address a = new Address(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
                Customer c = new Customer(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a);
                c.setEmail(EMAIL_KEY);
                c.setUsername(USER_KEY);
                c.setPassword(PASS_1);
                if (invocation.getArgument(0).equals(ID_KEY)) c.setLibraryCardID(ID_KEY);
                else c.setLibraryCardID(4);
                return c;
            }
            else if (invocation.getArgument(0).equals(3)) {
                Address a = new Address(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
                Customer c = new Customer(false, false, FIRSTNAME_1, LASTNAME_1, true, 0, a);
                c.setLibraryCardID(3);
                return c;
            }
            else if (invocation.getArgument(0).equals(5)) {
                Address a = new Address(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
                Customer c = new Customer(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a);
                c.setEmail("Test@Test.ca");
                c.setUsername("Tester");
                c.setPassword(PASS_1);
                c.setLibraryCardID(5);
                return c;
            }
            return null;
        });

        lenient().when(customerDao.findUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(USERNAME_1)) {
                Address a = new Address(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
                Customer c = new Customer(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a);
                c.setEmail(EMAIL_KEY);
                c.setUsername(USER_KEY);
                c.setPassword(PASS_1);
                return c;
            }
            return null;
        });

        lenient().when(customerDao.findUserByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EMAIL_1)) {
                Address a = new Address(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
                Customer c = new Customer(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a);
                c.setEmail(EMAIL_1);
                c.setUsername(USERNAME_1);
                c.setPassword(PASS_1);
                return c;
            }
            return null;
        });

        lenient().when(libraryDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            Address a = new Address(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
            Calendar c = new Calendar();
            LibrarySystem ls = new LibrarySystem(a, c);
            List<LibrarySystem> list = new ArrayList<>();
            list.add(ls);
            return list;
        });

        Answer<?> returnParamAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParamAsAnswer);
        lenient().when(customerDao.save(any(User.class))).thenAnswer(returnParamAsAnswer);
        lenient().when(addressDao.save(any(Address.class))).thenAnswer(returnParamAsAnswer);
    }

    @Test
    public void createCustomerSuccessful() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        try {
            c = customerService.createCustomer(FIRSTNAME_1,LASTNAME_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(c);
        assertEquals(c.getFirstName(), FIRSTNAME_1);
        assertEquals(c.getLastName(), LASTNAME_1);
        assertEquals(c.getAddress().getCivicNumber(), CIVIC_1);
        assertEquals(c.getAddress().getStreet(), STREET_1);
        assertEquals(c.getAddress().getCity(), CITY_1);
        assertEquals(c.getAddress().getPostalCode(), POST_1);
        assertEquals(c.getAddress().getProvince(), PROV_1);
        assertEquals(c.getAddress().getCountry(), COUNTRY_1);
    }

    @Test
    public void createCustomerFirstnameNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createCustomer(null,LASTNAME_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid name", error);
    }

    @Test
    public void createCustomerLastnameNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createCustomer(FIRSTNAME_1,null,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid name", error);
    }

    @Test
    public void createCustomerCivicNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createCustomer(FIRSTNAME_1,LASTNAME_1,null,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid address", error);
    }

    @Test
    public void createCustomerStreetNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createCustomer(FIRSTNAME_1,LASTNAME_1,CIVIC_1,null,CITY_1,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid address", error);
    }

    @Test
    public void createCustomerCityNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createCustomer(FIRSTNAME_1,LASTNAME_1,CIVIC_1,STREET_1,null,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid address", error);
    }

    @Test
    public void createCustomerPostNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createCustomer(FIRSTNAME_1,LASTNAME_1,CIVIC_1,STREET_1,CITY_1,null,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid address", error);
    }

    @Test
    public void createCustomerProvinceNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createCustomer(FIRSTNAME_1,LASTNAME_1,CIVIC_1,STREET_1,CITY_1,POST_1,null,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid address", error);
    }

    @Test
    public void createCustomerCountryNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createCustomer(FIRSTNAME_1,LASTNAME_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,null);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid address", error);
    }

    @Test
    public void createOnlineCustomerPass() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        try {
            c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(c);
        assertEquals(c.getFirstName(), FIRSTNAME_1);
        assertEquals(c.getLastName(), LASTNAME_1);
        assertEquals(c.getAddress().getCivicNumber(), CIVIC_1);
        assertEquals(c.getAddress().getStreet(), STREET_1);
        assertEquals(c.getAddress().getCity(), CITY_1);
        assertEquals(c.getAddress().getPostalCode(), POST_1);
        assertEquals(c.getAddress().getProvince(), PROV_1);
        assertEquals(c.getAddress().getCountry(), COUNTRY_1);
        assertEquals(c.getEmail(), EMAIL_1);
        assertEquals(c.getUsername(), USERNAME_1);
        assertEquals(c.getPassword(), PASS_1);
    }

    @Test
    public void createForeignCustomerPass() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        try {
            c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,"Laval",POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(c);
        assertEquals(c.getFirstName(), FIRSTNAME_1);
        assertEquals(c.getLastName(), LASTNAME_1);
        assertEquals(c.getAddress().getCivicNumber(), CIVIC_1);
        assertEquals(c.getAddress().getStreet(), STREET_1);
        assertEquals(c.getAddress().getCity(), "Laval");
        assertEquals(c.getAddress().getPostalCode(), POST_1);
        assertEquals(c.getAddress().getProvince(), PROV_1);
        assertEquals(c.getAddress().getCountry(), COUNTRY_1);
        assertEquals(c.getEmail(), EMAIL_1);
        assertEquals(c.getUsername(), USERNAME_1);
        assertEquals(c.getPassword(), PASS_1);
        assertEquals(c.getOutstandingBalance(), 50);
    }

    @Test
    public void createOnlineCustomerFirstnameNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createOnlineCustomer(null,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid name", error);
    }

    @Test
    public void createOnlineCustomerLastnameNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createOnlineCustomer(FIRSTNAME_1,null,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid name", error);
    }

    @Test
    public void createOnlineCustomerCivicNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,null,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid address", error);
    }

    @Test
    public void createOnlineCustomerStreetNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,null,CITY_1,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid address", error);
    }

    @Test
    public void createOnlineCustomerCityNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,null,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid address", error);
    }

    @Test
    public void createOnlineCustomerPostNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,null,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid address", error);
    }

    @Test
    public void createOnlineCustomerProvinceNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,null,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid address", error);
    }

    @Test
    public void createOnlineCustomerCountryNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,null);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid address", error);
    }

    @Test
    public void createOnlineCustomerEmailNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,null,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid email", error);
    }

    @Test
    public void createOnlineCustomerEmailExists() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_KEY,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Email in use, please enter a new email", error);
    }

    @Test
    public void createOnlineCustomerUserNull() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,null,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid username", error);
    }

    @Test
    public void createOnlineCustomerUserExists() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USER_KEY,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Username in use, please enter a new username", error);
    }

    @Test
    public void createOnlineCustomerPasswordTooShort() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        String error = null;
        try {
            c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,"1234",CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a password that is at least 8 characters long", error);
    }

    @Test
    public void modifyBalanceTest() {
        Customer c = null;
        try {
            c = customerService.modifyOutstandingBalance(1, 20);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(c);
        assertEquals(c.getOutstandingBalance(), 20);
    }

    @Test
    public void modifyBalanceFailID() {
        String error = null;
        Customer c = null;
        try {
            c = customerService.modifyOutstandingBalance(-10, 20);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid ID", error);
    }

    @Test
    public void modifyBalanceFailNoID() {
        Customer c = null;
        String error = null;
        try {
            c = customerService.modifyOutstandingBalance(10, -20);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Cannot find Customer with this ID", error);
    }

    @Test
    public void loginPass() {
        Customer c = null;
        try {
            c = customerService.login(USERNAME_1, PASS_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(c);
        assertTrue(c.getIsLoggedIn());
    }

    @Test
    public void loginPassfail() {
        String error = null;
        Customer c = null;
        try {
            c = customerService.login(USERNAME_1, "abc");
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Incorrect password!", error);
    }

    @Test
    public void loginNoUserfail() {
        String error = null;
        Customer c = null;
        try {
            c = customerService.login(10, PASS_1);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Cannot find Online Customer with this ID", error);
    }

    @Test
    public void loginEmailPass() {
        Customer c = null;
        try {
            c = customerService.loginByEmail(EMAIL_1, PASS_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(c);
        assertTrue(c.getIsLoggedIn());
    }

    @Test
    public void loginEmailfail() {
        String error = null;
        Customer c = null;
        try {
            c = customerService.loginByEmail(EMAIL_1, "abc");
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Incorrect password!", error);
    }

    @Test
    public void loginEmailIDfail() {
        String error = null;
        Customer c = null;
        try {
            c = customerService.loginByEmail(EMAIL_KEY, PASS_1);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Cannot find Online Customer with this email", error);
    }

    @Test
    public void loginIDPass() {
        Customer c = null;
        try {
            c = customerService.login(1, PASS_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(c);
        assertTrue(c.getIsLoggedIn());
    }

    @Test
    public void logoutIDPass() {
        boolean b = false;
        try {
            b = customerService.logout(1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertTrue(b);
    }

    @Test
    public void loginIDfail() {
        String error = null;
        Customer c = null;
        try {
            c = customerService.login(1, "abc");
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Incorrect password!", error);
    }

    @Test
    public void loginIDFailNoExist() {
        String error = null;
        Customer c = null;
        try {
            c = customerService.login(10, PASS_1);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Cannot find Online Customer with this ID", error);
    }

    @Test
    public void logoutPass() {
        boolean b = false;
        try {
            b = customerService.logout(USERNAME_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertTrue(b);
    }

    @Test
    public void logoutfail() {
        boolean b = false;
        String error = null;
        try {
            b = customerService.logout(USER_KEY);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertFalse(b);
        assertEquals("Cannot find Online Customer with this username", error);
    }

    @Test
    public void logoutEmailPass() {
        boolean b = false;
        try {
            b = customerService.logoutByEmail(EMAIL_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertTrue(b);
    }

    @Test
    public void logoutEmailfail() {
        boolean b = false;
        String error = null;
        try {
            b = customerService.logoutByEmail(EMAIL_KEY);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertFalse(b);
        assertEquals("Cannot find Online Customer with this email", error);
    }

    @Test
    public void deletePass() {
        Customer c = null;
        try {
            c = customerService.deleteCustomerByID(1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNull(c);
        assertEquals(customerService.getAllCustomers().size(), 0);
    }

    @Test
    public void deleteFailID() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        String error = null;
        try {
            c = customerService.deleteCustomerByID(-10);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNotNull(c);
        assertEquals("Please enter a valid libraryCardID", error);
    }

    @Test
    public void deleteFailMatch() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        String error = null;
        try {
            c = customerService.deleteCustomerByID(10);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNotNull(c);
        assertEquals("Cannot find Customer matching this libraryCardID", error);
    }
    
    @Test
    public void validateCustomerPass() {
        Customer c = null;
        try {
            c = customerService.validateCustomerByID(1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(c);
        assertTrue(c.getIsVerified());
    }

    @Test
    public void validateCustomerFailID() {
        String error = null;
        Customer c = null;
        try {
            c = customerService.validateCustomerByID(10);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Cannot find Customer with given ID", error);
    }

    @Test
    public void validateCustomerFailOutstandingBalance() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        c.setOutstandingBalance(50);
        Customer finalC = c;
        c = null;
        String error = null;

        lenient().when(customerDao.findUserByLibraryCardID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ID_KEY)) {
                return finalC;
            }
            return null;
        });
        try {
            c = customerService.validateCustomerByID(1);
        } catch (IllegalStateException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please ensure Customer pays outstanding balance before validating the account", error);
    }

    @Test
    public void getCustomerByIDTest() {
        Customer c = null;
        try {
            c = customerService.getCustomer(1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(c);
        assertEquals(c.getFirstName(), FIRSTNAME_1);
        assertEquals(c.getLastName(), LASTNAME_1);
        assertEquals(c.getAddress().getCivicNumber(), CIVIC_1);
        assertEquals(c.getAddress().getStreet(), STREET_1);
        assertEquals(c.getAddress().getCity(), CITY_1);
        assertEquals(c.getAddress().getPostalCode(), POST_1);
        assertEquals(c.getAddress().getProvince(), PROV_1);
        assertEquals(c.getAddress().getCountry(), COUNTRY_1);
    }

    @Test
    public void getCustomerByIDFailInput() {
        String error = null;
        Customer c = null;
        try {
            c = customerService.getCustomer(-10);
        } catch (IllegalArgumentException | NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid libraryCardID", error);
    }

    @Test
    public void getCustomerByIDNullPass() {
        Customer c = null;
        try {
            c = customerService.getCustomer(10);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNull(c);
    }

    @Test
    public void getCustomerByUsernamePass() {
        Customer c = null;
        try {
            c = customerService.getCustomer(USERNAME_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(c);
        assertEquals(c.getFirstName(), FIRSTNAME_1);
        assertEquals(c.getLastName(), LASTNAME_1);
        assertEquals(c.getAddress().getCivicNumber(), CIVIC_1);
        assertEquals(c.getAddress().getStreet(), STREET_1);
        assertEquals(c.getAddress().getCity(), CITY_1);
        assertEquals(c.getAddress().getPostalCode(), POST_1);
        assertEquals(c.getAddress().getProvince(), PROV_1);
        assertEquals(c.getAddress().getCountry(), COUNTRY_1);
    }

    @Test
    public void getCustomerByUsernameStringFail() {
        String error = null;
        Customer c = null;
        try {
            c = customerService.getCustomer("");
        } catch (IllegalArgumentException | NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid username", error);
    }

    @Test
    public void getCustomerByUsernameNullPass() {
        Customer c = null;
        try {
            c = customerService.getCustomer(10);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNull(c);
    }

    @Test
    public void getCustomersPass() {
        List<Customer> customerList = new ArrayList<>();
        lenient().when(customerDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
        List<Customer> customers = new ArrayList<>();
        Address a = new Address(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
        Customer cust1 = new Customer(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a);
        cust1.setEmail("a.c@a.com");
        cust1.setUsername("mikeym");
        cust1.setPassword(PASS_1);
        cust1.setLibraryCardID(1);
        Customer cust2 = new Customer(true, true, FIRSTNAME_1, LASTNAME_1, true, 0, a);
        cust2.setEmail(EMAIL_1);
        cust2.setUsername(USERNAME_1);
        cust2.setPassword(PASS_1);
        cust2.setLibraryCardID(2);
        customers.add(cust1);
        customers.add(cust2);
        return customers;
        });
        try {
            customerList = customerService.getAllCustomers();
        } catch (Exception msg) {
            fail(msg.getMessage());
        }
        assertNotNull(customerList);
        assertEquals(2, customerList.size());
        for (Customer c : customerList) {
            assertNotNull(c);
            assertEquals(c.getFirstName(), FIRSTNAME_1);
            assertEquals(c.getLastName(), LASTNAME_1);
            assertEquals(c.getAddress().getCivicNumber(), CIVIC_1);
            assertEquals(c.getAddress().getStreet(), STREET_1);
            assertEquals(c.getAddress().getCity(), CITY_1);
            assertEquals(c.getAddress().getPostalCode(), POST_1);
            assertEquals(c.getAddress().getProvince(), PROV_1);
            assertEquals(c.getAddress().getCountry(), COUNTRY_1);
        }
        assertEquals("mikeym", customerList.get(0).getUsername());
        assertEquals(USERNAME_1, customerList.get(1).getUsername());
        assertEquals("a.c@a.com", customerList.get(0).getEmail());
        assertEquals(EMAIL_1, customerList.get(1).getEmail());
        assertEquals(PASS_1, customerList.get(0).getPassword());
        assertEquals(PASS_1, customerList.get(1).getPassword());
    }

    @Test
    public void getCustomerByEmailPass() {
        Customer c = null;
        try {
            c = customerService.getCustomerByEmail(EMAIL_1);
        } catch (Exception msg) {
            fail(msg.getMessage());
        }
        assertNotNull(c);
        assertEquals(c.getFirstName(), FIRSTNAME_1);
        assertEquals(c.getLastName(), LASTNAME_1);
        assertEquals(c.getAddress().getCivicNumber(), CIVIC_1);
        assertEquals(c.getAddress().getStreet(), STREET_1);
        assertEquals(c.getAddress().getCity(), CITY_1);
        assertEquals(c.getAddress().getPostalCode(), POST_1);
        assertEquals(c.getAddress().getProvince(), PROV_1);
        assertEquals(c.getAddress().getCountry(), COUNTRY_1);
        assertEquals(c.getUsername(), USERNAME_1);
        assertEquals(c.getPassword(), PASS_1);
        assertEquals(c.getEmail(),EMAIL_1);
    }

    @Test
    public void getCustomersByNamePass() {
        List<Customer> customerList = new ArrayList<>();
        lenient().when(customerDao.findUserByFirstNameAndLastName(anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(FIRSTNAME_1) && invocation.getArgument(1).equals(LASTNAME_1)) {
                List<Customer> customers = new ArrayList<>();
                Address a = new Address(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
                Customer cust1 = new Customer(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a);
                cust1.setEmail("a.c@a.com");
                cust1.setUsername("mikeym");
                cust1.setPassword(PASS_1);
                cust1.setLibraryCardID(1);
                Customer cust2 = new Customer(true, true, FIRSTNAME_1, LASTNAME_1, true, 0, a);
                cust2.setEmail(EMAIL_1);
                cust2.setUsername(USERNAME_1);
                cust2.setPassword(PASS_1);
                cust2.setLibraryCardID(2);
                customers.add(cust1);
                customers.add(cust2);
                return customers;
            }
            return null;
        });
        try {
            customerList = customerService.getCustomersByFirstAndLastName(FIRSTNAME_1, LASTNAME_1);
        } catch (Exception msg) {
            fail(msg.getMessage());
        }
        assertNotNull(customerList);
        assertEquals(2, customerList.size());
        for (Customer c : customerList) {
            assertNotNull(c);
            assertEquals(c.getFirstName(), FIRSTNAME_1);
            assertEquals(c.getLastName(), LASTNAME_1);
            assertEquals(c.getAddress().getCivicNumber(), CIVIC_1);
            assertEquals(c.getAddress().getStreet(), STREET_1);
            assertEquals(c.getAddress().getCity(), CITY_1);
            assertEquals(c.getAddress().getPostalCode(), POST_1);
            assertEquals(c.getAddress().getProvince(), PROV_1);
            assertEquals(c.getAddress().getCountry(), COUNTRY_1);
        }
        assertEquals("mikeym", customerList.get(0).getUsername());
        assertEquals(USERNAME_1, customerList.get(1).getUsername());
        assertEquals("a.c@a.com", customerList.get(0).getEmail());
        assertEquals(EMAIL_1, customerList.get(1).getEmail());
        assertEquals(PASS_1, customerList.get(0).getPassword());
        assertEquals(PASS_1, customerList.get(1).getPassword());
    }

    @Test
    public void getCustomersByDemeritPtsPass() {
        List<Customer> customerList = new ArrayList<>();
        lenient().when(customerDao.findUserByDemeritPts(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(2)) {
                List<Customer> customers = new ArrayList<>();
                Address a = new Address(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
                Customer cust1 = new Customer(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a);
                cust1.setEmail("a.c@a.com");
                cust1.setUsername("mikeym");
                cust1.setPassword(PASS_1);
                cust1.setLibraryCardID(1);
                customers.add(cust1);
                return customers;
            }
            return null;
        });
        try {
            customerList = customerService.getCustomersByDemeritPts(2);
        } catch (Exception msg) {
            fail(msg.getMessage());
        }
        assertNotNull(customerList);
        assertEquals(1, customerList.size());
        for (Customer c : customerList) {
            assertNotNull(c);
            assertEquals(c.getFirstName(), FIRSTNAME_1);
            assertEquals(c.getLastName(), LASTNAME_1);
            assertEquals(c.getAddress().getCivicNumber(), CIVIC_1);
            assertEquals(c.getAddress().getStreet(), STREET_1);
            assertEquals(c.getAddress().getCity(), CITY_1);
            assertEquals(c.getAddress().getPostalCode(), POST_1);
            assertEquals(c.getAddress().getProvince(), PROV_1);
            assertEquals(c.getAddress().getCountry(), COUNTRY_1);
        }
        assertEquals("mikeym", customerList.get(0).getUsername());
        assertEquals("a.c@a.com", customerList.get(0).getEmail());
        assertEquals(PASS_1, customerList.get(0).getPassword());
    }

    @Test
    public void getCustomersByAddressPass() {
        List<Customer> customerList = new ArrayList<>();
        lenient().when(customerDao.findUserByAddress(any())).thenAnswer((InvocationOnMock invocation) -> {
            Address a = new Address(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
                List<Customer> customers = new ArrayList<>();
                Customer cust1 = new Customer(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a);
                cust1.setEmail("a.c@a.com");
                cust1.setUsername("mikeym");
                cust1.setPassword(PASS_1);
                cust1.setLibraryCardID(1);
                Customer cust2 = new Customer(true, true, FIRSTNAME_1, LASTNAME_1, true, 0, a);
                cust2.setEmail(EMAIL_1);
                cust2.setUsername(USERNAME_1);
                cust2.setPassword(PASS_1);
                cust2.setLibraryCardID(2);
                customers.add(cust1);
                customers.add(cust2);
                return customers;
        });
        lenient().when(addressDao.findAddressByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1)).thenAnswer((InvocationOnMock invocation) -> {
            Address a = new Address(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
            List<Address> addressList = new ArrayList<>();
            addressList.add(a);
            return addressList;
        });
        try {
            customerList = customerService.getCustomersByAddress(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
        } catch (Exception msg) {
            fail(msg.getMessage());
        }
        assertNotNull(customerList);
        assertEquals(2, customerList.size());
        for (Customer c : customerList) {
            assertNotNull(c);
            assertEquals(c.getFirstName(), FIRSTNAME_1);
            assertEquals(c.getLastName(), LASTNAME_1);
            assertEquals(c.getAddress().getCivicNumber(), CIVIC_1);
            assertEquals(c.getAddress().getStreet(), STREET_1);
            assertEquals(c.getAddress().getCity(), CITY_1);
            assertEquals(c.getAddress().getPostalCode(), POST_1);
            assertEquals(c.getAddress().getProvince(), PROV_1);
            assertEquals(c.getAddress().getCountry(), COUNTRY_1);
        }
        assertEquals("mikeym", customerList.get(0).getUsername());
        assertEquals(USERNAME_1, customerList.get(1).getUsername());
        assertEquals("a.c@a.com", customerList.get(0).getEmail());
        assertEquals(EMAIL_1, customerList.get(1).getEmail());
        assertEquals(PASS_1, customerList.get(0).getPassword());
        assertEquals(PASS_1, customerList.get(1).getPassword());
    }

    @Test
    public void getCustomersByLoggedInTest() {
        List<Customer> customerList = new ArrayList<>();
        lenient().when(customerDao.findByIsLogged(anyBoolean())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(true)) {
                List<Customer> customers = new ArrayList<>();
                Address a = new Address(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
                Customer cust1 = new Customer(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a);
                cust1.setEmail("a.c@a.com");
                cust1.setUsername("mikeym");
                cust1.setPassword(PASS_1);
                cust1.setLibraryCardID(1);
                customers.add(cust1);
                return customers;
            }
            return null;
        });
        try {
            customerList = customerService.getCustomersByLoggedIn(true);
        } catch (Exception msg) {
            fail(msg.getMessage());
        }
        assertNotNull(customerList);
        assertEquals(1, customerList.size());
        for (Customer c : customerList) {
            assertNotNull(c);
            assertEquals(c.getFirstName(), FIRSTNAME_1);
            assertEquals(c.getLastName(), LASTNAME_1);
            assertEquals(c.getAddress().getCivicNumber(), CIVIC_1);
            assertEquals(c.getAddress().getStreet(), STREET_1);
            assertEquals(c.getAddress().getCity(), CITY_1);
            assertEquals(c.getAddress().getPostalCode(), POST_1);
            assertEquals(c.getAddress().getProvince(), PROV_1);
            assertEquals(c.getAddress().getCountry(), COUNTRY_1);
        }
        assertEquals("mikeym", customerList.get(0).getUsername());
        assertEquals("a.c@a.com", customerList.get(0).getEmail());
        assertEquals(PASS_1, customerList.get(0).getPassword());
    }

    @Test
    public void convertLocalToOnlinePass() {
        Customer c = null;
        try {
            c = customerService.convertLocalToOnline(3, USERNAME_1, PASS_1, EMAIL_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(c);
        assertEquals(USERNAME_1, c.getUsername());
        assertEquals(PASS_1, c.getPassword());
        assertEquals(EMAIL_1, c.getEmail());
    }

    @Test
    public void convertLocalToOnlineFailUsernameUsed() {
        String error = null;
        Customer c = null;
        try {
            c = customerService.convertLocalToOnline(3, USER_KEY, PASS_1, EMAIL_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Username in use, please select a different one", error);
    }

    @Test
    public void convertLocalToOnlineFailEmailUsed() {
        String error = null;
        Customer c = null;
        try {
            c = customerService.convertLocalToOnline(3, USERNAME_1, PASS_1, EMAIL_KEY);
        } catch (IllegalArgumentException | NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Email in use, please select a different one", error);
    }

    @Test
    public void convertLocalToOnlineAlreadyOnline() {
        String error = null;
        Customer c = null;
        try {
            c = customerService.convertLocalToOnline(4, USERNAME_1, PASS_1, EMAIL_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Account is already fully configured", error);
    }

    @Test
    public void convertLocalToOnlinePassTooShort() {
        String error = null;
        Customer c = null;
        try {
            c = customerService.convertLocalToOnline(4, USERNAME_1, "1234", EMAIL_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a password that is at least 8 characters long", error);
    }

    @Test
    public void convertLocalToOnlineNoCustomer() {
        Customer c = null;
        String error = null;
        try {
            c = customerService.convertLocalToOnline(1, USERNAME_1, "12345678", EMAIL_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Cannot find a Customer with given ID", error);
    }

    @Test
    public void changeInfoPass() {
        Customer c = null;
        try {
            c = customerService.changeInfo(3, FIRSTNAME_1, LASTNAME_1, CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(c);
        assertEquals(c.getFirstName(), FIRSTNAME_1);
        assertEquals(c.getLastName(), LASTNAME_1);
        assertEquals(c.getAddress().getCivicNumber(), CIVIC_1);
        assertEquals(c.getAddress().getStreet(), STREET_1);
        assertEquals(c.getAddress().getCity(), CITY_1);
        assertEquals(c.getAddress().getPostalCode(), POST_1);
        assertEquals(c.getAddress().getProvince(), PROV_1);
        assertEquals(c.getAddress().getCountry(), COUNTRY_1);
    }

    @Test
    public void updateOnlineInfoNoCustomer() {
        String error = null;
        Customer c = null;
        try {
            c = customerService.updateOnlineInfo(1, USERNAME_1, PASS_1, EMAIL_1);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Cannot find Customer with given ID", error);
    }

    @Test
    public void updateOnlineInfoUsernameUsed() {
        String error = null;
        Customer c = null;
        try {
            c = customerService.updateOnlineInfo(5, USER_KEY, PASS_1, EMAIL_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("New username in use, please choose a valid one", error);
    }

    @Test
    public void updateOnlineInfoEmailUsed() {
        String error = null;
        Customer c = null;
        try {
            c = customerService.updateOnlineInfo(5, USERNAME_1, PASS_1, EMAIL_KEY);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("New email in use, please choose another", error);
    }

    @Test
    public void updateOnlineInfoPass() {
        Customer c = null;
        try {
            c = customerService.updateOnlineInfo(3, USERNAME_1, PASS_1, EMAIL_1);
        } catch (Exception msg) {
            fail(msg.getMessage());
        }
        assertNotNull(c);
        assertEquals(USERNAME_1, c.getUsername());
        assertEquals(PASS_1, c.getPassword());
        assertEquals(EMAIL_1, c.getEmail());
    }

    @Test
    public void changeInfoFailNoCustomer() {
        Customer c = null;
        String error = null;
        try {
            c = customerService.changeInfo(1, FIRSTNAME_1, LASTNAME_1, CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Cannot find Customer with given ID", error);
    }

    @Test
    public void changeInfoFailInvalidAddress() {
        Customer c = null;
        String error = null;
        try {
            c = customerService.changeInfo(1, FIRSTNAME_1, LASTNAME_1, "0", STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Please enter a valid address", error);
    }

    @Test
    public void changeDemeritPtsPass() {
        Customer c = null;
        try {
            c = customerService.changeDemeritPts(3, 2);
        } catch (Exception msg) {
            fail(msg.getMessage());
        }
        assertNotNull(c);
        assertEquals(2, c.getDemeritPts());
    }

    @Test
    public void changeDemeritPtsFailNoCustomer() {
        Customer c = null;
        String error = null;
        try {
            c = customerService.changeDemeritPts(2, 2);
        } catch (Exception msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Cannot find Customer with this ID", error);
    }
}
