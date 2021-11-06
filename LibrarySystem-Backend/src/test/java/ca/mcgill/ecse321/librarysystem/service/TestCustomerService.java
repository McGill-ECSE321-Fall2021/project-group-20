package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.AddressRepository;
import ca.mcgill.ecse321.librarysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Customer;
import ca.mcgill.ecse321.librarysystem.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

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
    private static int ID_KEY = 1;
    private static int ID = 2;

    @Mock
    private CustomerRepository customerDao;

    @Mock
    private AddressRepository addressDao;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setMockOutput() {
        // Basically intercept every possible repository call here!
        lenient().when(customerDao.existsByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EMAIL_KEY)) {
                return true;
            }
            return false;
        });

        lenient().when(customerDao.existsByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(USER_KEY)) {
                return true;
            }
            return false;
        });

        lenient().when(customerDao.existsByLibraryCardID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ID)) {
                return true;
            }
            return false;
        });

        lenient().when(customerDao.findUserByLibraryCardID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ID_KEY)) {
                Address a = new Address(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
                Customer c = new Customer(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a);
                c.setEmail(EMAIL_KEY);
                c.setUsername(USER_KEY);
                c.setPassword(PASS_1);
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
                c.setEmail(EMAIL_KEY);
                c.setUsername(USER_KEY);
                c.setPassword(PASS_1);
                return c;
            }
            return null;
        });

        Answer<?> returnParamAsAnswer = (InvocationOnMock invocation) -> {return invocation.getArgument(0);};
        lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParamAsAnswer);
        lenient().when(customerDao.save(any(User.class))).thenAnswer(returnParamAsAnswer);
        lenient().when(addressDao.save(any(Address.class))).thenAnswer(returnParamAsAnswer);
    }

    @Test
    public void createPersonSuccessful() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        try {
            c = customerService.createCustomer(FIRSTNAME_1,LASTNAME_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException error) {
            fail();
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
    public void createPersonFirstnameNull() {
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
    public void createPersonLastnameNull() {
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
    public void createPersonCivicNull() {
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
    public void createPersonStreetNull() {
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
    public void createPersonCityNull() {
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
    public void createPersonPostNull() {
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
    public void createPersonProvinceNull() {
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
    public void createPersonCountryNull() {
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
    public void createOnlinePersonPass() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = null;
        try {
            c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            fail();
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
    public void createOnlinePersonFirstnameNull() {
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
    public void createOnlinePersonLastnameNull() {
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
    public void createOnlinePersonCivicNull() {
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
    public void createOnlinePersonStreetNull() {
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
    public void createOnlinePersonCityNull() {
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
    public void createOnlinePersonPostNull() {
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
    public void createOnlinePersonProvinceNull() {
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
    public void createOnlinePersonCountryNull() {
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
    public void createOnlinePersonEmailNull() {
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
    public void createOnlinePersonEmailExists() {
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
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createCustomer(FIRSTNAME_1,LASTNAME_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        try {
            c = customerService.modifyOutstandingBalance(1, 20);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail();
        }
        assertNotNull(c);
        assertEquals(c.getOutstandingBalance(), 20);
    }

    @Test
    public void modifyBalanceFailID() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createCustomer(FIRSTNAME_1,LASTNAME_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        String error = null;
        c = null;
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
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createCustomer(FIRSTNAME_1,LASTNAME_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        String error = null;
        c = null;
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
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        c = null;
        try {
            c = customerService.login(USERNAME_1, PASS_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail();
        }
        assertNotNull(c);
        assertEquals(true, c.getIsLoggedIn());
    }

    @Test
    public void loginPassFail() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        String error = null;
        c = null;
        try {
            c = customerService.login(USERNAME_1, "abc");
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Incorrect password!", error);
    }

    @Test
    public void loginNoUserFail() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        String error = null;
        c = null;
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
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        c = null;
        try {
            c = customerService.loginByEmail(EMAIL_1, PASS_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail();
        }
        assertNotNull(c);
        assertEquals(true, c.getIsLoggedIn());
    }

    @Test
    public void loginEmailFail() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        String error = null;
        c = null;
        try {
            c = customerService.loginByEmail(EMAIL_1, "abc");
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(c);
        assertEquals("Incorrect password!", error);
    }

    @Test
    public void loginEmailIDFail() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        String error = null;
        c = null;
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
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        c = null;
        try {
            c = customerService.login(1, PASS_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail();
        }
        assertNotNull(c);
        assertEquals(true, c.getIsLoggedIn());
    }

    @Test
    public void loginIDFail() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        String error = null;
        c = null;
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
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        String error = null;
        c = null;
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
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        c.setIsLoggedIn(true);
        boolean b = false;
        try {
            b = customerService.logout(USERNAME_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail();
        }
        assertTrue(b);
    }

    @Test
    public void logoutFail() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        c.setIsLoggedIn(true);
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
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        c.setIsLoggedIn(true);
        boolean b = false;
        try {
            b = customerService.logoutByEmail(EMAIL_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail();
        }
        assertTrue(b);
    }

    @Test
    public void logoutEmailFail() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        c.setIsLoggedIn(true);
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
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        boolean b = false;
        try {
            b = customerService.deleteCustomerByID(1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail();
        }
        assertTrue(b);
        assertEquals(customerService.getAllCustomers().size(), 0);
    }

    @Test
    public void deleteFailID() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        boolean b = false;
        String error = null;
        try {
            b = customerService.deleteCustomerByID(-10);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertFalse(b);
        assertEquals("Please enter a valid libraryCardID", error);
    }

    @Test
    public void deleteFailMatch() {
        assertEquals(customerService.getAllCustomers().size(), 0);
        Customer c = customerService.createOnlineCustomer(FIRSTNAME_1,LASTNAME_1,EMAIL_1,USERNAME_1,PASS_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
        c.setLibraryCardID(1);
        boolean b = false;
        String error = null;
        try {
            b = customerService.deleteCustomerByID(10);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertFalse(b);
        assertEquals("Cannot find Customer matching this libraryCardID", error);
    }
}
