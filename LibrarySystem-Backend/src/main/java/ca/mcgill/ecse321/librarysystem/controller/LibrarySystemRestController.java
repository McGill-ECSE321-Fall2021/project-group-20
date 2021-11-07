package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.AddressDto;
import ca.mcgill.ecse321.librarysystem.dto.CalendarDto;
import ca.mcgill.ecse321.librarysystem.dto.LibrarySystemDto;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class LibrarySystemRestController {

    @Autowired
    private LibrarySystemService librarySystemService;


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

}
