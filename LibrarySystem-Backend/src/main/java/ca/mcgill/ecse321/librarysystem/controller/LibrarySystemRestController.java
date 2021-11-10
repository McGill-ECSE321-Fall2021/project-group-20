package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.dto.AddressDto;
import ca.mcgill.ecse321.librarysystem.dto.CalendarDto;
import ca.mcgill.ecse321.librarysystem.dto.LibrarySystemDto;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "*")
@RestController
public class LibrarySystemRestController {

    @Autowired
    private LibrarySystemService librarySystemService;

    @Autowired
    private LibrarySystemRepository librarySystemRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private TitleRepository titleRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CalendarRepository calendarRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private HourRepository hourRepository;
    @Autowired
    private ArchiveRepository archiveRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MusicAlbumRepository musicRepository;
    @Autowired
    private NewspaperRepository newspaperRepository;


    public LibrarySystemDto convertToDto(LibrarySystem ls) throws IllegalArgumentException, NullPointerException {
        Address businessAddress = ls.getBusinessaddress();
        Calendar calendar = ls.getCalendar();
        String systemID = ls.getSystemID();

        AddressDto addressDto = new AddressDto(businessAddress.getAddressID(),
                businessAddress.getCivicNumber(), businessAddress.getStreet(), businessAddress.getCity(),
                businessAddress.getPostalCode(), businessAddress.getProvince(), businessAddress.getCountry());

        CalendarDto calendarDto = new CalendarDto(calendar.getCalendarID());
        return new LibrarySystemDto(systemID, addressDto, calendarDto);
    }

    @PostMapping(value = {"/librarySystem/create", "/librarySystem/create/"})
    public LibrarySystemDto createLibrarySystem(@RequestParam Address businessAddress, @RequestParam Calendar calendar)
        throws IllegalArgumentException, NullPointerException {
        return convertToDto(librarySystemService.createLibrarySystem(businessAddress, calendar));
    }

    @PutMapping(value = {"/librarySystem/updateAddress/{id}", "/librarySystem/updateAddress/{id}/"})
    public boolean updateAddress(@PathVariable("id") String id, @RequestParam Address address)
            throws IllegalArgumentException, NullPointerException {
        if (librarySystemService.changeAddress(id, address) != null) return true;
        return false;
    }

    @PutMapping(value = {"/librarySystem/updateCalendar/{id}", "/librarySystem/updateCalendar/{id}/"})
    public boolean updateCalendar(@PathVariable("id") String id, @RequestParam Calendar calendar)
            throws IllegalArgumentException, NullPointerException {
        if (librarySystemService.changeCalendar(id, calendar) != null) return true;
        return false;
    }

    @DeleteMapping(value = {"/librarySystem/delete/{id}", "/librarySystem/delete/{id}"})
    public boolean deleteLibrarySystem(@PathVariable("id") String id) throws NullPointerException {
        return librarySystemService.deleteLibrarySystem(id);
    }

    @GetMapping(value = {"/librarySystem/{id}", "/librarySystem/{id}/"})
    public LibrarySystemDto getLibrarySystem(@PathVariable("id") String id) throws NullPointerException {
        return convertToDto(librarySystemService.getLibrarySystem(id));
    }

    @GetMapping(value = {"/librarySystem/user", "/librarySystem/user/"})
    public LibrarySystemDto getLibrarySystem(@RequestParam User user) throws NullPointerException {
        return convertToDto(librarySystemService.getLibrarySystem(user));
    }

    @GetMapping(value = {"/librarySystem/businessAddress", "/librarySystem/businessAddress/"})
    public LibrarySystemDto getLibrarySystem(@RequestParam Address businessAddress)
            throws NullPointerException {
        return convertToDto(librarySystemService.getLibrarySystem(businessAddress));
    }

    @GetMapping(value = {"/librarySystem/items", "/librarySystem/items/"})
    public LibrarySystemDto getLibrarySystem(@RequestParam Item items) throws NullPointerException {
        return convertToDto(librarySystemService.getLibrarySystem(items));
    }

    @GetMapping(value = {"/librarySystem/calendar", "/librarySystem/calendar/"})
    public LibrarySystemDto getLibrarySystem(@RequestParam Calendar calendar) throws NullPointerException {
        return convertToDto(librarySystemService.getLibrarySystem(calendar));
    }

    @DeleteMapping(value = { "/librarySystem/clear", "/librarySystem/clear/"})
    public ResponseEntity clear(@RequestParam String confirmbool) {
        try {
            if (confirmbool.equalsIgnoreCase("true")) {
                eventRepository.deleteAll();
                hourRepository.deleteAll();
                librarySystemRepository.deleteAll();
                bookingRepository.deleteAll();
                archiveRepository.deleteAll();
                bookRepository.deleteAll();
                movieRepository.deleteAll();
                musicRepository.deleteAll();
                newspaperRepository.deleteAll();
                itemRepository.deleteAll();
                titleRepository.deleteAll();
                authorRepository.deleteAll();
                customerRepository.deleteAll();
                employeeRepository.deleteAll();
                userRepository.deleteAll();
                addressRepository.deleteAll();
                calendarRepository.deleteAll();
                return ResponseEntity.status(HttpStatus.OK).body("Database has been wiped on " + (new Date()).toString());
            }
            else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Refused request to wipe database!");
        } catch (Exception msg) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not delete because of: " + msg.getMessage());
        }
    }

}
