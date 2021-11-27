package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.dto.AddressDto;
import ca.mcgill.ecse321.librarysystem.dto.CalendarDto;
import ca.mcgill.ecse321.librarysystem.dto.LibrarySystemDto;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.service.*;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class LibrarySystemRestController {

    @Autowired
    private LibrarySystemService librarySystemService;
    @Autowired
    private AddressService addressService;

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


    private LibrarySystemDto convertToDto(LibrarySystem ls) throws IllegalArgumentException, NullPointerException {
        Address businessAddress = ls.getBusinessaddress();
        Calendar calendar = ls.getCalendar();
        String systemID = ls.getSystemID();

        AddressDto addressDto = new AddressDto(businessAddress.getAddressID(),
                businessAddress.getCivicNumber(), businessAddress.getStreet(), businessAddress.getCity(),
                businessAddress.getPostalCode(), businessAddress.getProvince(), businessAddress.getCountry());

        CalendarDto calendarDto = new CalendarDto(calendar.getCalendarID(), calendar.getHour());
        return new LibrarySystemDto(systemID, addressDto, calendarDto);
    }

    @PostMapping(value = {"/librarySystem/create", "/librarySystem/create/"})
    public ResponseEntity createLibrarySystem(@RequestParam String civicNumber, @RequestParam String street,
                                              @RequestParam String city, @RequestParam String postalCode,
                                              @RequestParam String province, @RequestParam String country) {
        Address a;
        try {
            a = addressService.createAddress(civicNumber, street, city, postalCode, province, country);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        LibrarySystem librarySystem;
        try {
            librarySystem = librarySystemService.createLibrarySystem(a);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (librarySystem == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Could not create library system");
        return new ResponseEntity<>(convertToDto(librarySystem), HttpStatus.OK);
    }

    @PutMapping(value = {"/librarySystem/updateAddress/{id}", "/librarySystem/updateAddress/{id}/"})
    public ResponseEntity updateAddress(@PathVariable("id") String id, @RequestParam String civicNumber, @RequestParam String street,
                                        @RequestParam String city, @RequestParam String postalCode,
                                        @RequestParam String province, @RequestParam String country) {
        LibrarySystem librarySystem;
        Address a;
        try {
            a = addressService.createAddress(civicNumber, street, city, postalCode, province, country);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }

        try {
            librarySystem = librarySystemService.changeAddress(id, a);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (librarySystem == null)
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not change address");
        return new ResponseEntity<>(convertToDto(librarySystem), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/librarySystem/delete/{id}", "/librarySystem/delete/{id}"})
    public ResponseEntity deleteLibrarySystem(@PathVariable("id") String id) {
        boolean b;
        try {
            b = librarySystemService.deleteLibrarySystem(id);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (!b) return ResponseEntity.status(HttpStatus.OK).body("Deleted LibrarySystem");
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not delete LibrarySystem");
    }

    @GetMapping(value = {"/librarySystem/{id}", "/librarySystem/{id}/"})
    public ResponseEntity getLibrarySystem(@PathVariable("id") String id) {
        LibrarySystem librarySystem;
        try {
            librarySystem = librarySystemService.getLibrarySystem(id);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (librarySystem == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find Library System");
        return new ResponseEntity<>(convertToDto(librarySystem), HttpStatus.OK);
    }

    @GetMapping(value = {"/librarySystem", "/librarySystem/"})
    public ResponseEntity getAll() {
        List<LibrarySystem> ls = new ArrayList<>();
        try {
            ls = librarySystemService.getAll();
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (ls.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any library systems");
        List<LibrarySystemDto> lsDto = new ArrayList<>();
        for (LibrarySystem l : ls) {
            lsDto.add(convertToDto(l));
        }
        return new ResponseEntity<>(lsDto, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/librarySystem/clear", "/librarySystem/clear/"})
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
                return ResponseEntity.status(HttpStatus.OK).body("Database has been wiped on " + new Date());
            } else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Refused request to wipe database!");
        } catch (Exception msg) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not delete because of: " + msg.getMessage());
        }
    }
}
