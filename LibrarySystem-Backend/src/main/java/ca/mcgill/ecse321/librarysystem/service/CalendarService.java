package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.CalendarRepository;
import ca.mcgill.ecse321.librarysystem.dao.HourRepository;
import ca.mcgill.ecse321.librarysystem.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalendarService {

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    HourRepository hourRepository;

    /**
     * Creates a calendar and saves to the database.
     * @return newly created calendar
     */
    @Transactional
    public Calendar createCalendar() {
        Calendar calendar = new Calendar();
        calendarRepository.save(calendar);
        return calendar;
    }

    /**
     * Finds the calendar with calendarID and deletes it.
     * @param calendarID
     * @return true if calendar has been properly deleted, false else.
     */
    @Transactional
    public boolean deleteCalendar(String calendarID) {
        if (calendarID == null) throw new IllegalArgumentException("calendarID cannot be null");
        Calendar calendar = calendarRepository.findCalendarByCalendarID(calendarID);
        if (calendar == null) throw new NullPointerException("Calendar not found");
        calendarRepository.delete(calendar);
        calendar.delete();

//        Calendar test = calendarRepository.findCalendarByCalendarID(calendarID);
//        return (test == null);
        return calendarRepository.existsByCalendarID(calendarID);
    }

    /**
     * Retrieves the calendar from the database with calendarID.
     * @param calendarID
     * @return calendar
     */
    @Transactional
    public Calendar getCalendar(String calendarID) {
        if (calendarID == null) throw new IllegalArgumentException("CalendarID cannot be null");
        Calendar calendar = calendarRepository.findCalendarByCalendarID(calendarID);
        if (calendar == null) throw new NullPointerException("Calendar not found");
        return calendar;
    }

    /**
     *
     * @param calendarID
     * @return true if the calendar with calendarID exits, false else.
     */
    @Transactional
    public boolean checkCalendarExists(String calendarID) {
        if (calendarID == null) throw new IllegalArgumentException("CalendarID cannot be null");
        return calendarRepository.existsByCalendarID(calendarID);
    }
}