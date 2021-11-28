package ca.mcgill.ecse321.librarysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


import ca.mcgill.ecse321.librarysystem.dao.AddressRepository;
import ca.mcgill.ecse321.librarysystem.dao.EmployeeRepository;
import ca.mcgill.ecse321.librarysystem.dao.HourRepository;
import ca.mcgill.ecse321.librarysystem.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.librarysystem.dao.CalendarRepository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestCalendarService {

    @Mock
    private CalendarRepository calendarRepository;

    @InjectMocks
    private CalendarService calendarService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private HourRepository hourRepository;

    /**
     * Setting up mock calendar object.
     */
    private static final String calendarID = "CALENDAR1";

    /**
     * Setting up mock hour object.
     */
    private static final String weekday = "MONDAY";
    private static final Time startTime = new Time(9, 0, 0);
    private static final Time endTime = new Time(16, 0, 0);

    /**
     * Setting up moch employee object.
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

    private static List<Hour> list_hours = new ArrayList<>();

    @BeforeEach
    public void setMockOutput() {
        lenient().when(calendarRepository.findCalendarByCalendarID(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(calendarID)) {
                Calendar calendar = new Calendar();
                calendar.setCalendarID(calendarID);

                Address address = new Address();
                address.setAddressID(ADDRESSID_1);
                address.setCivicNumber(CIVIC_1);
                address.setStreet(STREET_1);
                address.setCity(CITY_1);
                address.setPostalCode(POST_1);
                address.setProvince(PROV_1);
                address.setCountry(COUNTRY_1);

                Employee e1 = new Employee(true, false, FIRSTNAME_1, LASTNAME_1, ID_1,
                        true, 0, address, Employee.Role.HeadLibrarian);
                e1.setEmail(EMAIL_1);
                e1.setUsername(USERNAME_1);
                e1.setPassword(PASS_1);

                Hour shift = new Hour(weekday, startTime, endTime, e1, calendar, Hour.Type.Shift);
                list_hours.add(shift);
                calendar.addHour(shift);
                return calendar;
            }
            return null;
        });

        lenient().when(calendarRepository.findCalendarByHourIn(any(List.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(list_hours)) {
                   return list_hours.get(0).getCalendar();
            }
            return null;
        });

        lenient().when(calendarRepository.existsByCalendarID(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            return invocation.getArgument(0).equals(calendarID);
        });

        Answer<?> returnParamAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(calendarRepository.save(any(Calendar.class))).thenAnswer(returnParamAsAnswer);
        lenient().when(hourRepository.save(any(Hour.class))).thenAnswer(returnParamAsAnswer);
        lenient().when(addressRepository.save(any(Address.class))).thenAnswer(returnParamAsAnswer);
        lenient().when(employeeRepository.save(any(Employee.class))).thenAnswer(returnParamAsAnswer);
    }

    @Test
    public void testCreateCalendarSuccessful() {
        Calendar calendar = null;
        try {
            calendar = calendarService.createCalendar();
            calendar.setCalendarID(calendarID);
        } catch (Exception msg) {
            fail(msg.getMessage());
        }
        assertNotNull(calendar);
        assertEquals(calendarID, calendar.getCalendarID());
    }

    @Test
    public void testDeleteCalendarSuccessful() {
        boolean deleted = false;
        try {
            deleted = calendarService.deleteCalendar(calendarID);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertEquals(true, deleted);
    }

    @Test
    public void testDeleteCalendarFailInvalidID() {
        boolean deleted = false;
        String error = null;
        try {
            deleted = calendarService.deleteCalendar(null);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertEquals(false, deleted);
        assertEquals("calendarID cannot be null", error);
    }

    @Test
    public void testDeleteCalendarFailInvalidCalendar() {
        boolean deleted = false;
        String error = null;
        try {
            deleted = calendarService.deleteCalendar("123");
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertEquals(false, deleted);
        assertEquals("Calendar not found", error);
    }

    @Test
    public void testGetCalendarSuccessful() {
        Calendar calendar = null;
        try {
            calendar = calendarService.getCalendar(calendarID);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(calendar);
        assertEquals(calendarID, calendar.getCalendarID());
        assertEquals(ADDRESSID_1, calendar.getHour(0).getEmployee().getAddress().getAddressID());
        assertEquals(ID_1, calendar.getHour(0).getEmployee().getLibraryCardID());
    }

    @Test
    public void testGetCalendarFailInvalidID() {
        String error = null;
        Calendar calendar = null;
        try {
            calendar = calendarService.getCalendar(null);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(calendar);
        assertEquals("CalendarID cannot be null", error);
    }

    @Test
    public void testGetCalendarFailInvalidCalendar() {
        String error = null;
        Calendar calendar = null;
        try {
            calendar = calendarService.getCalendar("123");
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(calendar);
        assertEquals("Calendar not found", error);
    }

    @Test
    public void testCheckCalendarExistsSuccessful() {
        boolean exists = false;
        try {
            exists = calendarService.checkCalendarExists(calendarID);
        } catch (IllegalArgumentException msg) {
            fail(msg.getMessage());
        }
        assertEquals(true, exists);
    }

    @Test
    public void testCheckCalendarExistsFailInvalidID() {
        boolean exists = false;
        String error = null;
        try {
            exists = calendarService.checkCalendarExists(null);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertEquals("CalendarID cannot be null", error);
    }

    @Test
    public void testCheckCalendarExistsFail() {
        boolean exists = false;
        try {
            exists = calendarService.checkCalendarExists("234");
        } catch (IllegalArgumentException msg) {
            fail(msg.getMessage());
        }
        assertEquals(false, exists);
    }
}
