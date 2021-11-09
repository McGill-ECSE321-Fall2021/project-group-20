package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.AddressRepository;
import ca.mcgill.ecse321.librarysystem.dao.EmployeeRepository;
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
public class TestEmployeeService {

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
    private static final int ID_1 = 1;
    private static final int ID_KEY = 2;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setMockOutput() {
        lenient().when(employeeRepository.existsByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EMAIL_KEY)) {
                return true;
            }
            return false;
        });
        
        lenient().when(employeeRepository.existsByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(USER_KEY)) {
                return true;
            }
            return false;
        });

        lenient().when(employeeRepository.existsByLibraryCardID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
           if (invocation.getArgument(0).equals(3)) {
               return true;
           }
           return false;
        });

        lenient().when(employeeRepository.findEmployeeByLibraryCardID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
           if (invocation.getArgument(0).equals(ID_1) || invocation.getArgument(0).equals(3)) {
               Address a = new Address(CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
               Employee e = new Employee(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a, Employee.Role.Librarian);
               e.setLibraryCardID(ID_1);
               e.setEmail(EMAIL_1);
               e.setPassword(PASS_1);
               e.setUsername(USERNAME_1);
               return e;
           }
           return null;
        });

        lenient().when(employeeRepository.findEmployeeByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EMAIL_1)) {
                Address a = new Address(CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
                Employee e = new Employee(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a, Employee.Role.Librarian);
                e.setLibraryCardID(ID_1);
                e.setEmail(EMAIL_1);
                e.setPassword(PASS_1);
                e.setUsername(USERNAME_1);
                return e;
            }
            return null;
        });

        lenient().when(employeeRepository.findEmployeeByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(USERNAME_1)) {
                Address a = new Address(CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
                Employee e = new Employee(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a, Employee.Role.Librarian);
                e.setLibraryCardID(ID_1);
                e.setEmail(EMAIL_1);
                e.setPassword(PASS_1);
                e.setUsername(USERNAME_1);
                return e;
            }
            return null;
        });
        
        lenient().when(employeeRepository.findEmployeeByUserbooking(any(Booking.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0) != null) {
                Address a = new Address(CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
                Employee e = new Employee(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a, Employee.Role.Librarian);
                e.setLibraryCardID(ID_1);
                e.setEmail(EMAIL_1);
                e.setPassword(PASS_1);
                e.setUsername(USERNAME_1);
                return e;
            }
            return null;
        });

        lenient().when(employeeRepository.findEmployeesByEmployeehour(any(Hour.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0) != null) {
                Address a = new Address(CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
                Employee e = new Employee(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a, Employee.Role.Librarian);
                e.setLibraryCardID(ID_1);
                e.setEmail(EMAIL_1);
                e.setPassword(PASS_1);
                e.setUsername(USERNAME_1);
                List<Employee> employees = new ArrayList<>();
                employees.add(e);
                return employees;
            }
            return new ArrayList<>();
        });

        lenient().when(employeeRepository.findEmployeesByRole(any(Employee.Role.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0) != null) {
                Address a = new Address(CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
                Employee e = new Employee(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a, Employee.Role.Librarian);
                e.setLibraryCardID(ID_1);
                e.setEmail(EMAIL_1);
                e.setPassword(PASS_1);
                e.setUsername(USERNAME_1);
                List<Employee> employees = new ArrayList<>();
                employees.add(e);
                return employees;
            }
            return new ArrayList<>();
        });

        lenient().when(employeeRepository.findEmployeesByDemeritPts(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(2)) {
                Address a = new Address(CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1);
                Employee e = new Employee(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a, Employee.Role.Librarian);
                e.setLibraryCardID(ID_1);
                e.setEmail(EMAIL_1);
                e.setPassword(PASS_1);
                e.setUsername(USERNAME_1);
                e.setDemeritPts(2);
                List<Employee> employees = new ArrayList<>();
                employees.add(e);
                return employees;
            }
            return new ArrayList<>();
        });
        
        Answer<?> returnParamAsAnswer = (InvocationOnMock invocation) -> {return invocation.getArgument(0);};
        lenient().when(employeeRepository.save(any(Employee.class))).thenAnswer(returnParamAsAnswer);
        lenient().when(addressRepository.save(any(Address.class))).thenAnswer(returnParamAsAnswer);
    }

    @Test
    public void testCreateEmployeeSuccessful() {
        assertEquals(employeeService.getAllEmployees().size(), 0);
        Employee e = null;
        try {
            e = employeeService.createEmployee(FIRSTNAME_1,LASTNAME_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1,
                    EMAIL_1,USERNAME_1,PASS_1,Employee.Role.HeadLibrarian);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(e);
        assertEquals(e.getFirstName(), FIRSTNAME_1);
        assertEquals(e.getLastName(), LASTNAME_1);
        assertEquals(e.getAddress().getCivicNumber(), CIVIC_1);
        assertEquals(e.getAddress().getStreet(), STREET_1);
        assertEquals(e.getAddress().getCity(), CITY_1);
        assertEquals(e.getAddress().getPostalCode(), POST_1);
        assertEquals(e.getAddress().getProvince(), PROV_1);
        assertEquals(e.getAddress().getCountry(), COUNTRY_1);
        assertEquals(e.getRole(), Employee.Role.HeadLibrarian);
        assertEquals(e.getUsername(), USERNAME_1);
        assertEquals(e.getPassword(), PASS_1);
        assertEquals(e.getEmail(), EMAIL_1);
    }

    @Test
    public void testCreateEmployeeNameFail() {
        assertEquals(employeeService.getAllEmployees().size(), 0);
        Employee e = null;
        String error = null;
        try {
            e = employeeService.createEmployee(null,LASTNAME_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1,
                    EMAIL_1,USERNAME_1,PASS_1,Employee.Role.HeadLibrarian);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Please enter a valid name", error);
    }

    @Test
    public void testCreateEmployeeEmailFail() {
        assertEquals(employeeService.getAllEmployees().size(), 0);
        Employee e = null;
        String error = null;
        try {
            e = employeeService.createEmployee(FIRSTNAME_1,LASTNAME_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1,
                    null,USERNAME_1,PASS_1,Employee.Role.HeadLibrarian);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Please enter a valid email", error);
    }

    @Test
    public void testCreateEmployeeUsernameFail() {
        assertEquals(employeeService.getAllEmployees().size(), 0);
        Employee e = null;
        String error = null;
        try {
            e = employeeService.createEmployee(FIRSTNAME_1,LASTNAME_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1,
                    EMAIL_1,null,PASS_1,Employee.Role.HeadLibrarian);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Please enter a valid username", error);
    }

    @Test
    public void testCreateEmployeePasswordFail() {
        assertEquals(employeeService.getAllEmployees().size(), 0);
        Employee e = null;
        String error = null;
        try {
            e = employeeService.createEmployee(FIRSTNAME_1,LASTNAME_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1,
                    EMAIL_1,USERNAME_1,"1234",Employee.Role.HeadLibrarian);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Please enter a password that is at least 8 characters long", error);
    }

    @Test
    public void testCreateEmployeeAddressFail() {
        assertEquals(employeeService.getAllEmployees().size(), 0);
        Employee e = null;
        String error = null;
        try {
            e = employeeService.createEmployee(FIRSTNAME_1,LASTNAME_1,CIVIC_1,STREET_1,null,POST_1,PROV_1,COUNTRY_1,
                    EMAIL_1,USERNAME_1,PASS_1,Employee.Role.HeadLibrarian);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Please enter a valid address", error);
    }

    @Test
    public void testCreateEmployeeUsernameExists() {
        assertEquals(employeeService.getAllEmployees().size(), 0);
        Employee e = null;
        String error = null;
        try {
            e = employeeService.createEmployee(FIRSTNAME_1,LASTNAME_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1,
                    EMAIL_1,USER_KEY,PASS_1,Employee.Role.HeadLibrarian);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Username in use, please choose another", error);
    }

    @Test
    public void testCreateEmployeeEmailExists() {
        assertEquals(employeeService.getAllEmployees().size(), 0);
        Employee e = null;
        String error = null;
        try {
            e = employeeService.createEmployee(FIRSTNAME_1,LASTNAME_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1,
                    EMAIL_KEY,USERNAME_1,PASS_1,Employee.Role.HeadLibrarian);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Email in use, please choose another", error);
    }

    @Test
    public void testCreateEmployeeInvalidRole() {
        assertEquals(employeeService.getAllEmployees().size(), 0);
        Employee e = null;
        String error = null;
        try {
            e = employeeService.createEmployee(FIRSTNAME_1,LASTNAME_1,CIVIC_1,STREET_1,CITY_1,POST_1,PROV_1,COUNTRY_1,
                    EMAIL_1,USERNAME_1,PASS_1,null);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Please select either <Librarian> or <HeadLibrarian> as role", error);
    }

    @Test
    public void testChangeDemeritPtsPass() {
        Employee e = null;
        try {
            e = employeeService.changeDemeritPts(ID_1, 2);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(e);
        assertEquals(2, e.getDemeritPts());
    }

    @Test
    public void testChangeDemeritPtsIDFail() {
        Employee e = null;
        String error = null;
        try {
            e = employeeService.changeDemeritPts(-10, 2);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Please enter a valid ID", error);
    }

    @Test
    public void testChangeDemeritPtsNullEmployeeFail() {
        Employee e = null;
        String error = null;
        try {
            e = employeeService.changeDemeritPts(ID_KEY, 2);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Cannot find Employee with this ID", error);
    }

    @Test
    public void testModifyBalancePass() {
        Employee e = null;
        try {
            e = employeeService.modifyOutstandingBalance(ID_1, 50);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(e);
        assertEquals(50, e.getOutstandingBalance());
    }

    @Test
    public void testModifyBalanceIDFail() {
        Employee e = null;
        String error = null;
        try {
            e = employeeService.modifyOutstandingBalance(-10, 2);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Please enter a valid ID", error);
    }

    @Test
    public void testModifyBalanceNullEmployeeFail() {
        Employee e = null;
        String error = null;
        try {
            e = employeeService.modifyOutstandingBalance(ID_KEY, 2);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Cannot find Employee with this ID", error);
    }

    @Test
    public void loginSuccessful() {
        Employee e = null;
        try {
            e = employeeService.login(USERNAME_1, PASS_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(e);
        assertEquals(true, e.getIsLoggedIn());
    }

    @Test
    public void loginPassfail() {
        String error = null;
        Employee e = null;
        try {
            e = employeeService.login(USERNAME_1, "abc");
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Incorrect password!", error);
    }

    @Test
    public void loginNoUserfail() {
        String error = null;
        Employee e = null;
        try {
            e = employeeService.login(10, PASS_1);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Cannot find Employee with this ID", error);
    }

    @Test
    public void loginEmailPass() {
        Employee e = null;
        try {
            e = employeeService.loginByEmail(EMAIL_1, PASS_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(e);
        assertEquals(true, e.getIsLoggedIn());
    }

    @Test
    public void loginEmailfail() {
        String error = null;
        Employee e = null;
        try {
            e = employeeService.loginByEmail(EMAIL_1, "abc");
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Incorrect password!", error);
    }

    @Test
    public void loginEmailIDfail() {
        String error = null;
        Employee e = null;
        try {
            e = employeeService.loginByEmail(EMAIL_KEY, PASS_1);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Cannot find Employee with this email", error);
    }

    @Test
    public void loginIDPass() {
        Employee e = null;
        try {
            e = employeeService.login(1, PASS_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(e);
        assertEquals(true, e.getIsLoggedIn());
    }

    @Test
    public void loginIDfail() {
        String error = null;
        Employee e = null;
        try {
            e = employeeService.login(1, "abc");
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Incorrect password!", error);
    }

    @Test
    public void loginIDFailNoExist() {
        String error = null;
        Employee e = null;
        try {
            e = employeeService.login(10, PASS_1);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Cannot find Employee with this ID", error);
    }

    @Test
    public void logoutPass() {
        boolean b = false;
        try {
            b = employeeService.logout(USERNAME_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertTrue(b);
    }

    @Test
    public void logoutPassID() {
        boolean b = false;
        try {
            b = employeeService.logout(ID_1);
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
            b = employeeService.logout(USER_KEY);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertFalse(b);
        assertEquals("Cannot find Employee with this username", error);
    }

    @Test
    public void logoutEmailPass() {
        boolean b = false;
        try {
            b = employeeService.logoutByEmail(EMAIL_1);
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
            b = employeeService.logoutByEmail(EMAIL_KEY);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertFalse(b);
        assertEquals("Cannot find Employee with this email", error);
    }

    @Test
    public void testGetEmployeeByIDPass() {
        Employee e = null;
        try {
            e = employeeService.getEmployee(ID_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(e);
        assertEquals(e.getFirstName(), FIRSTNAME_1);
        assertEquals(e.getLastName(), LASTNAME_1);
        assertEquals(e.getAddress().getCivicNumber(), CIVIC_1);
        assertEquals(e.getAddress().getStreet(), STREET_1);
        assertEquals(e.getAddress().getCity(), CITY_1);
        assertEquals(e.getAddress().getPostalCode(), POST_1);
        assertEquals(e.getAddress().getProvince(), PROV_1);
        assertEquals(e.getAddress().getCountry(), COUNTRY_1);
        assertEquals(e.getRole(), Employee.Role.Librarian);
        assertEquals(e.getUsername(), USERNAME_1);
        assertEquals(e.getPassword(), PASS_1);
        assertEquals(e.getEmail(), EMAIL_1);
    }

    @Test
    public void testGetEmployeeByIDFail() {
        Employee e = null;
        String error = null;
        try {
            e = employeeService.getEmployee(-10);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Please enter a valid libraryCardID", error);
    }

    @Test
    public void testGetEmployeeByIDNull() {
        Employee e = null;
        try {
            e = employeeService.getEmployee(ID_KEY);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNull(e);
    }

    @Test
    public void testGetEmployeeByUsernamePass() {
        Employee e = null;
        try {
            e = employeeService.getEmployee(USERNAME_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(e);
        assertEquals(e.getFirstName(), FIRSTNAME_1);
        assertEquals(e.getLastName(), LASTNAME_1);
        assertEquals(e.getAddress().getCivicNumber(), CIVIC_1);
        assertEquals(e.getAddress().getStreet(), STREET_1);
        assertEquals(e.getAddress().getCity(), CITY_1);
        assertEquals(e.getAddress().getPostalCode(), POST_1);
        assertEquals(e.getAddress().getProvince(), PROV_1);
        assertEquals(e.getAddress().getCountry(), COUNTRY_1);
        assertEquals(e.getRole(), Employee.Role.Librarian);
        assertEquals(e.getUsername(), USERNAME_1);
        assertEquals(e.getPassword(), PASS_1);
        assertEquals(e.getEmail(), EMAIL_1);
    }

    @Test
    public void testGetEmployeeByUsernameFail() {
        Employee e = null;
        String error = null;
        try {
            e = employeeService.getEmployee(null);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Please enter a valid username", error);
    }

    @Test
    public void testGetEmployeeByUsernameNull() {
        Employee e = null;
        try {
            e = employeeService.getEmployee(USER_KEY);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNull(e);
    }

    @Test
    public void testGetEmployeeByEmailPass() {
        Employee e = null;
        try {
            e = employeeService.getEmployeeByEmail(EMAIL_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(e);
        assertEquals(e.getFirstName(), FIRSTNAME_1);
        assertEquals(e.getLastName(), LASTNAME_1);
        assertEquals(e.getAddress().getCivicNumber(), CIVIC_1);
        assertEquals(e.getAddress().getStreet(), STREET_1);
        assertEquals(e.getAddress().getCity(), CITY_1);
        assertEquals(e.getAddress().getPostalCode(), POST_1);
        assertEquals(e.getAddress().getProvince(), PROV_1);
        assertEquals(e.getAddress().getCountry(), COUNTRY_1);
        assertEquals(e.getRole(), Employee.Role.Librarian);
        assertEquals(e.getUsername(), USERNAME_1);
        assertEquals(e.getPassword(), PASS_1);
        assertEquals(e.getEmail(), EMAIL_1);
    }

    @Test
    public void testGetEmployeeByEmailFail() {
        Employee e = null;
        String error = null;
        try {
            e = employeeService.getEmployeeByEmail("a.ca");
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Please enter a valid email", error);
    }

    @Test
    public void testGetEmployeeByEmailNull() {
        Employee e = null;
        try {
            e = employeeService.getEmployee(EMAIL_KEY);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNull(e);
    }
    
    @Test
    public void getEmployeesPass() {
        List<Employee> employeeList = new ArrayList<>();
        lenient().when(employeeRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
           List<Employee> employees = new ArrayList<>();
           Address a = new Address(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
           Employee e = new Employee(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a, Employee.Role.Librarian);
           e.setLibraryCardID(ID_1);
           e.setEmail(EMAIL_1);
           e.setPassword(PASS_1);
           e.setUsername(USERNAME_1);
           Employee e2 = new Employee(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a, Employee.Role.Librarian);
           e2.setLibraryCardID(5);
           e2.setUsername("mikeym");
           e2.setPassword(PASS_1);
           e2.setEmail("a.c@a.org");
           employees.add(e);
           employees.add(e2);
           return employees;
        });
        try {
            employeeList = employeeService.getAllEmployees();
        } catch (Exception msg) {
            fail(msg.getMessage());
        }
        assertNotNull(employeeList);
        assertEquals(2, employeeList.size());
        for (Employee e : employeeList) {
            assertNotNull(e);
            assertEquals(e.getFirstName(), FIRSTNAME_1);
            assertEquals(e.getLastName(), LASTNAME_1);
            assertEquals(e.getAddress().getCivicNumber(), CIVIC_1);
            assertEquals(e.getAddress().getStreet(), STREET_1);
            assertEquals(e.getAddress().getCity(), CITY_1);
            assertEquals(e.getAddress().getPostalCode(), POST_1);
            assertEquals(e.getAddress().getProvince(), PROV_1);
            assertEquals(e.getAddress().getCountry(), COUNTRY_1);
        }
        assertEquals(USERNAME_1, employeeList.get(0).getUsername());
        assertEquals("mikeym", employeeList.get(1).getUsername());
        assertEquals(PASS_1, employeeList.get(0).getPassword());
        assertEquals(PASS_1, employeeList.get(1).getPassword());
        assertEquals(EMAIL_1, employeeList.get(0).getEmail());
        assertEquals("a.c@a.org", employeeList.get(1).getEmail());
    }

    @Test
    public void getEmployeesByNamePass() {
        List<Employee> employeeList = new ArrayList<>();
        lenient().when(employeeRepository.findEmployeesByFirstNameAndLastName(anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(FIRSTNAME_1) && invocation.getArgument(1).equals(LASTNAME_1)) {
                List<Employee> employees = new ArrayList<>();
                Address a = new Address(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
                Employee e1 = new Employee(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a, Employee.Role.Librarian);
                e1.setEmail("a.c@a.com");
                e1.setUsername("mikeym");
                e1.setPassword(PASS_1);
                e1.setLibraryCardID(1);
                Employee e2 = new Employee(true, true, FIRSTNAME_1, LASTNAME_1, true, 0, a, Employee.Role.Librarian);
                e2.setEmail(EMAIL_1);
                e2.setUsername(USERNAME_1);
                e2.setPassword(PASS_1);
                e2.setLibraryCardID(2);
                employees.add(e1);
                employees.add(e2);
                return employees;
            }
            return new ArrayList<>();
        });
        try {
            employeeList = employeeService.getEmployeesByFirstAndLastName(FIRSTNAME_1, LASTNAME_1);
        } catch (Exception msg) {
            fail(msg.getMessage());
        }
        assertNotNull(employeeList);
        assertEquals(2, employeeList.size());
        for (Employee e : employeeList) {
            assertNotNull(e);
            assertEquals(e.getFirstName(), FIRSTNAME_1);
            assertEquals(e.getLastName(), LASTNAME_1);
            assertEquals(e.getAddress().getCivicNumber(), CIVIC_1);
            assertEquals(e.getAddress().getStreet(), STREET_1);
            assertEquals(e.getAddress().getCity(), CITY_1);
            assertEquals(e.getAddress().getPostalCode(), POST_1);
            assertEquals(e.getAddress().getProvince(), PROV_1);
            assertEquals(e.getAddress().getCountry(), COUNTRY_1);
        }
        assertEquals(USERNAME_1, employeeList.get(1).getUsername());
        assertEquals("mikeym", employeeList.get(0).getUsername());
        assertEquals(PASS_1, employeeList.get(1).getPassword());
        assertEquals(PASS_1, employeeList.get(0).getPassword());
        assertEquals(EMAIL_1, employeeList.get(1).getEmail());
        assertEquals("a.c@a.com", employeeList.get(0).getEmail());
    }
    
    @Test
    public void getEmployeesByHourPass() {
        List<Employee> employees = new ArrayList<>();
        try {
            employees = employeeService.getEmployeesByHour(new Hour());
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(employees);
        assertEquals(employees.get(0).getFirstName(), FIRSTNAME_1);
        assertEquals(employees.get(0).getLastName(), LASTNAME_1);
        assertEquals(employees.get(0).getAddress().getCivicNumber(), CIVIC_1);
        assertEquals(employees.get(0).getAddress().getStreet(), STREET_1);
        assertEquals(employees.get(0).getAddress().getCity(), CITY_1);
        assertEquals(employees.get(0).getAddress().getPostalCode(), POST_1);
        assertEquals(employees.get(0).getAddress().getProvince(), PROV_1);
        assertEquals(employees.get(0).getAddress().getCountry(), COUNTRY_1);
        assertEquals(employees.get(0).getRole(), Employee.Role.Librarian);
        assertEquals(employees.get(0).getUsername(), USERNAME_1);
        assertEquals(employees.get(0).getPassword(), PASS_1);
        assertEquals(employees.get(0).getEmail(), EMAIL_1);
    }

    @Test
    public void getEmployeesByHourFail() {
        List<Employee> e = new ArrayList<>();
        String error = null;
        try {
            e = employeeService.getEmployeesByHour(null);
        } catch (IllegalArgumentException | NullPointerException msg) {
            error = msg.getMessage();
        }
        assertEquals(0, e.size());
        assertEquals("Please enter a valid hour", error);
    }
    
    @Test
    public void getEmployeeByRolePass() {
        List<Employee> employees = new ArrayList<>();
        try {
            employees = employeeService.getEmployeesByRole(Employee.Role.Librarian);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(employees);
        assertEquals(employees.get(0).getFirstName(), FIRSTNAME_1);
        assertEquals(employees.get(0).getLastName(), LASTNAME_1);
        assertEquals(employees.get(0).getAddress().getCivicNumber(), CIVIC_1);
        assertEquals(employees.get(0).getAddress().getStreet(), STREET_1);
        assertEquals(employees.get(0).getAddress().getCity(), CITY_1);
        assertEquals(employees.get(0).getAddress().getPostalCode(), POST_1);
        assertEquals(employees.get(0).getAddress().getProvince(), PROV_1);
        assertEquals(employees.get(0).getAddress().getCountry(), COUNTRY_1);
        assertEquals(employees.get(0).getRole(), Employee.Role.Librarian);
        assertEquals(employees.get(0).getUsername(), USERNAME_1);
        assertEquals(employees.get(0).getPassword(), PASS_1);
        assertEquals(employees.get(0).getEmail(), EMAIL_1);
    }

    @Test
    public void getEmployeesByRoleFail() {
        List<Employee> employees = new ArrayList<>();
        String error = null;
        try {
            employees = employeeService.getEmployeesByRole(null);
        } catch (IllegalArgumentException | NullPointerException msg) {
            error = msg.getMessage();
        }
        assertEquals(0, employees.size());
        assertEquals("Please enter a valid role to search", error);
    }
    
    @Test
    public void getEmployeesByDemeritPtsPass() {
        List<Employee> employees = new ArrayList<>();
        try {
            employees = employeeService.getEmployeesByDemeritPts(2);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(employees);
        assertEquals(employees.get(0).getFirstName(), FIRSTNAME_1);
        assertEquals(employees.get(0).getLastName(), LASTNAME_1);
        assertEquals(employees.get(0).getAddress().getCivicNumber(), CIVIC_1);
        assertEquals(employees.get(0).getAddress().getStreet(), STREET_1);
        assertEquals(employees.get(0).getAddress().getCity(), CITY_1);
        assertEquals(employees.get(0).getAddress().getPostalCode(), POST_1);
        assertEquals(employees.get(0).getAddress().getProvince(), PROV_1);
        assertEquals(employees.get(0).getAddress().getCountry(), COUNTRY_1);
        assertEquals(employees.get(0).getRole(), Employee.Role.Librarian);
        assertEquals(employees.get(0).getUsername(), USERNAME_1);
        assertEquals(employees.get(0).getPassword(), PASS_1);
        assertEquals(employees.get(0).getEmail(), EMAIL_1);
        assertEquals(employees.get(0).getDemeritPts(), 2);
    }

    @Test
    public void getEmployeesByDemeritPtsFail() {
        List<Employee> employees = new ArrayList<>();
        String error = null;
        try {
            employees = employeeService.getEmployeesByDemeritPts(-2);
        } catch (IllegalArgumentException | NullPointerException msg) {
            error = msg.getMessage();
        }
        assertEquals(0, employees.size());
        assertEquals("Please enter a positive demeritPts int", error);
    }
    
    @Test
    public void getEmployeesByAddressPass() {
        List<Employee> employeeList = new ArrayList<>();
        lenient().when(employeeRepository.findEmployeesByAddress(any())).thenAnswer((InvocationOnMock invocation) -> {
            Address a = new Address(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
            List<Employee> customers = new ArrayList<>();
            Employee e1 = new Employee(true, false, FIRSTNAME_1, LASTNAME_1, true, 0, a, Employee.Role.Librarian);
            e1.setEmail("a.c@a.com");
            e1.setUsername("mikeym");
            e1.setPassword(PASS_1);
            e1.setLibraryCardID(1);
            Employee e2 = new Employee(true, true, FIRSTNAME_1, LASTNAME_1, true, 0, a, Employee.Role.Librarian);
            e2.setEmail(EMAIL_1);
            e2.setUsername(USERNAME_1);
            e2.setPassword(PASS_1);
            e2.setLibraryCardID(2);
            customers.add(e1);
            customers.add(e2);
            return customers;
        });
        lenient().when(addressRepository.findAddressByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1)).thenAnswer((InvocationOnMock invocation) -> {
            Address a = new Address(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
            List<Address> addressList = new ArrayList<>();
            addressList.add(a);
            return addressList;
        });
        try {
            employeeList = employeeService.getEmployeesByAddress(CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
        } catch (Exception msg) {
            fail(msg.getMessage());
        }
        assertNotNull(employeeList);
        assertEquals(2, employeeList.size());
        for (Employee c : employeeList) {
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
        assertEquals("mikeym", employeeList.get(0).getUsername());
        assertEquals(USERNAME_1, employeeList.get(1).getUsername());
        assertEquals("a.c@a.com", employeeList.get(0).getEmail());
        assertEquals(EMAIL_1, employeeList.get(1).getEmail());
        assertEquals(PASS_1, employeeList.get(0).getPassword());
        assertEquals(PASS_1, employeeList.get(1).getPassword());
    }

    @Test
    public void updateOnlineInfoPass() {
        Employee e = null;
        try {
            e = employeeService.updateOnlineInfo(3, USERNAME_1, PASS_1, EMAIL_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(e);
        assertEquals(USERNAME_1, e.getUsername());
        assertEquals(PASS_1, e.getPassword());
        assertEquals(EMAIL_1, e.getEmail());
    }

    @Test
    public void updateOnlineInfoFailUsernameUsed() {
        Employee e = null;
        String error = null;
        try {
            e = employeeService.updateOnlineInfo(3, USER_KEY, PASS_1, EMAIL_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("New username in use, please choose a valid one", error);
    }

    @Test
    public void updateOnlineInfoEmailUsed() {
        Employee e = null;
        String error = null;
        try {
            e = employeeService.updateOnlineInfo(3, USERNAME_1, PASS_1, EMAIL_KEY);
        } catch (IllegalArgumentException | NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("New email in use, please choose another", error);
    }

    @Test
    public void updateOnlineInfoNoEmployee() {
        Employee e = null;
        String error = null;
        try {
            e = employeeService.updateOnlineInfo(ID_1, USERNAME_1, PASS_1, EMAIL_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Cannot find Employee with given ID", error);
    }

    @Test
    public void changeInfoPass() {
        Employee e = null;
        try {
            e = employeeService.changeInfo(3, FIRSTNAME_1, LASTNAME_1, CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(e);
        assertEquals(e.getFirstName(), FIRSTNAME_1);
        assertEquals(e.getLastName(), LASTNAME_1);
        assertEquals(e.getAddress().getCivicNumber(), CIVIC_1);
        assertEquals(e.getAddress().getStreet(), STREET_1);
        assertEquals(e.getAddress().getCity(), CITY_1);
        assertEquals(e.getAddress().getPostalCode(), POST_1);
        assertEquals(e.getAddress().getProvince(), PROV_1);
        assertEquals(e.getAddress().getCountry(), COUNTRY_1);
    }

    @Test
    public void changeInfoFailNoCustomer() {
        Employee e = null;
        String error = null;
        try {
            e = employeeService.changeInfo(1, FIRSTNAME_1, LASTNAME_1, CIVIC_1, STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Cannot find Employee with given ID", error);
    }

    @Test
    public void changeInfoFailInvalidAddress() {
        Employee e = null;
        String error = null;
        try {
            e = employeeService.changeInfo(1, FIRSTNAME_1, LASTNAME_1, "0", STREET_1, CITY_1, POST_1, PROV_1, COUNTRY_1);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Please enter a valid address", error);
    }

    @Test
    public void updateRolePass() {
        Employee e = null;
        try {
            e = employeeService.updateRole(ID_1, Employee.Role.HeadLibrarian);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(e);
        assertEquals(Employee.Role.HeadLibrarian, e.getRole());
    }

    @Test
    public void updateRoleFailInvalidRole() {
        Employee e = null;
        String error = null;
        try {
            e = employeeService.updateRole(1, null);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Please enter a valid role", error);
    }

    @Test
    public void updateRoleIDFail() {
        Employee e = null;
        String error = null;
        try {
            e = employeeService.updateRole(-1, Employee.Role.Librarian);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Please enter a valid libraryCardID", error);
    }

    @Test
    public void updateRoleNullFail() {
        Employee e = null;
        String error = null;
        try {
            e = employeeService.updateRole(ID_KEY, Employee.Role.Librarian);
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(e);
        assertEquals("Cannot find Employee with this ID", error);
    }
}
