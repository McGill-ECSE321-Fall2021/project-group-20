package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.CalendarDto;
import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.Hour;
import ca.mcgill.ecse321.librarysystem.service.CalendarService;
import ca.mcgill.ecse321.librarysystem.service.HourService;
import ca.mcgill.ecse321.librarysystem.service.LibrarySystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CalendarRestController {

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private LibrarySystemService librarySystemService;

    @Autowired
    private HourService hourService;


    private CalendarDto convertToDto(Calendar c) {
        if (c == null) throw new NullPointerException("Cannot find Calendar");
        return new CalendarDto(c.getCalendarID());
    }

    @PostMapping(value = {"/calendar/create", "/calendar/create/"})
    public ResponseEntity createCalendar() {
        return new ResponseEntity<>(convertToDto(calendarService.createCalendar()), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/calendar/{id}", "/calendar/{id}/"})
    public ResponseEntity deleteCalendar(@PathVariable("id") String id) {
        boolean b;
        try {
            b = calendarService.deleteCalendar(id);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (!b) return ResponseEntity.status(HttpStatus.OK).body("Deleted calendar");
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not delete calendar");
    }

    @GetMapping(value = {"/calendar/{id}", "/calendar/{id}/"})
    public ResponseEntity getCalendarByCalendarID(@PathVariable("id") String id) {
        Calendar c;
        try {
            c = calendarService.getCalendar(id);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }

        return new ResponseEntity<>(convertToDto(calendarService.getCalendar(id)), HttpStatus.OK);
    }
}