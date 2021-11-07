package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.CalendarDto;
import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.Hour;
import ca.mcgill.ecse321.librarysystem.service.CalendarService;
import ca.mcgill.ecse321.librarysystem.service.HourService;
import ca.mcgill.ecse321.librarysystem.service.LibrarySystemService;
import org.springframework.beans.factory.annotation.Autowired;
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


    public CalendarDto convertToDto(Calendar c) {
        if (c == null) throw new NullPointerException("Cannot find Calendar");
        return new CalendarDto(c.getCalendarID());
    }

    @PostMapping(value = {"/calendar/create", "/calendar/create/"})
    public CalendarDto createCalendar() {
        return convertToDto(calendarService.createCalendar());
    }

    @DeleteMapping(value = {"/calendar/id", "/calendar/id/"})
    public boolean deleteCalendar(@PathVariable("id") String id) throws NullPointerException {
        return calendarService.deleteCalendar(id);
    }

    @GetMapping(value = {"/calendar/id", "/calendar/id/"})
    public CalendarDto getCalendarByCalendarID(@PathVariable("id") String id) throws NullPointerException {
        return convertToDto(calendarService.getCalendar(id));
    }

    @GetMapping(value = {"/calendars", "/calendars/"})
    public List<CalendarDto> getAllCalendars() {
        List<CalendarDto> calendarDtos = new ArrayList<>();
        for (Calendar c : calendarService.getAllCalendars()) {
            calendarDtos.add(convertToDto(c));
        }
        return calendarDtos;
    }

    @PutMapping(value = {"/calendar/updateCalendarHour/id", "/calendar/updatecalendarHour/id/"})
    public boolean addHourToCalendar(@PathVariable("id") String id, @RequestParam Hour hour) throws
            IllegalArgumentException, NullPointerException {
        return calendarService.addHourToCalendar(id, hour);
    }

    @DeleteMapping(value = {"/calendar/removeCalendarHour/id", "/calendar/removeCalendarHour/id/"})
    public boolean removeHourFromCalendar(@PathVariable("id") String id, @RequestParam Hour hour) throws
            IllegalArgumentException, NullPointerException {
        return calendarService.removeHour(id, hour);
    }
}
